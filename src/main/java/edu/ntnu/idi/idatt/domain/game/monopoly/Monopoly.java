package edu.ntnu.idi.idatt.domain.game.monopoly;

import edu.ntnu.idi.idatt.domain.entity.*;
import edu.ntnu.idi.idatt.domain.entity.monopoly.*;
import edu.ntnu.idi.idatt.domain.event.common.*;
import edu.ntnu.idi.idatt.domain.event.monopoly.*;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.game.BoardGame;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Monopoly extends BoardGame {
  private final Map<Player, AssetsAccount> accounts = new HashMap<>();
  private final PropertyRegistry propertyRegistry;
  private final double goBonus = 50;
  private final double startingBalance = 400;

  public Monopoly(
        Board board, List<Player> players, Dice dice, EventBus eventBus, PropertyRegistry propertyRegistry
  ) {
    super(board, players, dice, eventBus);
    players.forEach(player -> accounts.put(player, new AssetsAccount(player, startingBalance)));
    this.board = board;
    this.propertyRegistry = propertyRegistry;
  }

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

  public PropertyRegistry getPropertyRegistry() {
    return propertyRegistry;
  }

  private int rollDice(Player player) {
    int roll = dice.roll();
    int d1 = dice.getDie(0);
    int d2 = dice.getDie(1);
    eventBus.publish(new DiceRolledEvent(player, d1, d2));
    return roll;
  }

  private void movePlayer(Player player, int steps) {
    Tile from = player.getCurrentTile();
    player.move(steps);
    Tile to = player.getCurrentTile();
    handlePassingGo(player, from, steps);
    if (from != to) {
      eventBus.publish(new PlayerMovedEvent(player, from, to));
    }
  }

  private void handleLanding(Player player) {
    Tile tile = player.getCurrentTile();
    tile.landPlayer(player);
    if (tile.getLandAction().isPresent()) {
      eventBus.publish(new TileActionEvent(player, tile));
    }
    handlePropertyLanding(player, tile);
  }

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

  private void handlePassingGo(Player player, Tile fromTile, int steps) {
    if (passedGo(fromTile.getTileId(), steps)) {
      giveGoBonus(player);
      eventBus.publish(new PlayerPassedGoEvent(player, accounts.get(player).getBalance(), goBonus));
    }
  }

  private boolean passedGo(int fromId, int steps) {
    return (fromId - 1 + steps) >= board.getTiles().size();
  }

  private void giveGoBonus(Player player) {
    AssetsAccount account = accounts.get(player);
    account.credit(goBonus);
  }

  private void handleBankruptPlayer(Player player) {
    AssetsAccount account = accounts.get(player);
    if (account.isBankrupt()) {
      eventBus.publish(new PlayerBankruptEvent(player));
    }
  }
}