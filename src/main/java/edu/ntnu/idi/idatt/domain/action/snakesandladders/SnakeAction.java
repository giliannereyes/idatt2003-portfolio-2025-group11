package edu.ntnu.idi.idatt.domain.action.snakesandladders;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.utils.Validation;

import java.util.Optional;

/**
 * SnakeAction class is a class that represents the action of a snake tile.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class SnakeAction implements TileAction {
    public static final String actionType = "SnakeAction";
    private final Tile destinationTile;

    /**
     * Constructs a SnakeAction instance.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     *
     * @throws IllegalArgumentException if the destination tile is null.
     */
    public SnakeAction(Tile destinationTile) {
        Validation.validateNonNull(destinationTile, "Destination tile");
        this.destinationTile = destinationTile;
    }

    /**
     * Moves the player to the destination tile.
     *
     * @param player is the player that landed on the tile.
     *
     * @throws IllegalArgumentException if the player is null or the player is attempting to climb up.
     */
    @Override
    public void perform(Player player) {
        Validation.validateNonNull(player, "Player");
        if (player.getCurrentTile() != null
                && player.getCurrentTile().getTileId() < destinationTile.getTileId()
        ) {
            throw new IllegalStateException("Player should not be able to climb up a snake.");
        }
        player.placeOnTile(destinationTile);
    }

    /**
     * Retrieves the type of the action, which is "SnakeAction".
     *
     * @return the type of the action.
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Retrieves the destination tile of the action.
     *
     * @return an {@link Optional} containing the destination tile.
     */
    public Optional<Tile> getDestinationTile() {
        return Optional.of(destinationTile);
    }
}