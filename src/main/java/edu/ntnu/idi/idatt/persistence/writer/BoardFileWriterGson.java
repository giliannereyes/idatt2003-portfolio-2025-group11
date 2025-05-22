package edu.ntnu.idi.idatt.persistence.writer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.exceptions.BoardSerializeException;
import edu.ntnu.idi.idatt.utils.Validation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JSON-based {@link BoardFileWriter} implementation using GSON.
 *
 * <p>Serializes a {@link Board} into a JSON file including its name, description,
 * dimensions, tiles, links to next tiles, and any land actions.
 * </p>
 *
 * @version 0.3
 * @since 0.2
 * @author Gilianne Reyes
 */
public class BoardFileWriterGson implements BoardFileWriter {
  /**
   * Writes the given {@link Board} to the specified file path in JSON format.
   *
   * @param path  the target file path; must not be {@code null}
   * @param board the board to serialize; must not be {@code null}
   *
   * @throws IllegalArgumentException if {@code path} or {@code board} is {@code null}
   * @throws BoardSerializeException  if an I/O error occurs or writing is not permitted.
   */
  @Override
  public void writeBoard(Path path, Board board) {
    Validation.validateNonNull(board, "Board");
    Validation.validateNonNull(path, "Path to file");
    try {
      JsonObject boardJson = new JsonObject();
      boardJson.addProperty("name", board.getName());
      boardJson.addProperty("description", board.getDescription());
      boardJson.addProperty("rows", board.getRows());
      boardJson.addProperty("columns", board.getColumns());
      boardJson.add("tiles", serializeTiles(board));
      Files.writeString(path, boardJson.toString());
    } catch (IOException e) {
      throw new BoardSerializeException(
            "Error writing board due to I/O error: " + path, e);
    } catch (SecurityException e) {
      throw new BoardSerializeException(
            "Insufficient permissions to write board to file: " + path, e);
    } catch (Exception e) {
      throw new BoardSerializeException(
            "Error writing board to file: " + path, e);
    }
  }

  /**
   * Converts all tiles of the board into a {@link JsonArray}.
   *
   * <br><strong>GitHub Copilot provided guidance to implement stream in this method.</strong>
   *
   * @param board the board whose tiles are serialized.
   *
   * @return a JSON array of tile objects.
   *
   * @throws IllegalArgumentException if {@code board} is {@code null}
   */
  private JsonArray serializeTiles(Board board) {
    Validation.validateNonNull(board, "Board");
    return board.getTiles().values().stream().map(tile -> {
      JsonObject tileJson = new JsonObject();
      tileJson.addProperty("id", tile.getTileId());
      tileJson.addProperty("x", tile.getX());
      tileJson.addProperty("y", tile.getY());
      tile.getNextTile().ifPresent(nextTile ->
            tileJson.addProperty("nextTile", nextTile.getTileId())
      );
      if (tile.getLandAction().isPresent()) {
        tileJson.add("action", serializeTileAction(tile));
      }
      return tileJson;
    }).collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
  }

  /**
   * Serializes the land action of a single {@link Tile} into a {@link JsonObject}.
   *
   * @param tile the tile containing the land action; must not be {@code null}
   *
   * @return a JSON object representing the land action, or an empty object if none present.
   *
   * @throws IllegalArgumentException if {@code tile} is {@code null}.
   */
  private JsonObject serializeTileAction(Tile tile) {
    Validation.validateNonNull(tile, "Tile to serialize action for");
    JsonObject actionJson = new JsonObject();
    tile.getLandAction().ifPresent(action -> {
      String type = action.getActionType();
      actionJson.addProperty("type", type);
      action.getDestinationTile().ifPresent(destinationTile ->
            actionJson.addProperty("destinationTileId", destinationTile.getTileId())
      );
    });
    return actionJson;
  }
}