package edu.ntnu.idi.idatt.ui.view.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * A visual component for managing and animating player tokens on a game board.
 * This class handles the placement, movement, and overlap of tokens on a grid-based layout.
 *
 * @version 0.3
 * @since 0.1
 * @author Gilianne Reyes
 * @author Trang Duong
 */
public class PlayerTokenCanvas {
    private final Pane tokenPane = new Pane();
    private final Map<String, ImageView> playerTokens = new HashMap<>();
    private final Map<String, double[]> playerPosition = new HashMap<>();


    /**
     * Returns the pane that contains all player tokens.
     *
     * @return the token pane
     */
    public Pane getTokenPane() {
        return tokenPane;
    }

    /**
     * Adds a player token to the canvas using the specified image path.
     *
     * @param playerName the name of the player
     * @param imagePath the path to the token image resource
     * @throws RuntimeException if the image resource cannot be found
     */
    public void addPlayerToken(String playerName, String imagePath) {
        InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            throw new RuntimeException("Path to image for player token not found: " + imagePath);
        } else {
            Image tokenImage = new Image(is, 30, 30, true, true);
            ImageView token = new ImageView(tokenImage);
            token.setFitWidth(55);
            token.setFitHeight(50);
            playerTokens.put(playerName, token);
            tokenPane.getChildren().add(token);
        }
    }

    /**
     * Animates the movement of a player's token to a new grid position.
     * Takes into account overlapping tokens and adjusts the final position accordingly.
     *
     * @param playerName the name of the player
     * @param gridX the target column on the grid
     * @param gridY  the target row on the grid
     * @param totalRows the total number of rows in the grid
     * @param totalCols the total number of columns in the grid
     */
    public void animateTokenMovement(String playerName,
                                     double gridX, double gridY,
                                     int totalRows, int totalCols) {
        ImageView token = playerTokens.get(playerName);
        if (token == null) return;

        double paneW = tokenPane.getWidth();
        double paneH = tokenPane.getHeight();
        double tileW = paneW  / totalCols;
        double tileH = paneH  / totalRows;

        double targetX = gridX * tileW + tileW / 2 - token.getFitWidth() / 2;
        double targetY = (totalRows - gridY - 1) * tileH + tileH / 2 - token.getFitHeight() / 2;

        double[] currentPos = playerPosition.getOrDefault(playerName, new double[]{0, 0});
        double currentX = currentPos[0];
        double currentY = currentPos[1];

        double startX = token.getLayoutX();
        double startY = token.getLayoutY();

        int overlapCount = countTokensOnTile(gridX, gridY);
        double offset = overlapCount * 6.0;

        double finalTargetX = targetX + offset;
        double finalTargetY = targetY + offset;

        double diffX = finalTargetX - startX;
        double diffY = finalTargetY - startY;

        int steps = 10;
        double stepX = diffX / steps;
        double stepY = diffY / steps;

        Timeline timeline = new Timeline();
        for (int i = 0; i < steps; i++) {
            final int step = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(70 * i), event -> {
                double newX = startX + step * 1.12 * stepX;
                double newY = startY + step * 1.12 * stepY;
                token.setLayoutX(newX);
                token.setLayoutY(newY);
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setCycleCount(1);
        timeline.play();
        timeline.setOnFinished(event -> {
            playerPosition.put(playerName, new double[]{gridX, gridY});
        });
    }

    /**
     * Counts how many tokens are currently located on the specified tile.
     *
     * @param gridX the column of the tile
     * @param gridY the row of the tile
     * @return the number of tokens on the tile
     */
    private int countTokensOnTile(double gridX, double gridY) {
        int count = 0;
        for (double[] pos : playerPosition.values()) {
            if (pos[0] == gridX && pos[1] == gridY) {
                count++;
            }
        }
        return count;
    }
}