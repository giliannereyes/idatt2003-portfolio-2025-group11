package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.entities.Dice;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.model.events.DiceRolledEvent;
import edu.ntnu.idi.idatt.model.events.PlayerMovedEvent;
import edu.ntnu.idi.idatt.model.events.TileActionEvent;

import java.util.List;

/**
 * Represents a game of Snakes and Ladders.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class SnakesAndLadders extends BoardGame {
    /**
     * Constructs a SnakesAndLadders instance.
     */
    public SnakesAndLadders(Board board, List<Player> players, Dice dice) {
        super(board, players, dice);
    }

    /**
     * Plays a turn for a player. The player rolls the dice, moves, and
     * triggers the action of the tile they land on.
     *
     * @param player is the player taking their turn.
     */
    @Override
    protected void playTurn(Player player) {
        int diceRoll = dice.roll();
        notifyListeners(new DiceRolledEvent(player, diceRoll));
        Tile fromTile= player.getCurrentTile();
        player.move(diceRoll);
        Tile destinationTile = player.getCurrentTile();
        notifyListeners(new PlayerMovedEvent(player, fromTile.getTileId(), destinationTile.getTileId()));
        notifyListeners(new TileActionEvent(player, destinationTile));
    }

    /**
     * Checks if the game is over by checking if any player has reached the last tile.
     *
     * @return true if the game is over, false otherwise.
     */
    @Override
    protected boolean isGameOver() {
        for (Player player : players) {
            if (player.getCurrentTile().isLastTile()) {
                winner = player;
                return true;
            }
        }
        return false;
    }
}
