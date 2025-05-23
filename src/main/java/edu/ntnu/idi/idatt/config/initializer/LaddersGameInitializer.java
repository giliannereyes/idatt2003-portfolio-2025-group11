package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.domain.event.common.DiceRolledEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerMovedEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerWonEvent;
import edu.ntnu.idi.idatt.domain.event.common.TileActionEvent;
import edu.ntnu.idi.idatt.domain.factory.TileActionFactoryRegistry;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.LadderActionFactory;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.LaddersGameBoardFactory;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.ResetActionFactory;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.SkipTurnActionFactory;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.SnakeActionFactory;
import edu.ntnu.idi.idatt.persistence.reader.BoardFileReaderGson;
import edu.ntnu.idi.idatt.persistence.writer.BoardFileWriterGson;
import edu.ntnu.idi.idatt.service.laddersgame.LaddersGameBoardService;
import edu.ntnu.idi.idatt.service.laddersgame.LaddersGameService;
import edu.ntnu.idi.idatt.ui.controller.BoardConfigController;
import edu.ntnu.idi.idatt.ui.controller.laddersgame.LaddersGameController;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.ui.view.laddersgame.LaddersGameView;

/**
 * Initializes the Ladders game by configuring the board, game logic, and event handling.
 * This class extends {@link BaseGameInitializer} to reuse common initialization steps.
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public class LaddersGameInitializer extends BaseGameInitializer {
  private LaddersGameController controller;

  /**
   * Constructs a new LaddersGameInitializer.
   *
   * @param appInitializer the application initializer that provides shared dependencies.
   */
  public LaddersGameInitializer(AppInitializer appInitializer) {
    super(appInitializer);
  }

  /**
   * Configures the board setup for the Ladders game.
   * This includes creating the board factory, service, view,
   * and controller for board configuration.
   */
  @Override
  protected void configureBoardConfig() {
    TileActionFactoryRegistry registry = new TileActionFactoryRegistry();
    registry.registerDestinationFactory(new LadderActionFactory());
    registry.registerNoDestinationFactory(new SkipTurnActionFactory());
    registry.registerNoDestinationFactory(new ResetActionFactory());
    registry.registerDestinationFactory(new SnakeActionFactory());
    LaddersGameBoardFactory boardFactory = new LaddersGameBoardFactory(
          new BoardFileReaderGson(registry), new BoardFileWriterGson(), registry
    );
    LaddersGameBoardService boardService = new LaddersGameBoardService(boardFactory);
    BoardConfigView view = new BoardConfigView();
    BoardConfigController ctrl = new BoardConfigController(
          view, boardService, viewManager, configService, boardService
    );
    view.setController(ctrl);
    viewManager.add(view);
  }

  /**
   * Configures the main game logic and view for the Snakes and Ladders game.
   * This includes creating the game service, view, and controller.
   */
  @Override
  protected void configureGame() {
    LaddersGameService service = new LaddersGameService(config, eventBus);
    LaddersGameView view = new LaddersGameView();
    controller = new LaddersGameController(configService, service, view, manualService);
    view.setController(controller);
    viewManager.add(view);
  }

  /**
   * Registers event handlers for the Snakes and Ladders game.
   * This includes handling dice rolls, player movements, tile actions, and player wins.
   */
  @Override
  protected void registerEvents() {
    eventBus.register(DiceRolledEvent.class, controller::onDiceRolled);
    eventBus.register(PlayerMovedEvent.class, controller::onPlayerMoved);
    eventBus.register(TileActionEvent.class, controller::onTileAction);
    eventBus.register(PlayerWonEvent.class, controller::onPlayerWon);
  }
}