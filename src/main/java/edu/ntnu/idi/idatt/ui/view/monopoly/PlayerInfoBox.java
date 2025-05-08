package edu.ntnu.idi.idatt.ui.view.monopoly;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.List;

public class PlayerInfoBox extends VBox {
  private final Label nameLabel;
  private final Label balanceLabel;
  private final Label propertiesLabel;
  private final VBox propertiesBox;

  public PlayerInfoBox(String playerName) {
    getStyleClass().add("player-info-box");
    setSpacing(5);
    nameLabel = new Label(playerName);
    balanceLabel = new Label("Balance: $0");
    propertiesLabel = new Label("Owned properties:");
    propertiesBox = new VBox();
    nameLabel.setStyle("-fx-font-weight: bold;");
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

  /**
   * Call this whenever the player’s money or properties change.
   */
  public void update(int balance, List<String> properties) {
    balanceLabel.setText("Balance: $" + balance);
    if (properties.isEmpty()) {
      propertiesLabel.setText("Owned Spaces: –");
    } else {
      propertiesLabel.setText("Owned Spaces: " + String.join(", ", properties));
    }
  }
}

