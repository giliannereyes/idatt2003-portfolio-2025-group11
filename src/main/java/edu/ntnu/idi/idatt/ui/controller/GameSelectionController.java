package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.config.initializer.AppInitializer;
import edu.ntnu.idi.idatt.domain.enums.GameType;
import edu.ntnu.idi.idatt.utils.ViewManager;
import edu.ntnu.idi.idatt.ui.view.GameSelectionView;

public class GameSelectionController {
  private final GameSelectionView view;
  private final ViewManager viewManager;
  private final AppInitializer appInitializer;

  public GameSelectionController(
        ViewManager viewManager, GameSelectionView view, AppInitializer appInitializer
  ) {
    this.view = view;
    this.viewManager = viewManager;
    this.appInitializer = appInitializer;
  }

  public void selectGame(GameType gameType) {
    try {
      appInitializer.initializeGame(gameType);
      viewManager.switchToNextView();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      // view.showError("Failed to initialize game: " + e.getMessage());
    }
  }
}
