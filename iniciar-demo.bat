@echo off
REM Lanzador de la DEMO de AdminPyme (no necesita base de datos).
cd /d "%~dp0"
echo Iniciando AdminPyme DEMO...
start "" cmd /c "timeout /t 2 /nobreak > nul && start http://localhost:5000"
node servidor-demo.js
echo.
echo La demo se detuvo.
pause
