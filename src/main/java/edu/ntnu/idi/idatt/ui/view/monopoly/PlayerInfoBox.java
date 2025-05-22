package edu.ntnu.idi.idatt.ui.view.monopoly;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlayerInfoBox extends VBox {
  private final Label balanceLabel;
  private final VBox propertiesBox;

  public PlayerInfoBox(String playerName) {
    getStyleClass().add("player-grid");
    setSpacing(5);
    Label nameLabel = new Label(playerName);
    balanceLabel = new Label("Balance: $0");
    Label propertiesLabel = new Label("Owned properties:");
    propertiesBox = new VBox();
    nameLabel.setStyle(
          "-fx-font-weight: bold;"
          + "-fx-font-size: 16px;"
    );
    propertiesLabel.setStyle("-fx-font-weight: bold;");
    getChildren().addAll(nameLabel, balanceLabel, propertiesLabel, propertiesBox);
  }

  public void updateBalance(String balance) {
    balanceLabel.setText("Balance: $" + balance);
  }

  public void addProperty(String propertyName, String rent) {
    Label propertyLabel = new Label(propertyName + " (Rent: $" + rent + ")");
    propertyLabel.getStyleClass().add("property-label");
    propertiesBox.getChildren().add(propertyLabel);
  }

  public void removeAllProperties() {
    propertiesBox.getChildren().clear();
  }
}

