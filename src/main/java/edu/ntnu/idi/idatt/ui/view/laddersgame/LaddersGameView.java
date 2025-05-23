package edu.ntnu.idi.idatt.ui.view.laddersgame;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.ui.controller.BoardGameController;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX view that renders the Snakes and Ladders board, player‑token layer,
 * dice tray and status area.  The top section now mirrors the Monopoly view:
 * a game title, a clickable "User Manual" label, and a live status label.
 */
public class LaddersGameView implements View {
    /* --------------------------------------------------------------
     * FXML‑style fields & components
     * ----------------------------------------------------------- */
    private final BorderPane         root              = new BorderPane();
    private final LaddersGameBoardCanvas boardCanvas;
    private final PlayerTokenCanvas  playerTokenCanvas = new PlayerTokenCanvas();
    private final DiceCanvas         diceCanvas        = new DiceCanvas(100, 100);
    private final StackPane          boardWithTokens   = new StackPane();

    // ― Top‑box elements (copied from MonopolyView) ―
    private final Text               titleLabel   = new Text("Snakes & Ladders");
    private final Label              manualLabel  = new Label("Click for User Manual");
    private final Label              statusLabel  = new Label("Throw the dice to start the game!");

    private Button                  throwDiceButton;
    private BoardGameController     controller;
    private Board                   board;

    /** Holds the text shown when the user opens the manual dialog. */
    private String manualText = "Unavailable.";

    /* --------------------------------------------------------------
     * Construction & initialisation
     * ----------------------------------------------------------- */
    public LaddersGameView() {
        boardCanvas = new LaddersGameBoardCanvas(500, 500);
        boardCanvas.getStyleClass().add("board-canvas");

        root.setId("root-pane");
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
        /* ---------- Board area ------------------------------------------------ */
        boardWithTokens.setPrefSize(500, 500);
        boardWithTokens.getChildren().addAll(boardCanvas, playerTokenCanvas.getTokenPane());

        // Ensure the overlay exactly matches the board‑canvas size
        Pane overlay = playerTokenCanvas.getTokenPane();
        double w = boardCanvas.getWidth();
        double h = boardCanvas.getHeight();
        overlay.setMinSize(w, h);
        overlay.setPrefSize(w, h);
        overlay.setMaxSize(w, h);

        /* ---------- Top‑box (title / manual / status) ------------------------- */
        titleLabel.getStyleClass().add("title-text");
        manualLabel.getStyleClass().add("manual-text");
        manualLabel.setOnMouseClicked(e -> showUserManual());

        VBox topBox = new VBox(10, titleLabel, manualLabel, statusLabel);
        topBox.getStyleClass().add("centered-container");
        root.setTop(topBox);

        /* ---------- Dice area -------------------------------------------------- */
        VBox diceElementsContainer = new VBox(10);
        diceElementsContainer.setAlignment(Pos.CENTER);
        throwDiceButton = new Button("Throw Dice");
        throwDiceButton.setOnAction(event -> notifyDiceClicked());
        diceElementsContainer.getChildren().addAll(diceCanvas, throwDiceButton);
        root.setBottom(diceElementsContainer);

        /* ---------- Main layout ----------------------------------------------- */
        root.setCenter(boardWithTokens);

        controller.initialize();
    }

    /* --------------------------------------------------------------
     * Public API used by the controller
     * ----------------------------------------------------------- */
    public void registerBoard(Board board) {
        this.board = board;
        boardCanvas.drawBoard(board);
    }

    public void registerPlayerTokens(String playerName, String tokenImagePath) {
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
        Alert a = new Alert(Alert.AlertType.INFORMATION,
              playerName + " has won Snakes & Ladders!\n\nCongratulations!");
        a.setHeaderText("Game Over");
        a.showAndWait();
    }

    public void onErrorInitializingGame(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR,
              "The game could not be initialized:\n" + errorMessage +
                    "\nPlease restart the application and try again.");
        alert.setHeaderText("Error Initializing Game");
        alert.showAndWait();
    }

    /* --------------------------------------------------------------
     * Private helpers
     * ----------------------------------------------------------- */
    private void notifyDiceClicked() {
        controller.onDiceClicked();
        throwDiceButton.setDisable(true);
    }

    /** Displays the user manual in a modal dialog window. */
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

    /** Allows the controller to inject manual text loaded from a resource file. */
    public void setUserManualText(String txt) {
        this.manualText = txt == null ? "Unavailable." : txt;
    }
}
