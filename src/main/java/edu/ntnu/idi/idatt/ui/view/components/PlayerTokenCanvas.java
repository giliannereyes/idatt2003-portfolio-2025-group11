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
 * Layer that shows and animates player tokens on top of a board.
 *
 * @author Gilianne Reyes
 * @version 0.5
 * @since 0.1
 */
public class PlayerTokenCanvas {

    // ── Scene‐graph node & per‐player state ─────────────────────────
    private final Pane                      tokenPane      = new Pane();
    private final Map<String, ImageView>    playerTokens   = new HashMap<>();
    private final Map<String, double[]>     playerPosition = new HashMap<>();;

    public Pane getTokenPane() {
        return tokenPane;
    }

    public void addPlayerToken(String playerName, String imagePath) {
        InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            throw new RuntimeException("Token image not found: " + imagePath);
        }
        ImageView iv = new ImageView(new Image(is, 30, 30, true, true));
        iv.setFitWidth(55);
        iv.setFitHeight(50);
        playerTokens.put(playerName, iv);
        tokenPane.getChildren().add(iv);
    }

    public void animateTokenMovement(
          String playerName,
          double gridX, double gridY,
          int totalRows, int totalCols) {

        ImageView token = lookupToken(playerName);
        // 1 – compute raw anchor point
        double[] target = computeTargetPos(gridX, gridY, totalRows, totalCols, token);

        // 2 – bump for overlaps
        target = applyOverlapOffset(target, gridX, gridY);

        // 3 – tween from current to target
        runTween(token, target);

        // 4 – record new logical position
        playerPosition.put(playerName, new double[]{gridX, gridY});
    }

    // ═══════════════════ Private helpers ════════════════════════════

    private ImageView lookupToken(String playerName) {
        ImageView iv = playerTokens.get(playerName);
        if (iv == null) {
            throw new RuntimeException("Token not found for player: " + playerName);
        }
        return iv;
    }

    /**
     * Compute the pixel position (top‐left) where the token should land,
     * according to CENTER or BORDER placement.
     */
    private double[] computeTargetPos(
          double gridX, double gridY, int totalRows, int totalCols, ImageView token) {
        double paneW = tokenPane.getWidth();
        double paneH = tokenPane.getHeight();
        double tileW = paneW / totalCols;
        double tileH = paneH / totalRows;
        double x = gridX * tileW + (tileW - token.getFitWidth())  / 2;
        double y = (totalRows - gridY - 1) * tileH + (tileH - token.getFitHeight()) / 2;
        return new double[]{x, y};
    }

    /**
     * If multiple tokens occupy the same grid cell, bump each subsequent
     * one diagonally so they don’t completely overlap.
     */
    private double[] applyOverlapOffset(double[] anchor, double gridX, double gridY) {
        int count = countTokensOnTile(gridX, gridY);
        double bump = count * 6.0;
        return new double[]{anchor[0] + bump, anchor[1] + bump};
    }

    /** Smoothly interpolates the token’s layout from its current to target coords. */
    private void runTween(ImageView token, double[] target) {
        double startX = token.getLayoutX();
        double startY = token.getLayoutY();
        double endX   = target[0];
        double endY   = target[1];

        int frames = 10;
        double dx = (endX - startX) / frames;
        double dy = (endY - startY) / frames;

        Timeline tl = new Timeline();
        for (int i = 0; i <= frames; i++) {
            final int step = i;
            tl.getKeyFrames().add(new KeyFrame(Duration.millis(70 * step), e -> {
                token.setLayoutX(startX + dx * step);
                token.setLayoutY(startY + dy * step);
            }));
        }
        tl.play();
    }

    /** How many tokens are already at the given grid coordinates? */
    private int countTokensOnTile(double gridX, double gridY) {
        int c = 0;
        for (double[] pos : playerPosition.values()) {
            if (pos[0] == gridX && pos[1] == gridY) c++;
        }
        return c;
    }
}
