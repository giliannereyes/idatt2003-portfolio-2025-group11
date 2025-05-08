package edu.ntnu.idi.idatt.ui.controller.monopoly;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.monopoly.AssetsAccount;
import edu.ntnu.idi.idatt.domain.entity.monopoly.MonopolyBoard;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.ManualService;
import edu.ntnu.idi.idatt.service.monopoly.MonopolyGameService;
import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.ui.controller.BoardGameController;
import edu.ntnu.idi.idatt.domain.event.common.GameEventListener;
import edu.ntnu.idi.idatt.domain.event.monopoly.MonopolyEventListener;
import edu.ntnu.idi.idatt.ui.view.monopoly.MonopolyGameView;

/**
 * Controller for Monopoly Lite, bridging view, service, and events.
 */
public class MonopolyController implements BoardGameController, GameEventListener, MonopolyEventListener {
  private final GameConfigService<MonopolyBoard> configSvc;
  private final MonopolyGameService gameSvc;
  private final MonopolyGameView view;
  private final ManualService manualService;

  public MonopolyController(GameConfigService<MonopolyBoard> configSvc,
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
      GameConfig<MonopolyBoard> config = configSvc.build();
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
  public void onDiceRolled(Player player, int dice1, int dice2) {
    view.updateDice(dice1, dice2);

  }

  @Override
  public void onPlayerMoved(Player player, Tile from, Tile to) {
    view.setStatusLabel(
          String.format("%s moved from %d to %d",
                player.getName(), from.getTileId(), to.getTileId())
    );
    view.movePlayerToken(player.getName(), to.getX(), to.getY());
  }

  @Override
  public void onTileAction(Player player, TileAction action) {
    view.setStatusLabel(
          String.format("%s landed on %s", player.getName(), action.getActionType())
    );
  }

  @Override
  public void onPlayerWon(Player player) {
    view.onPlayerWon(player.getName());
  }

  @Override
  public void onBuyPropertyRequest(Player player, Property property, AssetsAccount account) {
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
  public void onInsufficientFunds(Player player, Property property) {
    view.setStatusLabel(
          String.format("%s does not have enough money to buy %s", player.getName(), property.getName())
    );
  }

  @Override
  public void onRentPaid(Player tenant, Player landlord, double rent, double tenantBalance, double landlordBalance) {
    view.setStatusLabel(
          String.format("%s paid $%.2f rent to %s", tenant.getName(), rent, landlord.getName())
    );
    view.updatePlayerBalance(tenant.getName(), String.valueOf(tenantBalance));
    view.updatePlayerBalance(landlord.getName(), String.valueOf(landlordBalance));
    System.out.println("Tenant balance: $" + tenantBalance);
    System.out.println("Landlord balance: $" + landlordBalance);
  }

  @Override
  public void onPlayerBankrupt(Player player) {
    view.setStatusLabel(
          String.format("%s is bankrupt and out of the game!", player.getName())
    );
    view.updatePlayerBalance(player.getName(), "BANKRUPT");
    view.removeAllPropertiesFromPlayer(player.getName());
  }
}


