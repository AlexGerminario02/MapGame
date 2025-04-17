@echo off
setlocal enabledelayedexpansion

REM --- Step 1: Imposta le variabili di percorso ---
set "PROJECT_DIR=%~dp0"
set "CLIENT_JAR=%PROJECT_DIR%Client.jar"
set "DEPENDENCIES_DIR=%PROJECT_DIR%Server\dependences"
set "MYSQL_JAR=%DEPENDENCIES_DIR%\mysql-connector-java-8.0.17.jar"
set "MAIN_CLASS=MainTest"

REM --- File sorgenti principali ---
set "MAIN_TEST_FILE=%PROJECT_DIR%MainTest.java"
set "KEYBOARD_FILE=%PROJECT_DIR%Keyboard.java"

REM --- Step 2: Verifica se il JAR esiste e lo elimina ---
if exist "%CLIENT_JAR%" (
    echo Eliminazione del file JAR esistente...
    del "%CLIENT_JAR%"
)

REM --- Step 3: Compilazione dei file .java ---
echo Compilazione dei file Java...

REM Compila i file .java inclusa la classe MainTest e Keyboard
javac -cp "%MYSQL_JAR%" -d %PROJECT_DIR% %MAIN_TEST_FILE% %KEYBOARD_FILE%

if %ERRORLEVEL% neq 0 (
    echo Errore nella compilazione del Client. Interruzione del processo.
    pause
    exit /b 1
)

REM --- Step 4: Creazione del Manifest ---
echo Creazione del file Manifest...

echo Main-Class: %MAIN_CLASS% > "%PROJECT_DIR%\Manifest.txt"

REM --- Step 5: Creazione del file JAR ---
echo Creazione del file JAR...

REM Includi il Manifest e tutti i file .class generati
cd /d "%PROJECT_DIR%"
jar cfm "%CLIENT_JAR%" Manifest.txt *.class

if %ERRORLEVEL% neq 0 (
    echo Errore durante la creazione del file JAR.
    pause
    exit /b 1
)

REM --- Step 6: Avvio del Client ---
echo 
cls
color 0A
echo ==============================
echo =  CLIENT AVVIATO CON SUCCESSO  =
echo ==============================
color 07

REM Avvia il Client con il classpath corretto
java -cp "%MYSQL_JAR%;%CLIENT_JAR%" %MAIN_CLASS% localhost 2025

if %ERRORLEVEL% neq 0 (
    echo Errore durante l'avvio del Client.
    pause
    exit /b 1
)

REM Mantieni la finestra del terminale aperta
pause
