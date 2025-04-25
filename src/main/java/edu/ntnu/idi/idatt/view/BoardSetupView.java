package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.controller.BoardSetupController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.File;

/**
 * Represents the view for setting up the game board. It is responsible for
 * displaying the available board options and allowing th e user to select one,
 * or load a board from a JSON file.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class BoardSetupView implements View {
    private final VBox root;
    private final ToggleGroup boardToggleGroup;
    private BoardSetupController controller;
    private final Label selectedBoardLabel;
    private final VBox boardSelectionBox;

    /**
     * Constructs a BoardSetupView instance.
     * Initializes the root layout and the toggle group for board options.
     */
    public BoardSetupView() {
        this.root = new VBox(10);
        this.boardToggleGroup = new ToggleGroup();
        this.boardSelectionBox = new VBox(10);
        this.selectedBoardLabel = new Label("No board selected");
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
    }

    /**
     * Sets the controller for this view.
     *
     * @param controller is the controller to set for this view.
     */
    public void setController(BoardSetupController controller) {
        this.controller = controller;
        controller.loadPredefinedBoards();
    }

    /**
     * Retrieves the root node of this view.
     *
     * @return the root node of this view.
     */
    @Override
    public Parent getRoot() {
        return root;
    }

    /**
     * Creates the initial layout of the view.
     */
    @Override
    public void initializeView() {
        Text title = new Text("Board Setup");
        title.getStyleClass().add("heading");
        Button loadJsonButton = new Button("Load Board Configuration from JSON");
        loadJsonButton.setOnAction(e -> onLoadJsonButtonClicked());
        boardSelectionBox.setAlignment(Pos.CENTER);
        Button startGameButton = new Button("Start Game");
        Button saveBoardButton = new Button("Save Board Configuration to JSON");
        saveBoardButton.setOnAction(e -> onSaveJsonButtonClicked());
        startGameButton.setOnAction(e -> controller.registerBoardSelection());
        root.getChildren().addAll(
                title,
                loadJsonButton,
                selectedBoardLabel,
                boardSelectionBox,
                startGameButton,
                saveBoardButton
        );
    }

    /**
     * Adds a board option to the view.
     *
     * @param boardName is the name of the board.
     * @param description is the description of the board.
     */
    public void addBoardOption(String boardName, String description) {
        RadioButton radioButton = new RadioButton(boardName);
        radioButton.setToggleGroup(boardToggleGroup);
        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        VBox boardOption = new VBox(5, radioButton, descriptionLabel);
        radioButton.setOnAction(e -> {
            controller.selectPredefinedBoard(boardName);
            selectedBoardLabel.setText("Selected: " + boardName);
        });
        boardOption.setAlignment(Pos.CENTER);
        boardSelectionBox.getChildren().add(boardOption);
    }

    /**
     * Handles the action when the "Load Board from JSON" button is clicked.
     */
    private void onLoadJsonButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Board JSON File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            controller.loadBoardFromJson(file);
        }
    }

    /**
     * Handles the action when the "Save Board to JSON" button is clicked.
     */
    private void onSaveJsonButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Board Configuration");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (file != null) {
            controller.saveBoardToJson(file);
        } else {
            showAlert("No File Selected",
                    "Please select a file to save the board configuration.");
        }
    }

    /**
     * Automatically fills the board selection with the loaded board's name and
     * description.
     *
     * @param boardName is the name of the board.
     */
    public void autoFillBoard(String boardName) {
        boardToggleGroup.selectToggle(null);
        selectedBoardLabel.setText("Selected: " + boardName + " (Loaded from JSON)");
    }

    /**
     * Displays an alert when no board is selected.
     */
    public void onNoBoardSelected() {
        showAlert("No Board Selected", "Please select a board before starting the game.");
    }

    /**
     * Displays an alert when there is an error loading the JSON file.
     *
     * @param errorMessage is the error message to display.
     */
    public void onErrorLoadingJson(String errorMessage) {
        showAlert("Error Loading JSON", errorMessage);
    }

    /**
     * Displays an alert when the board is saved successfully.
     */
    public void onBoardSaved() {
        showAlert("Board Saved", "The board configuration has been saved successfully.");
    }

    /**
     * Displays an alert when there is an error saving the board.
     *
     * @param errorMessage is the error message to display.
     */
    public void onErrorSavingBoard(String errorMessage) {
        showAlert("Error Saving Board",
                "The board configuration could not be saved: " + errorMessage);
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
