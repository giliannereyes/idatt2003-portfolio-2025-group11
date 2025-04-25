package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.controller.*;
import edu.ntnu.idi.idatt.io.*;
import edu.ntnu.idi.idatt.model.enums.GameType;
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
import edu.ntnu.idi.idatt.view.GameSelectionView;
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
    private final GameConfig gameConfig;
    private final GameConfigService gameConfigService;
    private final PlayerService playerService;
    private final BoardService boardService;
    private final EventBus eventBus;

    public AppInitializer(Stage primaryStage) {
        // FACTORY SETUP
        TileActionFactoryRegistry factoryRegistry = new TileActionFactoryRegistry();
        factoryRegistry.registerDestinationFactory(new LadderActionFactory());
        factoryRegistry.registerNoDestinationFactory(new SkipTurnActionFactory());
        factoryRegistry.registerNoDestinationFactory(new ResetActionFactory());
        factoryRegistry.registerDestinationFactory(new SnakeActionFactory());

        BoardFileWriter boardWriter = new BoardFileWriterGson();
        BoardFileReader boardReader = new BoardFileReaderGson(factoryRegistry);
        BoardGameFactory boardGameFactory = new BoardGameFactory(boardReader, boardWriter, factoryRegistry);

        PlayerFileReader playerReader = new PlayerFileReader();
        PlayerFileWriter playerWriter = new PlayerFileWriter();

        eventBus = new DefaultEventBus();
        gameConfig = new GameConfig();
        gameConfigService = new GameConfigService(gameConfig);
        playerService = new PlayerService(playerReader, playerWriter);
        boardService = new BoardService(boardGameFactory);

        viewManager = new ViewManager(primaryStage);

        // Game Selection Screen
        GameSelectionView selectionView = new GameSelectionView();
        GameSelectionController selectionController = new GameSelectionController(viewManager, selectionView, this);
        selectionView.setController(selectionController);
        viewManager.add(selectionView);
    }

    public void initializeGame(GameType gameType) {
        switch (gameType) {
            case SNAKES_AND_LADDERS -> initSnakesAndLadders();
            case LUDO -> initLudo();
            default -> throw new IllegalArgumentException("Unsupported game type: " + gameType);
        }
    }

    private void initSnakesAndLadders() {
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

        GameService gameService = new GameService(gameConfig, eventBus);
        GameView gameView = new GameView();
        SnakesAndLaddersController gameController = new SnakesAndLaddersController(
              gameConfigService, gameService, gameView
        );
        gameView.setController(gameController);

        viewManager.add(playerSetupView);
        viewManager.add(boardSetupView);
        viewManager.add(gameView);

        eventBus.register(DiceRolledEvent.class, new DiceRolledHandler(gameController));
        eventBus.register(PlayerMovedEvent.class, new PlayerMovedHandler(gameController));
        eventBus.register(TileActionEvent.class, new TileActionHandler(gameController));
        eventBus.register(PlayerWonEvent.class, new PlayerWonHandler(gameController));
    }

    private void initLudo() {
        /*
        LudoSetupView ludoSetupView = new LudoSetupView();
        LudoSetupController ludoController = new LudoSetupController(
              ludoSetupView, playerService, gameConfigService, viewManager
        );
        ludoSetupView.setController(ludoController);
        viewManager.add(ludoSetupView);
        viewManager.activate(LudoSetupView.class);
         */
    }

    public ViewManager getViewManager() {
        return viewManager;
    }
}
