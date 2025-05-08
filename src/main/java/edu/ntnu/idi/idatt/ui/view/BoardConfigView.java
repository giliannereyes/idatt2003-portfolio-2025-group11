package edu.ntnu.idi.idatt.ui.view;

import edu.ntnu.idi.idatt.ui.controller.BoardConfigController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.Objects;

/**
 * Represents the view for setting up the game board. It is responsible for
 * displaying the available board options and allowing th e user to select one,
 * or load a board from a JSON file.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class BoardConfigView implements View {
  protected final VBox root;
  protected final ToggleGroup boardToggleGroup;
  protected BoardConfigController<?> controller;
  protected Label selectedBoardLabel;
  private final VBox boardSelectionBox;
  private final Button startGameButton;
  private final Text titleText;
  private final Text descriptionText;
  private final Text placeholderText;

  /**
   * Constructs a BoardSetupView instance.
   * Initializes the root layout and the toggle group for board options.
   */
  public BoardConfigView() {
    root = new VBox(10);
    boardToggleGroup = new ToggleGroup();
    boardSelectionBox = new VBox(10);
    selectedBoardLabel = new Label("Board selected: None");
    startGameButton = new Button("Start Game");
    titleText = new Text("Board Configuration");
    descriptionText = new Text("Select a board configuration for the game.");
    placeholderText = new Text("An error occurred while loading the board options!");
    boardSelectionBox.getChildren().add(placeholderText);
    root.getStylesheets()
          .add(Objects.requireNonNull(getClass()
          .getResource("/css/style.css"))
          .toExternalForm());
  }

  /**
   * Sets the controller for this view.
   *
   * @param controller is the controller to set for this view.
   */
  public void setController(BoardConfigController<?> controller) {
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
    root.setPadding(new Insets(20));
    root.setAlignment(Pos.CENTER);
    boardSelectionBox.setAlignment(Pos.CENTER);
    startGameButton.setOnAction(e -> controller.registerBoardSelection());
    root.getChildren().addAll(
          titleText,
          descriptionText,
          selectedBoardLabel,
          boardSelectionBox,
          startGameButton
    );
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
    Label descriptionLabel = new Label(description);
    descriptionLabel.setWrapText(true);
    VBox boardOption = new VBox(5, radioButton, descriptionLabel);
    radioButton.setOnAction(e -> {
      controller.selectPredefinedBoard(boardName);
      selectedBoardLabel.setText("Board Selected: " + boardName);
    });
    boardOption.setAlignment(Pos.CENTER);
    boardSelectionBox.getChildren().add(boardOption);
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
}
