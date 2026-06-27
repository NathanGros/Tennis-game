@echo off
for %%f in (tennis-game-*-standalone.jar) do set JAR=%%f
java -jar %JAR%
if %errorlevel% neq 0 (
    echo.
    echo Java is not installed. Please install Java 21 from https://adoptium.net
    pause
)
