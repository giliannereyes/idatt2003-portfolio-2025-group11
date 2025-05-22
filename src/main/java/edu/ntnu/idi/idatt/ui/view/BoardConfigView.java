package edu.ntnu.idi.idatt.ui.view;

import edu.ntnu.idi.idatt.ui.controller.BoardConfigController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.File;

/**
 * Represents the view for setting up the game board. It is responsible for
 * displaying the available board options and allowing the user to select one,
 * or load a board from a JSON file.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class BoardConfigView implements View {
  protected final VBox root;
  protected final ToggleGroup boardToggleGroup;
  protected BoardConfigController controller;
  protected Label selectedBoardLabel;
  private final VBox boardSelectionBox;
  private final Button startGameButton;
  private final Text titleText;
  private final Text placeholderText;
  private Button loadBoardButton;
  private Button saveBoardButton;
  private VBox fileSection = new VBox(10);
  private boolean fileHandlingDisabled = false;

  /**
   * Constructs a BoardSetupView instance.
   * Initializes the root layout and the toggle group for board options.
   */
  public BoardConfigView() {
    root = new VBox(25);
    boardToggleGroup = new ToggleGroup();
    boardSelectionBox = new VBox(15);
    selectedBoardLabel = new Label("Board selected: None");
    startGameButton = new Button("Start Game");
    titleText = new Text("Board Configuration");
    placeholderText = new Text("An error occurred while loading the board options!");
    loadBoardButton = new Button("Load Board from JSON");
    saveBoardButton = new Button("Save Board Configuration");
    boardSelectionBox.getChildren().add(placeholderText);
    root.getStyleClass().add("centered-container");
    root.setId("root-pane");
  }

  /**
   * Sets the controller for this view.
   *
   * @param controller is the controller to set for this view.
   */
  public void setController(BoardConfigController controller) {
    this.controller = controller;
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
    root.setPadding(new Insets(30));
    root.setAlignment(Pos.CENTER);
    setupComponentStyling();
    VBox headerSection = createHeaderSection();
    fileSection = createFileSection();
    VBox selectionSection = createSelectionSection();
    VBox actionSection = createActionSection();
    root.getChildren().addAll(
          headerSection,
          fileSection,
          selectionSection,
          actionSection
    );
    startGameButton.setOnAction(e -> controller.registerBoardSelection());
    loadBoardButton.setOnAction(e -> onLoadFromFile());
    saveBoardButton.setOnAction(e -> onSaveToFile());
    if (fileHandlingDisabled) {
      hideFileSection();
    }
  }

  public void disableFileHandling() {
    fileHandlingDisabled = true;
  }

  private void hideFileSection() {
    if (fileSection == null) return;
    fileSection.setVisible(false);
    fileSection.setManaged(false);
    loadBoardButton.setVisible(false);
    loadBoardButton.setManaged(false);
    saveBoardButton.setVisible(false);
    saveBoardButton.setManaged(false);
  }

  /**
   * Sets up styling for all components
   */
  private void setupComponentStyling() {
    titleText.getStyleClass().add("title-text");
    placeholderText.getStyleClass().add("alternative-text");

    startGameButton.getStyleClass().add("primary-button");

    selectedBoardLabel.getStyleClass().add("section-label");

    boardSelectionBox.getStyleClass().addAll("board-selection-container", "player-grid");
    boardSelectionBox.setAlignment(Pos.CENTER);
  }

  /**
   * Creates the header section with title and description
   */
  private VBox createHeaderSection() {
    VBox headerSection = new VBox();
    headerSection.setAlignment(Pos.CENTER);
    headerSection.getChildren().addAll(titleText);
    return headerSection;
  }

  /**
   * Creates the file operations section
   */
  private VBox createFileSection() {
    VBox fileSection = new VBox(10);
    fileSection.setAlignment(Pos.CENTER);
    Text fileOptionsText = new Text("Option: Load Custom Board");
    fileOptionsText.getStyleClass().add("alternative-text");
    fileSection.getChildren().addAll(fileOptionsText, loadBoardButton);
    VBox.setMargin(fileSection, new Insets(15, 0, 15, 0));
    return fileSection;
  }

  /**
   * Creates the board selection section
   */
  private VBox createSelectionSection() {
    VBox selectionSection = new VBox(10);
    selectionSection.setAlignment(Pos.CENTER);
    Text selectionOptionsText = new Text("Option: Choose Predefined Board");
    selectionOptionsText.getStyleClass().add("alternative-text");
    selectedBoardLabel.getStyleClass().add("status-label");
    selectionSection.getChildren().addAll(
          selectionOptionsText,
          selectedBoardLabel,
          boardSelectionBox
    );
    VBox.setMargin(selectionSection, new Insets(15, 0, 10, 0));
    return selectionSection;
  }

  /**
   * Creates the action buttons section
   */
  private VBox createActionSection() {
    VBox actionSection = new VBox(15);
    actionSection.setAlignment(Pos.CENTER);
    HBox saveContainer = new HBox();
    saveContainer.setAlignment(Pos.CENTER);
    saveContainer.getChildren().add(saveBoardButton);
    actionSection.getChildren().addAll(saveContainer, startGameButton);
    VBox.setMargin(actionSection, new Insets(20, 0, 0, 0));
    return actionSection;
  }

  /**
   * Adds a board option to the view.
   *
   * @param boardName is the name of the board.
   * @param description is the description of the board.
   */
  public void addBoardOption(String boardName, String description) {
    boardSelectionBox.getChildren().remove(placeholderText);
    RadioButton radioButton = new RadioButton(boardName);
    radioButton.setToggleGroup(boardToggleGroup);
    radioButton.getStyleClass().add("board-radio-button");
    Label descriptionLabel = new Label(description);
    descriptionLabel.setWrapText(true);
    descriptionLabel.getStyleClass().add("board-description");
    VBox boardOption = new VBox(8);
    boardOption.getStyleClass().add("board-option-container");
    boardOption.setAlignment(Pos.CENTER);
    boardOption.getChildren().addAll(radioButton, descriptionLabel);

    radioButton.setOnAction(e -> {
      controller.selectPredefinedBoard(boardName);
      selectedBoardLabel.setText("Board Selected: " + boardName);
    });
    boardSelectionBox.getChildren().add(boardOption);
  }

  public void updateSelectedBoard(String boardName) {
    boardToggleGroup.selectToggle(null);
    selectedBoardLabel.setText("Selected: " + boardName);
  }

  /**
   * Displays an alert with the given title and content.
   *
   * @param title is the title of the alert.
   * @param content is the content of the alert.
   */
  public void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  private void onLoadFromFile() {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Load Board JSON File");
    chooser.getExtensionFilters().add(
          new FileChooser.ExtensionFilter("JSON Files", "*.json")
    );
    File file = chooser.showOpenDialog(getRoot().getScene().getWindow());
    if (file != null) {
      controller.loadBoardConfiguration(file);
    }
  }

  private void onSaveToFile() {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Save Board Configuration");
    chooser.getExtensionFilters().add(
          new FileChooser.ExtensionFilter("JSON Files", "*.json")
    );
    File file = chooser.showSaveDialog(getRoot().getScene().getWindow());
    if (file != null) {
      controller.saveBoardConfiguration(file);
    }
  }
}