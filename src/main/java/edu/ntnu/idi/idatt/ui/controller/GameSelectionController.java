package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.config.initializer.AppInitializer;
import edu.ntnu.idi.idatt.domain.enums.GameType;
import edu.ntnu.idi.idatt.utils.ViewManager;
import edu.ntnu.idi.idatt.ui.view.GameSelectionView;

/**
 * Controller responsible for handling game selection logic in the UI.
 * It initializes the selected game and transitions to the next view.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class GameSelectionController {
  private final GameSelectionView view;
  private final ViewManager viewManager;
  private final AppInitializer appInitializer;

  /**
   * Constructs a new GameSelectionController.
   *
   * @param viewManager the manager responsible for switching between views
   * @param view the view that allows the user to select a game
   * @param appInitializer the initializer responsible for setting up the selected game
   */
  public GameSelectionController(
        ViewManager viewManager, GameSelectionView view, AppInitializer appInitializer
  ) {
    this.view = view;
    this.viewManager = viewManager;
    this.appInitializer = appInitializer;
  }

  /**
   * Handles the selection of a game type by the user.
   * Initializes the selected game and transitions to the next view.
   * Displays an error message if initialization fails.
   *
   * @param gameType the type of game selected by the user
   */
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
