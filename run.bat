@echo off
chcp 65001 >nul
title FashionStyle - Server
cd /d "%~dp0"

echo ============================================
echo   FASHIONSTYLE - Th?i trang cho m?i phong c?ch
echo ============================================
echo.

:: Step 1 - Kiem tra MySQL
echo [1/3] Dang kiem tra MySQL...
sc query MySQL80 >nul 2>&1
if %errorlevel% neq 0 (
    sc query MySQL >nul 2>&1
    if %errorlevel% neq 0 (
        echo MySQL chua duoc cai dat hoac dang chay. Vui long chay MySQL truoc.
        pause
        exit /b
    )
)

:: Step 2 - Setup database
echo [2/3] Kiem tra database...
mysql -u root -p123456 -h localhost fashion_recommendation -e "SELECT 1;" >nul 2>&1
if %errorlevel% neq 0 (
    echo Dang tao database...
    mysql -u root -p123456 -h localhost < setup.sql
    mysql -u root -p123456 -h localhost fashion_recommendation < fix-data.sql
    mysql -u root -p123456 -h localhost fashion_recommendation < create-orders.sql
    echo Da tao database va du lieu mau.
) else (
    echo Database da san sang.
)

:: Step 3 - Chay ung dung
echo [3/3] Dang khoi dong server...
echo.
echo Mo trinh duyet va vao: http://localhost:8080
echo Nhan Ctrl+C de dung server.
echo.

java -jar target\fashion-recommendation-1.0.0.jar

pause
