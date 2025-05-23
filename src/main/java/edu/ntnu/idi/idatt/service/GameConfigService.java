package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.List;

/**
 * Service class responsible for managing and updating the {@link GameConfig} object.
 * Provides methods to build the configuration, update the board and player settings,
 * and check if the configuration is complete.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class GameConfigService {
  private final GameConfig gameConfig;

  /**
   * Constructs a new GameConfigService with the specified {@link GameConfig}.
   *
   * @param gameConfig the game configuration to manage
   */
  public GameConfigService(GameConfig gameConfig) {
    Validation.validateNonNull(gameConfig, "gameConfig");
    this.gameConfig = gameConfig;
  }

  /**
   * Returns the current game configuration.
   *
   * @return the {@link GameConfig} instance
   */
  public GameConfig getGameConfig() {
    return gameConfig;
  }

  /**
   * Updates the board configuration in the game config.
   *
   * @param board the new {@link Board} to set
   */
  public void updateBoard(Board board) {
    gameConfig.setBoard(board);
  }

  /**
   * Updates the list of player configurations in the game config.
   *
   * @param playerConfigs a list of {@link PlayerConfig} instances
   */
  public void updatePlayerConfigs(List<PlayerConfig> playerConfigs) {
    gameConfig.setPlayerConfigs(playerConfigs);
  }

  /**
   * Checks whether the game configuration is complete and ready to use.
   *
   * @return {@code true} if the configuration is complete, {@code false} otherwise
   */
  public boolean isConfigComplete() {
    return gameConfig.isComplete();
  }
}
