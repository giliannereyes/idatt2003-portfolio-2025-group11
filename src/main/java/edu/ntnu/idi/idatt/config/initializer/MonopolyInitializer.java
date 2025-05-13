package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.common.DiceRolledEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerMovedEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerWonEvent;
import edu.ntnu.idi.idatt.domain.event.common.TileActionEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.*;
import edu.ntnu.idi.idatt.domain.factory.monopoly.MonopolyBoardFactory;
import edu.ntnu.idi.idatt.persistence.reader.PlayerFileReader;
import edu.ntnu.idi.idatt.persistence.writer.PlayerFileWriter;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.ManualService;
import edu.ntnu.idi.idatt.service.PlayerService;
import edu.ntnu.idi.idatt.service.monopoly.MonopolyGameService;
import edu.ntnu.idi.idatt.service.monopoly.MonopolyBoardPresetService;
import edu.ntnu.idi.idatt.ui.controller.BoardConfigController;
import edu.ntnu.idi.idatt.ui.controller.PlayerSetupController;
import edu.ntnu.idi.idatt.ui.controller.monopoly.MonopolyController;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.ui.view.PlayerSetupView;
import edu.ntnu.idi.idatt.ui.view.monopoly.MonopolyGameView;
import edu.ntnu.idi.idatt.utils.ViewManager;

public class MonopolyInitializer implements GameInitializer {

  @Override
  public void initialize(AppInitializer app) {
    // core dependencies
    GameConfig config = createConfig();
    GameConfigService cs        = createConfigService(config);
    PlayerService                    ps        = createPlayerService();
    ManualService                    ms        = createManualService();
    ViewManager                      vm        = app.getViewManager();
    EventBus                         eb        = app.getEventBus();

    // assemble UI / controllers
    configurePlayerSetup(vm, ps, cs);
    configureBoardConfig(vm, cs);
    MonopolyController controller = configureGame(vm, eb, cs, ms, config);

    // wire up event listeners
    registerEvents(eb, controller);
  }

  private GameConfig createConfig() {
    return new GameConfig();
  }

  private GameConfigService createConfigService(GameConfig cfg) {
    return new GameConfigService(cfg);
  }

  private PlayerService createPlayerService() {
    return new PlayerService(
          new PlayerFileReader(),
          new PlayerFileWriter(),
          Player::new
    );
  }

  private ManualService createManualService() {
    return new ManualService();
  }

  private void configurePlayerSetup(
        ViewManager vm,
        PlayerService ps,
        GameConfigService cs
  ) {
    PlayerSetupView view     = new PlayerSetupView();
    PlayerSetupController ctrl =
          new PlayerSetupController(view, ps, cs, vm);
    view.setController(ctrl);
    vm.add(view);
  }

  private void configureBoardConfig(
        ViewManager vm,
        GameConfigService cs
  ) {
    MonopolyBoardFactory factory = new MonopolyBoardFactory();
    Board board = factory.loadLargeBoard();
    cs.updateBoard(board);

    BoardConfigView view = new BoardConfigView();
    BoardConfigController ctrl =
          new BoardConfigController(
                view,
                new MonopolyBoardPresetService(factory),
                vm,
                cs,
                null
          );
    view.setController(ctrl);
    vm.add(view);
  }

  private MonopolyController configureGame(
        ViewManager vm,
        EventBus eb,
        GameConfigService cs,
        ManualService ms,
        GameConfig cfg
  ) {
    MonopolyGameService service    = new MonopolyGameService(cfg, eb);
    MonopolyGameView view       = new MonopolyGameView();
    MonopolyController controller =
          new MonopolyController(cs, service, ms, view);
    view.setController(controller);
    vm.add(view);
    return controller;
  }

  private void registerEvents(
        EventBus eb,
        MonopolyController controller
  ) {
    eb.register(DiceRolledEvent.class, controller::onDiceRolled);
    eb.register(PlayerMovedEvent.class, controller::onPlayerMoved);
    eb.register(TileActionEvent.class, controller::onTileAction);
    eb.register(PlayerWonEvent.class, controller::onPlayerWon);
    eb.register(BuyPropertyRequestEvent.class, controller::onBuyPropertyRequest);
    eb.register(InsufficientFundsEvent.class, controller::onInsufficientFunds);
    eb.register(PlayerPaidRentEvent.class, controller::onRentPaid);
    eb.register(PlayerBankruptEvent.class, controller::onPlayerBankrupt);
  }
}
