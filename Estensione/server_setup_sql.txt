@echo off
setlocal enabledelayedexpansion

REM Imposta il percorso relativo alla directory dello script
set "SCRIPT_DIR=%~dp0"
echo "Script directory: %SCRIPT_DIR%"

REM --- Step 1: Esecuzione dello script SQL ---
echo Esecuzione dello script SQL...

REM Chiedi all'utente se vuole creare il database
set /p create_db="Vuoi creare il database 'mapdb'? (S/N): "
if /i "!create_db!"=="S" (
    mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS mapdb;"
    if !ERRORLEVEL! neq 0 (
        echo Errore nella creazione del database. Interruzione del processo.
        pause
        exit /b 1
    )
    echo Database 'mapdb' creato o già esistente.
)

REM Verifica l'esistenza del file SQL e lo esegue
if exist "%SCRIPT_DIR%\exampletab.sql" (
    mysql -u root -p mapdb < "%SCRIPT_DIR%\exampletab.sql"
    if %ERRORLEVEL% neq 0 (
        echo Errore nell'esecuzione dello script SQL. Interruzione del processo.
        pause
        exit /b 1
    )
    echo Script SQL eseguito correttamente!
) else (
    echo File SQL non trovato in %ESTENSIONE_PATH%\exampletab.sql
    echo Assicurati che il file sia presente nella posizione corretta.
    pause
    exit /b 1
)

echo Script SQL completato.
pause
