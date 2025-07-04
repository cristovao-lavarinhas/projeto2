@echo off
echo Testing Transportation Management System Connection
echo ================================================

echo.
echo 1. Starting Spring Boot Backend...
cd proj2-backend
start "Spring Boot Backend" cmd /k "gradlew bootRun"

echo.
echo 2. Waiting for backend to start (10 seconds)...
timeout /t 10 /nobreak > nul

echo.
echo 3. Testing API endpoints...
echo Testing GET /clientes...
curl -s http://localhost:8080/clientes
echo.
echo Testing GET /motoristas...
curl -s http://localhost:8080/motoristas
echo.
echo Testing GET /viagens...
curl -s http://localhost:8080/viagens

echo.
echo 4. Starting JavaFX Client...
cd ..\javafx-client
start "JavaFX Client" cmd /k "gradlew run"

echo.
echo Connection test completed!
echo Check the opened windows for the applications.
echo.
echo Backend should be running on: http://localhost:8080
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo.
pause 