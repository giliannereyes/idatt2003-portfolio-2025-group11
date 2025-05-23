package edu.ntnu.idi.idatt.ui.view.monopoly;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Represents a UI component that displays information about a player,
 * including their balance and owned properties.
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public class PlayerInfoBox extends VBox {
  private final Label balanceLabel;
  private final VBox propertiesBox;

  /**
   * Constructs a PlayerInfoBox for a specific player.
   *
   * @param playerName the name of the player.
   */
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

  /**
   * Updates the player's balance displayed in the info box.
   *
   * @param balance the new balance to display.
   */
  public void updateBalance(String balance) {
    balanceLabel.setText("Balance: $" + balance);
  }

  /**
   * Adds a property to the player's list of owned properties.
   *
   * @param propertyName the name of the property.
   * @param rent the rent value of the property.
   */
  public void addProperty(String propertyName, String rent) {
    Label propertyLabel = new Label(propertyName + " (Rent: $" + rent + ")");
    propertyLabel.getStyleClass().add("property-label");
    propertiesBox.getChildren().add(propertyLabel);
  }

  /**
   * Removes all properties from the player's list of owned properties.
   */
  public void removeAllProperties() {
    propertiesBox.getChildren().clear();
  }
}