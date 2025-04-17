@echo off
setlocal enabledelayedexpansion

REM --- Step 1: Esecuzione dello script SQL ---
echo Esecuzione dello script SQL...
cd /d "%~dp0"

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

REM Esegui lo script SQL
mysql -u root -p mapdb < exampletab.sql
if %ERRORLEVEL% neq 0 (
    echo Errore nell'esecuzione dello script SQL. Interruzione del processo.
    pause
    exit /b 1
)
echo Script SQL eseguito correttamente!
pause
