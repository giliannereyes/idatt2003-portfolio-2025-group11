package edu.ntnu.idi.idatt.ui.view.monopoly;

import edu.ntnu.idi.idatt.domain.action.monopoly.JailTileAction;
import edu.ntnu.idi.idatt.domain.action.monopoly.PropertyAction;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Map;

/**
 * A canvas for drawing a Monopoly-style board.
 * Reuses tile positioning logic but applies property colors and names.
 *
 * @version 0.1
 * @since 0.1
 */
public class MonopolyBoardCanvas extends Canvas {

  public MonopolyBoardCanvas(double width, double height) {
    super(width, height);
  }

  public void drawBoard(Board board) {
    GraphicsContext gc = getGraphicsContext2D();
    gc.clearRect(0, 0, getWidth(), getHeight());
    int cols = board.getColumns();
    int rows = board.getRows();
    double tileWidth = getWidth() / cols;
    double tileHeight = getHeight() / rows;
    gc.setFont(new Font(10));
    gc.setTextAlign(TextAlignment.CENTER);
    for (Map.Entry<Integer, Tile> entry : board.getTiles().entrySet()) {
      Tile tile = entry.getValue();
      double x = tile.getX() * tileWidth;
      double y = (rows - 1 - tile.getY()) * tileHeight;
      if (tile.getLandAction().isPresent() && tile.getLandAction().get() instanceof PropertyAction propertyAction) {
        drawPropertyTile(gc, x, y, tileWidth, tileHeight, propertyAction.getProperty());
      } else if (tile.getLandAction().isPresent() && tile.getLandAction().get() instanceof JailTileAction) {
        drawSpecialTile(gc, x, y, tileWidth, tileHeight, "JAIL");
      } else if (tile.equals(board.getStartTile())) {
        drawSpecialTile(gc, x, y, tileWidth, tileHeight, "GO");
      } else {
        drawSpecialTile(gc, x, y, tileWidth, tileHeight, "FREE \nPARKING");
      }
    }
  }

  private void drawPropertyTile(GraphicsContext gc, double x, double y, double w, double h, Property property) {
    // Top color bar
    Color color = getColorForProperty(property.getName());
    gc.setFill(color);
    gc.fillRect(x, y, w, h * 0.2);

    // Tile background
    gc.setFill(Color.WHITE);
    gc.fillRect(x, y + h * 0.2, w, h * 0.8);

    // Border
    gc.setStroke(Color.BLACK);
    gc.strokeRect(x, y, w, h);

    // Text: Name and Cost
    gc.setFill(Color.BLACK);
    gc.fillText(property.getName(), x + w / 2, y + h * 0.5);
    gc.fillText("$" + property.getCost(), x + w / 2, y + h * 0.7);
  }

  private void drawSpecialTile(GraphicsContext gc, double x, double y, double w, double h, String label) {
    gc.setFill(Color.WHITE);
    gc.fillRect(x, y, w, h);

    gc.setStroke(Color.BLACK);
    gc.strokeRect(x, y, w, h);

    gc.setFill(Color.BLACK);
    gc.fillText(label, x + w / 2, y + h / 2);
  }

  private Color getColorForProperty(String name) {
    return switch (name) {
      case "Evans Ave.", "Downing St." -> Color.PURPLE;
      case "Hill Way", "Jackson Ave." -> Color.LIGHTBLUE;
      case "Pajaro St.", "Blanco Rd." -> Color.BROWN;
      case "Kentucky Ave.", "Broadway" -> Color.ORANGE;
      case "Main St.", "Giggling Way" -> Color.RED;
      case "Atlantic Ave.", "Flatlands Ave." -> Color.GOLD;
      case "Reynolds Ave.", "Columbia Rd." -> Color.GREEN;
      case "Lombard St.", "17 Mile Drive" -> Color.DARKBLUE;
      default -> Color.LIGHTGRAY;
    };
  }
}

