package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.config.AppInitializer;
import edu.ntnu.idi.idatt.model.enums.GameType;
import edu.ntnu.idi.idatt.utils.ViewManager;
import edu.ntnu.idi.idatt.view.GameSelectionView;
import edu.ntnu.idi.idatt.view.PlayerSetupView;

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
      viewManager.switchTo(PlayerSetupView.class.getName());
    } catch (Exception e) {
      // view.showError("Failed to initialize game: " + e.getMessage());
    }
  }
}
