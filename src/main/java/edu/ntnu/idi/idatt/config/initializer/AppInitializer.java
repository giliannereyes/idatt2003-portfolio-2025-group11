package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.domain.enums.GameType;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.common.DefaultEventBus;
import edu.ntnu.idi.idatt.domain.event.common.DiceRolledEvent;
import edu.ntnu.idi.idatt.domain.event.common.DiceRolledHandler;
import edu.ntnu.idi.idatt.domain.event.common.PlayerMovedEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerMovedHandler;
import edu.ntnu.idi.idatt.domain.event.common.TileActionEvent;
import edu.ntnu.idi.idatt.domain.event.common.TileActionHandler;
import edu.ntnu.idi.idatt.domain.event.common.PlayerWonEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerWonHandler;
import edu.ntnu.idi.idatt.domain.event.common.GameEventListener;
import edu.ntnu.idi.idatt.ui.controller.GameSelectionController;
import edu.ntnu.idi.idatt.ui.view.GameSelectionView;
import edu.ntnu.idi.idatt.ui.view.PlayerSetupView;
import edu.ntnu.idi.idatt.ui.view.FileBoardConfigView;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.ui.view.monopoly.MonopolyGameView;
import edu.ntnu.idi.idatt.ui.view.snakesandladders.GameView;
import edu.ntnu.idi.idatt.utils.ViewManager;
import javafx.stage.Stage;

/**
 * Configures the application by initializing dependencies and registering scenes.
 */
public class AppInitializer {
  private final ViewManager viewManager;
  private final EventBus eventBus;

  public AppInitializer(Stage primaryStage) {
    this.eventBus = new DefaultEventBus();
    this.viewManager = new ViewManager(primaryStage);
    initGameSelection();
  }

  private void initGameSelection() {
    GameSelectionView view = new GameSelectionView();
    GameSelectionController controller =
          new GameSelectionController(viewManager, view, this);
    view.setController(controller);
    viewManager.add(view);
  }

  public void initializeGame(GameType gameType) {
    viewManager.registerTransition(GameSelectionView.class, PlayerSetupView.class);
    switch (gameType) {
      case SNAKES_AND_LADDERS -> {
        new SnakesAndLaddersInitializer().initialize(this);
        viewManager.registerTransition(PlayerSetupView.class, FileBoardConfigView.class);
        viewManager.registerTransition(FileBoardConfigView.class, GameView.class);
      }
      case MONOPOLY -> {
        new MonopolyInitializer().initialize(this);
        viewManager.registerTransition(PlayerSetupView.class, BoardConfigView.class);
        viewManager.registerTransition(BoardConfigView.class, MonopolyGameView.class);
      }
      default -> throw new IllegalArgumentException(
            "Unsupported game type: " + gameType);
    }
  }

  public void registerBasicEvents(GameEventListener listener) {
    eventBus.register(DiceRolledEvent.class, new DiceRolledHandler(listener));
    eventBus.register(PlayerMovedEvent.class, new PlayerMovedHandler(listener));
    eventBus.register(TileActionEvent.class, new TileActionHandler(listener));
    eventBus.register(PlayerWonEvent.class, new PlayerWonHandler(listener));
  }

  public ViewManager getViewManager() {
    return viewManager;
  }

  public EventBus getEventBus() {
    return eventBus;
  }
}
