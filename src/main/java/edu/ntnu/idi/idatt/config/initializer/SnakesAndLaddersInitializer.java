package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.factory.TileActionFactoryRegistry;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.LadderActionFactory;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.SnakeActionFactory;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.SkipTurnActionFactory;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.ResetActionFactory;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.SnakesAndLaddersFactory;
import edu.ntnu.idi.idatt.persistence.reader.PlayerFileReader;
import edu.ntnu.idi.idatt.persistence.writer.PlayerFileWriter;
import edu.ntnu.idi.idatt.persistence.reader.BoardFileReaderGson;
import edu.ntnu.idi.idatt.persistence.writer.BoardFileWriterGson;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.PlayerService;
import edu.ntnu.idi.idatt.service.snakesandladders.LaddersGameBoardService;
import edu.ntnu.idi.idatt.service.snakesandladders.GameService;
import edu.ntnu.idi.idatt.ui.controller.FileBoardConfigController;
import edu.ntnu.idi.idatt.ui.controller.PlayerSetupController;
import edu.ntnu.idi.idatt.ui.controller.snakesandladders.SnakesAndLaddersController;
import edu.ntnu.idi.idatt.ui.view.FileBoardConfigView;
import edu.ntnu.idi.idatt.ui.view.PlayerSetupView;
import edu.ntnu.idi.idatt.ui.view.snakesandladders.GameView;
import edu.ntnu.idi.idatt.utils.ViewManager;

public class SnakesAndLaddersInitializer implements GameInitializer {

  @Override
  public void initialize(AppInitializer appInitializer) {
    GameConfig<Board> config             = createConfig();
    GameConfigService<Board> cfgService  = new GameConfigService<>(config);
    PlayerService playerService          = createPlayerService();
    TileActionFactoryRegistry registry   = createRegistry();
    ViewManager vm                        = appInitializer.getViewManager();
    EventBus eb                           = appInitializer.getEventBus();

    configurePlayerSetup(vm, playerService, cfgService);
    configureBoardConfig(vm, registry, cfgService);
    configureGame(vm, config, cfgService, eb, appInitializer);
  }

  private GameConfig<Board> createConfig() {
    return new GameConfig<>();
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
        ViewManager vm, PlayerService ps, GameConfigService<Board> cs
  ) {
    PlayerSetupView view = new PlayerSetupView();
    PlayerSetupController ctrl =
          new PlayerSetupController(view, ps, cs, vm);
    view.setController(ctrl);
    vm.add(view);
  }

  private void configureBoardConfig(
        ViewManager vm, TileActionFactoryRegistry registry, GameConfigService<Board> cs
  ) {
    SnakesAndLaddersFactory boardFactory = new SnakesAndLaddersFactory(
                new BoardFileReaderGson(registry), new BoardFileWriterGson(), registry
    );
    LaddersGameBoardService boardService = new LaddersGameBoardService(boardFactory);
    FileBoardConfigView view = new FileBoardConfigView();
    FileBoardConfigController<Board> ctrl = new FileBoardConfigController<>(
          view, boardService, vm, cs, boardService
    );
    view.setController(ctrl);
    vm.add(view);
  }

  private void configureGame(ViewManager vm, GameConfig<Board> config,
        GameConfigService<Board> cs, EventBus eb, AppInitializer app
  ) {
    GameService gameService = new GameService(config, eb);
    GameView gameView       = new GameView();
    SnakesAndLaddersController ctrl =
          new SnakesAndLaddersController(cs, gameService, gameView);
    gameView.setController(ctrl);
    vm.add(gameView);
    app.registerBasicEvents(ctrl);
  }
}
