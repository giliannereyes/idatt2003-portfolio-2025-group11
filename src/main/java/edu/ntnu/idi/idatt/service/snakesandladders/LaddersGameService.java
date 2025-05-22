package edu.ntnu.idi.idatt.service.snakesandladders;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Dice;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.game.snakesandladders.SnakesAndLadders;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing the lifecycle and logic of a Snakes and Ladders game.
 * It initializes the game using the provided configuration and handles game-related events.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class LaddersGameService {
  private final GameConfig config;
  private final EventBus eventBus;
  private SnakesAndLadders game;

  /**
   * Constructs a new {@code LaddersGameService}
   * with the specified game configuration and event bus.
   *
   * @param config the configuration for the Snakes and Ladders game, including board and players
   * @param eventBus the event bus used to handle and dispatch game events
   * @throws NullPointerException if {@code config} or {@code eventBus} is {@code null}
   */
  public LaddersGameService(GameConfig config, EventBus eventBus) {
    if (config == null) {
      throw new NullPointerException("GameConfig cannot be null.");
    }
    if (eventBus == null) {
      throw new NullPointerException("EventBus cannot be null.");
    }
    this.config = config;
    this.eventBus = eventBus;
  }

  /**
   * Initializes and starts the game loop based on the config.
   *
   * @throws IllegalStateException if the config is incomplete.
   */
  public void startGame() {
    if (!config.isComplete()) {
      throw new IllegalStateException("Cannot start game: configuration incomplete.");
    }
    List<Player> players = config.getPlayerConfigs().stream()
          .map(PlayerConfig::getPlayer)
          .collect(Collectors.toList());
    Dice dice = new Dice(2);
    this.game = new SnakesAndLadders(config.getBoard(), players, dice, eventBus);
    game.setUpGame();
  }

  /**
   * Plays the next turn when the dice is thrown.
   *
   * @throws NullPointerException if the game has not been started
   */
  public void onDiceClicked() {
    game.playNextTurn();
  }
}
