package edu.ntnu.idi.idatt.model.events;

import edu.ntnu.idi.idatt.model.actions.*;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * A logger that listens for game events and prints them with terminal-compatible symbols.
 *
 * @version 0.3
 * @since 0.1
 * @author Trang Duong
 */
public class GameLogger implements GameEventListener {
    private static final Map<Class<? extends TileAction>, String> actionMessages = new HashMap<>();

    static {
        actionMessages.put(LadderAction.class, "[LADDER] %s climbed a ladder to Tile %d");
        actionMessages.put(SnakeAction.class, "[SNAKE] %s got caught by a snake and moved to Tile %d");
        actionMessages.put(SkipTurnAction.class, "[SKIP] %s landed on a Skip Turn tile and will miss their next turn.");
        actionMessages.put(StepBackAction.class, "[BACK] %s landed on a Step Back tile and moved to Tile %d");
    }

    @Override
    public void handleGameEvent(GameEvent event) {
        if (event instanceof DiceRolledEvent diceEvent) {
            log("[DICE] %s rolled a %d", diceEvent.getPlayer().getName(), diceEvent.getRoll());
        }
        else if (event instanceof PlayerMovedEvent moveEvent) {
            log("[MOVE] %s moved from Tile %d to Tile %d",
                    moveEvent.getPlayer().getName(), moveEvent.getFromTileId(), moveEvent.getToTileId());
        }
        else if (event instanceof TileActionEvent actionEvent) {
            handleTileAction(actionEvent);
        }
    }

    private void handleTileAction(TileActionEvent event) {
        Player player = event.getPlayer();
        Tile tile = event.getTile();
        TileAction action = tile.getLandAction();

        if (action != null) {
            String message = actionMessages.getOrDefault(
                    action.getClass(),
                    "[TILE] %s landed on a normal tile %d"
            );
            log(message, player.getName(), player.getCurrentTile().getTileId());
        } else {
            log("[TILE] %s landed on a normal tile %d", player.getName(), player.getCurrentTile().getTileId());
        }
    }

    private void log(String message, Object... args) {
        System.out.println("[LOG] " + String.format(message, args));
    }
}
