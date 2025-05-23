package edu.ntnu.idi.idatt.config.initializer;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.persistence.reader.PlayerFileReader;
import edu.ntnu.idi.idatt.persistence.writer.PlayerFileWriter;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.ManualService;
import edu.ntnu.idi.idatt.service.PlayerService;
import edu.ntnu.idi.idatt.ui.controller.PlayerSetupController;
import edu.ntnu.idi.idatt.ui.view.PlayerSetupView;
import edu.ntnu.idi.idatt.utils.ViewManager;

/**
 * Abstract base class to factor out common setup steps across different games.
 * Provides methods to configure player setup, board configuration, game logic, and event handling.
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public abstract class BaseGameInitializer implements GameInitializer {
  protected final GameConfig config;
  protected final ViewManager viewManager;
  protected final EventBus eventBus;
  protected final GameConfigService configService;
  protected final PlayerService playerService;
  protected final ManualService manualService;

  /**
   * Constructs a new BaseGameInitializer with the provided application initializer.
   *
   * @param appInitializer the application initializer that provides shared dependencies.
   */
  protected BaseGameInitializer(AppInitializer appInitializer) {
    this.config = new GameConfig();
    this.viewManager = appInitializer.getViewManager();
    this.eventBus = appInitializer.getEventBus();
    this.configService = new GameConfigService(config);
    this.playerService = new PlayerService(
          new PlayerFileReader(), new PlayerFileWriter(), Player::new
    );
    this.manualService = new ManualService();
  }

  /**
   * Initializes the game by configuring player setup, board configuration, game logic, and events.
   */
  @Override
  public void initialize() {
    configurePlayerSetup();
    configureBoardConfig();
    configureGame();
    registerEvents();
  }

  /**
   * Configures the player setup by creating and adding the player setup view and controller.
   */
  private void configurePlayerSetup() {
    PlayerSetupView view = new PlayerSetupView();
    PlayerSetupController controller =
          new PlayerSetupController(view, playerService, configService, viewManager);
    view.setController(controller);
    viewManager.add(view);
  }

  /**
   * Configures the board setup for the game.
   * This method must be implemented by subclasses to define specific board configuration logic.
   */
  protected abstract void configureBoardConfig();

  /**
   * Configures the main game logic and view.
   * This method must be implemented by subclasses to define specific game setup logic.
   */
  protected abstract void configureGame();

  /**
   * Registers event handlers for the game.
   * This method must be implemented by subclasses to define specific event handling logic.
   */
  protected abstract void registerEvents();
}