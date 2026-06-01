@echo off
chcp 65001 >nul
title FashionStyle - Server
cd /d "%~dp0"

echo ============================================
echo   FASHIONSTYLE - Th?i trang cho m?i phong c?ch
echo ============================================
echo.

echo [1/3] Dang kiem tra MySQL...
sc query MySQL80 >nul 2>&1
if %errorlevel% neq 0 (
    sc query MySQL >nul 2>&1
    if %errorlevel% neq 0 (
        echo MySQL chua chay. Vui long chay MySQL truoc.
        pause
        exit /b
    )
)

echo [2/3] Kiem tra database...
mysql -u root -p123456 -h localhost fashion_recommendation -e "SELECT 1;" >nul 2>&1
if %errorlevel% neq 0 (
    echo Database chua duoc tao! Vui long chay file setup.sql de tao database.
    echo mysql -u root -p123456 -h localhost ^< duong-dan-setup.sql
    pause
    exit /b
) else (
    echo Database da san sang.
)

echo [3/3] Dang khoi dong server...
echo.
echo Mo trinh duyet: http://localhost:8080
echo Nhan Ctrl+C de dung.
echo.

java -jar target\fashion-recommendation-1.0.0.jar
pause
