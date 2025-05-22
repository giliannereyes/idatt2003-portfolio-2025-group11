package edu.ntnu.idi.idatt.ui.view.monopoly;

import edu.ntnu.idi.idatt.domain.entity.monopoly.PropertyRegistry;
import edu.ntnu.idi.idatt.ui.controller.monopoly.MonopolyController;
import edu.ntnu.idi.idatt.domain.entity.Board;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * A UI-based view for Monopoly with dynamic player info boxes.
 */
public class MonopolyGameView implements View {
  private final BorderPane root;
  private final MonopolyBoardCanvas boardCanvas;
  private final PlayerTokenCanvas playerTokenCanvas;
  private final DiceCanvas diceCanvas;
  private final StackPane boardWithTokens;
  private final Label statusLabel;
  private Button throwDiceButton;
  private Board board;
  private MonopolyController controller;
  private final Map<String, PlayerInfoBox> infoBoxes = new LinkedHashMap<>();
  private final VBox infoBoxLeft  = new VBox(10);
  private final VBox infoBoxRight = new VBox(10);
  private String manualText = "Unavailable.";
  private final Text titleLabel;

  public MonopolyGameView() {
    root = new BorderPane();
    boardCanvas = new MonopolyBoardCanvas(500, 500);
    playerTokenCanvas = new PlayerTokenCanvas();
    diceCanvas = new DiceCanvas(100, 100);
    boardWithTokens = new StackPane();
    statusLabel = new Label("Throw the dice to start Monopoly Lite!");
    titleLabel = new Text("Monopoly");
    boardCanvas.getStyleClass().add("board-canvas");
    titleLabel.getStyleClass().add("title-text");
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  public void setController(MonopolyController controller) {
    this.controller = controller;
  }

  @Override
  public void initializeView() {
    boardWithTokens.setPrefSize(500, 500);
    boardWithTokens.getChildren().addAll(boardCanvas, playerTokenCanvas.getTokenPane());
    VBox diceBox = new VBox(10);
    diceBox.setAlignment(Pos.CENTER);
    throwDiceButton = new Button("Throw Dice");
    throwDiceButton.setOnAction(e -> notifyDiceClicked());
    diceBox.getChildren().addAll(diceCanvas, throwDiceButton);
    titleLabel.setOnMouseClicked(e -> {
      showUserManual();
    });
    VBox topContainer = new VBox(10, titleLabel, statusLabel);
    topContainer.getStyleClass().add("centered-container");
    root.setTop(topContainer);
    root.setCenter(boardWithTokens);
    root.setLeft(infoBoxLeft);
    root.setRight(infoBoxRight);
    root.setBottom(diceBox);
    controller.initialize();
  }

  public void registerBoard(Board board) {
    this.board = board;
    boardCanvas.drawBoard(board, null);
  }

  /**
   * Adds a player's token and creates their info box.
   */
  public void registerPlayerToken(String playerName, String tokenPath) {
    createPlayerBox(playerName);
    updatePlayerBalance(playerName, "300.0");
    playerTokenCanvas.addPlayerToken(playerName, tokenPath);
  }

  public void setStatusLabel(String message) {
    statusLabel.setText(message);
  }

  public void createPlayerBox(String playerName) {
    PlayerInfoBox box = new PlayerInfoBox(playerName);
    infoBoxes.put(playerName, box);
    refreshInfoBoxesLayout();
  }

  public void updatePlayerBalance(String playerName, String balance) {
    PlayerInfoBox box = infoBoxes.get(playerName);
    if (box != null) {
      box.updateBalance(balance);
    }
  }

  public void addPropertyToPlayer(String playerName, String propertyName, String rent) {
    PlayerInfoBox box = infoBoxes.get(playerName);
    if (box != null) {
      box.addProperty(propertyName, rent);
    }
  }

  public void removeAllPropertiesFromPlayer(String playerName) {
    PlayerInfoBox box = infoBoxes.get(playerName);
    if (box != null) {
      box.removeAllProperties();
    }
  }

  /**
   * Splits your players between left and right side,
   * e.g. N=3 ⇒ 2 on the left, 1 on the right.
   */
  private void refreshInfoBoxesLayout() {
    infoBoxLeft .getChildren().clear();
    infoBoxRight.getChildren().clear();

    int total     = infoBoxes.size();
    int leftCount = (total + 1) / 2;  // e.g. 3⇒2, 4⇒2, 5⇒3

    int idx = 0;
    for (PlayerInfoBox box : infoBoxes.values()) {
      if (idx++ < leftCount) {
        infoBoxLeft.getChildren().add(box);
      } else {
        infoBoxRight.getChildren().add(box);
      }
    }
  }

  public void updateDice(int d1, int d2) {
    diceCanvas.updateDice(d1, d2);
  }

  public void movePlayerToken(String playerName, double x, double y) {
    PauseTransition after = new PauseTransition(Duration.seconds(1.2));
    after.setOnFinished(evt -> {
      playerTokenCanvas.animateTokenMovement(
            playerName, x, y,
            board.getRows(), board.getColumns()
      );
      Platform.runLater(() -> throwDiceButton.setDisable(false));
    });
    after.play();
  }

  public boolean promptYesNo(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.YES;
  }

  public void onPlayerWon(String playerName) {
    statusLabel.setText(playerName + " has won Monopoly Lite!");
    throwDiceButton.setDisable(true);
    System.out.println("🎉 " + playerName + " has won Monopoly Lite! 🎉");
  }

  public void onErrorInitializingGame(String errorMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText("Error Initializing Game");
    alert.setContentText(
          "The game could not be initialized: " + errorMessage +
                "\nPlease restart the application and try again."
    );
    alert.showAndWait();
  }

  public void setUserManualText(String text) {
    this.manualText = text;
  }

  private void notifyDiceClicked() {
    throwDiceButton.setDisable(true);
    controller.onDiceClicked();
  }

  private void showUserManual( ) {
    Stage manualStage = new Stage();
    manualStage.setTitle("Monopoly: User Manual");

    TextArea manualTextArea = new TextArea();
    manualTextArea.setText(manualText); // Load from a file or string
    manualTextArea.setWrapText(true);
    manualTextArea.setEditable(false);

    Scene scene = new Scene(new StackPane(manualTextArea), 400, 500);
    manualStage.setScene(scene);
    manualStage.show();
  }
}
