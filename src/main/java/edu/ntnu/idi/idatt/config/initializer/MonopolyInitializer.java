package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.domain.event.common.DiceRolledEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerMovedEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerWonEvent;
import edu.ntnu.idi.idatt.domain.event.common.TileActionEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.BuyPropertyRequestEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.InsufficientFundsEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.PlayerBankruptEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.PlayerPaidRentEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.PlayerPassedGoEvent;
import edu.ntnu.idi.idatt.domain.factory.monopoly.MonopolyBoardFactory;
import edu.ntnu.idi.idatt.service.monopoly.MonopolyBoardPresetService;
import edu.ntnu.idi.idatt.service.monopoly.MonopolyGameService;
import edu.ntnu.idi.idatt.ui.controller.BoardConfigController;
import edu.ntnu.idi.idatt.ui.controller.monopoly.MonopolyController;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.ui.view.monopoly.MonopolyView;

/**
 * Initializes the Monopoly game by configuring the board, game logic, and event handling.
 * This class extends {@link BaseGameInitializer} to reuse common initialization steps.
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public class MonopolyInitializer extends BaseGameInitializer {
  private MonopolyController controller;
  private MonopolyBoardFactory boardFactory;

  /**
   * Constructs a new MonopolyInitializer.
   *
   * @param appInitializer the application initializer that provides shared dependencies.
   */
  public MonopolyInitializer(AppInitializer appInitializer) {
    super(appInitializer);
  }

  /**
   * Configures the board setup for the Monopoly game.
   * This includes creating the board factory, view, and controller for board configuration.
   */
  @Override
  protected void configureBoardConfig() {
    boardFactory = new MonopolyBoardFactory();
    BoardConfigView view = new BoardConfigView();
    BoardConfigController ctrl = new BoardConfigController(
          view,
          new MonopolyBoardPresetService(boardFactory),
          viewManager,
          configService,
          null
    );
    view.setController(ctrl);
    viewManager.add(view);
  }

  /**
   * Configures the main game logic and view for the Monopoly game.
   * This includes creating the game service, view, and controller.
   */
  @Override
  protected void configureGame() {
    MonopolyGameService service = new MonopolyGameService(config, eventBus, boardFactory);
    MonopolyView view = new MonopolyView();
    controller = new MonopolyController(configService, service, manualService, view);
    view.setController(controller);
    viewManager.add(view);
  }

  /**
   * Registers event handlers for the Monopoly game.
   * This includes handling dice rolls, player movements, tile actions, and other game events.
   */
  @Override
  protected void registerEvents() {
    eventBus.register(DiceRolledEvent.class, controller::onDiceRolled);
    eventBus.register(PlayerMovedEvent.class, controller::onPlayerMoved);
    eventBus.register(TileActionEvent.class, controller::onTileAction);
    eventBus.register(PlayerWonEvent.class, controller::onPlayerWon);
    eventBus.register(BuyPropertyRequestEvent.class, controller::onBuyPropertyRequest);
    eventBus.register(InsufficientFundsEvent.class, controller::onInsufficientFunds);
    eventBus.register(PlayerPaidRentEvent.class, controller::onRentPaid);
    eventBus.register(PlayerBankruptEvent.class, controller::onPlayerBankrupt);
    eventBus.register(PlayerPassedGoEvent.class, controller::onPlayerPassedGo);
  }
}