package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.monopoly.MonopolyBoard;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.monopoly.BuyPropertyRequestEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.PlayerPaidRentEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.PlayerBankruptEvent;
import edu.ntnu.idi.idatt.domain.event.monopoly.BuyPropertyRequestHandler;
import edu.ntnu.idi.idatt.domain.event.monopoly.PlayerPaidRentHandler;
import edu.ntnu.idi.idatt.domain.event.monopoly.PlayerBankruptHandler;
import edu.ntnu.idi.idatt.domain.factory.monopoly.MonopolyBoardFactory;
import edu.ntnu.idi.idatt.persistence.reader.PlayerFileReader;
import edu.ntnu.idi.idatt.persistence.writer.PlayerFileWriter;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.ManualService;
import edu.ntnu.idi.idatt.service.PlayerService;
import edu.ntnu.idi.idatt.service.monopoly.MonopolyGameService;
import edu.ntnu.idi.idatt.service.monopoly.MonopolyPredefinedBoardService;
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
    GameConfig<MonopolyBoard>        config    = createConfig();
    GameConfigService<MonopolyBoard> cs        = createConfigService(config);
    PlayerService                    ps        = createPlayerService();
    ManualService                    ms        = createManualService();
    ViewManager                      vm        = app.getViewManager();
    EventBus                         eb        = app.getEventBus();

    // assemble UI / controllers
    configurePlayerSetup(vm, ps, cs);
    configureBoardConfig(vm, cs);
    MonopolyController controller = configureGame(vm, eb, cs, ms, config);

    // wire up event listeners
    registerEvents(app, eb, controller);
  }

  private GameConfig<MonopolyBoard> createConfig() {
    return new GameConfig<>();
  }

  private GameConfigService<MonopolyBoard> createConfigService(GameConfig<MonopolyBoard> cfg) {
    return new GameConfigService<>(cfg);
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
        GameConfigService<MonopolyBoard> cs
  ) {
    PlayerSetupView view     = new PlayerSetupView();
    PlayerSetupController ctrl =
          new PlayerSetupController(view, ps, cs, vm);
    view.setController(ctrl);
    vm.add(view);
  }

  private void configureBoardConfig(
        ViewManager vm,
        GameConfigService<MonopolyBoard> cs
  ) {
    MonopolyBoardFactory factory = new MonopolyBoardFactory();
    MonopolyBoard      board   = factory.loadLargeBoard();
    cs.updateBoard(board);

    BoardConfigView view = new BoardConfigView();
    BoardConfigController<MonopolyBoard> ctrl =
          new BoardConfigController<>(
                view,
                new MonopolyPredefinedBoardService(factory),
                vm,
                cs
          );
    view.setController(ctrl);
    vm.add(view);
  }

  private MonopolyController configureGame(
        ViewManager vm,
        EventBus eb,
        GameConfigService<MonopolyBoard> cs,
        ManualService ms,
        GameConfig<MonopolyBoard> cfg
  ) {
    MonopolyGameService service    = new MonopolyGameService(cfg, eb);
    MonopolyGameView    view       = new MonopolyGameView();
    MonopolyController  controller =
          new MonopolyController(cs, service, ms, view);
    view.setController(controller);
    vm.add(view);
    return controller;
  }

  private void registerEvents(
        AppInitializer app,
        EventBus eb,
        MonopolyController controller
  ) {
    app.registerBasicEvents(controller);
    eb.register(BuyPropertyRequestEvent.class,
          new BuyPropertyRequestHandler(controller));
    eb.register(PlayerPaidRentEvent.class,
          new PlayerPaidRentHandler(controller));
    eb.register(PlayerBankruptEvent.class,
          new PlayerBankruptHandler(controller));
  }
}
