package edu.ntnu.idi.idatt.model.events;

/**
 * A logger that listens for game events and prints them.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class GameLogger implements GameEventListener {
    @Override
    public void handleGameEvent(GameEvent event) {
        if (event instanceof DiceRolledEvent diceEvent) {
            System.out.println("[LOG] " + diceEvent.getPlayer().getName() + " rolled a " + diceEvent.getRoll());
        } else if (event instanceof PlayerMovedEvent moveEvent) {
            System.out.println("[LOG] " + moveEvent.getPlayer().getName() + " moved from Tile " +
                    moveEvent.getFromTileId() + " to Tile " + moveEvent.getToTileId());
        } else if (event instanceof TileActionEvent actionEvent) {
            System.out.println("[LOG] " + actionEvent.getPlayer().getName() +
                    " landed on Tile " + actionEvent.getTile().getTileId());
        }
    }
}
