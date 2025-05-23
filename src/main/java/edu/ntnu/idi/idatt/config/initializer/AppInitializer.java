package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.domain.game.GameType;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.common.DefaultEventBus;
import edu.ntnu.idi.idatt.ui.controller.GameSelectionController;
import edu.ntnu.idi.idatt.ui.view.GameSelectionView;
import edu.ntnu.idi.idatt.ui.view.PlayerSetupView;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.ui.view.monopoly.MonopolyView;
import edu.ntnu.idi.idatt.ui.view.laddersgame.LaddersGameView;
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
    this.viewManager = new ViewManager(primaryStage, "/css/style.css", 1200, 800);
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
        new LaddersGameInitializer().initialize(this);
        viewManager.registerTransition(PlayerSetupView.class, BoardConfigView.class);
        viewManager.registerTransition(BoardConfigView.class, LaddersGameView.class);
      }
      case MONOPOLY -> {
        new MonopolyInitializer().initialize(this);
        viewManager.registerTransition(PlayerSetupView.class, BoardConfigView.class);
        viewManager.registerTransition(BoardConfigView.class, MonopolyView.class);
      }
      default -> throw new IllegalArgumentException(
            "Unsupported game type: " + gameType);
    }
  }

  public ViewManager getViewManager() {
    return viewManager;
  }

  public EventBus getEventBus() {
    return eventBus;
  }
}
