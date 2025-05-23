package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.common.DefaultEventBus;
import edu.ntnu.idi.idatt.domain.game.GameType;
import edu.ntnu.idi.idatt.ui.controller.GameSelectionController;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.ui.view.GameSelectionView;
import edu.ntnu.idi.idatt.ui.view.PlayerSetupView;
import edu.ntnu.idi.idatt.ui.view.laddersgame.LaddersGameView;
import edu.ntnu.idi.idatt.ui.view.monopoly.MonopolyView;
import edu.ntnu.idi.idatt.utils.ViewManager;
import javafx.stage.Stage;

/**
 * Configures the application by initializing dependencies and registering scenes.
 * This class is responsible for setting up the main application components,
 * including the event bus, view manager, and game-specific initializers.
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public class AppInitializer {
  private final ViewManager viewManager;
  private final EventBus eventBus;

  /**
   * Constructs a new AppInitializer.
   *
   * @param primaryStage the primary stage of the JavaFX application.
   */
  public AppInitializer(Stage primaryStage) {
    this.eventBus = new DefaultEventBus();
    this.viewManager = new ViewManager(primaryStage, "/css/style.css", 1000, 700);
    initGameSelection();
  }

  /**
   * Initializes the game selection view and controller.
   * This method sets up the initial scene where the user can select a game type.
   */
  private void initGameSelection() {
    GameSelectionView view = new GameSelectionView();
    GameSelectionController controller =
          new GameSelectionController(viewManager, view, this);
    view.setController(controller);
    viewManager.add(view);
  }

  /**
   * Initializes the selected game based on the provided game type.
   * This method sets up the transitions between views and initializes the game-specific logic.
   *
   * @param gameType the type of game to initialize (e.g., LADDERS_GAME, MONOPOLY).
   * @throws IllegalArgumentException if the game type is unsupported.
   */
  public void initializeGame(GameType gameType) {
    viewManager.registerTransition(GameSelectionView.class, PlayerSetupView.class);
    switch (gameType) {
      case LADDERS_GAME -> {
        new LaddersGameInitializer(this).initialize();
        viewManager.registerTransition(PlayerSetupView.class, BoardConfigView.class);
        viewManager.registerTransition(BoardConfigView.class, LaddersGameView.class);
      }
      case MONOPOLY -> {
        new MonopolyInitializer(this).initialize();
        viewManager.registerTransition(PlayerSetupView.class, BoardConfigView.class);
        viewManager.registerTransition(BoardConfigView.class, MonopolyView.class);
      }
      default -> throw new IllegalArgumentException(
            "Unsupported game type: " + gameType);
    }
  }

  /**
   * Gets the view manager used to manage and transition between views.
   *
   * @return the view manager.
   */
  public ViewManager getViewManager() {
    return viewManager;
  }

  /**
   * Gets the event bus used for event handling and communication between components.
   *
   * @return the event bus.
   */
  public EventBus getEventBus() {
    return eventBus;
  }
}