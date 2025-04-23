package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.model.entities.Dice;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.events.bus.EventBus;
import edu.ntnu.idi.idatt.model.game.SnakesAndLadders;

import java.util.List;
import java.util.stream.Collectors;

public class GameService {
  private final GameConfig config;
  private final EventBus eventBus;
  private SnakesAndLadders game;

  public GameService(GameConfig config, EventBus eventBus) {
    this.config = config;
    this.eventBus = eventBus;
  }

  /**
   * Initializes and starts the game loop based on the config.
   * Throws RuntimeException if the config is incomplete.
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
   */
  public void onDiceClicked() {
    game.playNextTurn();
  }
}
