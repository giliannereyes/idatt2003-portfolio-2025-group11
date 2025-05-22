package edu.ntnu.idi.idatt.ui.controller.monopoly;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.entity.monopoly.AssetsAccount;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.event.common.*;
import edu.ntnu.idi.idatt.domain.event.monopoly.*;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.ManualService;
import edu.ntnu.idi.idatt.service.monopoly.MonopolyGameService;
import edu.ntnu.idi.idatt.ui.controller.BoardGameController;
import edu.ntnu.idi.idatt.ui.view.monopoly.MonopolyGameView;

/**
 * Controller for Monopoly Lite, bridging view, service, and events.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class MonopolyController implements
      BoardGameController,
      DiceRolledListener,
      PlayerMovedListener,
      PlayerWonListener,
      TileActionListener,
      BuyPropertyRequestListener,
      InsufficientFundsListener,
      PlayerPaidRentListener,
      PlayerBankruptListener {
  private final GameConfigService configSvc;
  private final MonopolyGameService gameSvc;
  private final MonopolyGameView view;
  private final ManualService manualService;

  /**
   * Constructs a MonopolyController with the required services and view.
   *
   * @param configSvc the service managing game configuration
   * @param gameSvc the Monopoly-specific game logic service
   * @param manualService the service for loading user manuals
   * @param view the Monopoly game view
   */
  public MonopolyController(GameConfigService configSvc,
                            MonopolyGameService gameSvc,
                            ManualService manualService,
                            MonopolyGameView view) {
    this.configSvc = configSvc;
    this.gameSvc   = gameSvc;
    this.manualService = manualService;
    this.view      = view;
  }

  /**
   * Initializes the Monopoly game by loading configuration, setting up the board and players,
   * and starting the game.
   */
  @Override
  public void initialize() {
    try {
      if (!configSvc.isConfigComplete()) {
        throw new IllegalStateException("Configuration incomplete");
      }
      view.setUserManualText(manualService.loadManualText("/userManuals/monopoly_user_manual.txt"));
      GameConfig config = configSvc.build();
      view.registerBoard(config.getBoard());
      config.getPlayerConfigs().forEach(pc -> {
            view.registerPlayerToken(pc.getPlayer().getName(),
                  pc.getTokenImagePath());
      }
      );
      gameSvc.startGame();
    } catch (Exception ex) {
      view.onErrorInitializingGame(ex.getMessage());
    }
  }

  /**
   * Handles the event when the dice is clicked by the user.
   */
  @Override
  public void onDiceClicked() {
    gameSvc.onDiceClicked();
  }

  /**
   * Updates the view with the result of a dice roll.
   *
   * @param e the dice rolled event
   */
  @Override
  public void onDiceRolled(DiceRolledEvent e) {
    view.updateDice(e.roll1(), e.roll2());
  }

  /**
   * Updates the view when a player moves from one tile to another.
   *
   * @param e the player moved event
   */
  @Override
  public void onPlayerMoved(PlayerMovedEvent e) {
    Player player = e.player();
    Tile from = e.fromTile();
    Tile to = e.destinationTile();
    view.setStatusLabel(
          String.format("%s moved from %d to %d",
                player.getName(), from.getTileId(), to.getTileId())
    );
    view.movePlayerToken(player.getName(), to.getX(), to.getY());
  }

  /**
   * Handles tile-specific actions when a player lands on a tile.
   * (Currently commented out for future implementation.)
   *
   * @param e the tile action event
   */
  @Override
  public void onTileAction(TileActionEvent e) {
    /*Player player = e.player();
    Tile tile = e.tile();
    tile.getLandAction().ifPresent({
          action -> {
            view.setStatusLabel(
                  String.format("%s landed on %s", player.getName(), action.getActionType())
            );
          }
    });*/
  }

  /**
   * Displays the winner when a player wins the game.
   *
   * @param e the player won event
   */
  @Override
  public void onPlayerWon(PlayerWonEvent e) {
    view.onPlayerWon(e.winner().getName());
  }

  /**
   * Prompts the player to buy a property and processes the purchase if accepted.
   *
   * @param e the buy property request event
   */
  @Override
  public void onBuyPropertyRequest(BuyPropertyRequestEvent e) {
    Player player = e.player();
    Property property = e.property();
    AssetsAccount account = e.account();
    boolean buy = view.promptYesNo(
          "Player " + player.getName(),
          "Pay $" + property.getCost() + "to buy " + property.getName() + "?"
          + "\n The rent is $" + property.getRent() + ".");
    if (buy) {
      gameSvc.buyProperty(player, property);
      view.setStatusLabel(
            String.format("%s bought %s for $%f",
                  player.getName(), property.getName(), property.getCost())
      );
      view.updatePlayerBalance(player.getName(), String.valueOf(account.getBalance()));
      view.addPropertyToPlayer(player.getName(), property.getName(),
              String.valueOf(property.getRent()));
    } else {
      view.setStatusLabel(
            String.format("%s declined to buy %s", player.getName(), property.getName())
      );
    }
  }

  /**
   * Notifies the view when a player cannot afford a property.
   *
   * @param e the insufficient funds event
   */
  @Override
  public void onInsufficientFunds(InsufficientFundsEvent e) {
    Player player = e.player();
    Property property = e.property();
    view.setStatusLabel(
          String.format("%s does not have enough money to buy %s",
                  player.getName(), property.getName())
    );
  }

  /**
   * Updates the view when rent is paid from one player to another.
   *
   * @param e the rent paid event
   */
  @Override
  public void onRentPaid(PlayerPaidRentEvent e) {
    Player tenant = e.tenant();
    Player landlord = e.landlord();
    double rent = e.rent();
    double tenantBalance = e.tenantBalance();
    double landlordBalance = e.landlordBalance();
    view.setStatusLabel(
          String.format("%s paid $%.2f rent to %s", tenant.getName(), rent, landlord.getName())
    );
    view.updatePlayerBalance(tenant.getName(), String.valueOf(tenantBalance));
    view.updatePlayerBalance(landlord.getName(), String.valueOf(landlordBalance));
    System.out.println("Tenant balance: $" + tenantBalance);
    System.out.println("Landlord balance: $" + landlordBalance);
  }

  /**
   * Handles the event when a player goes bankrupt.
   *
   * @param e the player bankrupt event
   */
  @Override
  public void onPlayerBankrupt(PlayerBankruptEvent e) {
    Player player = e.player();
    view.setStatusLabel(
          String.format("%s is bankrupt and out of the game!", player.getName())
    );
    view.updatePlayerBalance(player.getName(), "BANKRUPT");
    view.removeAllPropertiesFromPlayer(player.getName());
  }
}


