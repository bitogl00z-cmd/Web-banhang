@echo off
cd /d C:\Users\Public\banhang
echo Starting server...
start /B mvn spring-boot:run > app-run.log 2>&1
echo Waiting for startup...
ping -n 25 127.0.0.1 > nul
echo Testing pages...
echo ================
echo Homepage:
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/' -TimeoutSec 5 -UseBasicParsing; Write-Output $r.StatusCode } catch { Write-Output 'FAIL' }"
echo Cluster 0:
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/cluster/0' -TimeoutSec 5 -UseBasicParsing; Write-Output $r.StatusCode } catch { Write-Output 'FAIL' }"
echo Product 1:
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/product/1' -TimeoutSec 5 -UseBasicParsing; Write-Output $r.StatusCode } catch { Write-Output 'FAIL' }"
echo Cart:
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/cart' -TimeoutSec 5 -UseBasicParsing; Write-Output $r.StatusCode } catch { Write-Output 'FAIL' }"
echo Search:
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/search?q=ao' -TimeoutSec 5 -UseBasicParsing; Write-Output $r.StatusCode } catch { Write-Output 'FAIL' }"
echo KMeans:
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/kmeans' -TimeoutSec 5 -UseBasicParsing; Write-Output $r.StatusCode } catch { Write-Output 'FAIL' }"
echo ================
echo Done
