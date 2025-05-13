package edu.ntnu.idi.idatt.domain.action.monopoly;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;

/**
 * PropertyAction class is a class that represents the
 * action of a property tile. It marks a tile as a tile with a property
 * and stores the property object.
 *
 * @author Gilianne Reyes
 * @version 0.1
 */
public class PropertyAction implements TileAction{
  private static final String actionType = "PropertyAction";
  private final Property property;

  public PropertyAction(Property property){
    this.property = property;
  }

  @Override
  public void perform(Player p) {
    System.err.println("PropertyRentAction.perform does not do anything");
  }

  @Override
  public String getActionType() {
    return actionType;
  }

  public Property getProperty() {
    return property;
  }
}
