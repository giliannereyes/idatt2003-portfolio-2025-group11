package edu.ntnu.idi.idatt.ui.controller.snakesandladders;

import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.event.common.*;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.snakesandladders.LaddersGameService;
import edu.ntnu.idi.idatt.ui.controller.BoardGameController;
import edu.ntnu.idi.idatt.ui.view.snakesandladders.GameView;
import java.nio.file.Path;
import java.util.List;

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

  public SnakesAndLaddersController(
        GameConfigService gameConfigService, LaddersGameService laddersGameService, GameView view
  ) {
    this.gameConfigService = gameConfigService;
    this.laddersGameService = laddersGameService;
    this.view = view;
  }

  @Override
  public void onDiceRolled(DiceRolledEvent e) {
    view.updateDice(e.roll1(), e.roll2());
  }

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

  @Override
  public void onTileAction(TileActionEvent e) {
    /*
    PauseTransition afterAction = new PauseTransition(Duration.seconds(2.0));
    afterAction.setOnFinished(evt -> {
      view.setStatusLabel("Player " + player.getName() + " landed on a" + action.getActionType());
      action.getDestinationTile().ifPresent(dest -> view.movePlayerToken(
            player.getName(),
            dest.getX(), dest.getY()
      ));
    });
    afterAction.play();
     */
  }

  public void onDiceClicked() {
    laddersGameService.onDiceClicked();
  }

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
        playerConfigs.forEach(playerConfig -> view.registerPlayerTokens(
              playerConfig.getPlayer().getName(),
              Path.of(playerConfig.getTokenImagePath()))
        );
        laddersGameService.startGame();
      }
    } catch (Exception e) {
      view.onErrorInitializingGame(e.getMessage());
    }
  }
}
