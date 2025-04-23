package edu.ntnu.idi.idatt.model.enums;

import javafx.scene.paint.Color;

public enum TileColorType {
    LADDER_START(Color.GREEN),
    LADDER_END(Color.LIGHTGREEN),
    SNAKE_START(Color.web("#982020")),
    SNAKE_END(Color.web("#f73333")),
    RESET(Color.YELLOW),
    SKIP_TURN(Color.VIOLET),
    DEFAULT_DARK(Color.LIGHTGREY),
    DEFAULT_LIGHT(Color.web("#F0F0F0"));

    private final Color color;

    TileColorType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
