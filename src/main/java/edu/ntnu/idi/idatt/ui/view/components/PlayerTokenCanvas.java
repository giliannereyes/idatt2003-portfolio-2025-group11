package edu.ntnu.idi.idatt.ui.view.components;

import edu.ntnu.idi.idatt.domain.entity.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PlayerTokenCanvas {
    private final Pane tokenPane = new Pane();
    private final Map<String, ImageView> playerTokens = new HashMap<>();
    private final Map<String, double[]> playerPosition = new HashMap<>();
    private Map<String, Player> players = new HashMap<>();

    public Pane getTokenPane() {
        return tokenPane;
    }

    public void addPlayerToken(String playerName, String imagePath) {
        InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            throw new RuntimeException("Path to image for player token not found: " + imagePath);
        } else {
            Image tokenImage = new Image(is, 30, 30, true, true);
            ImageView token = new ImageView(tokenImage);
            token.setFitWidth(30);
            token.setFitHeight(60);
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

        double targetX = gridX * tileW + tileW / 2 - token.getFitWidth() / 2;
        double targetY = (totalRows - gridY - 1) * tileH + tileH / 2 - token.getFitHeight() / 2;

        Player player = players.get(playerName);

        if (player != null && player.getCurrentTile().getLandAction().isPresent()) {
            token.setLayoutX(targetX);
            token.setLayoutY(targetY);
        } else {
            double[] currentPos = playerPosition.getOrDefault(playerName, new double[]{0, 0});
            double currentX = currentPos[0];
            double currentY = currentPos[1];

            double startX = token.getLayoutX();
            double startY = token.getLayoutY();

            double diffX = targetX - startX;
            double diffY = targetY - startY;

            int steps = 10;
            double stepX = diffX / steps;
            double stepY = diffY / steps;

            Timeline timeline = new Timeline();
            for (int i = 0; i < steps; i++) {
                final int step = i;
                KeyFrame keyFrame = new KeyFrame(Duration.millis(70 * i), event -> {
                    double newX = startX + step * 1.12*stepX;
                    double newY = startY + step * 1.12*stepY;
                    token.setLayoutX(newX);
                    token.setLayoutY(newY);
                });
                timeline.getKeyFrames().add(keyFrame);
            }

            timeline.setCycleCount(1);
            timeline.play();
            timeline.setOnFinished(event -> {
                // Check if the destination tile has a LandAction
                if (player != null && player.getCurrentTile().getLandAction().isPresent()) {
                    System.out.println("Executing action for player " + playerName + " on destination tile.");
                    token.setLayoutX(targetX);
                    token.setLayoutY(targetY);
                }
            });
            playerPosition.put(playerName, new double[]{gridX, gridY});
        }
    }
}
