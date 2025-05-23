package edu.ntnu.idi.idatt.domain.entity.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.game.monopoly.Monopoly;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.Optional;

/**
 * Represents a property in the {@link Monopoly} game. It has a name, cost, rent,
 * and can be owned by a player. The class provides methods to retrieve
 * property details, set ownership, and check if the property is owned.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Property {
  private final String name;
  private final double cost;
  private final double rent;
  private Player owner;

  /**
   * Constructs a Property instance with the given name, cost, and rent.
   * The property's owner is initially set to null, indicating that it is not owned.
   *
   * @param name is the name of the property.
   * @param cost is the cost to purchase the property.
   * @param rent is the rent paid when another player lands on this property.
   *
   * @throws IllegalArgumentException if any of arguments are invalid.
   */
  public Property(String name, double cost, double rent) {
    Validation.validateNonEmptyStr(name, "Property name");
    Validation.validatePositiveNum(cost, "Property cost");
    Validation.validatePositiveNum(rent, "Property rent");
    this.name = name;
    this.cost = cost;
    this.rent = rent;
    this.owner = null;
  }

  /**
   * Retrieves the name of the property.
   *
   * @return the name of the property.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the cost of the property.
   *
   * @return the cost of the property.
   */
  public double getCost() {
    return cost;
  }

  /**
   * Retrieves the rent amount for this property.
   *
   * @return the rent amount.
   */
  public double getRent() {
    return rent;
  }

  /**
   * Retrieves the owner of the property.
   *
   * @return An {@link Optional} containing the owner of the property, or an empty
   *                             Optional if the property is not owned.
   */
  public Optional<Player> getOwner() {
    return Optional.ofNullable(owner);
  }

  /**
   * Sets the owner of the property.
   *
   * @param owner the player who owns the property.
   *
   * @throws IllegalArgumentException if the owner is null.
   */
  public void setOwner(Player owner) {
    Validation.validateNonNull(owner, "Property's owner");
    this.owner = owner;
  }

  /**
   * Checks if the property is currently owned by any player.
   *
   * @return {@code true} if owned, {@code false} otherwise.
   */
  public boolean isOwned() {
    return owner != null;
  }

  /**
   * Resets ownership of the property to null,
   * indicating that it is not owned.
   */
  public void resetOwnership() {
    this.owner = null;
  }
}

