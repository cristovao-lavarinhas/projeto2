# Projeto2 - JavaFX + Spring Boot Connection

This project consists of a JavaFX frontend application connected to a Spring Boot backend that communicates with a PostgreSQL database.

## Project Structure

- `backend_springboot/` - Spring Boot REST API backend
- `javafx/` - JavaFX frontend application
- `estrutura_bd_final.sql` - Database schema

## Prerequisites

1. **Java 17 or higher**
2. **PostgreSQL database** (pgAdmin)
3. **Gradle** (included in the project)

## Database Setup

1. Create a PostgreSQL database named `projeto`
2. Import the database schema:
   ```sql
   psql -U postgres -d projeto -f estrutura_bd_final.sql
   ```

## Backend Setup (Spring Boot)

1. Navigate to the backend directory:
   ```bash
   cd backend_springboot
   ```

2. Update database connection in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/projeto
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Build and run the Spring Boot application:
   ```bash
   ./gradlew bootRun
   ```

   The backend will start on `http://localhost:8080`

4. Verify the API is working by visiting:
   - `http://localhost:8080/swagger-ui.html` (API documentation)
   - `http://localhost:8080/motoristas` (Motoristas endpoint)
   - `http://localhost:8080/viaturas` (Viaturas endpoint)

## Frontend Setup (JavaFX)

1. Navigate to the JavaFX directory:
   ```bash
   cd javafx
   ```

2. Build the project:
   ```bash
   ./gradlew build
   ```

3. Run the JavaFX application:
   ```bash
   ./gradlew run
   ```

## Connection Features

### API Client
The JavaFX application uses an `ApiClient` service that provides:
- HTTP GET, POST, PUT, DELETE operations
- JSON serialization/deserialization
- Asynchronous operations with CompletableFuture
- Error handling

### Data Models
- **Frontend Models**: `Motorista.java`, `Viatura.java` (for UI)
- **Backend Models**: `BackendMotorista.java`, `BackendViatura.java` (for API communication)
- **Conversion Methods**: Automatic conversion between frontend and backend models

### Service Layer
- **MotoristaService**: Handles motorista CRUD operations with backend
- **ViaturaService**: Handles viatura CRUD operations with backend
- **Real-time Updates**: UI updates immediately while data is sent to backend

## API Endpoints

### Motoristas
- `GET /motoristas` - List all motoristas
- `GET /motoristas/{id}` - Get motorista by ID
- `POST /motoristas` - Create new motorista
- `PUT /motoristas/{id}` - Update motorista
- `DELETE /motoristas/{id}` - Delete motorista

### Viaturas
- `GET /viaturas` - List all viaturas
- `GET /viaturas/{id}` - Get viatura by ID
- `POST /viaturas` - Create new viatura
- `PUT /viaturas/{id}` - Update viatura
- `DELETE /viaturas/{id}` - Delete viatura

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify PostgreSQL is running
   - Check database credentials in `application.properties`
   - Ensure database `projeto` exists

2. **JavaFX Dependencies Error**
   - Run `./gradlew clean build` to refresh dependencies
   - Ensure Java 17+ is being used

3. **CORS Issues**
   - Backend has CORS configured to allow all origins
   - If issues persist, check firewall settings

4. **API Connection Error**
   - Ensure Spring Boot backend is running on port 8080
   - Check if `http://localhost:8080` is accessible

### Logs
- Backend logs: Check console output when running `./gradlew bootRun`
- Frontend logs: Check console output when running `./gradlew run`

## Development

### Adding New Entities
1. Create backend model in `backend_springboot/src/main/java/com/example/projeto2/base/model/`
2. Create controller in `backend_springboot/src/main/java/com/example/projeto2/base/controller/`
3. Create frontend model in `javafx/src/main/java/com/example/projetojavafx/Modelo/`
4. Create backend model in `javafx/src/main/java/com/example/projetojavafx/Modelo/`
5. Create service in `javafx/src/main/java/com/example/projetojavafx/Service/`
6. Update UI controllers to use the new service

### Testing
- Test backend endpoints using Swagger UI: `http://localhost:8080/swagger-ui.html`
- Test frontend functionality through the JavaFX UI
- Check database to verify data persistence
