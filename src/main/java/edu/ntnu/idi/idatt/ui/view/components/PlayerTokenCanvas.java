package edu.ntnu.idi.idatt.ui.view.components;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * A canvas that maintains a collection of player tokens
 * and handles their movement animation on a game board.
 *
 * @author Gilianne Reyes
 * @author Trang Duong
 * @version 0.5
 * @since 0.1
 */
public class PlayerTokenCanvas {
  private final Pane tokenPane = new Pane();
  private final Map<String, ImageView> playerTokens = new HashMap<>();
  private final Map<String, double[]> playerPosition = new HashMap<>();

  /**
   * Retrieves the pane that contains the player tokens.
   *
   * @return the token pane.
   */
  public Pane getTokenPane() {
    return tokenPane;
  }

  /**
   * Adds a player token to the canvas.
   *
   * @param playerName the name of the player.
   * @param imagePath the path to the token image.
   */
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

  /**
   * Animates the movement of a player token to a specified grid position.
   *
   * @param playerName the name of the player.
   * @param gridX the x-coordinate of the grid position.
   * @param gridY the y-coordinate of the grid position.
   * @param totalRows the total number of rows in the grid.
   * @param totalCols the total number of columns in the grid.
   */
  public void animateTokenMovement(
        String playerName, double gridX, double gridY, int totalRows, int totalCols
  ) {
    ImageView token = lookupToken(playerName);
    double[] target = computeTargetPos(gridX, gridY, totalRows, totalCols, token);
    target = applyOverlapOffset(target, gridX, gridY);
    runTween(token, target);
    playerPosition.put(playerName, new double[]{gridX, gridY});
  }

  /**
   * Retrieves the ImageView of a player token by player name.
   *
   * @param playerName the name of the player.
   *
   * @return the ImageView of the player token.
   */
  private ImageView lookupToken(String playerName) {
    ImageView iv = playerTokens.get(playerName);
    if (iv == null) {
      throw new RuntimeException("Token not found for player: " + playerName);
    }
    return iv;
  }

  /**
   * Computes the target position for a token based on its grid coordinates.
   *
   * @param gridX the x-coordinate of the grid position.
   * @param gridY the y-coordinate of the grid position.
   * @param totalRows the total number of rows in the grid.
   * @param totalCols the total number of columns in the grid.
   * @param token the ImageView of the token.
   *
   * @return an array containing the x and y coordinates of the target position.
   */
  private double[] computeTargetPos(
        double gridX, double gridY, int totalRows, int totalCols, ImageView token
  ) {
    double paneW = tokenPane.getWidth();
    double paneH = tokenPane.getHeight();
    double tileW = paneW / totalCols;
    double tileH = paneH / totalRows;
    double x = gridX * tileW + (tileW - token.getFitWidth()) / 2;
    double y = (totalRows - gridY - 1) * tileH + (tileH - token.getFitHeight()) / 2;
    return new double[]{x, y};
  }

  /**
   * Applies an offset to the target position to avoid overlap with other tokens.
   *
   * @param anchor the original target position.
   * @param gridX the x-coordinate of the grid position.
   * @param gridY the y-coordinate of the grid position.
   *
   * @return an array containing the adjusted x and y coordinates.
   */
  private double[] applyOverlapOffset(double[] anchor, double gridX, double gridY) {
    int count = countTokensOnTile(gridX, gridY);
    double bump = count * 6.0;
    return new double[]{anchor[0] + bump, anchor[1] + bump};
  }

  /**
   * Animates the token movement using a tweening effect.
   *
   * @param token the ImageView of the token.
   * @param target the target position for the token.
   */
  private void runTween(ImageView token, double[] target) {
    double startX = token.getLayoutX();
    double startY = token.getLayoutY();
    double endX = target[0];
    double endY = target[1];

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

  /**
   * Counts the number of tokens on a specific tile.
   *
   * @param gridX the x-coordinate of the grid position.
   * @param gridY the y-coordinate of the grid position.
   *
   * @return the number of tokens on the tile.
   */
  private int countTokensOnTile(double gridX, double gridY) {
    int c = 0;
    for (double[] pos : playerPosition.values()) {
      if (pos[0] == gridX && pos[1] == gridY) {
        c++;
      }
    }
    return c;
  }
}