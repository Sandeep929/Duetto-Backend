# Duetto — Collaborative Music Sync Engine (Backend)

Duetto is a Spring Boot service that acts as the real-time sync engine and data repository for the collaborative music application. It orchestrates user authentication, manages session rooms, maintains shared playlists, integrates with Cloudinary CDN for audio distribution, and exposes a high-throughput WebSocket STOMP broker for latency-compensated audio synchronization.

---

## 🛠️ Technology Stack

*   **Java Version:** 17
*   **Framework:** Spring Boot (with Spring WebMVC, Spring Data JPA, and Spring WebSocket modules)
*   **Database:** MySQL (configured for auto-schema update)
*   **CDN & Media Delivery:** Cloudinary SDK
*   **Build Tool:** Maven
*   **Orchestration & Packaging:** Docker (multi-stage build)
*   **Utilities:** Project Lombok (boilerplates simplification)

---

## 📂 Core Packages & Module Structure

```
Duetto/src/main/java/com/duetto/demo/
├── DuettoApplication.java      # Application bootstrap class
├── config/                     # Configurations (CORS, WebSocket, Cloudinary)
│   ├── CorsConfig.java
│   ├── CloudinaryConfig.java
│   └── WebSocektConfig.java    # WebSocket STOMP configuration
├── controller/                 # REST & Message controllers
│   ├── AuthController.java
│   ├── RoomController.java
│   ├── SongController.java
│   ├── PlayListController.java
│   ├── SongPlaylistController.java
│   └── SyncController.java     # STOMP broker endpoint handlers
├── dto/                        # Data Transfer Objects (SyncMessage, LiveUsers, Song)
├── entity/                     # JPA Database Entities
│   ├── User.java               # Credentials
│   ├── Room.java               # Sync rooms metadata
│   ├── Users.java              # Connected users log
│   ├── Playlist.java           # Custom playlists
│   ├── Song.java               # Track database
│   └── SongPlaylist.java       # Playlist-Song mappings
├── idClasses/                  # JPA composite ID mappings
├── repository/                 # Database access interfaces
└── service/                    # Business logic & caching handlers
```

---

## 💾 Database Schema & Persistence

The application maintains the following entity schemas in MySQL:

1.  **User (`User`):** Stores user registration details (`userId`, `pass`).
2.  **Room (`Room`):** Tracks active rooms, identifying the creator (`roomId`, `hostId`, `createdAt`).
3.  **Users (`Users`):** A join record linking user IDs to their currently occupied room.
4.  **Playlist (`Playlist`):** Holds owner and playlist definitions (`playlistId`, `playlistName`, `userId`).
5.  **Song (`Song`):** Maintains titles, audio streaming URLs, Cloudinary public IDs, and accumulated play counters (`plays`).
6.  **SongPlaylist (`SongPlaylist`):** Composite relationship linking a `Song` to a `Playlist`.

---

## ⚡ Real-Time In-Memory Caching & Lock Safety

To minimize database overhead and support ultra-low response times, room metadata and playback configurations are cached in-memory inside `RoomService`:

*   **`roomUsers` (`ConcurrentHashMap<String, Set<String>>`):** Stores active participant IDs per room.
*   **`roomState` (`ConcurrentHashMap<String, SyncMessage>`):** Records the current song URL, playback state (playing/paused), timeline position, and timestamp of the last status change.
*   **`roomLocks` (`ConcurrentHashMap<String, Object>`):** Generates fine-grained lock handles dynamically on a per-room basis.

### Thread-Safe Room Synchronization
Playback actions must be applied sequentially to avoid race conditions. State updates are synchronized using room-specific lock keys:

```java
public boolean updateRoomState(String roomId, SyncMessage rState) {
    synchronized (roomLocks.computeIfAbsent(roomId, l -> new Object())) {
        roomState.put(roomId, rState);
        return true;
    }
}
```

---

## 📡 WebSocket STOMP Endpoints

WebSocket connections are configured under `WebSocektConfig.java` to mount on the path `/ws` and handle messages using the following broker topologies:

| Destination Client Publishes To | Broker Target (Subscribed Clients) | Payload Type | Description |
| :--- | :--- | :--- | :--- |
| `/app/sync/{roomId}` | `/topic/room/{roomId}/sync` | `SyncMessage` | Broadcasts playback action events (play, pause, seek, stop). Updates the state cache. |
| `/app/userCount/{roomId}` | `/topic/room/{roomId}/users` | `LiveUsers` | Broadcasts current user count and active participant list. |
| `/app/loadSongs/{roomId}` | `/topic/room/{roomId}/songs` | `List<Song>` | Broadcasts loaded songs queue/playlist. |

---

## 🌐 REST Endpoints

All endpoints are mapped to root patterns and accept Cross-Origin Request parameters (CORS is open globally for ease of deployment).

### 1. User Authentication (`/auth`)
*   `GET /auth/login?userId={id}&pass={pw}`: Verifies credentials. Returns `userId` on success, `400` on failure.
*   `POST /auth/register`: Creates a new user profile using request body object.
*   `GET /auth/getAllUsers`: Fetches a list of all registered profiles.
*   `DELETE /auth/deleteUser?users={list}`: Batch deletes a list of users.

### 2. Session Room Management (`/room`)
*   `POST /room/create?hostId={id}`: Dynamically generates a UUID room. Returns `roomId`.
*   `POST /room/join?roomId={rid}&userId={uid}`: Connects a user record to the room.
*   `GET /room/userCount?roomId={rid}`: Returns count of currently connected room users.
*   `POST /room/removeUser?roomId={rid}&userId={uid}`: Removes a user from the room.
*   `DELETE /room/deleteRoom`: Removes a room record and clears its associated caches.
*   `GET /room/getAllRooms`: Returns all active rooms.
*   `GET /room/checkRoomState?roomId={rid}`: Reconciles client status with the cached room state on reload.

### 3. Songs Database (`/songs`)
*   `POST /songs/upload`: Accepts a multipart request (`song` key) of files. Uploads to Cloudinary, adds records in db, returns mapped URLs.
*   `GET /songs/getAllSongs`: Retrieves the entire database of CDN-backed songs.
*   `GET /songs/getLocalSongs`: Retrieves files directly from local storage.
*   `DELETE /songs/removeSongs`: Takes a list of `Song` bodies and deletes records from both database and Cloudinary.
*   `PUT /songs/updatePlays`: Increment track play metrics from local analytics buffers.
*   `GET /songs/GetTop5`: Returns top 5 songs ranked by play count.

---

## ⚙️ Configuration Properties

Set the following credentials and parameters inside `src/main/resources/application.properties` (or via Environment Variables):

*   **Database:**
    *   `spring.datasource.url` (Default: `jdbc:mysql://localhost:3306/duetto`)
    *   `spring.datasource.username` (Default: `root`)
    *   `spring.datasource.password` (Default: `root`)
*   **Media Cloud (Cloudinary):**
    *   `cloudinary.cloud-name`
    *   `cloudinary.api-key`
    *   `cloudinary.api-secret`
*   **Multipart Upload Limits:**
    *   `spring.servlet.multipart.max-file-size=-1` (Unlimited)
    *   `spring.servlet.multipart.max-request-size=-1` (Unlimited)

---

## 🐳 Building and Deployment

### Running with Docker

This repository includes a multi-stage Docker build config for building and packing the Spring Boot application container.

1.  **Configure environment variables** or supply your variables inside Docker compose.
2.  **Spin up the database container** in the `duetto-db` directory:
    ```bash
    cd duetto-db
    docker build -t duetto-db .
    docker run -d --name duetto-db -p 3306:3306 duetto-db
    ```
3.  **Build and run the Duetto Backend container**:
    ```bash
    cd Duetto
    docker build -t duetto-backend .
    docker run -d --name duetto-backend -p 8080:8080 --link duetto-db:mysql duetto-backend
    ```

### Manual Compilation & Execution
If running bare-metal:

```bash
mvn clean install
mvn spring-boot:run
```
The application will launch on `http://localhost:8080`.
