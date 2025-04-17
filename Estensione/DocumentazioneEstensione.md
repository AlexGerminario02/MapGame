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
   - Installazione JDK Versione 22 tramite Terminale di Windows:
     - Aprire il Prompt dei comandi di Windows come amministratore.
     - Digitare il comando seguente per scaricare l'eseguibile del Java JDK versione 22:
      ```
      curl -o jdk-22_windows-x64_bin.exe https://download.oracle.com/java/22/latest/jdk-22_windows-x64_bin.exe
      ```
   - Una volta completato il download, installare l'eseguibile del JDK eseguendo il comando:
    ```
     jdk-22_windows-x64_bin.exe
    ```
   - Questo avvierà il processo di installazione del JDK. Al termine dell'installazione, verificare che sia andata a buon fine digitando il seguente comando:
    ```
     java -version
     ```
   - L'output dovrebbe restituire la versione di Java appena installata (22). Un esempio di output atteso è:  
     java version "22.0.2" 2024-07-16  
     Java(TM) SE Runtime Environment (build 22.0.2+9-70)  
     Java HotSpot(TM) 64-Bit Server VM (build 22.0.2+9-70, mixed mode, sharing)  
   - Per aggiungere il JDK al PATH delle variabili di sistema, digitare il comando:
    ```
     setx PATH "%PATH%;C:\Program Files\Java\jdk-22\bin"
     ```
   - Se si desidera verificare l'aggiunta del percorso al PATH, utilizzare il comando:
    ```
     echo %PATH%
      ```
<br>

2. **Scaricare ed Estrarre JavaFX (SDK):**  
Il download delle librerie javafx è fondamentale per il corretto avvio dell'applicazione.

  - [Scarica JavaFX SDK](https://download2.gluonhq.com/openjfx/22.0.2/openjfx-22.0.2_windows-x64_bin-jmods.zip)
   - Installazione SDK Versione 22.0.2 tramite Terminale di Windows:
     - Aprire il Prompt dei comandi di Windows come amministratore.
     - Digitare il comando seguente per scaricare il file .zip del JavaFX SDK versione 22.0.2:
      ```
      curl -o C:\javafx-sdk-22.0.2_windows-x64_bin-sdk.zip https://download2.gluonhq.com/openjfx/22.0.2/openjfx-22.0.2_windows-x64_bin-sdk.zip
      ```
      - Dopo il download, estrarre il contenuto del file ZIP utilizzando il comando:
      ```
      powershell -Command "Expand-Archive -Path 'C:\javafx-sdk-22.0.2_windows-x64_bin-sdk.zip' -DestinationPath 'C:\javafx-sdk'"
      ```
      Assicurarsi di inserire i percorsi corretti sia per il path di partenza che per quello di destinazione.

<br>

3. **Scaricare e Installare MySQL:**
   - Installare MySQL Community Server. È possibile scaricarlo dal sito ufficiale di MySQL.
     - [Scarica MySQL Community Server](https://dev.mysql.com/downloads/mysql/)

4. **Inserire MySQL tra le variabili d'ambiente:**
  - Aprire il menu `Start` e cercare "variabili d'ambiente".

  - Selezionare `Modifica le variabili d'ambiente di sistema`.

  - Nella finestra `Proprietà del sistema`, cliccare su `Variabili d'ambiente...`.

  - Nella sezione `Variabili di sistema`, cercare e selezionare la variabile `Path`, quindi cliccare su `Modifica...`.

  - Cliccare su `Nuovo` e aggiungere il percorso della cartella `bin` di MySQL. Il percorso predefinito è solitamente: `C:\Program Files\MySQL\MySQL Server [versione]\bin`

  - Cliccare su `OK` per chiudere tutte le finestre aperte.

5. **Verificare l'installazione**

  - Aprire un nuovo prompt dei comandi.

  - Digitare `mysql --version` e premere `Invio`.

  - Se MySQL è stato aggiunto correttamente al `PATH`, vedrai la versione di MySQL installata.


## **3.  Configurazione del progetto**

Questa guida descrive il processo di configurazione del progetto, tenendo in considerazione la necessità di adattare i percorsi specifici delle directory (path) per l'esecuzione corretta dei file batch. Di seguito troverai i dettagli su come configurare il database, il server e il client.

### **3.1  Configurazione del database**

Per configurare il database, utilizza il file batch `server_setup_sql.bat` che automatizza l'esecuzione di uno script SQL per creare il database e caricare la tabella.

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
3. **Esecuzione dello script SQL:** Lo script `exampletab.sql` viene eseguito nel database mapdb.
Se si verifica un errore durante l'esecuzione dello script SQL, il processo viene interrotto.
In caso di successo, viene confermato il corretto funzionamento dello script.


In conclusione, questo file batch automatizza il processo di configurazione del database, rendendolo accessibile sia agli utenti che preferiscono l'interfaccia grafica (doppio clic) sia a coloro che preferiscono lavorare da terminale.

### **3.2 Configurazione del server**

Nella directory Server\src del progetto, troverai il file `server_setup.bat`. Questo file batch è progettato per automatizzare la compilazione e l'avvio del server, assicurandoti di avere tutto il necessario per eseguire correttamente il tuo progetto.

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

1. **Configurazione delle variabili di percorso**: 
   - Imposta variabili per i percorsi dei file e delle directory utilizzate nel progetto.
   - Viene impostato il percorso corrente (`%CD%`) come `SRC_DIR`, che rappresenta la directory `Server\src`.
   - Vengono configurati i percorsi della cartella delle dipendenze (`dependences`), del file JAR da generare (`Server.jar`), e della classe principale (`Main`).

2. **Compilazione del Server**:
   - Il batch compila tutti i file `.java` presenti nelle sottodirectory `clustering`, `data`, `database`, `distance`, e `server`, oltre al file principale `Main.java`.
   - La compilazione utilizza il classpath che include le dipendenze presenti nella cartella `dependences`.
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

6. **Conferma dell'Avvio**:
   - La finestra del terminale rimarrà aperta con il comando `pause` per evitare la chiusura automatica e per consentire all'utente di vedere il messaggio di conferma.



### **3.3 Configurazione del client**

Nella directory Client\src del progetto, troverai il file `client_setup.bat`. Questo file batch è progettato per automatizzare la compilazione e l'avvio del client, assicurandoti di avere tutto il necessario per eseguire correttamente il tuo progetto.

### Esecuzione del file batch `client_setup.bat`

- **Metodo 1: Doppio clic**
  - Individuare il file `client_setup.bat` nella directory Client\src del progetto.
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

1. **Configurazione delle variabili di percorso**:
   - Vengono impostate le variabili per i percorsi dei file e delle directory utilizzate nel progetto.
   - Viene definito il percorso della directory corrente (`%~dp0`), il percorso della libreria JavaFX (`javafx-sdk-22.0.2\lib`), e il percorso delle dipendenze, incluso il connettore MySQL.
   - Vengono specificati il nome del file JAR da generare (`Client.jar`) e la classe principale (`MainTest`).

2. **Verifica ed eliminazione del file JAR esistente**:
   - Se un file JAR precedente esiste, viene eliminato per evitare conflitti con la nuova compilazione.

3. **Compilazione del Client**:
   - Vengono compilati i file `.java`, inclusi `MainTest.java` e `TeamMember.java`, utilizzando il classpath che include le librerie JavaFX e MySQL.
   - Se si verifica un errore durante la compilazione, viene visualizzato un messaggio di errore e il processo viene interrotto.

4. **Creazione del file Manifest**:
   - Viene generato un file `Manifest.txt` che specifica la classe principale (`MainTest`) del progetto. Questo file sarà utilizzato nella creazione del file JAR.

5. **Creazione del file JAR**:
   - Viene creato il file JAR del client (`Client.jar`) utilizzando il comando `jar`, che include il manifest e tutti i file `.class` generati dalla compilazione.
   - Se si verifica un errore durante la creazione del file JAR, verrà visualizzato un messaggio di errore e il processo verrà interrotto.

6. **Avvio del Client**:
   - Il client viene avviato utilizzando il comando `java` con il classpath che include tutte le dipendenze per JavaFX e MySQL, insieme al file JAR generato (`Client.jar`).
   - Vengono passati al client gli argomenti `localhost` e `2025` necessari per la connessione.
   - Se si verifica un errore durante l'avvio del client, viene visualizzato un messaggio di errore.
   - Se tutto va a buon fine, viene visualizzato un messaggio che conferma l'avvio corretto del client.

7. **Conferma dell'Avvio**:
   - La finestra del terminale rimarrà aperta con il comando `pause` per evitare la chiusura automatica e per consentire all'utente di vedere il messaggio di conferma.



## **4. Istruzioni per l'uso**

### Schermata di avvio del programma:


<p style="text-align: center;">
    <img src="img\Estensione\PRIMASCHERMATA.png" alt="">
</p>


L'interfaccia utente si presenta : 
- Stato Connesso: Ci fa capire che il client è connesso al server
- Pulsante Home: Ci riporta alla home in ogni schermata dell'interfaccia utente
- Pulsante Team: Apre una schermata che permette agli utenti di capire quali sono stati i membri del team che hanno realizzato il progetto. La schermata che si apre contiene i link ai profili github degli autori
- Pulstante Light/Dark Mode : in base alla scelta dell'utente, questo pulsante cambia l'interfaccia da chiara a scura e viceversa


Nome tabella: Inserire il nome della tabella su cui si vuole lavorare, in questo caso exampletab.

---
### Inserimento tabella errato:
Inserimento di un nome di tabella errato (nel nostro caso TabellaProva):

<p style="text-align: center;">
    <img src="img\Estensione\TabellaProva1.png" alt="">
</p>
Il programma ci dirà che la tabella non è stata trovata e ci dara la possibilità di inserire un'altro nome.

<p style="text-align: center;">
    <img src="img\Estensione\TabellaProva2NonEsiste.png" alt="">
</p>

---

### Impostazione della  modalità Dark Mode:
Modalità dark mode:

<p style="text-align: center;">
    <img src="img\Estensione\DarkMode.png" alt="">
</p>
Il programma darà la possibilità di impostare la modalità dark mode tramite il pulstante in alto a destra cambiando l'interfaccia utente da chiara a scura, utilizzando sfondi scuri con testo chiaro. Questo permette una riduzione dell'affaticamento visivo, dato che in ambienti poco illuminati, la modalità scura riduce la luminosità dello schermo, diminuendo il contrasto tra l'ambiente e lo schermo, il che può ridurre l'affaticamento degli occhi.

---
### Inserimento nome tabella:
Inserimento del nome della tabella (nel nostro caso exampletab):

<p style="text-align: center;">
    <img src="img\Estensione\Inserimentoexampletab.png" alt="">
</p>
Dopo aver inserito il nome della tabella ci compare a video una scelta.

---
### Scelta a video :
Impostazione  di una delle 3 scelte disponibili (nel nostro caso 1):
<p style="text-align: center;">
    <img src="img\Estensione\SECONDASCHERMATA.png" alt="">
</p>

Come notiamo, ci sono 3 scelte diverse

- Carica dendrogramma : caricamento da file del relativo dendrogramma
- Apprendi dendrogramma: apprendimento del dendrogramma con impostazioni della distanza e profondità
- Gestisci file .dat : gestione generale dei file.dat, permettendo anche la cancellazione di essi
---
### Scelta Caricamento: 

Se impostiamo il caricamento del dendrogramma da file: 

<p style="text-align: center;">
    <img src="img\Estensione\CaricamentoOK.png" alt="">
</p>
Ci verrà richiesto di inserire il nome dell'archivio con la relativa estensione.
Come è possibile notare, il programma ci darà a video il dendrogramma.

---
### Inserimento nome file errato:
Inserimento del nome del file (nel nostro caso esempio2.dat, un file che non esiste):

<p style="text-align: center;">
    <img src="img\Estensione\nonesiste.png" alt="">
</p>
Il programma ci dirà che il file non è stato trovare e darà la possibilità di inserire il nome di un file corretto o di tornare indietro.

---
### Scelta Apprendi:
Se impostiamo  l'opzione apprendi dendrogramma dal database:

<p style="text-align: center;">
    <img src="img\estensione\apprendi.png" alt="">
</p>

Ci comparirà successivamente l'inserimento della :
- profondità del dendrogramma desiderata
- tipo di distanza desiderata
- nome dell'archivio

Se si effettuano delle scelte corrette il risultato sarà questo

<p style="text-align: center;">
    <img src="img\estensione\apprendi3.1.png" alt="">
</p>


---
### Inserimento profondità errato:
Inserimento di una profondità errata (nel nostro caso 6):

<p style="text-align: center;">
    <img src="img\Estensione\1.png" alt="">
</p>

il programma ci chiederà comunque le opzioni per calcolare la distanza ma una volta inserita ci dirà che la profondità è errata.

<p style="text-align: center;">
    <img src="img\Estensione\2.png" alt="">
</p>
Il programma da comunque la possibilità all'utente di correggere la profondità.

---
### Inserimento distanza Single-Link Distance:
Se inseriamo una scelta corretta (Single-Link o Average-Link), in questo caso Single-Link:

<p style="text-align: center;">
    <img src="img\Estensione\primoesempiosingle.png" alt="">
</p>

Ci darà a video il dendrogramma e ci chiederà il nome dell'archivio in cui salvarlo.

Inserimento del nome del file (nel nostro caso primoesempio.dat):

---
### Inserimento distanza Average-Link Distance:
Se inseriamo una scelta corretta (Single-Link o Average-Link), in questo caso Average-Link:

<p style="text-align: center;">
    <img src="img\Estensione\secondoesempio.png" alt="">
</p>

Ci darà a video il dendrogramma e ci chiederà il nome dell'archivio in cui salvarlo.
Inserimento del nome del file (nel nostro caso secondoesempio.dat):

---
### Membri del team:
Visione dei membri del team di sviluppo del progetto, con i relativi link ai nostri profili github.

<p style="text-align: center;">
    <img src="img\Estensione\github.png" alt="">
</p>


---

## **5. Modello UML**

### UML CLIENT
<p style="text-align: center;">
    <img src="img\ProgettoBase\umlclient.png" alt="">
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
- [JavaDoc del Client](./JavaDoc/ClientDoc/index.html)
- [JavaDoc del Server](./JavaDoc/ServerDoc/index.html)

## **7. Contatti**

Per ulteriori informazioni, contattare:

- **Germinario Alessandro**: germinarioalex@gmail.com;
- **Falcone Luca**: falconeluca03@gmail.com;
- **Cannone Giuseppe**: cannonegiuseppe13@gmail.com;

