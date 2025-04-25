package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.actions.*;
import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.model.enums.TileColorType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.Map;
import java.util.Optional;

/**
 * A canvas for drawing the Snakes and Ladders board.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class BoardCanvas extends Canvas {
    private final Map<String, TileColorType> ACTION_COLOR_MAPPING = Map.of(
            LadderAction.actionType, TileColorType.LADDER_START,
            SnakeAction.actionType, TileColorType.SNAKE_START,
            ResetAction.actionType, TileColorType.RESET,
            SkipTurnAction.actionType, TileColorType.SKIP_TURN
    );
    private static final Map<String, TileColorType> ACTION_END_COLOR_MAPPING = Map.of(
            LadderAction.actionType, TileColorType.LADDER_END,
            SnakeAction.actionType, TileColorType.SNAKE_END
    );
    private static final double CONNECTOR_MARGIN = 2; // pixels to inset from tile edge

    public BoardCanvas(double width, double height) {
        super(width, height);
    }

    /**
     * Draws the board on the canvas.
     *
     * @param board is the board to draw.
     */
    public void drawBoard(Board board) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        int rows = board.getRows();
        int cols = board.getColumns();
        double tileWidth = getWidth() / cols;
        double tileHeight = getHeight() / rows;
        gc.setFont(new Font(10));
        board.getTiles().forEach((tileId, tile) ->
                drawTile(gc, tileId, tile, tileWidth, tileHeight, rows, board)
        );
        board.getTiles().forEach((tileId, tile) ->
                tile.getLandAction().ifPresent(action ->
                        drawActionConnector(gc, tile, action, tileWidth, tileHeight, rows)
                )
        );
        System.out.println("All tiles drawn");
    }

    /**
     * Draws a tile on the canvas.
     *
     * @param gc is the graphics context.
     * @param tileId is the tile ID.
     * @param tile is the tile to draw.
     * @param tileWidth is the width of a tile.
     * @param tileHeight is the height of a tile.
     * @param rows is the number of rows in the board.
     * @param board is the board containing the tile.
     */
    private void drawTile(GraphicsContext gc, int tileId, Tile tile,
      double tileWidth, double tileHeight, int rows, Board board
    ) {
        double canvasX = tile.getX() * tileWidth;
        double canvasY = (rows - 1 - tile.getY()) * tileHeight;
        Color fillColor = getTileColor(tile, board);
        gc.setFill(fillColor);
        gc.fillRect(canvasX, canvasY, tileWidth, tileHeight);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(canvasX, canvasY, tileWidth, tileHeight);
        gc.setFill(Color.BLACK);
        gc.fillText(String.valueOf(tileId), canvasX + 5, canvasY + 15);
    }

    /**
     * Gets the color of a tile based on its type - default or
     * has an action (ladder, snake, reset, skip turn).
     *
     * @param tile is the tile to get the color for.
     * @param board is the board containing the tile.
     *
     * @return the color of the tile.
     */
    private Color getTileColor(Tile tile, Board board) {
        if (tile.getLandAction().isPresent()) {
            String actionType = tile.getLandAction().get().getActionType();
            return ACTION_COLOR_MAPPING.get(actionType).getColor();
        }
        for (Tile startTile : board.getTiles().values()) {
            Optional<TileAction> maybeAction = startTile.getLandAction();
            if (maybeAction.isPresent() && maybeAction.get().getDestinationTile().isPresent()) {
                Tile destination = maybeAction.get().getDestinationTile().get();
                if (destination == tile) {
                    String actionType = maybeAction.get().getActionType();
                    return ACTION_END_COLOR_MAPPING
                            .getOrDefault(actionType, TileColorType.DEFAULT_DARK).getColor();
                }
            }
        }
        return ((tile.getX() + tile.getY()) % 2 == 0)
                ? TileColorType.DEFAULT_DARK.getColor()
                : TileColorType.DEFAULT_LIGHT.getColor();
    }

    /**
     * Computes the center (canvas) coordinates of a tile.
     *
     * @param tile is the tile to get the center for.
     * @param tileWidth is the width of a tile.
     * @param tileHeight is the height of a tile.
     */
    private double[] getTileCenter(Tile tile, double tileWidth, double tileHeight, int rows) {
        double centerX = tile.getX() * tileWidth + tileWidth / 2;
        double centerY = (rows - 1 - tile.getY()) * tileHeight + tileHeight / 2;
        return new double[]{centerX, centerY};
    }

    /**
     * Draws a connector (line or image) for a ladder/snake from the start tile to its destination tile.
     * The connector coordinates are calculated using the tile centers.
     */
    private void drawActionConnector(
        GraphicsContext gc, Tile startTile, TileAction action,
        double tileWidth, double tileHeight, int rows
    ) {
        Optional<Tile> destOpt = action.getDestinationTile();
        if (destOpt.isPresent()) {
            Tile destTile = destOpt.get();
            double[] startCenter = getTileCenter(startTile, tileWidth, tileHeight, rows);
            double[] endCenter   = getTileCenter(destTile, tileWidth, tileHeight, rows);
            double dx = endCenter[0] - startCenter[0];
            double dy = endCenter[1] - startCenter[1];
            double len = Math.sqrt(dx * dx + dy * dy);
            if (len > 0) {
                dx /= len;
                dy /= len;
                startCenter[0] += dx * CONNECTOR_MARGIN;
                startCenter[1] += dy * CONNECTOR_MARGIN;
                endCenter[0] -= dx * CONNECTOR_MARGIN;
                endCenter[1] -= dy * CONNECTOR_MARGIN;
            }
            switch (action.getActionType()) {
                case "LadderAction" -> gc.setStroke(Color.DARKGREEN);
                case "SnakeAction"  -> gc.setStroke(Color.DARKRED);
                default             -> gc.setStroke(Color.BLUE);
            }
            gc.setLineWidth(5);
            gc.strokeLine(startCenter[0], startCenter[1], endCenter[0], endCenter[1]);
        }
    }
}
