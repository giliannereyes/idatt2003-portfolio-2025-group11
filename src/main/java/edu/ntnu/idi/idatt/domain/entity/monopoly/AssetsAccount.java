package edu.ntnu.idi.idatt.domain.entity.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the assets account of a player. It contains the player's
 * balance, properties owned, and whether the player is bankrupt. It also
 * provides methods to credit and debit money, buy and release properties,
 * and pay rent to other players' accounts.
 *
 * <p>Note: The class is designed to be used in a game context, where
 * players can buy properties, pay rent, and go bankrupt.</p>
 *
 * @author Gilianne Reyes
 * @version 0.1
 */
public class AssetsAccount {
  private final Player owner;
  private double balance;
  private final List<Property> properties;
  private boolean bankrupt;

  /**
   * Constructs an AssetsAccount for a player with a starting balance.
   *
   * @param owner is the player who owns this account.
   * @param startingBalance is the initial amount of money in the account.
   *
   * @throws IllegalArgumentException if the owner is null.
   */
  public AssetsAccount(Player owner, double startingBalance) {
    Validation.validateNonNull(owner, "Owner");
    this.owner = owner;
    this.balance = startingBalance;
    this.properties = new ArrayList<>();
    this.bankrupt = false;
  }

  /**
   * Retrieves the balance of the player's account.
   *
   * @return the current balance of the account.
   */
  public double getBalance() {
    return balance;
  }

  /**
   * Checks if the player is bankrupt. A player is considered bankrupt
   * if their balance is negative.
   *
   * @return {@code true} if the player is bankrupt, {@code false} otherwise.
   */
  public boolean isBankrupt() {
    return bankrupt;
  }

  /**
   * Credits a specified amount to the player's balance.
   *
   * @param amount is the amount to be credited.
   *
   * @throws IllegalArgumentException if the amount is not positive.
   */
  public void credit(double amount) {
    Validation.validateNonNegativeNum(amount, "Amount");
    this.balance += amount;
    if (balance > 0) {
      this.bankrupt = false;
    }
  }

  /**
   * Buys a property for the player by debiting the cost from their balance
   * and setting the property as owned by the player.
   *
   * @param property is the property to be bought.
   *
   * @throws IllegalArgumentException if the property is null, owned
   *                                  or if the player cannot afford it.
   */
  public void buyProperty(Property property) {
    Validation.validateNonNull(property, "Property");
    if (isBankrupt() || balance < property.getCost()) {
      throw new IllegalArgumentException("Player does not have enough money to buy the property.");
    }
    debit(property.getCost());
    property.setOwner(owner);
    properties.add(property);
  }

  /**
   * Pays rent to the owner of a property. If the player does not have enough money,
   * they go bankrupt and release all properties.
   *
   * @param landlordAccount is the property owner's account.
   * @param rent is the amount of rent to be paid.
   *
   * @throws IllegalArgumentException if the owner is null or if the rent is not positive.
   */
  public void payRent(AssetsAccount landlordAccount, double rent) {
    Validation.validateNonNull(landlordAccount, "Landlord's account");
    Validation.validatePositiveNum(rent, "Rent");
    if (balance >= rent) {
      debit(rent);
      landlordAccount.credit(rent);
    } else {
      landlordAccount.credit(balance); // TODO: Player pays all remaining money! (Re-asses this)
      debit(balance);
      bankrupt = true;
      releaseProperties();
    }
  }

  /**
   * Retrieves the player who owns this account.
   *
   * @return a player representing the owner of this account.
   */
  public Player getOwner() {
    return owner;
  }

  /**
   * Retrieves the list of properties owned by the player.
   *
   * @return a list of properties owned by the player.
   */
  public List<Property> getProperties() {
    return Collections.unmodifiableList(properties);
  }

  /**
   * Debits a specified amount from the player's balance.
   *
   * @param amount is the amount to be debited.
   *
   * @throws IllegalArgumentException if the amount is not positive.
   */
  public void debit(double amount) {
    Validation.validateNonNegativeNum(amount, "Amount");
    if (balance < amount) {
      throw new IllegalArgumentException("Not enough money to debit.");
    }
    balance -= amount;
  }

  /**
   * Releases all properties owned by the player, resetting their ownership.
   */
  private void releaseProperties() {
    properties.forEach(Property::resetOwnership);
    properties.clear();
  }
}
