package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.common.DiceRolledEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerMovedEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerWonEvent;
import edu.ntnu.idi.idatt.domain.event.common.TileActionEvent;
import edu.ntnu.idi.idatt.domain.factory.TileActionFactoryRegistry;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.LadderActionFactory;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.SnakeActionFactory;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.SkipTurnActionFactory;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.ResetActionFactory;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.LaddersGameBoardFactory;
import edu.ntnu.idi.idatt.persistence.reader.PlayerFileReader;
import edu.ntnu.idi.idatt.persistence.writer.PlayerFileWriter;
import edu.ntnu.idi.idatt.persistence.reader.BoardFileReaderGson;
import edu.ntnu.idi.idatt.persistence.writer.BoardFileWriterGson;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.ManualService;
import edu.ntnu.idi.idatt.service.PlayerService;
import edu.ntnu.idi.idatt.service.laddersgame.LaddersGameBoardService;
import edu.ntnu.idi.idatt.service.laddersgame.LaddersGameService;
import edu.ntnu.idi.idatt.ui.controller.BoardConfigController;
import edu.ntnu.idi.idatt.ui.controller.PlayerSetupController;
import edu.ntnu.idi.idatt.ui.controller.laddersgame.LaddersGameController;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.ui.view.PlayerSetupView;
import edu.ntnu.idi.idatt.ui.view.laddersgame.LaddersGameView;
import edu.ntnu.idi.idatt.utils.ViewManager;

public class LaddersGameInitializer implements GameInitializer {

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
    LaddersGameBoardFactory boardFactory = new LaddersGameBoardFactory(
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
    LaddersGameView gameView       = new LaddersGameView();
    LaddersGameController controller =
          new LaddersGameController(cs, laddersGameService, gameView, new ManualService());
    gameView.setController(controller);
    vm.add(gameView);
    eb.register(DiceRolledEvent.class, controller::onDiceRolled);
    eb.register(PlayerMovedEvent.class, controller::onPlayerMoved);
    eb.register(TileActionEvent.class, controller::onTileAction);
    eb.register(PlayerWonEvent.class, controller::onPlayerWon);
  }
}
