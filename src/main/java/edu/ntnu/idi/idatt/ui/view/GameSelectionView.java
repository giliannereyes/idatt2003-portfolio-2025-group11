package edu.ntnu.idi.idatt.ui.view;

import static edu.ntnu.idi.idatt.domain.enums.GameType.MONOPOLY;
import static edu.ntnu.idi.idatt.domain.enums.GameType.SNAKES_AND_LADDERS;

import edu.ntnu.idi.idatt.ui.controller.GameSelectionController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameSelectionView implements View {
  private final VBox root;
  private GameSelectionController controller;

  public GameSelectionView() {
    root = new VBox(20); // Increased spacing between elements
    root.setPadding(new Insets(40));
    root.setMaxWidth(500);
    root.setMinHeight(500);
    root.getStyleClass().add("centered-container");
    root.setId("root-pane");
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  @Override
  public void initializeView() {
    Text title = new Text("Digital Board Games");
    title.getStyleClass().add("title-text");
    Text attribution = new Text("By Gilianne & Trang");
    attribution.getStyleClass().add("attribution-text");
    Text selectPrompt = new Text("Select a Game:");
    selectPrompt.getStyleClass().add("subtitle-text");

    Button snakesAndLaddersButton = new Button("Snakes and Ladders");
    snakesAndLaddersButton.getStyleClass().add("primary-button");
    snakesAndLaddersButton.setOnAction(e -> controller.selectGame(SNAKES_AND_LADDERS));

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

  public void setController(GameSelectionController controller) {
    this.controller = controller;
  }
}