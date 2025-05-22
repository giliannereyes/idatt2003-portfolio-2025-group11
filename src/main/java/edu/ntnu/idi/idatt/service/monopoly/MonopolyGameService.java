package edu.ntnu.idi.idatt.service.monopoly;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Dice;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.game.monopoly.Monopoly;
import java.util.List;

/**
 * Service to initialize and drive a Monopoly Lite game.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class MonopolyGameService {
  private final GameConfig config;
  private Monopoly game;
  private final EventBus eventBus;

  /**
   * Constructs a new {@code MonopolyGameService}
   * with the specified game configuration and event bus.
   *
   * @param config the configuration settings for the game, including board setup and rules
   * @param eventBus the event bus used to publish and subscribe to game events
   */
  public MonopolyGameService(GameConfig config, EventBus eventBus) {
    this.config   = config;
    this.eventBus = eventBus;
  }

  /**
   * Initializes and starts the MonopolyGame based on the provided configuration.
   *
   * @throws IllegalStateException if the configuration is incomplete.
   */
  public void startGame() {
    if (!config.isComplete()) {
      throw new IllegalStateException("Cannot start Monopoly: configuration incomplete.");
    }
    // use two dice (standard Monopoly)
    Dice dice = new Dice(2);
    List<Player> players = config.getPlayerConfigs().stream()
          .map(PlayerConfig::getPlayer)
          .toList();

    // build the MonopolyGame with board, players, dice and event bus
    this.game = new Monopoly(
          config.getBoard(),
          players,
          dice,
          eventBus
    );
    // place all players on GO
    game.setUpGame();
  }

  /**
   * Called when the UI signals the dice have been clicked/rolled.
   * Delegates to the game to advance one turn.
   */
  public void onDiceClicked() {
    if (game == null) {
      throw new IllegalStateException("Game has not been started yet.");
    }
    game.playNextTurn();
  }

  /**
   * Allows the specified player to purchase the given property.
   * Delegates the purchase logic to the underlying game engine.
   *
   * @param player the player attempting to buy the property
   * @param property the property to be purchased
   * @throws IllegalArgumentException if the player or property is null
   * @throws IllegalStateException if the property cannot be bought due to game rules
   */
  public void buyProperty(Player player, Property property) {
    game.buyProperty(player, property);
  }
}

