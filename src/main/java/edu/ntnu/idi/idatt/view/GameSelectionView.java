package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.controller.GameSelectionController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static edu.ntnu.idi.idatt.model.enums.GameType.LUDO;
import static edu.ntnu.idi.idatt.model.enums.GameType.SNAKES_AND_LADDERS;

public class GameSelectionView implements View {
  private final VBox root;
  private GameSelectionController controller;

  public GameSelectionView() {
    root = new VBox(10);
  }
  @Override
  public Parent getRoot() {
    return root;
  }

  @Override
  public void initializeView() {
    Text title = new Text("Game Selection View");
    title.getStyleClass().add("heading");
    Button snakesAndLaddersButton = new Button("Snakes and Ladders");
    Button ludoButton = new Button("Ludo");
    snakesAndLaddersButton.setOnAction(e -> controller.selectGame(SNAKES_AND_LADDERS));
    ludoButton.setOnAction(e -> controller.selectGame(LUDO));
    root.getChildren().addAll(title, snakesAndLaddersButton, ludoButton);
    root.setAlignment(Pos.CENTER);
  }

  public void setController(GameSelectionController controller) {
    this.controller = controller;
  }
}
