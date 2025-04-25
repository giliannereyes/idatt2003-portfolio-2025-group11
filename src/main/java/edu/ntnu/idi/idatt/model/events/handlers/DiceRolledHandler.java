package edu.ntnu.idi.idatt.model.events.handlers;

import edu.ntnu.idi.idatt.controller.GameEventListener;
import edu.ntnu.idi.idatt.model.events.types.DiceRolledEvent;

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
