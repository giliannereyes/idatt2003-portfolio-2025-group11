package edu.ntnu.idi.idatt.ui.view.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.monopoly.PropertyRegistry;
import edu.ntnu.idi.idatt.ui.controller.monopoly.MonopolyController;
import edu.ntnu.idi.idatt.ui.view.View;
import edu.ntnu.idi.idatt.ui.view.components.DiceCanvas;
import edu.ntnu.idi.idatt.ui.view.components.PlayerTokenCanvas;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * JavaFX view that renders the Monopoly board, player-token layer,
 * dice tray and player information boxes.
 */
public class MonopolyView implements View {
  private final BorderPane         root            = new BorderPane();
  private final StackPane          boardWithTokens = new StackPane();
  private final VBox               infoBoxLeft     = new VBox(10);
  private final VBox               infoBoxRight    = new VBox(10);
  private final Label              statusLabel     = new Label("Throw the dice to start playing Monopoly!");
  private final Text               titleLabel      = new Text("Monopoly");
  private final DiceCanvas         diceCanvas      = new DiceCanvas(100, 100);
  private final Button             throwDiceButton = new Button("Throw Dice");
  private final PlayerTokenCanvas  playerTokenCanvas;
  private final Label manualLabel;
  private final MonopolyBoardCanvas boardCanvas;
  private final Map<String, PlayerInfoBox> infoBoxes = new LinkedHashMap<>();
  private       MonopolyController         controller;
  private       Board                      board;
  private       String                     manualText = "Unavailable.";

  /** Creates the view with a fixed 500 × 500 px board area. */
  public MonopolyView() {
    final double boardPx = 550;
    boardCanvas       = new MonopolyBoardCanvas(boardPx, boardPx);
    playerTokenCanvas = new PlayerTokenCanvas();
    manualLabel = new Label("Click for User Manual");
    boardWithTokens.setPrefSize(boardPx, boardPx);
    boardWithTokens.getChildren().addAll(boardCanvas,
          playerTokenCanvas.getTokenPane());

    throwDiceButton.setOnAction(e -> notifyDiceClicked());

    titleLabel.getStyleClass().add("title-text");
    boardCanvas.getStyleClass().add("board-canvas");
    root.setId("root-pane");
  }

  /* ---------- View ---------------------------------------------------------------- */

  @Override public Parent getRoot() { return root; }

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
   * Draws the board and resizes the token pane to exactly match the
   * board-canvas pixel area.
   */
  public void registerBoard(Board board, PropertyRegistry registry) {
    this.board = board;
    boardCanvas.drawBoard(board, registry);

    double w = boardCanvas.getWidth();
    double h = boardCanvas.getHeight();
    playerTokenCanvas.getTokenPane().setMinSize (w, h);
    playerTokenCanvas.getTokenPane().setPrefSize(w, h);
    playerTokenCanvas.getTokenPane().setMaxSize (w, h);
  }

  /** Adds a player token to the token layer and creates an info box. */
  public void registerPlayerToken(String playerName, String tokenPath) {
    createPlayerBox(playerName);
    updatePlayerBalance(playerName, "300.0");
    playerTokenCanvas.addPlayerToken(playerName, tokenPath);
  }

  /** Animates a token to the specified grid coordinate after a short delay. */
  public void movePlayerToken(String playerName, double gridX, double gridY) {
    PauseTransition pt = new PauseTransition(Duration.seconds(1.2));
    pt.setOnFinished(e -> {
      playerTokenCanvas.animateTokenMovement(
            playerName, gridX, gridY,
            board.getRows(), board.getColumns());
      Platform.runLater(() -> throwDiceButton.setDisable(false));
    });
    pt.play();
  }

  public void updateDice(int d1, int d2)        { diceCanvas.updateDice(d1, d2); }
  public void setStatusLabel(String msg)        { statusLabel.setText(msg);      }
  public void setController(MonopolyController c){ this.controller = c;          }

  /* ---------- Player-info utilities --------------------------------------------- */

  private void createPlayerBox(String player) {
    infoBoxes.put(player, new PlayerInfoBox(player));
    refreshInfoBoxesLayout();
  }
  private void refreshInfoBoxesLayout() {
    infoBoxLeft .getChildren().clear();
    infoBoxRight.getChildren().clear();
    int leftCount = (infoBoxes.size() + 1) / 2;
    int i = 0;
    for (PlayerInfoBox box : infoBoxes.values())
      (i++ < leftCount ? infoBoxLeft : infoBoxRight).getChildren().add(box);
  }

  public void updatePlayerBalance(String p, String bal){
    Optional.ofNullable(infoBoxes.get(p)).ifPresent(b -> b.updateBalance(bal));
  }
  public void addPropertyToPlayer(String p,String prop,String rent){
    Optional.ofNullable(infoBoxes.get(p)).ifPresent(b -> b.addProperty(prop,rent));
  }
  public void removeAllPropertiesFromPlayer(String p){
    Optional.ofNullable(infoBoxes.get(p)).ifPresent(PlayerInfoBox::removeAllProperties);
  }

  public boolean promptYesNo(String title, String msg) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg,
          ButtonType.YES, ButtonType.NO);
    alert.setTitle(title);
    alert.setHeaderText("Property Purchase");
    Stage owner = (Stage) root.getScene().getWindow();
    alert.initOwner(owner);
    alert.initModality(Modality.WINDOW_MODAL);
    return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
  }

  public void onPlayerWon(String player) {
    statusLabel.setText(player + " has won Monopoly!");
    throwDiceButton.setDisable(true);
    Alert a = new Alert(Alert.AlertType.INFORMATION,
          player + " has won Monopoly!\n\n" +
                "Congratulations!");
  }

  public void onErrorInitializingGame(String msg) {
    Alert a = new Alert(Alert.AlertType.ERROR,
          "The game could not be initialized:\n" + msg +
                "\nPlease restart and try again.");
    a.setHeaderText("Error Initializing Game");
    a.showAndWait();
  }

  private void notifyDiceClicked() {
    throwDiceButton.setDisable(true);
    controller.onDiceClicked();
  }

  private void showUserManual() {
    Stage dlg = new Stage();
    dlg.setTitle("Monopoly: User Manual");
    TextArea ta = new TextArea(manualText);
    ta.setWrapText(true);
    ta.setEditable(false);
    dlg.setScene(new Scene(new StackPane(ta), 400, 500));
    dlg.show();
  }

  public void setUserManualText(String txt) { this.manualText = txt; }
}
