package edu.ntnu.idi.idatt.ui.view.laddersgame;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.ui.controller.laddersgame.LaddersGameController;
import edu.ntnu.idi.idatt.ui.view.View;
import edu.ntnu.idi.idatt.ui.view.components.DiceCanvas;
import edu.ntnu.idi.idatt.ui.view.components.PlayerTokenCanvas;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX view that renders the Snakes and Ladders board, player‑token layer,
 * dice tray and status area. The top section now mirrors the Monopoly view:
 * a game title, a clickable "User Manual" label, and a live status label.
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public class LaddersGameView implements View {
  private final BorderPane root;
  private final LaddersGameBoardCanvas boardCanvas;
  private final PlayerTokenCanvas playerTokenCanvas;
  private final DiceCanvas diceCanvas;
  private final StackPane boardWithTokens;
  private final Text titleLabel;
  private final Label manualLabel;
  private final Label statusLabel;
  private Button throwDiceButton;
  private LaddersGameController controller;
  private Board board;
  private String manualText;

  /**
   * Constructs a LaddersGameView instance.
   * Initializes the UI components and layout.
   */
  public LaddersGameView() {
    root = new BorderPane();
    boardCanvas = new LaddersGameBoardCanvas(500, 500);
    playerTokenCanvas = new PlayerTokenCanvas();
    diceCanvas = new DiceCanvas(100, 100);
    boardWithTokens = new StackPane();
    titleLabel = new Text("Ladders Game");
    manualLabel = new Label("Click for User Manual");
    statusLabel = new Label("Throw the dice to start the game!");
    manualText = "Unavailable.";

    boardCanvas.getStyleClass().add("board-canvas");
    root.setId("root-pane");
  }

  /**
   * Retrieves the root node of the view.
   *
   * @return the root node of the view.
   */
  @Override
  public Parent getRoot() {
    return root;
  }

  /**
   * Sets the controller for this view.
   *
   * @param controller is the controller to set for this view.
   */
  public void setController(LaddersGameController controller) {
    this.controller = controller;
  }

  /**
   * Initializes the view by creating and adding UI components.
   * Sets up the layout and styles for the game board.
   * <br>
   * <b>Note:</b> This method is called to set up the complete state of the view,
   * and should be called after the configuration of the game is complete.
   */
  @Override
  public void initializeView() {
    boardWithTokens.setPrefSize(500, 500);
    boardWithTokens.getChildren().addAll(boardCanvas, playerTokenCanvas.getTokenPane());

    Pane overlay = playerTokenCanvas.getTokenPane();
    double w = boardCanvas.getWidth();
    double h = boardCanvas.getHeight();
    overlay.setMinSize(w, h);
    overlay.setPrefSize(w, h);
    overlay.setMaxSize(w, h);

    titleLabel.getStyleClass().add("title-text");
    manualLabel.getStyleClass().add("manual-text");
    manualLabel.setOnMouseClicked(e -> showUserManual());

    VBox topBox = new VBox(10, titleLabel, manualLabel, statusLabel);
    topBox.getStyleClass().add("centered-container");
    root.setTop(topBox);

    VBox diceElementsContainer = new VBox(10);
    diceElementsContainer.setAlignment(Pos.CENTER);
    throwDiceButton = new Button("Throw Dice");
    throwDiceButton.setOnAction(event -> notifyDiceClicked());
    diceElementsContainer.getChildren().addAll(diceCanvas, throwDiceButton);
    root.setBottom(diceElementsContainer);

    root.setCenter(boardWithTokens);
    controller.initialize();
  }

  /**
   * Registers the board for the game.
   *
   * @param board the board to be registered.
   */
  public void registerBoard(Board board) {
    this.board = board;
    boardCanvas.drawBoard(board);
  }

  /**
   * Registers the player tokens on the board.
   *
   * @param playerName the name of the player.
   * @param tokenImagePath the path to the token image.
   */
  public void registerPlayerTokens(String playerName, String tokenImagePath) {
    playerTokenCanvas.addPlayerToken(playerName, tokenImagePath);
  }

  /**
   * Sets the status label text.
   *
   * @param message the message to be displayed on the status label.
   */
  public void setStatusLabel(String message) {
    statusLabel.setText(message);
  }

  /**
   * Updates the dice values on the canvas.
   *
   * @param value1 the first dice value.
   * @param value2 the second dice value.
   */
  public void updateDice(int value1, int value2) {
    diceCanvas.updateDice(value1, value2);
  }

  /**
   * Moves the player token to a new position on the board.
   *
   * @param playerName the name of the player.
   * @param x the x-coordinate of the new position.
   * @param y the y-coordinate of the new position.
   */
  public void movePlayerToken(String playerName, double x, double y) {
    PauseTransition afterDice = new PauseTransition(Duration.seconds(1));
    afterDice.setOnFinished(evt -> {
      playerTokenCanvas.animateTokenMovement(
            playerName, x, y, board.getRows(), board.getColumns()
      );
      Platform.runLater(() -> throwDiceButton.setDisable(false));
    });
    afterDice.play();
  }

  /**
   * Presents a message to the user when a player wins the game.
   *
   * @param playerName the name of the player who won.
   */
  public void onPlayerWon(String playerName) {
    statusLabel.setText(playerName + " has won the game!");
    throwDiceButton.setDisable(true);
    Alert a = new Alert(Alert.AlertType.INFORMATION,
          playerName + " has won Snakes & Ladders!\n\nCongratulations!");
    a.setHeaderText("Game Over");
    a.showAndWait();
  }

  /**
   * Displays an error message when the game cannot be initialized.
   *
   * @param errorMessage the error message to display.
   */
  public void onErrorInitializingGame(String errorMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR,
          "The game could not be initialized:\n" + errorMessage
                + "\nPlease restart the application and try again.");
    alert.setHeaderText("Error Initializing Game");
    alert.showAndWait();
  }

  /**
   * Notifies the controller when the dice are clicked.
   */
  private void notifyDiceClicked() {
    controller.onDiceClicked();
    throwDiceButton.setDisable(true);
  }

  /**
   * Displays the user manual in a dialog.
   */
  private void showUserManual() {
    Stage dlg = new Stage();
    dlg.setTitle("Snakes & Ladders: User Manual");
    TextArea ta = new TextArea(manualText);
    ta.setWrapText(true);
    ta.setEditable(false);
    dlg.setScene(new Scene(new StackPane(ta), 400, 500));
    dlg.initModality(Modality.WINDOW_MODAL);
    Stage owner = (Stage) root.getScene().getWindow();
    dlg.initOwner(owner);
    dlg.show();
  }

  /**
   * Sets the user manual text.
   *
   * @param txt the text to set as the user manual.
   */
  public void setUserManualText(String txt) {
    this.manualText = txt == null ? "Unavailable." : txt;
  }
}