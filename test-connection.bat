@echo off
echo Testing JavaFX + Spring Boot Connection
echo ======================================

echo.
echo 1. Testing Spring Boot Backend...
echo --------------------------------

cd backend_springboot
echo Building Spring Boot application...
call gradlew build -q

echo Starting Spring Boot backend...
start "Spring Boot Backend" cmd /k "gradlew bootRun"

echo Waiting for backend to start...
timeout /t 10 /nobreak > nul

echo Testing backend endpoints...
curl -s http://localhost:8080/motoristas > nul
if %errorlevel% equ 0 (
    echo ✓ Backend is running and accessible
) else (
    echo ✗ Backend is not accessible
)

echo.
echo 2. Testing JavaFX Frontend...
echo -----------------------------

cd ..\javafx
echo Building JavaFX application...
call gradlew build -q

if %errorlevel% equ 0 (
    echo ✓ JavaFX build successful
    echo.
    echo 3. Starting JavaFX Application...
    echo --------------------------------
    echo Starting JavaFX frontend...
    start "JavaFX Frontend" cmd /k "gradlew run"
) else (
    echo ✗ JavaFX build failed
)

echo.
echo ======================================
echo Connection test completed!
echo.
echo Backend URL: http://localhost:8080
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo.
echo Both applications should now be running.
echo Check the console windows for any error messages.
echo.
pause 