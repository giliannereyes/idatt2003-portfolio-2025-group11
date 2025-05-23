package edu.ntnu.idi.idatt.ui.view;

import static edu.ntnu.idi.idatt.domain.game.GameType.LADDERS_GAME;
import static edu.ntnu.idi.idatt.domain.game.GameType.MONOPOLY;

import edu.ntnu.idi.idatt.ui.controller.GameSelectionController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Represents the view for selecting a game to play. It provides buttons for
 * selecting different games and displays an alert when a game is selected.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class GameSelectionView implements View {
  private final VBox root;
  private GameSelectionController controller;

  /**
   * Constructs a GameSelectionView instance.
   * Initializes the root layout and sets its properties.
   */
  public GameSelectionView() {
    root = new VBox(20); // Increased spacing between elements
    root.setPadding(new Insets(40));
    root.setMaxWidth(500);
    root.setMinHeight(500);
    root.getStyleClass().add("centered-container");
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
   * Initializes the view by creating and adding UI components.
   * Sets up the layout and styles for the game selection screen.
   */
  @Override
  public void initializeView() {
    Text title = new Text("Digital Board Games");
    title.getStyleClass().add("title-text");
    Text attribution = new Text("By Gilianne & Trang");
    attribution.getStyleClass().add("attribution-text");
    Text selectPrompt = new Text("Select a Game:");
    selectPrompt.getStyleClass().add("subtitle-text");

    Button snakesAndLaddersButton = new Button("Ladders Game");
    snakesAndLaddersButton.getStyleClass().add("primary-button");
    snakesAndLaddersButton.setOnAction(e -> controller.selectGame(LADDERS_GAME));

    Button monopolyButton = new Button("Monopoly");
    monopolyButton.getStyleClass().add("primary-button");
    monopolyButton.setOnAction(e -> controller.selectGame(MONOPOLY));

    HBox buttonBox = new HBox(20);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(
          snakesAndLaddersButton,
          monopolyButton
    );
    VBox.setMargin(buttonBox, new Insets(15, 0, 0, 0));

    root.getChildren().addAll(
          title,
          attribution,
          selectPrompt,
          buttonBox
    );
    root.setAlignment(Pos.CENTER);
  }

  /**
   * Sets the controller for this view.
   *
   * @param controller is the controller to set for this view.
   */
  public void setController(GameSelectionController controller) {
    this.controller = controller;
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