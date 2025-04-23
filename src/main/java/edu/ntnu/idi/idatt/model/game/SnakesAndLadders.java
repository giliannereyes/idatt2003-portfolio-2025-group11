package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.entities.Dice;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.model.events.types.DiceRolledEvent;
import edu.ntnu.idi.idatt.model.events.bus.EventBus;
import edu.ntnu.idi.idatt.model.events.types.PlayerMovedEvent;
import edu.ntnu.idi.idatt.model.events.types.TileActionEvent;

import java.util.List;

/**
 * Represents a game of Snakes and Ladders.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class SnakesAndLadders extends BoardGame {
    /**
     * Constructs a SnakesAndLadders instance.
     */
    public SnakesAndLadders(Board board, List<Player> players, Dice dice, EventBus eventBus) {
        super(board, players, dice, eventBus);
    }

    /**
     * Plays a turn for a player. The player rolls the dice, moves, and
     * triggers the action of the tile they land on.
     *
     * @param player is the player taking their turn.
     */
    @Override
    protected void playTurn(Player player) {
        int diceRollTotal = dice.roll();
        int diceRoll2 = dice.getDie(1);
        int diceRoll1 = dice.getDie(0);
        publishDiceRoll(player, diceRoll1, diceRoll2);
        Tile fromTile= player.getCurrentTile();
        player.move(diceRollTotal);
        Tile firstDestinationTile = player.getCurrentTile();
        publishPlayerMoved(player, fromTile, firstDestinationTile);
        firstDestinationTile.landPlayer(player);
        publishTileAction(player, firstDestinationTile);
        /*
        Tile postActionDestination = player.getCurrentTile();
        publishPlayerMoved(player, firstDestinationTile, postActionDestination);
         */
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

    /**
     * Publishes a DiceRolledEvent to the event bus.
     *
     * @param player is the player that rolled the dice.
     * @param diceRoll1 is the result of the roll of the first die.
     * @param diceRoll2 is the result of the roll of the second die.
     */
    private void publishDiceRoll(Player player, int diceRoll1, int diceRoll2) {
        eventBus.publish(new DiceRolledEvent(player, diceRoll1, diceRoll2));
    }

    /**
     * Publishes a PlayerMovedEvent to the event bus.
     *
     * @param player is the player that moved.
     * @param currentTile is the tile the player was on.
     * @param destinationTile is the tile the player moved to.
     */
    private void publishPlayerMoved(Player player, Tile currentTile, Tile destinationTile) {
        if (currentTile != destinationTile) {
            eventBus.publish(new PlayerMovedEvent(player, currentTile, destinationTile));
        }
    }

    /**
     * Publishes a TileActionEvent to the event bus.
     *
     * @param player is the player that landed on the tile.
     * @param destinationTile is the tile the player landed on.
     */
    private void publishTileAction(Player player, Tile destinationTile) {
        if (destinationTile.getLandAction().isPresent()) {
            eventBus.publish(new TileActionEvent(player, destinationTile));
        }
    }
}
