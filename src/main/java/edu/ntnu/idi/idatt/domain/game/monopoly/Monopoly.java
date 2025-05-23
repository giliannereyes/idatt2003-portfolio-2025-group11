package edu.ntnu.idi.idatt.domain.game.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Dice;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.entity.monopoly.AssetsAccount;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.entity.monopoly.PropertyRegistry;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.common.DiceRolledEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerMovedEvent;
import edu.ntnu.idi.idatt.domain.event.common.TileActionEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.BuyPropertyRequestEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.InsufficientFundsEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.PlayerBankruptEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.PlayerPaidRentEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.PlayerPassedGoEvent;
import edu.ntnu.idi.idatt.domain.game.BoardGame;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a game of Monopoly.
 *
 * <p>This class extends the {@link BoardGame} class and implements the rules and mechanics
 * specific to the Monopoly game. It manages player turns, property transactions, and
 * handles events related to the game.</p>
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Monopoly extends BoardGame {
  private final Map<Player, AssetsAccount> accounts = new HashMap<>();
  private final PropertyRegistry propertyRegistry;
  private final double goBonus = 50;
  private final double startingBalance = 400;

  /**
   * Constructs a Monopoly game instance.
   *
   * @param board is the game board.
   * @param players is the list of players.
   * @param dice is the dice used in the game.
   * @param eventBus is the event bus for publishing events.
   * @param propertyRegistry is the registry for properties on the board.
   *
   * @throws IllegalArgumentException if any of the parameters are null.
   */
  public Monopoly(
        Board board, List<Player> players, Dice dice,
        EventBus eventBus, PropertyRegistry propertyRegistry
  ) {
    super(board, players, dice, eventBus);
    Validation.validateNonNull(propertyRegistry, "Property Registry");
    players.forEach(player -> accounts.put(player, new AssetsAccount(player, startingBalance)));
    this.board = board;
    this.propertyRegistry = propertyRegistry;
  }

  /**
   * Plays a turn for a player. The player rolls the dice, moves, and
   * triggers the action of the tile they land on. All the game
   * events are published to the event bus.
   *
   * @param player is the player taking their turn.
   *
   * @throws IllegalArgumentException if the player is null.
   */
  @Override
  protected void playTurn(Player player) {
    Validation.validateNonNull(player, "Player");
    AssetsAccount account = accounts.get(player);
    if (account.isBankrupt()) {
      handleBankruptPlayer(player);
    } else {
      int roll = rollDice(player);
      movePlayer(player, roll);
      handleLanding(player);
    }
  }

  /**
   * Checks if the game is over by checking if only one player is left
   * with a non-bankrupt account.
   *
   * @return true if the game is over, false otherwise.
   */
  @Override
  protected boolean isGameOver() {
    long activePlayers = players.stream()
          .filter(player -> !accounts.get(player).isBankrupt())
          .count();
    if (activePlayers == 1) {
      this.winner = players.stream()
            .filter(player -> !accounts.get(player).isBankrupt())
            .findFirst()
            .orElse(null);
      return true;
    }
    return false;
  }

  /**
   * Buys a property for the player.
   *
   * @param player is the player buying the property.
   * @param property is the property to be bought.
   *
   * @throws IllegalArgumentException if the player or property is null.
   * @throws IllegalStateException if the property is already owned.
   */
  public void buyProperty(Player player, Property property) {
    Validation.validateNonNull(player, "Player");
    Validation.validateNonNull(property, "Property");
    AssetsAccount account = accounts.get(player);
    if (property.isOwned()) {
      throw new IllegalStateException("Property is already owned.");
    } else {
      account.buyProperty(property);
    }
  }

  /**
   * Retrieves the assets account for a player.
   *
   * @param player is the player whose account is to be retrieved.
   *
   * @return the assets account of the player.
   */
  private int rollDice(Player player) {
    int roll = dice.roll();
    int d1 = dice.getDie(0);
    int d2 = dice.getDie(1);
    eventBus.publish(new DiceRolledEvent(player, d1, d2));
    return roll;
  }

  /**
   * Moves the player a specified number of steps on the board.
   * If the player passes the "Go" tile, they receive a bonus.
   *
   * @param player the player to move.
   * @param steps the number of steps to move the player.
   */
  private void movePlayer(Player player, int steps) {
    Tile from = player.getCurrentTile();
    player.move(steps);
    Tile to = player.getCurrentTile();
    handlePassingGo(player, from, steps);
    if (from != to) {
      eventBus.publish(new PlayerMovedEvent(player, from, to));
    }
  }

  /**
   * Handles the landing of a player on a tile.
   * If the tile has an action, it is executed.
   * If the tile is a property, it checks if it is owned and handles rent payment or purchase.
   *
   * @param player the player who landed on the tile.
   */
  private void handleLanding(Player player) {
    Tile tile = player.getCurrentTile();
    tile.landPlayer(player);
    if (tile.getLandAction().isPresent()) {
      eventBus.publish(new TileActionEvent(player, tile));
    }
    handlePropertyLanding(player, tile);
  }

  /**
   * Handles the landing of a player on a property tile.
   * If the property is owned, it handles rent payment.
   * If the property is not owned and the player has enough balance,
   * it requests to buy the property.
   *
   * @param player the player who landed on the property.
   * @param tile the tile that was landed on.
   */
  private void handlePropertyLanding(Player player, Tile tile) {
    propertyRegistry.getPropertyAt(tile).ifPresent(property -> {
      if (property.isOwned()) {
        handleRentPayment(player, property);
      } else if (accounts.get(player).getBalance() < property.getCost()) {
        eventBus.publish(new InsufficientFundsEvent(player, property));
      } else {
        eventBus.publish(new BuyPropertyRequestEvent(player, property, accounts.get(player)));
      }
    });
  }

  /**
   * Handles the payment of rent when a player lands on an owned property.
   *
   * @param tenant the player who landed on the property.
   * @param property the property that was landed on.
   */
  private void handleRentPayment(Player tenant, Property property) {
    property.getOwner().ifPresent(landlord -> {
      if (!tenant.equals(landlord)) {
        AssetsAccount tenantAccount = accounts.get(tenant);
        AssetsAccount landlordAccount = accounts.get(landlord);
        tenantAccount.payRent(landlordAccount, property.getRent());
        eventBus.publish(new PlayerPaidRentEvent(
              tenant, landlord, property.getRent(),
              tenantAccount.getBalance(), landlordAccount.getBalance()));
      }
    });
  }

  /**
   * Handles the event when a player passes the "Go" tile.
   * The player receives a bonus for passing "Go".
   *
   * @param player the player who passed "Go".
   * @param fromTile the tile the player moved from.
   * @param steps the number of steps moved.
   */
  private void handlePassingGo(Player player, Tile fromTile, int steps) {
    if (passedGo(fromTile.getTileId(), steps)) {
      giveGoBonus(player);
      eventBus.publish(new PlayerPassedGoEvent(player, accounts.get(player).getBalance(), goBonus));
    }
  }

  /**
   * Checks if the player has passed the "Go" tile.
   *
   * @param fromId the ID of the tile the player moved from.
   * @param steps the number of steps moved.
   *
   * @return true if the player passed "Go", false otherwise.
   */
  private boolean passedGo(int fromId, int steps) {
    return (fromId - 1 + steps) >= board.getTiles().size();
  }

  /**
   * Gives the player a bonus for passing "Go".
   *
   * @param player the player who passed "Go".
   */
  private void giveGoBonus(Player player) {
    AssetsAccount account = accounts.get(player);
    account.credit(goBonus);
  }

  /**
   * Handles the event when a player goes bankrupt.
   * If the player is bankrupt, an event is published.
   *
   * @param player the player who may be bankrupt.
   */
  private void handleBankruptPlayer(Player player) {
    AssetsAccount account = accounts.get(player);
    if (account.isBankrupt()) {
      eventBus.publish(new PlayerBankruptEvent(player));
    }
  }
}