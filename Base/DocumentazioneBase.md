# MANUALE UTENTE H-CLUS

## INDICE


[1.Introduzione](#1-introduzione)
- [1.2 Obiettivi](#12-obiettivi-del-progetto)

[2. Istruzioni per l'installazione](#2-istruzioni-per-linstallazione)

[3. Configurazione del progetto](#3-configurazione-del-progetto)
- [3.1 Configurazione del database](#31-configurazione-del-database)
- [3.2 Configurazione del server](#32-configurazione-del-server)
-  [3.3 Configurazione del client](#33-configurazione-del-client)
  
[4. Istruzioni per l'uso](#4-istruzioni-per-luso)


[5. Modello UML](#5-modello-uml)

[6. Java doc](#6-javadoc)

[7. Contatti](#7-contatti)


## **1. Introduzione**

Il progetto H-CLUS, sviluppato nell'ambito del corso di Metodi Avanzati di Programmazione (Anno Accademico 2023-24), si propone di implementare un sistema client-server per il clustering gerarchico di dati.

### 1.2 Obiettivi del Progetto

L'obiettivo principale del progetto è la realizzazione di un sistema denominato "H-CLUS", il quale include le seguenti componenti:
- **Server**: Modulo responsabile dell'applicazione di algoritmi di data mining per la scoperta di dendrogrammi di cluster di dati utilizzando tecniche di clustering agglomerativo.
- **Client**: Un'applicazione Java che consente agli utenti di accedere ai servizi di scoperta remota offerti dal server e visualizzare i cluster di dati identificati.

## **2. Istruzioni per l'installazione**

Per installare il software H-CLUS, è necessario seguire i seguenti passaggi:

1. **Scaricare e Installare Java Development Kit (JDK):**
   - Assicurarsi di avere installato il JDK versione 11 o successiva. È possibile scaricarlo dal sito ufficiale di Oracle.
     - [Scarica JDK da Oracle](https://download.oracle.com/java/22/latest/jdk-22_windows-x64_bin.exe)

2. **Scaricare e Installare MySQL:**
   - Installare MySQL Community Server. È possibile scaricarlo dal sito ufficiale di MySQL.
     - [Scarica MySQL Community Server](https://dev.mysql.com/downloads/mysql/)

3. **Inserire MySQL tra le variabili d'ambiente:**
  - Aprire il menu `Start` e cercare "variabili d'ambiente".

  - Selezionare `Modifica le variabili d'ambiente di sistema`.

  - Nella finestra `Proprietà del sistema`, cliccare su `Variabili d'ambiente...`.

  - Nella sezione `Variabili di sistema`, cercare e selezionare la variabile `Path`, quindi cliccare su `Modifica...`.

  - Cliccare su `Nuovo` e aggiungere il percorso della cartella `bin` di MySQL. Il percorso predefinito è solitamente: `C:\Program Files\MySQL\MySQL Server [versione]\bin`

  - Cliccare su `OK` per chiudere tutte le finestre aperte.

4. **Verificare l'installazione**

  - Aprire un nuovo prompt dei comandi.

  - Digitare `mysql --version` e premere `Invio`.

  - Se MySQL è stato aggiunto correttamente al `PATH`, vedrai la versione di MySQL installata.


## **3.  Configurazione del progetto**

### **3.1  Configurazione del database**

Nella directory principale del progetto, troverai il file `server_setup_sql.bat`. Questo file batch ti aiuterà a configurare il database eseguendo uno script SQL e permettendoti di creare il database se necessario.

Prima di avviare il file `server_setup_sql.bat`, assicurati di modificare i valori dei campi username e password con le credenziali del tuo account amministratore MySQL, se richiesto all'interno dello script.

Infine, seguire questi passaggi per eseguirlo:

### Esecuzione del file batch `server_setup_sql.bat`

- **Metodo 1: Doppio clic**
  - Individuare il file `server_setup_sql.bat` nella directory del progetto.
  - Fare doppio clic sul file `server_setup_sql.bat` per eseguirlo.

- **Metodo 2: Da terminale**
  - Aprire un terminale o prompt dei comandi.
  - Navigare alla directory dove si trova il file `server_setup_sql.bat`.
  - Eseguire il comando:
    ```sh
    server_setup_sql.bat
    ```

### Descrizione delle funzionalità del file batch

Il file batch eseguirà i seguenti passaggi:

1. **Configurazione delle variabili di progetto:** Imposta variabili per i percorsi dei file e delle directory utilizzate nel progetto.
2. **Richiesta all'utente per la creazione del database:** Viene richiesto all'utente se desidera creare il database mapdb (risposta "S" o "N").
Se l'utente sceglie "S", il database viene creato utilizzando il comando MySQL.
Se si verifica un errore durante la creazione del database, il processo viene interrotto.
3. **Esecuzione dello script SQL:** Lo script `exampletab.sql` viene eseguito nel database `MapDB`.
Se si verifica un errore durante l'esecuzione dello script SQL, il processo viene interrotto.
In caso di successo, viene confermato il corretto funzionamento dello script.


In conclusione, questo file batch automatizza il processo di configurazione del database, rendendolo accessibile sia agli utenti che preferiscono l'interfaccia grafica (doppio clic) sia a coloro che preferiscono lavorare da terminale.

### **3.2 Configurazione del server**

Nella directory "..\Server\src", troverai il file `server_setup.bat`. Questo file batch è progettato per automatizzare la compilazione e l'avvio del server, assicurandoti di avere tutto il necessario per eseguire correttamente il tuo progetto.

**Prima di eseguire il file** `server_setup.bat`
Assicurati di aver configurato correttamente tutte le dipendenze necessarie, come il connettore MySQL (mysql-connector-java-8.0.17.jar), e che il file Java principale del server (Main.java) sia presente nella directory corretta (Server\src).



### Esecuzione del file batch `server_setup.bat`

- **Metodo 1: Doppio clic**
  - Individuare il file `server_setup.bat` nella directory Server\src del progetto.
  - Fare doppio clic sul file `server_setup.bat` per eseguirlo.

- **Metodo 2: Da terminale**
  - Aprire un terminale o prompt dei comandi.
  - Navigare alla directory dove si trova il file `server_setup.bat`.
  - Eseguire il comando:
    ```sh
    server_setup.bat
    ```

### Descrizione delle funzionalità del file batch

Il file batch eseguirà i seguenti passaggi:

1. **Impostazione delle variabili di percorso**: 
   - Viene impostato il percorso della directory corrente (`%CD%`) come `SRC_DIR`, che rappresenta la directory `Server\src`.
   - Viene inoltre configurato il percorso della cartella delle dipendenze (`dependences`).
   - Viene specificato il nome del file JAR che verrà generato (`Server.jar`) e la classe principale (`Main`).

2. **Compilazione del Server**:
   - Il batch compila tutti i file `.java` presenti nelle sottodirectory `clustering`, `data`, `database`, `distance`, e `server` all'interno della directory `Server\src`.
   - Viene utilizzato il comando `javac` con il classpath che include le dipendenze presenti nella cartella `dependences`.
   - Se si verifica un errore durante la compilazione, verrà visualizzato un messaggio di errore e il processo verrà interrotto.

3. **Creazione del file manifest**:
   - Viene generato un file `manifest.txt` che specifica la classe principale (`Main`) del progetto. Questo file sarà utilizzato nella creazione del file JAR.

4. **Creazione del file JAR**:
   - Viene creato il file JAR del server (`Server.jar`) utilizzando il comando `jar`, che include il manifest e tutti i file `.class` generati dalla compilazione.
   - Se si verifica un errore durante la creazione del file JAR, verrà visualizzato un messaggio di errore e il processo verrà interrotto.

5. **Avvio del Server**:
   - Il server viene avviato utilizzando il comando `java` con il classpath che include tutte le dipendenze (`dependences`) e il file JAR generato (`Server.jar`).
   - Se si verifica un errore durante l'avvio del server, verrà visualizzato un messaggio di errore.
   - Se tutto va a buon fine, verrà visualizzato un messaggio che conferma l'avvio corretto del server.


### **3.3 Configurazione del client**

Nella directory "Client\src" del progetto, troverai il file `client_setup.bat`. Questo file batch è progettato per automatizzare la compilazione e l'avvio del client, assicurandoti di avere tutto il necessario per eseguire correttamente il tuo progetto.

**Prima di eseguire il file** `client_setup.bat`,assicurati che tutte le dipendenze necessarie, come il connettore MySQL (mysql-connector-java-8.0.17.jar), siano presenti nella directory corretta `(Server\dependences)` . Inoltre, verifica che il file Java principale del client `(MainTest.java)` sia presente nella directory `Client\src`.


### Esecuzione del file batch `client_setup.bat`

- **Metodo 1: Doppio clic**
  - Individuare il file `client_setup.bat` nella directory del progetto.
  - Fare doppio clic sul file `client_setup.bat` per eseguirlo.

- **Metodo 2: Da terminale**
  - Aprire un terminale o prompt dei comandi.
  - Navigare alla directory dove si trova il file `client_setup.bat`.
  - Eseguire il comando:
    ```sh
    client_setup.bat
    ```

### Descrizione delle funzionalità del file batch

Il file batch eseguirà i seguenti passaggi:

1. **Configurazione delle variabili di progetto**: 
   - Imposta variabili per i percorsi dei file e delle directory utilizzate nel progetto.
   - Vengono definiti i percorsi del progetto (`PROJECT_DIR`), del file JAR del client (`CLIENT_JAR`), della directory delle dipendenze (`DEPENDENCIES_DIR`), del connettore MySQL (`MYSQL_JAR`), e della classe principale (`MAIN_CLASS`).

2. **Compilazione del Client**:
   - Il batch compila i file `.java` principali `MainTest.java` e `Keyboard.java` utilizzando `javac`, includendo il classpath per il connettore MySQL.
   - Se si verifica un errore durante la compilazione, verrà visualizzato un messaggio di errore e il processo verrà interrotto.

3. **Creazione del file manifest**:
   - Viene generato un file `Manifest.txt` che specifica la classe principale (`MainTest`) del progetto. Questo file sarà utilizzato nella creazione del file JAR.

4. **Creazione del file JAR**:
   - Viene creato il file JAR del client (`Client.jar`) utilizzando il comando `jar`, che include il manifest e tutti i file `.class` generati dalla compilazione.
   - Se si verifica un errore durante la creazione del file JAR, verrà visualizzato un messaggio di errore e il processo verrà interrotto.

5. **Avvio del Client**:
   - Dopo aver pulito la console e cambiato il colore del testo, il client viene avviato utilizzando il comando `java` con il classpath che include il connettore MySQL e il file JAR generato (`Client.jar`).
   - Vengono passati come argomenti `localhost` e `2025`.
   - Se si verifica un errore durante l'avvio del client, verrà visualizzato un messaggio di errore.

6. **Conferma dell'Avvio**:
   - Se tutto va a buon fine, verrà visualizzato un messaggio che conferma l'avvio corretto del client.
   - La finestra del terminale rimarrà aperta con il comando `pause` per evitare la chiusura automatica.



## **4. Istruzioni per l'uso**

### Schermata di avvio del programma:


<p style="text-align: center;">
    <img src="img\ProgettoBase\SchermataIniziale.png" alt="">
</p>
addr indica l'indirizzo ip a cui si è connessi.

Socket invece ha 3 campi: 
- addr: nome del dispotivo/indirizzo locale
- Port: la porta del server
- localport: la porta locale

Nome tabella: Inserire il nome della tabella su cui si vuole lavorare, in questo caso exampletab.

---
### Inserimento tabella errato:
Inserimento di un nome di tabella errato (nel nostro caso prova):

<p style="text-align: center;">
    <img src="img\ProgettoBase\TabellaNonEsiste.png" alt="">
</p>
Il programma ci dirà che la tabella non è stata trovata e ci dara la possibilità di inserire un'altro nome.

---
### Inserimento nome tabella:
Inserimento del nome della tabella (nel nostro caso exampletab):

<p style="text-align: center;">
    <img src="img\ProgettoBase\exampletabProva.png" alt="">
</p>
Dopo aver inserito il nome della tabella ci compare a video una scelta.

---
### Scelta diversa da 1 o 2:
Inserimento di una scelta diversa da 1 o 2 (nel nostro caso 0 e 3):
<p style="text-align: center;">
    <img src="img\ProgettoBase\SceltaSbagliata.png" alt="">
</p>

Come notiamo, inserendo una scelta diversa da 1 o 2 ci ripropone la scelta fin quando non inseriremo un valore valido.

---
### Scelta 1: 

Se inseriamo 1 avremo scelto il caricamento del dendrogramma da file: 

<p style="text-align: center;">
    <img src="img\ProgettoBase\CaricamentoRiuscito.png" alt="">
</p>
Ci verrà richiesto di inserire il nome dell'archivio con la relativa estensione.
Come è possibile notare, il programma ci darà a video il dendrogramma e terminerà l'esecuzione.

---
### Inserimento nome file errato:
Inserimento del nome del file (nel nostro caso TabellaDiProva, un file che non esiste):

<p style="text-align: center;">
    <img src="img\ProgettoBase\TabellaDiProva_Caricamento.png" alt="">
</p>
Il programma ci dirà che il file non è stato trovare e terminerà l'esecuzione.


---
### Scelta 2:
Se inseriamo 2 avremo scelto l'opzione apprendi dendrogramma dal database:

<p style="text-align: center;">
    <img src="img\ProgettoBase\apprendi.png" alt="">
</p>

Ci comparirà successivamente l'inserimento della profondità del dendrogramma desiderata

---
### Inserimento profondità errato:
Inserimento di una profondità errata (nel nostro caso 6):

<p style="text-align: center;">
    <img src="img\ProgettoBase\ProfonditàMaggiore.png" alt="">
</p>

il programam ci chiederà comunque le opzioni per calcolare la distanza ma una volta inserita ci dirà che la profondità è errata e il programma termina l'esecuzione.

---
### Inserimento profondità corretto:
Se inseriamo una profondità corretta (da 1 a 5):

<p style="text-align: center;">
    <img src="img\ProgettoBase\imgg.png" alt="">
</p>

Ci verrà chiesto il tipo di distanza desiderata.

---
### Inserimento distanza errata:
Se inseriamo una scelta errata (nel nostro caso 4):

<p style="text-align: center;">
    <img src="img\ProgettoBase\DistanzaErrata.png" alt="">
</p>

In questo caso ci chiederà di inserire di nuovo una scelta di valori corretti.

---
### Inserimento distanza 1:
Se inseriamo una scelta corretta (1 o 2), in questo caso 1:

<p style="text-align: center;">
    <img src="img\ProgettoBase\PrimoCaso.png" alt="">
</p>

Ci darà a video il dendrogramma e ci chiederà il nome dell'archivio in cui salvarlo.

---
### Inserimento nome file:
Inserimento del nome del file (nel nostro caso example2.dat):

<p style="text-align: center;">
    <img src="img\ProgettoBase\FilePrimoCaso.png" alt="">
</p>

Il programma termina.

---
### Inserimento distanza 2:
Se inseriamo una scelta corretta (1 o 2), in questo caso 2:

<p style="text-align: center;">
    <img src="img\ProgettoBase\SecondoCaso.png" alt="">
</p>

Ci darà a video il dendrogramma e ci chiederà il nome dell'archivio in cui salvarlo.

---
### Inserimento nome file:
Inserimento del nome del file (nel nostro caso example22.dat):

<p style="text-align: center;">
    <img src="img\ProgettoBase\FileSecondoCaso.png" alt="">
</p>
Il programma termina.

---

## **5. Modello UML**

### UML CLIENT
<p style="text-align: center;">
    <img src="img\ProgettoBase\Main.jpg" alt="">
</p>

### UML SERVER COMPLETO
<p style="text-align: center;">
    <img src="img\ProgettoBase\Server.png"alt="">
</p>

### UML PACKAGE SERVER
<p style="text-align: center;">
    <img src="img\ProgettoBase\servers.png" alt="">
</p>

### UML PACKAGE CLUSTERING
<p style="text-align: center;">
    <img src="img\ProgettoBase\Clustering.png" alt="">
</p>

### UML PACKAGE DISTANCE
<p style="text-align: center;">
    <img src="img\ProgettoBase\ClusterPackage.png" alt="">
</p>

### UML PACKAGE DATA
<p style="text-align: center;">
    <img src="img\ProgettoBase\Data.png" alt="">
</p>

### UML PACKAGE DATABASE 
<p style="text-align: center;">
    <img src="img\ProgettoBase\Database.png" alt="">
</p>

---
## **6. JavaDoc**

Per accedere alla documentazione JavaDoc del progetto, fare clic sui link sottostanti:
- [JavaDoc del Client](./JavaDoc/Client/index.html)
- [JavaDoc del Server](./JavaDoc/Server/index.html)

## **7. Contatti**

Per ulteriori informazioni, contattare:

- **Germinario Alessandro**: germinarioalex@gmail.com;
- **Falcone Luca**: falconeluca03@gmail.com;
- **Cannone Giuseppe**: cannonegiuseppe13@gmail.com;

