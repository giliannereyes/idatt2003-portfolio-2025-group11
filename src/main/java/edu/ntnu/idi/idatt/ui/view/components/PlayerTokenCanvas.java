package edu.ntnu.idi.idatt.ui.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class PlayerTokenCanvas {
    private final Pane tokenPane = new Pane();
    private final Map<String, ImageView> playerTokens = new HashMap<>();

    public Pane getTokenPane() {
        return tokenPane;
    }

    public void addPlayerToken(String playerName, Path imagePath) {
        InputStream is = getClass().getResourceAsStream(imagePath.toString());
        if (is == null) {
            throw new RuntimeException("Path to image for player token not found: " + imagePath);
        } else {
            Image tokenImage = new Image(is, 30, 30, true, true);
            ImageView token = new ImageView(tokenImage);
            token.setFitWidth(30);
            token.setFitHeight(30);
            playerTokens.put(playerName, token);
            tokenPane.getChildren().add(token);
        }
    }

    public void animateTokenMovement(String playerName,
                                     double gridX, double gridY,
                                     int totalRows, int totalCols) {
        ImageView token = playerTokens.get(playerName);
        if (token == null) return;

        double paneW = tokenPane.getWidth();
        double paneH = tokenPane.getHeight();
        double tileW = paneW  / totalCols;
        double tileH = paneH  / totalRows;

        double centerX = gridX * tileW + tileW / 2;
        double centerY = (totalRows - 1 - gridY) * tileH + tileH / 2;

        double targetX = centerX - token.getFitWidth()  / 2;
        double targetY = centerY - token.getFitHeight() / 2;

        token.setLayoutX(targetX);
        token.setLayoutY(targetY);
    }
}
