package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

public class PlayerBankruptEvent implements GameEvent {
    private final Player player;

    public PlayerBankruptEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
