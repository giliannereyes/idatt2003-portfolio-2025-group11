package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.event.EventHandler;
import edu.ntnu.idi.idatt.ui.controller.GameEventListener;

public class DiceRolledHandler implements EventHandler<DiceRolledEvent> {
    private final GameEventListener listener;
    public DiceRolledHandler(GameEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void handle(DiceRolledEvent event) {
        listener.onDiceRolled(event.getPlayer(), event.getRoll1(), event.getRoll2());
    }
}
