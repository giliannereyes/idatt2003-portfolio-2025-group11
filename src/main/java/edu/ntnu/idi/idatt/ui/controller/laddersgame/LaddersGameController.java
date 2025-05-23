package edu.ntnu.idi.idatt.ui.controller.laddersgame;

import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.event.common.DiceRolledEvent;
import edu.ntnu.idi.idatt.domain.event.common.DiceRolledListener;
import edu.ntnu.idi.idatt.domain.event.common.PlayerMovedEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerMovedListener;
import edu.ntnu.idi.idatt.domain.event.common.PlayerWonEvent;
import edu.ntnu.idi.idatt.domain.event.common.PlayerWonListener;
import edu.ntnu.idi.idatt.domain.event.common.TileActionEvent;
import edu.ntnu.idi.idatt.domain.event.common.TileActionListener;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.ManualService;
import edu.ntnu.idi.idatt.service.laddersgame.LaddersGameService;
import edu.ntnu.idi.idatt.ui.controller.BoardGameController;
import edu.ntnu.idi.idatt.ui.view.laddersgame.LaddersGameView;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.util.Duration;


/**
 * Controller class for managing the Ladders game logic and interactions.
 * Implements various event listeners to respond to game events and update the view accordingly.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 * @author Trang Duong
 * @see LaddersGameView
 */
public class LaddersGameController implements
      BoardGameController,
      DiceRolledListener,
      PlayerMovedListener,
      PlayerWonListener,
      TileActionListener {
  LaddersGameView view;
  GameConfigService gameConfigService;
  LaddersGameService laddersGameService;
  ManualService manualService;

  /**
   * Constructs a new SnakesAndLaddersController with the given services and view.
   *
   * @param gameConfigService the service managing game configuration.
   * @param laddersGameService the service handling game logic for Ladders game.
   * @param view the view interface for updating the UI.
   */
  public LaddersGameController(
        GameConfigService gameConfigService,
        LaddersGameService laddersGameService,
        LaddersGameView view,
        ManualService manualService
  ) {
    this.gameConfigService = gameConfigService;
    this.laddersGameService = laddersGameService;
    this.manualService = manualService;
    this.view = view;
  }

  /**
   * Handles the event when the dice is rolled.
   * Updates the view with the result of the dice roll.
   *
   * @param e the dice rolled event containing the roll values.
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
    PauseTransition afterAction = new PauseTransition(Duration.seconds(2.0));
    afterAction.setOnFinished(evt -> {
      Player player = e.player();
      view.movePlayerToken(
            player.getName(), player.getCurrentTile().getX(), player.getCurrentTile().getY()
      );
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
        view.registerBoard(gameConfigService.getGameConfig().getBoard());
        String manualText = manualService
              .loadManualText("/userManuals/ladders_game_user_manual.txt");
        view.setUserManualText(manualText);
        List<PlayerConfig> playerConfigs = gameConfigService.getGameConfig().getPlayerConfigs();
        playerConfigs.forEach(playerConfig -> view.registerPlayerTokens(
                playerConfig.getPlayer().getName(), playerConfig.getTokenImagePath())
        );
        laddersGameService.startGame();
      }
    } catch (Exception e) {
      view.onErrorInitializingGame(e.getMessage());
    }
  }
}
