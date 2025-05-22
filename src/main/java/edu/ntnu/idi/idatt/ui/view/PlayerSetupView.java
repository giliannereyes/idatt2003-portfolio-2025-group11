package edu.ntnu.idi.idatt.ui.view;

import edu.ntnu.idi.idatt.ui.controller.PlayerSetupController;
import edu.ntnu.idi.idatt.ui.enums.PlayerToken;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.*;

/**
 * The PlayerSetupView class is responsible for displaying the player setup
 * screen in the game. It allows users to input player names and select tokens,
 * or load player data from a CSV file.
 *
 * @author Gilianne Reyes
 * @version 0.1
 * @since 0.1
 */
public class PlayerSetupView implements View {
    private final VBox root;
    private final ComboBox<Integer> playerCountDropdown;
    private final GridPane playerGrid;
    private final List<TextField> nameFields;
    private final List<ComboBox<String>> tokenSelectors;
    private PlayerSetupController controller;

    /**
     * Constructs a PlayerSetupView instance.
     * Initializes the UI components and layout.
     */
    public PlayerSetupView() {
        root = new VBox(20);
        playerCountDropdown = new ComboBox<>();
        playerGrid = new GridPane();
        nameFields = new ArrayList<>();
        tokenSelectors = new ArrayList<>();
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("centered-container");
        root.setId("root-pane");
    }

    /**
     * Sets the controller for this view.
     *
     * @param controller is the controller to set for this view.
     */
    public void setController(PlayerSetupController controller) {
        this.controller = controller;
    }

    /**
     * Retrieves the controller for this view.
     *
     * @return the controller for this view.
     */
    @Override
    public Parent getRoot() {
        return root;
    }

    /**
     * Creates the UI components and layout for the player setup view.
     */
    public void initializeView() {
        Text title = new Text("Player Setup");
        title.getStyleClass().add("title-text");

        Text csvAlternativeText = new Text("Option 1: Load player configuration from CSV");
        csvAlternativeText.getStyleClass().add("alternative-text");
        Button loadCsvButton = new Button("Load Player Configuration (CSV)");
        loadCsvButton.setPrefSize(260, 40);

        Text manualAlternativeText = new Text("Option 2: Manually enter player names and tokens");
        manualAlternativeText.getStyleClass().add("alternative-text");

        Label playerCountLabel = new Label("Number of players:");
        playerCountLabel.getStyleClass().add("section-label");

        playerCountDropdown.getItems().addAll(2, 3, 4, 5);
        playerCountDropdown.setValue(2);
        playerCountDropdown.getStyleClass().add("player-count-dropdown");

        Button saveCsvButton = new Button("Save This Configuration (CSV)");
        saveCsvButton.setPrefSize(260, 40);

        Button registerButton = new Button("Register Players");
        registerButton.getStyleClass().add("primary-button");

        updatePlayerInputs();
        playerCountDropdown.setOnAction(e -> updatePlayerInputs());
        loadCsvButton.setOnAction(e -> onLoadCsvButtonClicked());
        registerButton.setOnAction(e -> onRegisterButtonClicked());
        saveCsvButton.setOnAction(e -> onSaveCsvButtonClicked());

        playerGrid.setHgap(15);
        playerGrid.setVgap(12);
        playerGrid.setAlignment(Pos.CENTER);
        playerGrid.getStyleClass().add("player-grid");

        VBox.setMargin(csvAlternativeText, new Insets(15, 0, 0, 0));
        VBox.setMargin(manualAlternativeText, new Insets(25, 0, 0, 0));
        VBox.setMargin(registerButton, new Insets(20, 0, 0, 0));
        VBox.setMargin(saveCsvButton, new Insets(10, 0, 0, 0));

        root.getChildren().addAll(
              title,
              csvAlternativeText,
              loadCsvButton,
              manualAlternativeText,
              playerCountLabel,
              playerCountDropdown,
              playerGrid,
              saveCsvButton,
              registerButton
        );
    }

    /**
     * Updates the amount of visible player input fields based on the selected
     * number of players in the dropdown.
     */
    private void updatePlayerInputs() {
        playerGrid.getChildren().clear();
        nameFields.clear();
        tokenSelectors.clear();
        int playerCount = playerCountDropdown.getValue();
        Set<String> availableTokens = new HashSet<>();
        for (PlayerToken token : PlayerToken.values()) {
            availableTokens.add(token.getName());
        }
        for (int i = 0; i < playerCount; i++) {
            Label nameLabel = new Label("Player " + (i + 1) + " Name:");
            nameLabel.getStyleClass().add("input-label");

            TextField nameField = new TextField();
            nameField.setPromptText("Enter player name");
            nameField.getStyleClass().add("player-name-field");

            Label tokenLabel = new Label("Token:");
            tokenLabel.getStyleClass().add("input-label");

            ComboBox<String> tokenDropdown = new ComboBox<>();
            tokenDropdown.getItems().addAll(availableTokens);
            tokenDropdown.setPromptText("Select token");
            tokenDropdown.getStyleClass().add("token-dropdown");

            nameFields.add(nameField);
            tokenSelectors.add(tokenDropdown);

            playerGrid.add(nameLabel, 0, i);
            playerGrid.add(nameField, 1, i);
            playerGrid.add(tokenLabel, 2, i);
            playerGrid.add(tokenDropdown, 3, i);
        }
    }

    /**
     * Automatically fills the player names and tokens from a CSV file.
     *
     * @param playerData is a list of player data, consisting of entries
     *                   in the format [name, token].
     */
    public void autoFillPlayersFromCSV(List<String[]> playerData) {
        playerCountDropdown.setValue(playerData.size());
        updatePlayerInputs();
        for (int i = 0; i < playerData.size(); i++) {
            nameFields.get(i).setText(playerData.get(i)[0]);
            tokenSelectors.get(i).setValue(playerData.get(i)[1]);
        }
    }

    /**
     * Handles the action when the "Register Player Configuration" button is clicked.
     */
    private void onRegisterButtonClicked() {
        controller.registerPlayerConfigs(getPlayerNames(), getSelectedTokens());
    }

    /**
     * Handles the action when the "Save Players to CSV" button is clicked.
     */
    private void onSaveCsvButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Player Configuration");
        fileChooser.getExtensionFilters().add(
              new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (file != null) {
            controller.savePlayersToCsv(getPlayerNames(), getSelectedTokens(), file);
        } else {
            showAlert("Save Canceled",
                  "The player configuration file was not saved. Please try again.");
        }
    }

    /**
     * Handles the action when the "Load Players from CSV" button is clicked.
     */
    private void onLoadCsvButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV file");
        fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            controller.loadPlayersFromCsv(file);
        } else {
            showAlert("No file selected",
                  "No players were loaded because no file was selected. Please try again."
            );
        }
    }

    /**
     * Retrieves the player names entered in the text fields.
     *
     * @return a list of player names.
     */
    public List<String> getPlayerNames() {
        List<String> names = new ArrayList<>();
        for (TextField nameField : nameFields) {
            names.add(nameField.getText().trim());
        }
        return names;
    }

    /**
     * Retrieves the selected tokens from the dropdowns.
     *
     * @return a list of selected tokens.
     */
    public List<String> getSelectedTokens() {
        List<String> tokens = new ArrayList<>();
        for (ComboBox<String> tokenDropdown : tokenSelectors) {
            tokens.add(tokenDropdown.getValue());
        }
        return tokens;
    }

    /**
     * Displays an alert when the CSV data is invalid.
     */
    public void onInvalidCsvData() {
        showAlert("Invalid CSV data",
              "The players could not be loaded from the CSV file because some " +
                    "player names or token names are invalid or not unique."
        );
    }

    /**
     * Displays an alert when the CSV file is loaded successfully.
     */
    public void onSuccessfulCsvLoad() {
        showAlert("Players loaded successfully",
              "Players have been loaded from the CSV file."
        );
    }

    /**
     * Displays an alert when there is an error loading the CSV file.
     */
    public void onErrorLoadingCsv(String errorMessage) {
        showAlert("Error loading players",
              "An error occurred while loading players from the CSV file: "
                    + errorMessage
        );
    }

    public void onErrorSavingCsv(String errorMessage) {
        showAlert("Error saving players",
              "An error occurred while saving players to the CSV file: "
                    + errorMessage
        );
    }

    public void onSuccessfulCsvSave() {
        showAlert("Players saved successfully",
              "Players have been saved to the CSV file."
        );
    }

    /**
     * Displays an alert with the given title and content.
     *
     * @param title is the title of the alert.
     * @param content is the content of the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}