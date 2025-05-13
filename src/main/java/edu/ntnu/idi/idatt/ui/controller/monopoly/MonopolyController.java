package edu.ntnu.idi.idatt.ui.controller.monopoly;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.monopoly.AssetsAccount;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.event.common.*;
import edu.ntnu.idi.idatt.domain.event.monopoly.*;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.ManualService;
import edu.ntnu.idi.idatt.service.monopoly.MonopolyGameService;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.ui.controller.BoardGameController;
import edu.ntnu.idi.idatt.ui.view.monopoly.MonopolyGameView;

/**
 * Controller for Monopoly Lite, bridging view, service, and events.
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
      PlayerBankruptListener
{
  private final GameConfigService configSvc;
  private final MonopolyGameService gameSvc;
  private final MonopolyGameView view;
  private final ManualService manualService;

  public MonopolyController(GameConfigService configSvc,
                            MonopolyGameService gameSvc,
                            ManualService manualService,
                            MonopolyGameView view) {
    this.configSvc = configSvc;
    this.gameSvc   = gameSvc;
    this.manualService = manualService;
    this.view      = view;
  }

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

  @Override
  public void onDiceClicked() {
    gameSvc.onDiceClicked();
  }

  @Override
  public void onDiceRolled(DiceRolledEvent e) {
    view.updateDice(e.roll1(), e.roll2());
  }

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

  @Override
  public void onTileAction(TileActionEvent e) {
    /*
    e.getTile().getLandAction().ifPresent({
          action -> {
            view.setStatusLabel(
                  String.format("%s landed on %s", e.getPlayer().getName(), action.getActionType())
            );
          }
    });
     */
  }

  @Override
  public void onPlayerWon(PlayerWonEvent e) {
    view.onPlayerWon(e.winner().getName());
  }

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
      view.addPropertyToPlayer(player.getName(), property.getName(), String.valueOf(property.getRent()));
    } else {
      view.setStatusLabel(
            String.format("%s declined to buy %s", player.getName(), property.getName())
      );
    }
  }

  @Override
  public void onInsufficientFunds(InsufficientFundsEvent e) {
    /*
    view.setStatusLabel(
          String.format("%s does not have enough money to buy %s", e.getPlayer().getName(), property.getName())
    );
     */
  }

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


