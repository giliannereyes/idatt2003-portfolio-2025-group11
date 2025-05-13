package edu.ntnu.idi.idatt.ui.view;

import edu.ntnu.idi.idatt.ui.controller.GameSelectionController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import static edu.ntnu.idi.idatt.domain.enums.GameType.MONOPOLY;
import static edu.ntnu.idi.idatt.domain.enums.GameType.SNAKES_AND_LADDERS;

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
    Button ludoButton = new Button("Monopoly");
    snakesAndLaddersButton.setOnAction(e -> controller.selectGame(SNAKES_AND_LADDERS));
    ludoButton.setOnAction(e -> controller.selectGame(MONOPOLY));
    root.getChildren().addAll(title, snakesAndLaddersButton, ludoButton);
    root.setAlignment(Pos.CENTER);
  }

  public void setController(GameSelectionController controller) {
    this.controller = controller;
  }
}
