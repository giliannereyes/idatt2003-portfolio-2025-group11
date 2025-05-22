package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.common.DiceRolledEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerMovedEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerWonEvent;
import edu.ntnu.idi.idatt.domain.event.common.TileActionEvent;
import edu.ntnu.idi.idatt.domain.factory.TileActionFactoryRegistry;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.LadderActionFactory;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.SnakeActionFactory;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.SkipTurnActionFactory;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.ResetActionFactory;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.SnakesAndLaddersBoardFactory;
import edu.ntnu.idi.idatt.persistence.reader.PlayerFileReader;
import edu.ntnu.idi.idatt.persistence.writer.PlayerFileWriter;
import edu.ntnu.idi.idatt.persistence.reader.BoardFileReaderGson;
import edu.ntnu.idi.idatt.persistence.writer.BoardFileWriterGson;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.ManualService;
import edu.ntnu.idi.idatt.service.PlayerService;
import edu.ntnu.idi.idatt.service.snakesandladders.LaddersGameBoardService;
import edu.ntnu.idi.idatt.service.snakesandladders.LaddersGameService;
import edu.ntnu.idi.idatt.ui.controller.BoardConfigController;
import edu.ntnu.idi.idatt.ui.controller.PlayerSetupController;
import edu.ntnu.idi.idatt.ui.controller.snakesandladders.SnakesAndLaddersController;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.ui.view.PlayerSetupView;
import edu.ntnu.idi.idatt.ui.view.snakesandladders.SnakesAndLaddersView;
import edu.ntnu.idi.idatt.utils.ViewManager;

public class SnakesAndLaddersInitializer implements GameInitializer {

  @Override
  public void initialize(AppInitializer appInitializer) {
    GameConfig config             = createConfig();
    GameConfigService cfgService  = new GameConfigService(config);
    PlayerService playerService          = createPlayerService();
    TileActionFactoryRegistry registry   = createRegistry();
    ViewManager vm                        = appInitializer.getViewManager();
    EventBus eb                           = appInitializer.getEventBus();

    configurePlayerSetup(vm, playerService, cfgService);
    configureBoardConfig(vm, registry, cfgService);
    configureGame(vm, config, cfgService, eb);
  }

  private GameConfig createConfig() {
    return new GameConfig();
  }

  private PlayerService createPlayerService() {
    return new PlayerService(
          new PlayerFileReader(),
          new PlayerFileWriter(),
          Player::new
    );
  }

  private TileActionFactoryRegistry createRegistry() {
    TileActionFactoryRegistry reg = new TileActionFactoryRegistry();
    reg.registerDestinationFactory(new LadderActionFactory());
    reg.registerNoDestinationFactory(new SkipTurnActionFactory());
    reg.registerNoDestinationFactory(new ResetActionFactory());
    reg.registerDestinationFactory(new SnakeActionFactory());
    return reg;
  }

  private void configurePlayerSetup(
        ViewManager vm, PlayerService ps, GameConfigService cs
  ) {
    PlayerSetupView view = new PlayerSetupView();
    PlayerSetupController ctrl =
          new PlayerSetupController(view, ps, cs, vm);
    view.setController(ctrl);
    vm.add(view);
  }

  private void configureBoardConfig(
        ViewManager vm, TileActionFactoryRegistry registry, GameConfigService cs
  ) {
    SnakesAndLaddersBoardFactory boardFactory = new SnakesAndLaddersBoardFactory(
                new BoardFileReaderGson(registry), new BoardFileWriterGson(), registry
    );
    LaddersGameBoardService boardService = new LaddersGameBoardService(boardFactory);
    BoardConfigView view = new BoardConfigView();
    BoardConfigController ctrl = new BoardConfigController(view, boardService, vm, cs, boardService);
    view.setController(ctrl);
    vm.add(view);
  }

  private void configureGame(ViewManager vm, GameConfig config,
        GameConfigService cs, EventBus eb
  ) {
    LaddersGameService laddersGameService = new LaddersGameService(config, eb);
    SnakesAndLaddersView gameView       = new SnakesAndLaddersView();
    SnakesAndLaddersController controller =
          new SnakesAndLaddersController(cs, laddersGameService, gameView, new ManualService());
    gameView.setController(controller);
    vm.add(gameView);
    eb.register(DiceRolledEvent.class, controller::onDiceRolled);
    eb.register(PlayerMovedEvent.class, controller::onPlayerMoved);
    eb.register(TileActionEvent.class, controller::onTileAction);
    eb.register(PlayerWonEvent.class, controller::onPlayerWon);
  }
}
