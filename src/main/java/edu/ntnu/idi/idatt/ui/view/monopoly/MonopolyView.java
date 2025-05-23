package edu.ntnu.idi.idatt.ui.view.monopoly;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.monopoly.PropertyRegistry;
import edu.ntnu.idi.idatt.ui.controller.monopoly.MonopolyController;
import edu.ntnu.idi.idatt.ui.view.View;
import edu.ntnu.idi.idatt.ui.view.components.DiceCanvas;
import edu.ntnu.idi.idatt.ui.view.components.PlayerTokenCanvas;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * MonopolyView is a JavaFX class that represents the graphical
 * user interface for the Monopoly game. It provides methods to
 * initialize the view, register players, update the game state,
 * and handle user interactions.
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public class MonopolyView implements View {
  private final BorderPane root;
  private final StackPane boardWithTokens;
  private final VBox infoBoxLeft;
  private final VBox infoBoxRight;
  private final Label statusLabel;
  private final Text titleLabel;
  private final DiceCanvas diceCanvas;
  private final Button throwDiceButton;
  private final PlayerTokenCanvas playerTokenCanvas;
  private final Label manualLabel;
  private final MonopolyBoardCanvas boardCanvas;
  private final Map<String, PlayerInfoBox> infoBoxes;
  private MonopolyController controller;
  private Board board;
  private String manualText;

  /**
   * Constructs a MonopolyView instance. Initializes the
   * layout components and sets up the initial state.
   */
  public MonopolyView() {
    root = new BorderPane();
    boardWithTokens = new StackPane();
    infoBoxLeft = new VBox(10);
    infoBoxRight = new VBox(10);
    statusLabel = new Label("Throw the dice to start playing Monopoly!");
    titleLabel = new Text("Monopoly");
    diceCanvas = new DiceCanvas(100, 100);
    throwDiceButton = new Button("Throw Dice");
    playerTokenCanvas = new PlayerTokenCanvas();
    manualLabel = new Label("(?) Click for User Manual");
    boardCanvas = new MonopolyBoardCanvas(400, 400);
    infoBoxes = new LinkedHashMap<>();
    manualText = "Unavailable.";

    boardWithTokens.setPrefSize(400, 400);
    boardWithTokens.getChildren().addAll(boardCanvas, playerTokenCanvas.getTokenPane());

    throwDiceButton.setOnAction(e -> notifyDiceClicked());

    titleLabel.getStyleClass().add("title-text");
    boardCanvas.getStyleClass().add("board-canvas");
    root.setId("root-pane");
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
   * Initializes the view by setting up the layout, including
   * the board, player tokens, and other UI components.
   * <b>This method should be called after the {@link GameConfig}
   * is complete.</b>
   */
  @Override
  public void initializeView() {
    VBox diceBox = new VBox(10, diceCanvas, throwDiceButton);
    diceBox.setAlignment(Pos.CENTER);

    VBox topBox = new VBox(10, titleLabel, manualLabel, statusLabel);
    topBox.getStyleClass().add("centered-container");
    manualLabel.setOnMouseClicked(e -> showUserManual());
    manualLabel.getStyleClass().add("manual-text");
    infoBoxLeft.setMinWidth(250);
    infoBoxRight.setMinWidth(250);

    root.setTop(topBox);
    root.setCenter(boardWithTokens);
    root.setLeft(infoBoxLeft);
    root.setRight(infoBoxRight);
    root.setBottom(diceBox);

    controller.initialize();
  }

  /**
   * Registers a board with the view and updates the UI accordingly.
   *
   * @param board the game board to be registered.
   * @param registry the property registry for board properties.
   */
  public void registerBoard(Board board, PropertyRegistry registry) {
    this.board = board;
    boardCanvas.drawBoard(board, registry);

    double w = boardCanvas.getWidth();
    double h = boardCanvas.getHeight();
    playerTokenCanvas.getTokenPane().setMinSize(w, h);
    playerTokenCanvas.getTokenPane().setPrefSize(w, h);
    playerTokenCanvas.getTokenPane().setMaxSize(w, h);
  }

  /**
   * Registers a player token with the view and updates the UI.
   *
   * @param playerName the name of the player.
   * @param tokenPath the path to the player's token image.
   */
  public void registerPlayerToken(String playerName, String tokenPath) {
    createPlayerBox(playerName);
    updatePlayerBalance(playerName, "300.0");
    playerTokenCanvas.addPlayerToken(playerName, tokenPath);
  }

  /**
   * Moves the player token to a new position on the board.
   *
   * @param playerName the name of the player.
   * @param gridX the x-coordinate of the new position.
   * @param gridY the y-coordinate of the new position.
   */
  public void movePlayerToken(String playerName, double gridX, double gridY) {
    PauseTransition pt = new PauseTransition(Duration.seconds(1));
    pt.setOnFinished(e -> {
      playerTokenCanvas
            .animateTokenMovement(playerName, gridX, gridY, board.getRows(), board.getColumns());
      Platform.runLater(() -> throwDiceButton.setDisable(false));
    });
    pt.play();
  }

  /**
   * Updates the dice values displayed on the UI.
   *
   * @param d1 the value of the first die.
   * @param d2 the value of the second die.
   */
  public void updateDice(int d1, int d2) {
    diceCanvas.updateDice(d1, d2);
  }

  /**
   * Sets the status label text on the UI.
   *
   * @param msg the message to be displayed.
   */
  public void setStatusLabel(String msg) {
    statusLabel.setText(msg);
  }

  /**
   * Sets the controller for this view.
   *
   * @param c the controller to be set.
   */
  public void setController(MonopolyController c) {
    this.controller = c;
  }

  /**
   * Creates a player info box for the specified player.
   *
   * @param player the name of the player.
   */
  private void createPlayerBox(String player) {
    infoBoxes.put(player, new PlayerInfoBox(player));
    refreshInfoBoxesLayout();
  }

  /**
   * Refreshes the layout of the info boxes to ensure they are
   * displayed correctly in the UI.
   */
  private void refreshInfoBoxesLayout() {
    infoBoxLeft.getChildren().clear();
    infoBoxRight.getChildren().clear();
    int leftCount = (infoBoxes.size() + 1) / 2;
    int i = 0;
    for (PlayerInfoBox box : infoBoxes.values()) {
      (i++ < leftCount ? infoBoxLeft : infoBoxRight).getChildren().add(box);
    }
  }

  /**
   * Updates the player balance in the info box.
   *
   * @param p the name of the player.
   * @param bal the new balance to be set.
   */
  public void updatePlayerBalance(String p, String bal) {
    Optional.ofNullable(infoBoxes.get(p)).ifPresent(b -> b.updateBalance(bal));
  }

  /**
   * Adds a property to the player's info box.
   *
   * @param p the name of the player.
   * @param prop the name of the property.
   * @param rent the rent value of the property.
   */
  public void addPropertyToPlayer(String p, String prop, String rent) {
    Optional.ofNullable(infoBoxes.get(p)).ifPresent(b -> b.addProperty(prop, rent));
  }

  /**
   * Removes all properties from the player's info box.
   *
   * @param p the name of the player.
   */
  public void removeAllPropertiesFromPlayer(String p) {
    Optional.ofNullable(infoBoxes.get(p)).ifPresent(PlayerInfoBox::removeAllProperties);
  }

  /**
   * Prompts the user with a yes/no dialog.
   *
   * @param title the title of the dialog.
   * @param msg the message to be displayed.
   * @return true if the user selects "Yes", false otherwise.
   */
  public boolean promptYesNo(String title, String msg) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
    alert.setTitle(title);
    alert.setHeaderText("Property Purchase");
    Stage owner = (Stage) root.getScene().getWindow();
    alert.initOwner(owner);
    alert.initModality(Modality.WINDOW_MODAL);
    alert.setOnShown(evt -> {
      DialogPane pane = alert.getDialogPane();
      pane.setPrefWidth(270);
      pane.setPrefHeight(200);
    });
    return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
  }

  /**
   * Displays an error message when a player has won the game.
   *
   * @param player the name of the winning player.
   */
  public void onPlayerWon(String player) {
    statusLabel.setText(player + " has won Monopoly!");
    throwDiceButton.setDisable(true);
    Alert alert = new Alert(
          Alert.AlertType.INFORMATION, player + " has won Monopoly!\n\nCongratulations!");
    alert.setHeaderText("Game Over!");
    alert.showAndWait();
  }

  /**
   * Displays an error message when the game could not be initialized.
   *
   * @param msg the error message to be displayed.
   */
  public void onErrorInitializingGame(String msg) {
    Alert a = new Alert(
          Alert.AlertType.ERROR, "The game could not be initialized:\n" + msg
          + "\nPlease restart and try again.");
    a.setHeaderText("Error Initializing Game");
    a.showAndWait();
  }

  /**
   * Notifies the controller that the dice button has been clicked.
   */
  private void notifyDiceClicked() {
    throwDiceButton.setDisable(true);
    controller.onDiceClicked();
  }

  /**
   * Displays the user manual in a modal dialog window.
   */
  private void showUserManual() {
    Stage dlg = new Stage();
    dlg.setTitle("Monopoly: User Manual");
    TextArea ta = new TextArea(manualText);
    ta.setWrapText(true);
    ta.setEditable(false);
    dlg.setScene(new Scene(new StackPane(ta), 400, 500));
    dlg.show();
  }

  /**
   * Sets the user manual text to be displayed in the dialog.
   *
   * @param txt the user manual text.
   */
  public void setUserManualText(String txt) {
    this.manualText = txt;
  }
}