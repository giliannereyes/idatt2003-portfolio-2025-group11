package edu.ntnu.idi.idatt.ui.view;

import edu.ntnu.idi.idatt.ui.controller.BoardGameController;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.ui.view.components.BoardCanvas;
import edu.ntnu.idi.idatt.ui.view.components.DiceCanvas;
import edu.ntnu.idi.idatt.ui.view.components.PlayerTokenCanvas;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.nio.file.Path;

public class GameView implements View {
    private final BorderPane root;
    private final BoardCanvas boardCanvas;
    private BoardGameController controller;
    private final PlayerTokenCanvas playerTokenCanvas;
    private final DiceCanvas diceCanvas;
    private final StackPane boardWithTokens;
    private final Label statusLabel;
    private Button throwDiceButton;
    private Board board;

    public GameView() {
        root = new BorderPane();
        boardCanvas = new BoardCanvas(500,500);
        playerTokenCanvas = new PlayerTokenCanvas();
        diceCanvas = new DiceCanvas(100, 100);
        boardWithTokens = new StackPane();
        statusLabel = new Label("Throw the dice to start the game!");
        boardCanvas.getStyleClass().add("board-canvas");
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    public void setController(BoardGameController controller) {
        this.controller = controller;
    }

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
        StackPane boardWithTokens = new StackPane(boardCanvas, overlay);
        root.setTop(statusLabel);
        root.setCenter(boardWithTokens);
        root.setCenter(boardWithTokens);
        VBox diceElementsContainer = new VBox(10);
        diceElementsContainer.setAlignment(Pos.CENTER);
        throwDiceButton = new Button("Throw Dice");
        throwDiceButton.setOnAction(event -> notifyDiceClicked());
        diceElementsContainer.getChildren().addAll(diceCanvas, throwDiceButton);
        root.setBottom(diceElementsContainer);
        controller.initialize();
    }

    public void registerBoard(Board board) {
        this.board = board;
        boardCanvas.drawBoard(board);
    }

    public void registerPlayerTokens(String playerName, Path tokenImagePath) {
        playerTokenCanvas.addPlayerToken(playerName, tokenImagePath);
    }

    public void setStatusLabel(String message) {
        statusLabel.setText(message);
    }

    public void updateDice(int value1, int value2) {
        diceCanvas.updateDice(value1, value2);
    }

    public void movePlayerToken(String playerName, double x, double y) {
        PauseTransition afterDice = new PauseTransition(Duration.seconds(1.2));
        afterDice.setOnFinished(evt -> {
            playerTokenCanvas.animateTokenMovement(
                  playerName, x, y,
                  board.getRows(), board.getColumns()
            );
            Platform.runLater(() -> throwDiceButton.setDisable(false));
        });
        afterDice.play();
    }

    public void onPlayerWon(String playerName) {
        statusLabel.setText(playerName + " has won the game!");
        throwDiceButton.setDisable(true);
    }

    public void onErrorInitializingGame(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error Initializing Game");
        alert.setContentText("The game could not be initialized: "
              + errorMessage
              + "\nPlease restart the application and try again.");
    }

    private void notifyDiceClicked() {
        controller.onDiceClicked();
        throwDiceButton.setDisable(true);
    }
}
