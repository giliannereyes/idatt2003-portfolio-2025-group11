package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.snakesandladders.GameService;
import edu.ntnu.idi.idatt.ui.view.GameView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.nio.file.Path;
import java.util.List;

public class SnakesAndLaddersController implements BoardGameController, GameEventListener {
    GameView view;
    GameConfigService gameConfigService;
    GameService gameService;

    public SnakesAndLaddersController(
          GameConfigService gameConfigService, GameService gameService, GameView view
    ) {
        this.gameConfigService = gameConfigService;
        this.gameService = gameService;
        this.view = view;
    }

    @Override
    public void onDiceRolled(Player player, int diceRoll1, int diceRoll2) {
        view.updateDice(diceRoll1, diceRoll2);
    }

    @Override
    public void onPlayerMoved(Player player, Tile fromTile, Tile destinationTile) {
        view.setStatusLabel(
              "Player " + player.getName() + " moved from "
                    + fromTile.getTileId() + " to " + destinationTile.getTileId()
        );
        view.movePlayerToken(player.getName(), destinationTile.getX(), destinationTile.getY());
    }

    @Override
    public void onTileAction(Player player, TileAction action) {
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

    public void onDiceClicked() {
        gameService.onDiceClicked();
    }

    @Override
    public void onPlayerWon(Player player) {
        view.onPlayerWon(player.getName());
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
               gameService.startGame();
           }
       } catch (Exception e) {
           view.onErrorInitializingGame(e.getMessage());
       }
    }
}
