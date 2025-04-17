@echo off
setlocal enabledelayedexpansion

REM --- Step 1: Imposta le variabili di percorso ---
set SCRIPT_DIR=%~dp0
set "JAVAFX_DIR=%SCRIPT_DIR%javafx-sdk-22.0.2\lib"
set "DEPENDENCIES_DIR=%SCRIPT_DIR%..\..\Server\dependences"
set "JAR_FILE=%SCRIPT_DIR%Client.jar"
set "MAIN_CLASS=MainTest"

REM --- File sorgenti principali ---
set "MAIN_TEST_FILE=%SCRIPT_DIR%MainTest.java"
set "TEAM_MEMBER_FILE=%SCRIPT_DIR%TeamMember.java"

REM --- Step 2: Verifica se il JAR esiste e lo elimina ---
if exist "%JAR_FILE%" (
    echo Eliminazione del file JAR esistente...
    del "%JAR_FILE%"
)

REM --- Step 3: Compilazione dei file .java ---
echo Compilazione dei file Java...

REM Percorso per la JavaFX SDK e MySQL Driver
set "JAVAFX_PATH=%JAVAFX_DIR%\javafx.base.jar;%JAVAFX_DIR%\javafx.controls.jar;%JAVAFX_DIR%\javafx.fxml.jar;%JAVAFX_DIR%\javafx.graphics.jar"
set "MYSQL_JAR=%DEPENDENCIES_DIR%mysql-connector-java-8.0.17.jar"

REM Compila i file .java inclusa la classe MainTest e TeamMember
javac -cp %JAVAFX_PATH%;%MYSQL_JAR% -d %SCRIPT_DIR% %MAIN_TEST_FILE% %TEAM_MEMBER_FILE%

if errorlevel 1 (
    echo Errore durante la compilazione dei file Java.
    pause
    exit /b 1   
)

REM --- Step 4: Creazione del Manifest ---
echo Creazione del file Manifest...

echo Main-Class: %MAIN_CLASS% > "%SCRIPT_DIR%\Manifest.txt"

REM --- Step 5: Creazione del file JAR ---
echo Creazione del file JAR...

REM Includi il Manifest e tutti i file .class generati
cd /d "%SCRIPT_DIR%"
jar cfm "%JAR_FILE%" Manifest.txt *.class

if errorlevel 1 (
    echo Errore durante la creazione del file JAR.
    pause
    exit /b 1
)


REM --- Step 6: Avvio del Client ---
echo Avvio del Client...

REM Avvia il client includendo le dipendenze per JavaFX e MySQL
java --module-path "%JAVAFX_DIR%" --add-modules javafx.controls,javafx.fxml -cp "%MYSQL_JAR%;%JAR_FILE%" %MAIN_CLASS% localhost 2025

if errorlevel 1 (
    echo Errore durante l'avvio del Client.
    pause
    exit /b 1
)

echo Client avviato correttamente!
pause
