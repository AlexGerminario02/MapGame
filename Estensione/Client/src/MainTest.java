import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.awt.Desktop;

/**
 * MainTest
 */
public class MainTest extends Application {
    private List<Control> uiElements = new ArrayList<>();
    private ObjectOutputStream out;
    private TextFlow logFlow;
    private ObjectInputStream in;
    private Text statusIcon;
    private TextArea dendrogramArea;
    private TextField tableNameField;
    private TextField archiveNameField;
    private Label connectionStatusLabel;
    private StackPane inputPanel;
    private Label homeLabel;
    private int logMessageCount = 0; // Contatore dei messaggi di log
    private Label teamLabel;
    private boolean isDarkMode = false;
    private BorderPane mainPane;
    private List<Button> allButtons = new ArrayList<>();

    /**
     * costruttore main test
     */
    public MainTest() {
    }

    /**
     * Aggiunge un messaggio di log al pannello di log con il colore specificato.
     * <p>
     * Questo metodo aggiunge un messaggio al pannello di log ({@code logFlow})
     * nell'interfaccia utente. Il messaggio viene visualizzato con il colore
     * specificato e con un font predefinito. Se il numero di messaggi di log
     * supera il limite (5), il pannello di log viene svuotato prima di aggiungere
     * il nuovo messaggio. Il metodo opera in modo asincrono tramite
     * {@code Platform.runLater()} per assicurare che le modifiche all'interfaccia
     * utente siano eseguite sul thread JavaFX.
     * </p>
     *
     * @param message Il messaggio di log da aggiungere.
     * @param color   Il colore del testo del messaggio, specificato come stringa
     *                esadecimale (es. "#FF0000" per il rosso).
     */
    private void appendLogMessage(String message, String color) {
        Platform.runLater(() -> {
            if (logMessageCount >= 5) {
                logFlow.getChildren().clear();
                logMessageCount = 0; // Resetta il contatore
            }

            Text text = new Text(message + "\n");
            text.setFont(Font.font("Roboto", 14));
            text.setFill(Color.web(color));
            logFlow.getChildren().add(text);

            logMessageCount++; // Incrementa il contatore dei messaggi di log
        });
    }

    /**
     * Crea una label (etichetta) con uno stile predefinito e un testo specificato.
     * <p>
     * La label utilizza il font "Roboto" con dimensione 14. Dopo la creazione,
     * viene aggiunta alla lista di elementi UI e viene applicato uno stile
     * personalizzato
     * tramite il metodo {@code updateElementStyle}.
     *
     * @param text Il testo da visualizzare nella label.
     * @return La label creata con lo stile applicato.
     */
    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Roboto", 14));
        uiElements.add(label);
        updateElementStyle(label);
        return label;
    }

    /**
     * Crea un campo di testo con uno stile predefinito e un testo segnaposto
     * (prompt).
     * <p>
     * Il campo di testo viene configurato con il testo segnaposto specificato. Dopo
     * la creazione,
     * viene aggiunto alla lista di elementi UI e viene applicato uno stile
     * personalizzato
     * tramite il metodo {@code updateElementStyle}.
     *
     * @param promptText Il testo segnaposto da visualizzare nel campo di testo.
     * @return Il campo di testo creato con lo stile applicato.
     */
    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        uiElements.add(textField);
        updateElementStyle(textField);
        return textField;
    }

    /**
     * Crea una combo box (menu a tendina) con uno stile predefinito.
     * <p>
     * Dopo la creazione, la combo box viene aggiunta alla lista di elementi UI
     * e viene applicato uno stile personalizzato tramite il metodo
     * {@code updateElementStyle}.
     *
     * @return La combo box creata con lo stile applicato.
     */
    private ComboBox<String> createStyledComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        uiElements.add(comboBox);
        updateElementStyle(comboBox);
        return comboBox;
    }

    /**
     * Crea un pannello di stato personalizzato utilizzando un layout di tipo
     * {@link BorderPane}.
     * <p>
     * Il pannello di stato visualizza l'icona di stato della connessione e il testo
     * "Stato: Non Connesso"
     * sulla sinistra. Al centro, visualizza le etichette di navigazione "Home" e
     * "Team". Sulla destra,
     * visualizza un pulsante di toggle per attivare/disattivare la modalità scura e
     * una label per la modalità corrente.
     * <ul>
     * <li>La label dello stato della connessione utilizza il font "Roboto" e il
     * colore nero.</li>
     * <li>L'icona di stato è un cerchio rosso di tipo {@link Text}.</li>
     * </ul>
     *
     * @return Il pannello di stato creato, configurato con gli elementi UI
     *         corretti.
     */
    private BorderPane createStatusPanel() {
        connectionStatusLabel = new Label("Stato: Non Connesso");
        connectionStatusLabel.setFont(Font.font("Roboto", 14));
        connectionStatusLabel.setTextFill(Color.BLACK);

        statusIcon = new Text("●");
        statusIcon.setFill(Color.RED);
        statusIcon.setFont(Font.font("Roboto", 20));

        HBox statusPanel = new HBox(10);
        statusPanel.setAlignment(Pos.CENTER_LEFT);
        statusPanel.getChildren().addAll(statusIcon, connectionStatusLabel);

        homeLabel = createNavLabel("Home");
        teamLabel = createNavLabel("Team");

        setNavLabelStyle(homeLabel);
        setNavLabelStyle(teamLabel);

        HBox navLabels = new HBox(10);
        navLabels.setPadding(new Insets(1, 7, 1, 7));
        navLabels.setAlignment(Pos.CENTER);

        navLabels.getChildren().addAll(homeLabel, teamLabel);

        Label modeLabel = new Label("Light Mode");
        modeLabel.setFont(Font.font("Roboto", 14));
        modeLabel.setTextFill(Color.BLACK);

        ToggleButton darkModeButton = createScrollToggleButton(modeLabel);

        HBox modePanel = new HBox(10, darkModeButton, modeLabel);
        modePanel.setAlignment(Pos.CENTER_RIGHT);

        BorderPane topPanel = new BorderPane();
        topPanel.setLeft(statusPanel);
        topPanel.setCenter(navLabels);
        topPanel.setRight(modePanel);

        return topPanel;
    }

    /**
     * Mostra un pannello per l'inserimento del nome di una tabella.
     * <p>
     * Questo metodo crea un layout a griglia con un campo di testo per
     * l'inserimento del nome della tabella
     * e un pulsante "OK" per inviare i dati. Al click del pulsante, il metodo tenta
     * di caricare i dati sul server.
     * Se si verifica un errore, viene visualizzato un messaggio di log.
     */
    private void showTableNamePanel() {
        clearUIElements();
        GridPane panel = new GridPane();
        panel.setHgap(10);
        panel.setVgap(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.CENTER);

        tableNameField = createStyledTextField("Inserisci il nome della tabella");
        Button submitTableButton = createStyledButton("OK");

        submitTableButton.setOnAction(e -> {
            try {
                loadDataOnServer(tableNameField.getText());
            } catch (IOException | ClassNotFoundException ex) {
                appendLogMessage("Errore: " + ex.getMessage(), "red");
            }
        });

        Label tableNameLabel = createStyledLabel("Nome tabella:");

        panel.add(tableNameLabel, 0, 0);
        panel.add(tableNameField, 1, 0);
        panel.add(submitTableButton, 2, 0);

        inputPanel.getChildren().clear();
        inputPanel.getChildren().add(panel);
    }

    /**
     * Metodo principale che avvia l'applicazione.
     * <p>
     * Questo metodo richiede due argomenti: l'indirizzo IP e la porta. Se non
     * vengono
     * forniti abbastanza argomenti, il programma stampa un messaggio di utilizzo e
     * si chiude.
     *
     * @param args Gli argomenti della riga di comando (IP e porta).
     */

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java MainTest <IP> <PORT>");
            System.exit(1);
        }

        @SuppressWarnings("unused")
        String ip = args[0];
        @SuppressWarnings("unused")
        int port = Integer.parseInt(args[1]);

        launch(args);
    }

    /**
     * Mostra un pannello per l'inserimento dei parametri per l'apprendimento.
     * <p>
     * Questo metodo crea un layout a griglia che consente all'utente di inserire la
     * profondità, selezionare il tipo di distanza,
     * e specificare il nome dell'archivio. Fornisce anche pulsanti per l'invio e il
     * ritorno al pannello del menu.
     * Al click del pulsante "Apprendi", il metodo valida i dati e crea un file con
     * i parametri inseriti.
     * Se il file esiste già o se si verificano errori, viene visualizzato un
     * messaggio di log.
     */
    private void showMinePanel() {
        clearUIElements();
        GridPane panel = new GridPane();
        panel.setHgap(10);
        panel.setVgap(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.CENTER);

        TextField depthField = createStyledTextField("Inserisci la profondità");
        ComboBox<String> distanceTypeCombo = createStyledComboBox();
        distanceTypeCombo.getItems().addAll("Single-Link", "Average-Link");
        distanceTypeCombo.setPromptText("Seleziona il tipo di distanza");

        archiveNameField = createStyledTextField("Inserisci il nome dell'archivio");

        Button submitButton = createStyledButton("Apprendi");
        Button backButton = createStyledButton("Indietro");

        submitButton.setOnAction(e -> {
            try {
                // Validazione della profondità
                int depth = validateDepth(depthField.getText());

                // Validazione del tipo di distanza
                int distanceType = distanceTypeCombo.getSelectionModel().getSelectedIndex() + 1;
                if (distanceType == 0) {
                    appendLogMessage("Errore: Seleziona un tipo di distanza valido.", "red");
                    return;
                }

                String archiveName = archiveNameField.getText().trim();
                if (archiveName.isEmpty()) {
                    appendLogMessage("Errore: Inserisci un nome valido per l'archivio.", "red");
                    return;
                }
                if (!archiveName.endsWith(".dat")) {
                    archiveName += ".dat";
                }
                // Controlla se esiste già un file con lo stesso nome
                boolean exists = fileExists(archiveName);
                // Controlla se esiste già un file con lo stesso nome
                if (exists) {
                    appendLogMessage("Errore: Esiste già un file con questo nome.", "red");
                } else {
                    // Crea il file se non esiste
                    createNewFile(depth, distanceType, archiveName);
                    appendLogMessage("File creato correttamente.", "green");
                }
            } catch (IllegalArgumentException | ClassNotFoundException | IOException ex) {
                appendLogMessage("Errore: " + ex.getMessage(), "red");
            }
        });

        backButton.setOnAction(e -> showMenuPanel());

        Label depthLabel = createStyledLabel("Profondità:");
        Label distanceTypeLabel = createStyledLabel("Tipo di distanza:");
        Label archiveNameLabel = createStyledLabel("Nome dell'archivio:");

        panel.add(depthLabel, 0, 0);
        panel.add(depthField, 1, 0);
        panel.add(distanceTypeLabel, 0, 1);
        panel.add(distanceTypeCombo, 1, 1);
        panel.add(archiveNameLabel, 0, 2);
        panel.add(archiveNameField, 1, 2);
        panel.add(submitButton, 0, 3);
        panel.add(backButton, 1, 3);

        inputPanel.getChildren().clear();
        inputPanel.getChildren().add(panel);
    }

    /**
     * Valida il valore di profondità inserito dall'utente.
     *
     * @param depthText Il testo che rappresenta la profondità.
     * @return La profondità valida come numero intero.
     * @throws IllegalArgumentException Se la profondità non è valida.
     */
    private int validateDepth(String depthText) throws IllegalArgumentException {
        try {
            int depth = Integer.parseInt(depthText);
            if (depth <= 0 || depth > 5) { // Assumiamo che la profondità massima valida sia 10
                throw new IllegalArgumentException("La profondità deve essere un numero positivo non superiore a 5.");
            }
            return depth;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Profondità non valida. Inserisci un numero intero positivo.");
        }
    }

    /**
     * Crea un nuovo file per il dendrogramma basato sui parametri forniti.
     *
     * @param depth        La profondità dell'albero.
     * @param distanceType Il tipo di distanza selezionato.
     * @param archiveName  Il nome del file da creare.
     * @throws ClassNotFoundException Se si verifica un errore di classe non
     *                                trovata.
     * @throws IOException            Se si verifica un errore durante la creazione
     *                                del file.
     */

    private void createNewFile(int depth, int distanceType, String archiveName)
            throws ClassNotFoundException, IOException {
        if (fileExists(archiveName)) {
            // Se il file esiste già, lancia un'eccezione o gestisci il caso in modo
            // appropriato.
            throw new IOException("Errore: Il file " + archiveName + " esiste già. Non è possibile sovrascrivere.");
        } else {
            // Se il file non esiste, crealo.
            mineDendrogramOnServer(depth, distanceType, archiveName);
            appendLogMessage("File " + archiveName + " creato correttamente.", "green");
        }
    }

    /**
     * Controlla se un file con il nome specificato esiste nella directory del
     * progetto.
     *
     * @param fileName Il nome del file da controllare.
     * @return true se il file esiste, altrimenti false.
     */
    private boolean fileExists(String fileName) {
        // Ottieni il percorso di lavoro corrente
        String currentDir = System.getProperty("user.dir");

        // Risali di due livelli fino alla directory "Progetto"
        String projectDir = new File(currentDir).getParentFile().getParent();

        // Costruisci il percorso completo al file nella directory Server/src usando
        // File.separator
        String fullPath = projectDir + File.separator + "Server" + File.separator + "src" + File.separator + fileName;

        Path filePath = Paths.get(fullPath);
        // Controlla se il file esiste
        return Files.exists(filePath);
    }

    /**
     * Crea un bottone con uno stile personalizzato.
     *
     * @param text Il testo da visualizzare sul bottone.
     * @return Un'istanza del bottone stilizzato.
     */

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Roboto", 14));

        // Imposta il colore di sfondo, il colore del testo e aggiungi ombra
        updateButtonStyle(button);

        button.setPrefSize(220, 40);

        allButtons.add(button); // Aggiungi il bottone alla lista
        return button;
    }

    /**
     * Metodo principale di avvio dell'applicazione JavaFX.
     *
     * @param primaryStage Il palcoscenico principale dell'applicazione.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GUI Dendrogramma");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        mainPane = new BorderPane();
        mainPane.setPadding(new Insets(20));
        mainPane.setStyle("-fx-background-color: #F0F0F0;");

        VBox outputPanel = createOutputPanel();
        mainPane.setCenter(outputPanel);

        BorderPane statusPanel = createStatusPanel();
        mainPane.setTop(statusPanel);

        inputPanel = new StackPane();
        mainPane.setBottom(inputPanel);

        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);

        showTableNamePanel();

        primaryStage.show();

        connectToServer(getParameters().getRaw().get(0), Integer.parseInt(getParameters().getRaw().get(1)));
    }

    /**
     * Si connette al server utilizzando l'indirizzo IP e la porta forniti.
     *
     * @param ip   L'indirizzo IP del server.
     * @param port La porta del server.
     */
    private void connectToServer(String ip, int port) {
        try {
            InetAddress addr = InetAddress.getByName(ip);
            @SuppressWarnings("resource")
            Socket socket = new Socket(addr, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            updateConnectionStatus(true);
        } catch (IOException e) {
            System.out.println("Errore di connessione: " + e.getMessage());
            updateConnectionStatus(false);
        }
    }

    /**
     * Crea e restituisce un pannello di output composto da una TextArea per il
     * dendrogramma
     * e un TextFlow per i log.
     *
     * @return Un pannello VBox contenente gli elementi di output.
     */
    private VBox createOutputPanel() {
        dendrogramArea = new TextArea();
        dendrogramArea.setEditable(false);
        dendrogramArea.setFont(Font.font("Roboto", 14));
        dendrogramArea.setStyle(
                "-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-border-color: #DCDCDC; -fx-border-width: 1px; -fx-border-radius: 5px;");

        logFlow = new TextFlow();
        logFlow.setPadding(new Insets(10));
        logFlow.setStyle(
                "-fx-background-color: #FFFFFF; -fx-border-color: #DCDCDC; -fx-border-width: 1px; -fx-border-radius: 5px;");

        VBox outputPanel = new VBox(10);
        outputPanel.getChildren().addAll(dendrogramArea, logFlow);
        VBox.setVgrow(dendrogramArea, Priority.ALWAYS);
        VBox.setVgrow(logFlow, Priority.ALWAYS);

        return outputPanel;
    }

    /**
     * Aggiorna lo stato della connessione visualizzando un'icona e un messaggio
     * testuale.
     *
     * @param isConnected Indica se la connessione è attiva (true) o meno (false).
     */
    private void updateConnectionStatus(boolean isConnected) {
        Platform.runLater(() -> {
            if (isConnected) {
                connectionStatusLabel.setText("Stato: Connesso");
                statusIcon.setFill(Color.GREEN);
            } else {
                connectionStatusLabel.setText("Stato: Non Connesso");
                statusIcon.setFill(Color.RED);
            }
        });
    }

    /**
     * Mostra il pannello del menu principale con pulsanti per caricare, apprendere
     * e gestire i file dendrogrammi.
     */
    private void showMenuPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.CENTER);

        // Create and style buttons
        Button loadFileButton = createStyledButton("Carica Dendrogramma");
        Button mineButton = createStyledButton("Apprendi Dendrogramma");
        Button deleteFileButton = createStyledButton("Gestisci File .dat");
        Button backButton = createStyledButton("Indietro");

        // Set button actions
        loadFileButton.setOnAction(e -> showLoadFilePanel());
        mineButton.setOnAction(e -> showMinePanel());
        deleteFileButton.setOnAction(e -> showDeleteFilePanel());
        backButton.setOnAction(e -> showTableNamePanel());

        panel.getChildren().addAll(loadFileButton, mineButton, deleteFileButton);

        inputPanel.getChildren().clear();
        inputPanel.getChildren().add(panel);
    }

    /**
     * Applica lo stile al Label di navigazione in base alla modalità corrente
     * (chiara o scura),
     * e gestisce gli eventi di hover per modificare lo stile quando il mouse entra
     * o esce dall'area del Label.
     *
     * @param label Il Label a cui applicare lo stile.
     */
    private void setNavLabelStyle(Label label) {
        label.setFont(Font.font("Roboto", 12)); // Riduci la dimensione del font
        label.setPadding(new Insets(4, 8, 4, 8)); // Riduci il padding
        updateNavLabelStyle(label, isDarkMode); // Aggiorna lo stile iniziale basato sulla modalità attuale

        // Gestione dell'hover basata sulla modalità attuale
        label.setOnMouseEntered(e -> {
            if (isDarkMode) {
                label.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-text-fill: black; " + // Scuro su sfondo chiaro
                                "-fx-border-radius: 20px; " +
                                "-fx-background-radius: 20px;");
            } else {
                label.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-text-fill: black; " +
                                "-fx-border-radius: 20px; " +
                                "-fx-background-radius: 20px;");
            }
        });

        label.setOnMouseExited(e -> {
            updateNavLabelStyle(label, isDarkMode); // Ripristina lo stile normale basato sulla modalità
        });
    }

    /**
     * Applica lo stile al Label di navigazione in base alla modalità corrente
     * (chiara o scura),
     * e gestisce gli eventi di hover per modificare lo stile quando il mouse entra
     * o esce dall'area del Label.
     *
     * @param label Il Label a cui applicare lo stile.
     */

    private void updateNavLabelStyle(Label label, boolean darkMode) {
        if (darkMode) {
            label.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-text-fill: white; " + // Testo bianco in modalità scura
                            "-fx-border-radius: 20px; " +
                            "-fx-background-radius: 20px;");
        } else {
            label.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-text-fill: black; " + // Testo nero in modalità chiara
                            "-fx-border-radius: 20px; " +
                            "-fx-background-radius: 20px;");
        }
    }

    /**
     * Mostra il pannello per l'eliminazione di un file.
     * <p>
     * Questo metodo cancella gli elementi dell'interfaccia utente corrente e
     * visualizza
     * un nuovo pannello con un ComboBox per selezionare il file da eliminare e due
     * pulsanti
     * per confermare l'eliminazione o tornare indietro.
     * </p>
     */
    private void showDeleteFilePanel() {
        clearUIElements();
        GridPane panel = new GridPane();
        panel.setHgap(10);
        panel.setVgap(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.CENTER);

        ComboBox<String> fileComboBox = createStyledComboBox();
        updateFileComboBox(fileComboBox);

        Button deleteButton = createStyledButton("Elimina");
        Button backButton = createStyledButton("Indietro");

        deleteButton.setOnAction(e -> {
            String selectedFile = fileComboBox.getSelectionModel().getSelectedItem();
            if (selectedFile != null && !selectedFile.isEmpty()) {
                try {
                    deleteFile(selectedFile);
                    updateFileComboBox(fileComboBox);
                } catch (IOException ex) {
                    appendLogMessage("Errore: " + ex.getMessage(), "red");
                }
            } else {
                appendLogMessage("Seleziona un file valido.", "red");
            }
        });

        backButton.setOnAction(e -> showMenuPanel());

        Label selectFileLabel = createStyledLabel("Seleziona File da Eliminare:");

        panel.add(selectFileLabel, 0, 0);
        panel.add(fileComboBox, 1, 0);
        panel.add(deleteButton, 0, 1);
        panel.add(backButton, 1, 1);

        inputPanel.getChildren().clear();
        inputPanel.getChildren().add(panel);
    }

    /**
     * Aggiorna lo stile di tutti gli elementi dell'interfaccia utente.
     * <p>
     * Questo metodo itera attraverso tutti gli elementi dell'interfaccia utente e
     * aggiorna il loro stile
     * in base alla modalità corrente (Dark Mode o Light Mode).
     * </p>
     */
    private void updateAllUIElements() {
        for (Control element : uiElements) {
            updateElementStyle(element);
        }
    }

    /**
     * Cancella la lista di tutti gli elementi dell'interfaccia utente.
     * <p>
     * Questo metodo svuota la collezione che contiene i riferimenti a tutti gli
     * elementi dell'interfaccia utente.
     * Viene utilizzato per preparare l'interfaccia a nuovi elementi da mostrare.
     * </p>
     */
    private void clearUIElements() {
        uiElements.clear();
    }

    /**
     * Aggiorna il contenuto del ComboBox dei file.
     * <p>
     * Questo metodo popola il ComboBox con i nomi dei file .dat presenti nella
     * cartella specificata.
     * La cartella viene determinata risalendo di due livelli rispetto alla
     * directory di lavoro corrente.
     * </p>
     *
     * @param fileComboBox Il ComboBox da aggiornare con i nomi dei file.
     */
    private void updateFileComboBox(ComboBox<String> fileComboBox) {
        fileComboBox.getItems().clear();
        String currentDir = System.getProperty("user.dir");
        String parentDir = new File(currentDir).getParentFile().getParent();
        String directoryPath = parentDir + File.separator + "Server" + File.separator + "src";

        File directory = new File(directoryPath);

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".dat"));

        if (files != null) {
            System.out.println("Found " + files.length + " files.");
            Arrays.sort(files);
            for (File file : files) {
                fileComboBox.getItems().add(file.getName());
            }
        } else {
            System.out.println("No files found or directory does not exist.");
        }
    }

    /**
     * Mostra il pannello per caricare un file.
     * <p>
     * Questo metodo visualizza un pannello che consente all'utente di inserire il
     * nome di un file
     * e di caricarlo. Fornisce anche un pulsante per tornare al menu principale.
     * </p>
     */
    private void showLoadFilePanel() {
        GridPane panel = new GridPane();
        panel.setHgap(10);
        panel.setVgap(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.CENTER);

        TextField fileNameField = createStyledTextField("Inserisci il nome del file");
        Button submitButton = createStyledButton("Carica");
        Button backButton = createStyledButton("Indietro");

        submitButton.setOnAction(e -> {
            try {
                loadDedrogramFromFileOnServer(fileNameField.getText());
            } catch (IOException | ClassNotFoundException ex) {
                appendLogMessage("Errore: " + ex.getMessage(), "red");
            }
        });

        backButton.setOnAction(e -> showMenuPanel());

        Label fileNameLabel = createStyledLabel("Nome del file:");
        panel.add(fileNameLabel, 0, 0);
        panel.add(fileNameField, 1, 0);
        panel.add(submitButton, 2, 0);
        panel.add(backButton, 2, 1);

        inputPanel.getChildren().clear();
        inputPanel.getChildren().add(panel);
    }

    /**
     * Carica i dati di una tabella sul server.
     * <p>
     * Invia il nome della tabella al server per caricarne i dati. Se il caricamento
     * ha successo, viene mostrato
     * il pannello del menu e viene loggato un messaggio di successo. In caso
     * contrario, viene mostrato un messaggio di errore.
     * </p>
     *
     * @param tableName Il nome della tabella da caricare sul server.
     * @throws IOException            Se si verifica un errore di I/O durante la
     *                                comunicazione con il server.
     * @throws ClassNotFoundException Se la classe dell'oggetto ricevuto dal server
     *                                non può essere trovata.
     */
    private void loadDataOnServer(String tableName) throws IOException, ClassNotFoundException {
        out.writeObject(0);
        out.writeObject(tableName);
        String risposta = (String) (in.readObject());
        if (risposta.equals("OK")) {
            appendLogMessage("Tabella caricata con successo.", "green");
            showMenuPanel();
        } else {
            appendLogMessage(risposta, "red");
        }
    }

    /**
     * Carica un dendrogramma da un file sul server.
     * <p>
     * Invia il nome del file al server e riceve il dendrogramma se il file esiste.
     * Gestisce vari scenari di errore, come file non trovato o dati non caricati.
     * </p>
     *
     * @param fileName Il nome del file contenente il dendrogramma da caricare.
     * @throws IOException            Se si verifica un errore di I/O durante la
     *                                comunicazione con il server.
     * @throws ClassNotFoundException Se la classe dell'oggetto ricevuto dal server
     *                                non può essere trovata.
     */
    private void loadDedrogramFromFileOnServer(String fileName) throws IOException, ClassNotFoundException {
        if (fileName != null && !fileName.isEmpty()) {
            out.writeObject(2);
            out.writeObject(fileName);
            String risposta = (String) (in.readObject());
            switch (risposta) {
                case "OK":
                    dendrogramArea.clear();
                    String dendrogram = (String) in.readObject();
                    dendrogramArea.setText(dendrogram);
                    appendLogMessage("Dendrogramma caricato con successo.", "green");
                    break;
                case "FILE_NOT_FOUND":
                    appendLogMessage("Il file non esiste.", "red");
                    break;
                case "DATA_NOT_LOADED":
                    appendLogMessage("Dati non caricati. Caricare prima i dati della tabella.", "red");
                    break;
                case "DEPTH_MISMATCH":
                    appendLogMessage("Numero di esempi maggiore della profondità del dendrogramma!", "red");
                    break;
                default:
                    appendLogMessage("Errore: " + risposta, "red");
            }
        }
    }

    /**
     * Esegue l'apprendimento di un dendrogramma sul server.
     * <p>
     * Invia al server i parametri di profondità, tipo di distanza e nome
     * dell'archivio,
     * e riceve il dendrogramma appreso. Se l'operazione ha successo, il
     * dendrogramma
     * viene visualizzato e viene loggato un messaggio di successo.
     * </p>
     *
     * @param depth        La profondità del dendrogramma da apprendere.
     * @param distanceType Il tipo di distanza da utilizzare per l'apprendimento.
     * @param archiveName  Il nome del file in cui salvare il dendrogramma.
     * @throws IOException            Se si verifica un errore di I/O durante la
     *                                comunicazione con il server.
     * @throws ClassNotFoundException Se la classe dell'oggetto ricevuto dal server
     *                                non può essere trovata.
     */
    private void mineDendrogramOnServer(int depth, int distanceType, String archiveName)
            throws IOException, ClassNotFoundException {
        out.writeObject(1);
        out.writeObject(depth);
        out.writeObject(distanceType);
        out.writeObject(archiveName);

        String risposta = (String) (in.readObject());
        if (risposta.equals("OK")) {
            appendLogMessage("Dendrogramma appreso con successo e salvato in " + archiveName + ".", "green");
            dendrogramArea.clear();
            String dendrogram = (String) in.readObject();
            dendrogramArea.setText(dendrogram);
        } else {
            appendLogMessage(risposta, "red");
        }
    }

    /**
     * Crea un pulsante ToggleButton per attivare o disattivare la modalità Dark
     * Mode.
     * <p>
     * Il pulsante cambia aspetto e testo in base allo stato (Dark Mode o Light
     * Mode).
     * Viene aggiunta un'animazione di transizione per un effetto visivo fluido.
     * Al cambio di stato, viene attivata o disattivata la Dark Mode
     * nell'interfaccia utente.
     * </p>
     *
     * @param modeLabel L'etichetta che mostra lo stato corrente della modalità
     *                  (Dark Mode o Light Mode).
     * @return Il ToggleButton configurato per attivare o disattivare la Dark Mode.
     */
    private ToggleButton createScrollToggleButton(Label modeLabel) {
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setPrefSize(40, 20);
        toggleButton.setStyle("-fx-background-color: #B0B0B0; -fx-background-radius: 10px;");

        Circle toggleCircle = new Circle(8);
        toggleCircle.setFill(Color.WHITE);
        toggleCircle.setTranslateX(-12);

        StackPane togglePane = new StackPane(toggleCircle);
        togglePane.setPrefSize(40, 20);

        toggleButton.setGraphic(togglePane);

        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.25), toggleCircle);
            if (newValue) {
                transition.setToX(12);
                toggleButton.setStyle("-fx-background-color: #4CAF50; -fx-background-radius: 10px;");
                modeLabel.setText("Dark Mode");
                modeLabel.setTextFill(Color.WHITE);
            } else {
                transition.setToX(-12);
                toggleButton.setStyle("-fx-background-color: #B0B0B0; -fx-background-radius: 10px;");
                modeLabel.setText("Light Mode");
                modeLabel.setTextFill(Color.BLACK);
            }
            transition.play();

            isDarkMode = newValue;
            toggleDarkMode(isDarkMode);
        });

        return toggleButton;
    }

    /**
     * Aggiorna lo stile di un pulsante specifico in base alla modalità attiva (dark
     * mode o light mode).
     * <p>
     * Modifica i colori di sfondo, testo, ombra e aggiunge un'animazione durante
     * l'hover.
     * </p>
     *
     * @param button Il pulsante di cui aggiornare lo stile.
     */
    private void updateButtonStyle(Button button) {
        String backgroundColor = isDarkMode ? "#333333" : "#DDDDDD"; // Colore di sfondo per dark e light mode
        String textColor = isDarkMode ? "#FFFFFF" : "#000000"; // Colore del testo per dark e light mode
        String normalShadowColor = isDarkMode ? "rgba(255,255,255,0.3)" : "rgba(0,0,0,0.3)"; // Colore dell'ombra
                                                                                             // normale
        String hoverShadowColor = isDarkMode ? "rgba(255,255,255,0.6)" : "rgba(0,0,0,0.6)"; // Colore dell'ombra durante
                                                                                            // l'hover

        // Imposta lo stile del bottone
        button.setStyle(
                "-fx-background-color: " + backgroundColor + "; " +
                        "-fx-text-fill: " + textColor + "; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-effect: dropshadow(gaussian, " + normalShadowColor + ", 5, 0.5, 0, 1);" // Ombra normale
        );

        // Aggiungi l'animazione per l'hover
        button.setOnMouseEntered(e -> {
            TranslateTransition shadowTransition = new TranslateTransition(Duration.millis(200), button);
            shadowTransition.setToX(0);
            shadowTransition.setToY(0);
            shadowTransition.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
            shadowTransition.play();

            button.setStyle(
                    "-fx-background-color: " + backgroundColor + "; " +
                            "-fx-text-fill: " + textColor + "; " +
                            "-fx-font-weight: bold; " +
                            "-fx-border-radius: 10px; " +
                            "-fx-background-radius: 10px; " +
                            "-fx-effect: dropshadow(gaussian, " + hoverShadowColor + ", 8, 0.5, 0, 2);" // Ombra durante
                                                                                                        // l'hover
            );
        });

        button.setOnMouseExited(e -> {
            TranslateTransition shadowTransition = new TranslateTransition(Duration.millis(200), button);
            shadowTransition.setToX(0);
            shadowTransition.setToY(0);
            shadowTransition.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
            shadowTransition.play();

            button.setStyle(
                    "-fx-background-color: " + backgroundColor + "; " +
                            "-fx-text-fill: " + textColor + "; " +
                            "-fx-font-weight: bold; " +
                            "-fx-border-radius: 10px; " +
                            "-fx-background-radius: 10px; " +
                            "-fx-effect: dropshadow(gaussian, " + normalShadowColor + ", 5, 0.5, 0, 1);" // Ombra
                                                                                                         // normale
            );
        });
    }

    /**
     * Aggiorna lo stile di tutti i pulsanti presenti nella lista allButtons.
     * <p>
     * Chiama il metodo {@link #updateButtonStyle(Button)} per ciascun pulsante
     * nella lista.
     * </p>
     */
    private void updateAllButtonStyles() {
        for (Button button : allButtons) {
            updateButtonStyle(button);
        }
    }

    /**
     * Elimina un file specificato nella cartella "Server/src".
     * <p>
     * Costruisce il percorso completo del file risalendo di due livelli dal
     * percorso corrente.
     * Se il file esiste, viene eliminato, altrimenti viene loggato un messaggio di
     * errore.
     * </p>
     *
     * @param fileName Il nome del file da eliminare.
     * @throws IOException Se si verifica un errore durante l'eliminazione del file.
     */
    private void deleteFile(String fileName) throws IOException {
        // Ottieni il percorso di lavoro corrente usando System.getProperty("user.dir")
        String currentDir = System.getProperty("user.dir");

        // Costruisci il percorso alla cartella "Estensione" risalendo di due livelli
        String parentDir = new File(currentDir).getParentFile().getParent();

        // Costruisci il percorso completo del file usando File.separator
        String fullPath = parentDir + File.separator + "Server" + File.separator + "src" + File.separator + fileName;

        File file = new File(fullPath);
        if (file.exists()) {
            Files.delete(file.toPath());
            appendLogMessage("File " + fileName + " eliminato con successo.", "green");
        } else {
            appendLogMessage("Il file " + fileName + " non esiste. Percorso utilizzato: " + fullPath, "red");
        }
    }

    /**
     * Aggiorna lo stile di un elemento dell'interfaccia utente in base alla
     * modalità attiva (dark mode o light mode).
     * <p>
     * Supporta etichette, campi di testo e combo box, modificando i colori di
     * sfondo, testo e bordo.
     * </p>
     *
     * @param element L'elemento dell'interfaccia utente di cui aggiornare lo stile.
     */
    private void updateElementStyle(Control element) {
        if (element instanceof Label) {
            Label label = (Label) element;
            label.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
        } else if (element instanceof TextField || element instanceof ComboBox) {
            element.setStyle(
                    isDarkMode ? "-fx-background-color: #3C3F41; -fx-text-fill: white; -fx-border-color: #606060;"
                            : "-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-border-color: #DCDCDC;");
        }
    }

    /**
     * Attiva o disattiva la modalità dark mode e aggiorna lo stile dell'interfaccia
     * utente.
     * <p>
     * Aggiorna i colori di sfondo, testo, bordi, e altri elementi dell'interfaccia
     * in base alla modalità attiva.
     * Chiama i metodi {@link #updateAllUIElements()} e
     * {@link #updateAllButtonStyles()} per aggiornare tutti i componenti.
     * </p>
     *
     * @param darkMode {@code true} per attivare la dark mode, {@code false} per la
     *                 light mode.
     */
    private void toggleDarkMode(boolean darkMode) {
        isDarkMode = darkMode;

        if (darkMode) {
            mainPane.setStyle("-fx-background-color: #2B2B2B;");
            connectionStatusLabel.setTextFill(Color.WHITE);
            dendrogramArea
                    .setStyle("-fx-control-inner-background: black; -fx-text-fill: white; -fx-border-color: white;");
            logFlow.setStyle("-fx-background-color: black; -fx-text-fill: green; -fx-border-color: white;");
        } else {
            mainPane.setStyle("-fx-background-color: #F0F0F0;");
            connectionStatusLabel.setTextFill(Color.BLACK);
            dendrogramArea.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-border-color: #DCDCDC;");
            logFlow.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-border-color: #DCDCDC;");
        }

        // Aggiorna lo stile delle etichette di navigazione
        updateNavLabelStyle(homeLabel, darkMode);
        updateNavLabelStyle(teamLabel, darkMode);

        updateAllUIElements();
        updateAllButtonStyles(); // Aggiorna tutti i bottoni
    }

    /**
     * Crea un'etichetta di navigazione con uno stile personalizzato.
     * <p>
     * L'etichetta è cliccabile e a seconda del testo, esegue un'azione specifica:
     * "Home" per visualizzare il pannello della tabella dei nomi, "Team" per
     * visualizzare il pannello del team.
     * </p>
     *
     * @param text Il testo da visualizzare sull'etichetta di navigazione.
     * @return L'etichetta di navigazione creata.
     */
    private Label createNavLabel(String text) {
        Label label = new Label(text);
        setNavLabelStyle(label);
        label.setOnMouseClicked(e -> {
            switch (text) {
                case "Home":
                    dendrogramArea.clear();
                    logFlow.getChildren().clear();
                    showTableNamePanel();
                    break;
                case "Team":
                    showTeamPanel();
                    break;
            }
        });
        return label;
    }

    /**
     * Crea e visualizza una finestra separata che mostra un pannello del team.
     * <p>
     * Il pannello del team include una tabella che elenca i membri del team, i loro
     * nomi e i link ai loro profili GitHub.
     * Ogni nome e link è visualizzato in una riga della tabella, con i link GitHub
     * che sono visualizzati come hyperlink cliccabili.
     * </p>
     * <p>
     * La finestra si chiude automaticamente dopo 20 secondi.
     * </p>
     *
     * <p>
     * La struttura principale della finestra include:
     * <ul>
     * <li>Un titolo in alto centrato con il nome del gruppo.</li>
     * <li>Una {@link TableView} che mostra i membri del team e i loro link
     * GitHub.</li>
     * </ul>
     * </p>
     *
     * @implNote La finestra è implementata utilizzando JavaFX e si basa su un
     *           {@link Stage} separato.
     *           I collegamenti GitHub sono resi cliccabili tramite l'uso di
     *           {@link Hyperlink} all'interno delle celle della tabella.
     *           La finestra viene chiusa automaticamente dopo 20 secondi
     *           utilizzando una {@link PauseTransition}.
     *
     * @throws RuntimeException Se si verifica un errore durante il tentativo di
     *                          aprire il link GitHub nel browser predefinito.
     */
    @SuppressWarnings("unchecked")
    private void showTeamPanel() {
        // Crea una nuova finestra (Stage) per mostrare il pannello del team
        Stage teamStage = new Stage();
        teamStage.setTitle("GRUPPO MAP");

        // Crea un layout principale
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #D3D3D3;"); // Sfondo grigio chiaro

        // Configura il titolo
        Label titleLabel = new Label("GRUPPO MAP");
        titleLabel.setFont(Font.font("Arial", 24));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2F4F4F;"); // Scritte bianco scuro
        titleLabel.setAlignment(Pos.CENTER);

        // Configura la TableView
        TableView<TeamMember> tableView = new TableView<>();
        tableView.setPrefWidth(800);

        // Colonna per i nomi
        TableColumn<TeamMember, String> nameColumn = new TableColumn<>("Nome e Cognome");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(300);

        // Colonna per i link GitHub
        TableColumn<TeamMember, String> githubColumn = new TableColumn<>("GitHub");
        githubColumn.setCellValueFactory(new PropertyValueFactory<>("github"));
        githubColumn.setPrefWidth(500);
        githubColumn.setCellFactory(column -> new TableCell<TeamMember, String>() {
            private final Hyperlink hyperlink = new Hyperlink();

            {
                hyperlink.setOnAction(event -> {
                    try {
                        Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    hyperlink.setText(item);
                    setGraphic(hyperlink);
                }
            }
        });

        // Aggiungi le colonne alla TableView
        tableView.getColumns().addAll(nameColumn, githubColumn);

        // Dati dei membri del team
        TeamMember[] teamMembers = {
                new TeamMember("Alessandro Germinario", "https://github.com/AlexGerminario02"),
                new TeamMember("Luca Falcone", "https://github.com/Luca-Falcone"),
                new TeamMember("Giuseppe Cannone", "https://github.com/pinopostino")
        };

        // Aggiungi i membri alla tabella
        tableView.getItems().addAll(teamMembers);

        // Crea un layout per il titolo e la TableView
        VBox titleAndTable = new VBox(20);
        titleAndTable.setAlignment(Pos.CENTER);
        titleAndTable.getChildren().addAll(titleLabel, tableView);

        // Aggiungi il layout alla parte centrale del BorderPane
        root.setCenter(titleAndTable);

        // Imposta il layout sulla finestra e mostra la finestra
        Scene teamScene = new Scene(root, 740, 210);
        teamStage.setScene(teamScene);
        teamStage.show();

        // Chiudi la finestra dopo 20 secondi
        PauseTransition pause = new PauseTransition(Duration.seconds(20));
        pause.setOnFinished(event -> teamStage.close());
        pause.play();
    }

}