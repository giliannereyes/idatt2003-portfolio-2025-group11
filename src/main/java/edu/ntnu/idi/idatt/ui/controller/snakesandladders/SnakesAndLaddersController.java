package edu.ntnu.idi.idatt.ui.controller.snakesandladders;

import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.event.common.*;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.snakesandladders.LaddersGameService;
import edu.ntnu.idi.idatt.ui.controller.BoardGameController;
import edu.ntnu.idi.idatt.ui.view.snakesandladders.GameView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.List;

/**
 * Controller class for managing the Snakes and Ladders game logic and interactions.
 * Implements various event listeners to respond to game events and update the view accordingly.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 * @author Trang Duong
 */
public class SnakesAndLaddersController implements
      BoardGameController,
      DiceRolledListener,
      PlayerMovedListener,
      PlayerWonListener,
      TileActionListener
{
  GameView view;
  GameConfigService gameConfigService;
  LaddersGameService laddersGameService;

  /**
   * Constructs a new SnakesAndLaddersController with the given services and view.
   *
   * @param gameConfigService the service managing game configuration
   * @param laddersGameService the service handling game logic for Snakes and Ladders
   * @param view the view interface for updating the UI
   */
  public SnakesAndLaddersController(
        GameConfigService gameConfigService, LaddersGameService laddersGameService, GameView view
  ) {
    this.gameConfigService = gameConfigService;
    this.laddersGameService = laddersGameService;
    this.view = view;
  }

  /**
   * Handles the event when the dice is rolled.
   * Updates the view with the result of the dice roll.
   *
   * @param e the dice rolled event containing the roll values
   */
  @Override
  public void onDiceRolled(DiceRolledEvent e) {
    view.updateDice(e.roll1(), e.roll2());
  }

  /**
   * Handles the event when a player moves.
   * Updates the status label and moves the player's token on the board.
   *
   * @param e the player moved event containing player and tile information
   */
  @Override
  public void onPlayerMoved(PlayerMovedEvent e) {
    Player player = e.player();
    Tile fromTile = e.fromTile();
    Tile destinationTile = e.destinationTile();
    view.setStatusLabel(
          "Player " + player.getName() + " moved from "
                + fromTile.getTileId() + " to " + destinationTile.getTileId()
    );
    view.movePlayerToken(player.getName(), destinationTile.getX(), destinationTile.getY());
  }

  /**
   * Handles the event when a player moves.
   * Updates the status label and moves the player's token on the board.
   *
   * @param e the player moved event containing player and tile information
   */
  @Override
  public void onTileAction(TileActionEvent e) {
    Player player = e.player();
    TileAction action = e.tile().getLandAction().orElse(null);
    PauseTransition afterAction = new PauseTransition(Duration.seconds(2.0));
    afterAction.setOnFinished(evt -> {
      view.setStatusLabel("Player " + player.getName() + " landed on a" + action.getActionType());
      action.getDestinationTile().ifPresent(dest -> view.movePlayerToken(
            player.getName(),
            dest.getX(), dest.getY()
      ));
    });
    afterAction.play();
  }

  /**
   * Called when the dice is clicked by the user.
   * Delegates the action to the game service.
   */
  public void onDiceClicked() {
    laddersGameService.onDiceClicked();
  }

  /**
   * Handles the event when a player wins the game.
   * Notifies the view to display the winner.
   *
   * @param e the player won event containing the winning player
   */
  @Override
  public void onPlayerWon(PlayerWonEvent e) {
    view.onPlayerWon(e.winner().getName());
  }

  /**
   * Initializes the game view with the board and player tokens.
   */
  @Override
  public void initialize() {
    try {
      if (gameConfigService.isConfigComplete()) {
        view.registerBoard(gameConfigService.build().getBoard());
        List<PlayerConfig> playerConfigs = gameConfigService.build().getPlayerConfigs();
        playerConfigs.forEach(playerConfig -> {
                  view.registerPlayerTokens(
                          playerConfig.getPlayer().getName(), playerConfig.getTokenImagePath());
                }
        );
        laddersGameService.startGame();
      }
    } catch (Exception e) {
      view.onErrorInitializingGame(e.getMessage());
    }
  }
}
