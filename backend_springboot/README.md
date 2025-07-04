# Transportation Management System

This project consists of a Spring Boot backend API and a JavaFX client application for managing a transportation/taxi service system.

## Project Structure

```
├── proj2-backend/          # Spring Boot Backend
│   ├── src/main/java/
│   │   └── com/example/projeto2/base/
│   │       ├── controller/     # REST API Controllers
│   │       ├── model/          # JPA Entities
│   │       ├── repository/     # Data Access Layer
│   │       ├── service/        # Business Logic
│   │       └── config/         # Configuration
│   └── build.gradle
│
└── javafx-client/          # JavaFX Frontend
    ├── src/main/java/
    │   └── com/example/javafxclient/
    │       ├── controller/     # JavaFX Controllers
    │       ├── model/          # Data Models
    │       └── service/        # API Client
    ├── src/main/resources/
    │   └── fxml/              # UI Layouts
    └── build.gradle
```

## Prerequisites

- Java 17 or higher
- Gradle 7.0 or higher
- PostgreSQL database
- JavaFX SDK (included in Java 17+)

## Setup Instructions

### 1. Database Setup

1. Install PostgreSQL if not already installed
2. Create a database named `projeto`:
   ```sql
   CREATE DATABASE projeto;
   ```
3. Update database credentials in `proj2-backend/src/main/resources/application.properties` if needed

### 2. Spring Boot Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd proj2-backend
   ```

2. Build the project:
   ```bash
   ./gradlew build
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

The backend will start on `http://localhost:8080`

### 3. JavaFX Client Setup

1. Navigate to the client directory:
   ```bash
   cd javafx-client
   ```

2. Build the project:
   ```bash
   ./gradlew build
   ```

3. Run the application:
   ```bash
   ./gradlew run
   ```

## API Endpoints

The Spring Boot backend provides the following REST endpoints:

### Clientes (Clients)
- `GET /clientes` - List all clients
- `GET /clientes/{id}` - Get client by ID
- `POST /clientes/add` - Create new client
- `PUT /clientes/{id}` - Update client
- `DELETE /clientes/{id}` - Delete client

### Motoristas (Drivers)
- `GET /motoristas` - List all drivers
- `GET /motoristas/{id}` - Get driver by ID
- `POST /motoristas` - Create new driver
- `PUT /motoristas/{id}` - Update driver
- `DELETE /motoristas/{id}` - Delete driver

### Viagens (Trips)
- `GET /viagens` - List all trips
- `GET /viagens/{id}` - Get trip by ID
- `POST /viagens` - Create new trip
- `PUT /viagens/{id}` - Update trip
- `DELETE /viagens/{id}` - Delete trip

## JavaFX Client Features

The JavaFX client provides a user-friendly interface with:

1. **Clientes Tab**: 
   - View all clients in a table
   - Add new clients with form fields
   - Update existing client information
   - Delete clients with confirmation

2. **Motoristas Tab**:
   - View all drivers in a table
   - Display driver information (name, phone, license)

3. **Viagens Tab**:
   - View all trips in a table
   - Display trip details (driver, client, price)

## Key Features

- **Real-time Data**: The JavaFX client fetches data from the Spring Boot API
- **CRUD Operations**: Full Create, Read, Update, Delete functionality
- **Asynchronous Operations**: Non-blocking UI with CompletableFuture
- **Error Handling**: User-friendly error messages and confirmations
- **Modern UI**: Clean, responsive interface with tabs and tables

## Troubleshooting

### Common Issues

1. **Database Connection Error**:
   - Ensure PostgreSQL is running
   - Check database credentials in `application.properties`
   - Verify database `projeto` exists

2. **JavaFX Client Won't Start**:
   - Ensure Java 17+ is installed
   - Check that the Spring Boot backend is running on port 8080
   - Verify all dependencies are resolved

3. **CORS Errors**:
   - The backend includes CORS configuration to allow JavaFX client requests
   - If issues persist, check the `CorsConfig.java` file

### Development Tips

1. **Adding New Features**:
   - Add new endpoints in Spring Boot controllers
   - Create corresponding methods in `ApiClient.java`
   - Update JavaFX UI and controller as needed

2. **Testing**:
   - Use Swagger UI at `http://localhost:8080/swagger-ui.html` to test API endpoints
   - Test JavaFX client functionality manually

## Technologies Used

### Backend
- Spring Boot 3.4.4
- Spring Data JPA
- PostgreSQL
- Gradle
- Swagger/OpenAPI

### Frontend
- JavaFX 17
- Apache HttpClient
- Gson (JSON parsing)
- Gradle

## License

This project is for educational purposes. 