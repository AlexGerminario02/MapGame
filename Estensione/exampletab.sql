DROP USER IF EXISTS MapUser;

CREATE USER 'MapUser' IDENTIFIED BY 'map';

-- Crea il database se non esiste
CREATE DATABASE IF NOT EXISTS MapDB;

-- Usa il database appena creato
USE MapDB;

GRANT ALL PRIVILEGES ON MapDB.* TO 'MapUser'@'%';

-- Nome tabella: exampleTab

DROP TABLE IF EXISTS exampleTab;

-- Crea la tabella mapdb.exampleTab
CREATE TABLE exampleTab (
    X1 FLOAT,
    X2 FLOAT,
    X3 FLOAT
);

-- Inserisci i dati nella tabella exampleTab
insert into mapdb.exampleTab values(1,2,0); 
insert into mapdb.exampleTab values(0,1,-1); 
insert into mapdb.exampleTab values(1,3,5); 
insert into mapdb.exampleTab values(1,3,4); 
insert into mapdb.exampleTab values(2,2,0); 

-- Conferma le operazioni
commit;