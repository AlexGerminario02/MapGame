@echo off
setlocal enabledelayedexpansion

REM --- Step 1: Imposta le variabili di percorso ---
set "SRC_DIR=%CD%"  REM Il percorso corrente, dato che siamo in Server\src
set "DEPENDENCIES_DIR=%SRC_DIR%\..\dependences"
set "JAR_FILE=%SRC_DIR%\Server.jar"
set "MAIN_CLASS=Main"

REM --- Percorsi delle directory con i file sorgente ---
set "CLUSTERING_DIR=%SRC_DIR%\clustering"
set "DATA_DIR=%SRC_DIR%\data"
set "DATABASE_DIR=%SRC_DIR%\database"
set "DISTANCE_DIR=%SRC_DIR%\distance"
set "SERVER_DIR=%SRC_DIR%\server"

REM --- Step 2: Compilazione dei file .java ---
echo Compilazione dei file Java...

REM Compila tutti i file .java inclusa la classe Main
javac -cp %DEPENDENCIES_DIR%\* -d %SRC_DIR% %SRC_DIR%\Main.java %CLUSTERING_DIR%\*.java %DATA_DIR%\*.java %DATABASE_DIR%\*.java %DISTANCE_DIR%\*.java %SERVER_DIR%\*.java

if errorlevel 1 (
    echo Errore durante la compilazione dei file Java.
    pause
    exit /b 1
)

REM --- Step 3: Creazione del manifest ---
echo Creazione del file manifest...

echo Main-Class: %MAIN_CLASS% > "%SRC_DIR%\manifest.txt"

REM --- Step 4: Creazione del file JAR ---
echo Creazione del file JAR...

REM Includi il manifest e tutti i file .class generati
jar cfm "%JAR_FILE%" "%SRC_DIR%\manifest.txt" -C "%SRC_DIR%" .

if errorlevel 1 (
    echo Errore durante la creazione del file JAR.
    pause
    exit /b 1
)

REM --- Step 5: Avvio del Server ---
echo Avvio del Server...

REM Includi tutte le dipendenze nel classpath
java -cp "%DEPENDENCIES_DIR%\*;%JAR_FILE%" %MAIN_CLASS%

if errorlevel 1 (
    echo Errore durante l'avvio del Server.
    pause
    exit /b 1
)

echo Server avviato correttamente!
pause
