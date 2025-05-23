package edu.ntnu.idi.idatt.ui.view.monopoly;

import edu.ntnu.idi.idatt.domain.action.monopoly.GoToJailAction;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.entity.monopoly.PropertyRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Paints a Monopoly board at a fixed pixel size.
 * Supply the board‐surface width/height in the constructor; each time you
 * call {@link #drawBoard(Board, PropertyRegistry)} the tile dimensions are
 * derived from those numbers.
 */
public class MonopolyBoardCanvas extends Canvas {
  private static final double HEADER_RATIO = 0.40;
  private static final Color LOGO_COLOR = Color.BLACK;
  private final double boardPxW;
  private final double boardPxH;

  /**
   * Constructs a MonopolyBoardCanvas with the specified width and height.
   *
   * @param boardWidthPx  is the width of the canvas.
   * @param boardHeightPx is the height of the canvas.
   */
  public MonopolyBoardCanvas(double boardWidthPx, double boardHeightPx) {
    super(boardWidthPx, boardHeightPx);
    this.boardPxW = boardWidthPx;
    this.boardPxH = boardHeightPx;
  }

  /**
   * Draws the Monopoly board on the canvas.
   *
   * @param board    the board to draw.
   * @param registry the property registry to use for drawing properties.
   */
  public void drawBoard(Board board, PropertyRegistry registry) {
    int cols = board.getColumns();
    int rows = board.getRows();

    final double tileW = boardPxW / cols;
    final double tileH = boardPxH / rows;

    setWidth(boardPxW);
    setHeight(boardPxH);

    GraphicsContext gc = getGraphicsContext2D();
    gc.clearRect(0, 0, boardPxW, boardPxH);
    gc.setFont(new Font(10));
    gc.setTextAlign(TextAlignment.CENTER);

    for (Map.Entry<Integer, Tile> e : board.getTiles().entrySet()) {
      Tile tile = e.getValue();
      double x = tile.getX() * tileW;
      double y = (rows - 1 - tile.getY()) * tileH;

      Optional<Property> prop = registry.getPropertyAt(tile);
      if (prop.isPresent()) {
        drawPropertyTile(gc, x, y, tileW, tileH, prop.get());
      } else {
        Optional<GoToJailAction> jailAct =
              tile.getLandAction().filter(a -> a instanceof GoToJailAction)
                    .map(a -> (GoToJailAction) a);

        if (jailAct.isPresent()) {
          String label = jailAct.get()
                .getDestinationTile()
                .filter(dest -> dest == tile)
                .isPresent()
                ? "JAIL"
                : "GO TO\nJAIL";

          drawSpecialTile(gc, x, y, tileW, tileH, label);
        } else if (tile.equals(board.getStartTile())) {
          drawSpecialTile(gc, x, y, tileW, tileH, "GO");
        } else {
          drawSpecialTile(gc, x, y, tileW, tileH, "FREE\nPARKING");
        }
      }
    }

    drawMonopolyLogo(gc, tileW, tileH, cols, rows);
  }

  /**
   * Draws a property tile on the canvas.
   *
   * @param gc the graphics context.
   * @param x the x-coordinate of the tile.
   * @param y the y-coordinate of the tile.
   * @param w the width of the tile.
   * @param h the height of the tile.
   * @param p the property to draw.
   */
  private void drawPropertyTile(
        GraphicsContext gc, double x, double y, double w, double h, Property p
  ) {
    double headerH = h * HEADER_RATIO;

    gc.setFill(Color.LIGHTGRAY);
    gc.fillRect(x, y, w, headerH);

    gc.setFill(Color.WHITE);
    gc.fillRect(x, y + headerH, w, h - headerH);

    gc.setStroke(Color.BLACK);
    gc.strokeRect(x, y, w, h);

    gc.setFill(Color.BLACK);
    Font font = gc.getFont();

    List<String> lines = wrapText(p.getName(), font, w * 0.90);
    if (lines.size() > 2) {
      lines = lines.subList(0, 2);
    }

    double lineH = font.getSize() + 2;
    double total = lines.size() * lineH;
    double yStart = y + (headerH - total) / 2 + lineH * 0.8;

    for (int i = 0; i < lines.size(); i++) {
      gc.fillText(lines.get(i), x + w / 2, yStart + i * lineH);
    }

    gc.fillText("Cost: $" + p.getCost(), x + w / 2, y + headerH + (h - headerH) * 0.60);
  }

  /**
   * Draws a special tile on the canvas.
   *
   * @param gc the graphics context.
   * @param x the x-coordinate of the tile.
   * @param y the y-coordinate of the tile.
   * @param w the width of the tile.
   * @param h the height of the tile.
   * @param label the label to display on the tile.
   */
  private void drawSpecialTile(
        GraphicsContext gc, double x, double y, double w, double h, String label
  ) {
    gc.setFill(Color.WHITE);
    gc.fillRect(x, y, w, h);

    gc.setStroke(Color.BLACK);
    gc.strokeRect(x, y, w, h);

    gc.setFill(Color.BLACK);
    gc.fillText(label, x + w / 2, y + h / 2);
  }

  /**
   * Draws the Monopoly logo on the canvas.
   *
   * @param gc the graphics context.
   * @param tw the tile width.
   * @param th the tile height.
   * @param cols the number of columns.
   * @param rows the number of rows.
   */
  private void drawMonopolyLogo(
        GraphicsContext gc, double tw, double th, int cols, int rows
  ) {
    double innerW = (cols - 2) * tw;
    double innerH = (rows - 2) * th;

    double cx = tw + innerW / 2;
    double cy = th + innerH / 2;

    double size = Math.min(innerW, innerH) * 0.18;

    gc.save();
    gc.translate(cx, cy);
    gc.rotate(-45);
    gc.setEffect(null);

    gc.setFont(new Font("Arial", size));
    gc.setFill(LOGO_COLOR);
    gc.setTextAlign(TextAlignment.CENTER);
    gc.fillText("MONOPOLY", 0, 0);

    gc.restore();
  }

  /**
   * Wraps text to fit within a specified width.
   *
   * @param txt the text to wrap.
   * @param f the font to use.
   * @param maxPx the maximum width in pixels.
   * @return a list of wrapped lines.
   */
  private List<String> wrapText(String txt, Font f, double maxPx) {
    List<String> lines = new ArrayList<>();
    StringBuilder cur = new StringBuilder();

    for (String w : txt.split(" ")) {
      String test = (cur.isEmpty() ? "" : cur + " ") + w;

      if (computeTextWidth(f, test) > maxPx && !cur.isEmpty()) {
        lines.add(cur.toString());
        cur.setLength(0);
        cur.append(w);
      } else {
        if (!cur.isEmpty()) {
          cur.append(' ');
        }
        cur.append(w);
      }
    }

    lines.add(cur.toString());
    return lines;
  }

  /**
   * Computes the width of a text string in pixels.
   *
   * @param f the font to use.
   * @param txt the text to measure.
   * @return the width of the text in pixels.
   */
  private static double computeTextWidth(Font f, String txt) {
    Text t = new Text(txt);
    t.setFont(f);
    return t.getLayoutBounds().getWidth();
  }
}