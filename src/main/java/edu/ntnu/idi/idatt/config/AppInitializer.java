package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.controller.*;
import edu.ntnu.idi.idatt.io.*;
import edu.ntnu.idi.idatt.model.events.bus.DefaultEventBus;
import edu.ntnu.idi.idatt.model.events.bus.EventBus;
import edu.ntnu.idi.idatt.model.events.handlers.DiceRolledHandler;
import edu.ntnu.idi.idatt.model.events.handlers.PlayerMovedHandler;
import edu.ntnu.idi.idatt.model.events.handlers.PlayerWonHandler;
import edu.ntnu.idi.idatt.model.events.handlers.TileActionHandler;
import edu.ntnu.idi.idatt.model.events.types.DiceRolledEvent;
import edu.ntnu.idi.idatt.model.events.types.PlayerMovedEvent;
import edu.ntnu.idi.idatt.model.events.types.PlayerWonEvent;
import edu.ntnu.idi.idatt.model.events.types.TileActionEvent;
import edu.ntnu.idi.idatt.model.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.model.factory.TileActionFactoryRegistry;
import edu.ntnu.idi.idatt.model.factory.LadderActionFactory;
import edu.ntnu.idi.idatt.model.factory.SnakeActionFactory;
import edu.ntnu.idi.idatt.model.factory.SkipTurnActionFactory;
import edu.ntnu.idi.idatt.model.factory.ResetActionFactory;
import edu.ntnu.idi.idatt.service.BoardService;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.GameService;
import edu.ntnu.idi.idatt.service.PlayerService;
import edu.ntnu.idi.idatt.utils.ViewManager;
import edu.ntnu.idi.idatt.view.BoardSetupView;
import edu.ntnu.idi.idatt.view.GameView;
import edu.ntnu.idi.idatt.view.PlayerSetupView;
import javafx.stage.Stage;

/**
 * Configures the application by initializing dependencies and registering scenes.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class AppInitializer {
    private final ViewManager viewManager;

    public AppInitializer(Stage primaryStage) {
        // FACTORY REGISTRY + FACTORIES
        TileActionFactoryRegistry factoryRegistry = new TileActionFactoryRegistry();
        factoryRegistry.registerDestinationFactory(new LadderActionFactory());
        factoryRegistry.registerNoDestinationFactory(new SkipTurnActionFactory());
        factoryRegistry.registerNoDestinationFactory(new ResetActionFactory());
        factoryRegistry.registerDestinationFactory(new SnakeActionFactory());

        // FILE WRITERS + READERS
        BoardFileWriter boardWriter = new BoardFileWriterGson();
        BoardFileReader boardReader = new BoardFileReaderGson(factoryRegistry);
        PlayerFileReader playerReader = new PlayerFileReader();
        PlayerFileWriter playerWriter = new PlayerFileWriter();
        BoardGameFactory boardGameFactory = new BoardGameFactory(boardReader, boardWriter, factoryRegistry);

        // EVENT BUS
        EventBus eventBus = new DefaultEventBus();

        // GAME CONFIG
        GameConfig gameConfig = new GameConfig();

        // SERVICES
        GameConfigService gameConfigService = new GameConfigService(gameConfig);
        PlayerService playerService = new PlayerService(playerReader, playerWriter);
        BoardService boardService = new BoardService(boardGameFactory);

        // VIEWS + CONTROLLERS
        viewManager = new ViewManager(primaryStage);

        PlayerSetupView playerSetupView = new PlayerSetupView();
        PlayerSetupController playerSetupController = new PlayerSetupController(
              playerSetupView, playerService, gameConfigService, viewManager
        );
        playerSetupView.setController(playerSetupController);

        BoardSetupView boardSetupView = new BoardSetupView();
        BoardSetupController boardSetupController = new BoardSetupController(
              boardSetupView, boardService, gameConfigService, viewManager
        );
        boardSetupView.setController(boardSetupController);

        // GAME SERVICE
        GameService gameService = new GameService(gameConfig, eventBus);

        GameView gameView = new GameView();
        SnakesAndLaddersController boardGameController = new SnakesAndLaddersController(
              gameConfigService, gameService, gameView);
        gameView.setController(boardGameController);

        viewManager.add(playerSetupView );
        viewManager.add(boardSetupView);
        viewManager.add(gameView);

        // GAME EVENTS + HANDLERS
        eventBus.register(DiceRolledEvent.class, new DiceRolledHandler(boardGameController));
        eventBus.register(PlayerMovedEvent.class, new PlayerMovedHandler(boardGameController));
        eventBus.register(TileActionEvent.class, new TileActionHandler(boardGameController));
        eventBus.register(PlayerWonEvent.class, new PlayerWonHandler(boardGameController));
    }

    public ViewManager getDisplayManager() {
        return viewManager;
    }
}
