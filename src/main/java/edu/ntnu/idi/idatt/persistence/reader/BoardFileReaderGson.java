package edu.ntnu.idi.idatt.persistence.reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.factory.TileActionFactoryRegistry;
import edu.ntnu.idi.idatt.exceptions.BoardParsingException;
import edu.ntnu.idi.idatt.utils.Validation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.StreamSupport;

/**
 * A GSON-based implementation of {@link BoardFileReader} that reads and deserializes
 * a {@link Board} from JSON files, including its name, description, dimensions,
 * tiles, next-tile links, and tile land actions.
 *
 * @author Gilianne Reyes
 * @version 0.3
 * @since 0.2
 */
public class BoardFileReaderGson implements BoardFileReader {
  private final TileActionFactoryRegistry registry;

  /**
   * Constructs a reader with the given tile action factory registry.
   *
   * @param registry the registry used to instantiate tile actions.
   *
   * @throws IllegalArgumentException if {@code registry} is {@code null}.
   */
  public BoardFileReaderGson(TileActionFactoryRegistry registry) {
    Validation.validateNonNull(registry, "Tile action factory registry");
    this.registry = registry;
  }

  /**
   * Reads and deserializes a board from the specified JSON file path.
   *
   * @param path is the file system path to read.
   *
   * @return the {@link Board} deserialized from the file.
   *
   * @throws IllegalArgumentException if {@code path} is {@code null}.
   * @throws BoardParsingException if JSON is invalid or parsing fails.
   */
  @Override
  public Board readBoard(Path path) {
    Validation.validateNonNull(path, "Path to file");
    try {
      String jsonString = Files.readString(path);
      JsonObject boardJson = JsonParser.parseString(jsonString).getAsJsonObject();
      int rows = boardJson.get("rows").getAsInt();
      int columns = boardJson.get("columns").getAsInt();
      Board board = new Board(rows, columns);
      JsonArray tilesJson = boardJson.getAsJsonArray("tiles");
      parseBoardMetaData(board, boardJson);
      parseTilesAndAddToBoard(board, tilesJson);
      return board;
    } catch (IOException e) {
      throw new BoardParsingException(
            "Failed to read board from file due to I/O error: " + path, e);
    } catch (JsonSyntaxException e) {
      throw new BoardParsingException(
            "Failed to read board from file due to JSON syntax error: " + path, e);
    } catch (Exception e) {
      throw new BoardParsingException(
            "Failed to read board from file: " + path, e);
    }
  }

  /**
   * Parses tiles from JSON and adds them to the board.
   *
   * @param board is the board to add the tiles to.
   * @param tilesJson is the JSON array of tiles.
   */
  private void parseTilesAndAddToBoard(Board board, JsonArray tilesJson) {
    addAllTiles(board, tilesJson);
    processTileActions(board, tilesJson);
  }

  /**
   * Adds all tiles to the board from the given JSON array.
   *
   * @param board is the target board.
   * @param tilesJson is the JSON array of tile definitions.
   *
   * @throws BoardParsingException if any tile definition is malformed.
   */
  private void addAllTiles(Board board, JsonArray tilesJson) {
    StreamSupport.stream(tilesJson.spliterator(), false)
          .map(JsonElement::getAsJsonObject)
          .forEach(tileJson -> {
            int tileId = tileJson.get("id").getAsInt();
            double x = tileJson.get("x").getAsDouble();
            double y = tileJson.get("y").getAsDouble();
            Tile tile = new Tile(tileId, x, y);
            board.addTile(tile);
          });
  }

  /**
   * Processes nextTile links and land actions for each tile.
   *
   * @param board is the target board.
   * @param tilesJson is the JSON array of tile definitions.
   *
   * @throws BoardParsingException if any action factory is missing or malformed.
   */
  private void processTileActions(Board board, JsonArray tilesJson) {
    StreamSupport.stream(tilesJson.spliterator(), false)
          .map(JsonElement::getAsJsonObject)
          .forEach(tileJson -> {
            Tile tile = board.getTile(tileJson.get("id").getAsInt());
            parseNextTile(board, tile, tileJson);
            parseTileAction(board, tile, tileJson);
          });
  }

  /**
   * Parses and sets the next-tile link for a tile.
   *
   * @param tile is the tile to update.
   * @param board is the board containing all tiles.
   * @param tileJson is the JSON object with tile properties.
   */
  private void parseNextTile(Board board, Tile tile, JsonObject tileJson) {
    if (tileJson.has("nextTile") && !tileJson.get("nextTile").isJsonNull()) {
      int nextTileId = tileJson.get("nextTile").getAsInt();
      tile.setNextTile(board.getTile(nextTileId));
    } else {
      tile.setNextTile(null);
    }
  }

  /**
   * Parses the action of a tile from JSON.
   *
   * @param board is the board containing the tiles.
   * @param tile is the tile to set the action of.
   * @param tileJson is the JSON object representing the tile.
   */
  private void parseTileAction(Board board, Tile tile, JsonObject tileJson) {
    if (tileJson.has("action") && !tileJson.get("action").isJsonNull()) {
      JsonObject actionJson = tileJson.get("action").getAsJsonObject();
      String actionType = actionJson.get("type").getAsString();
      if (actionJson.has("destinationTileId")) {
        parseDestinationTileAction(board, tile, actionJson, actionType);
      } else {
        parseNoDestinationTileAction(tile, actionType);
      }
    }
  }

  /**
   * Parses a tile action with a destination tile from JSON.
   *
   * @param board is the board containing the tiles.
   * @param tile is the tile to set the action of.
   * @param actionJson is the JSON object representing the action.
   * @param actionType is the type of the action in saved in JSON.
   *
   * @throws BoardParsingException if the tile action cannot be parsed due to unregistered factory.
   */
  private void parseDestinationTileAction(
        Board board, Tile tile, JsonObject actionJson, String actionType
  ) {
    int destId = actionJson.get("destinationTileId").getAsInt();
    Tile destinationTile = board.getTile(destId);
    registry.getDestinationFactory(actionType)
        .ifPresentOrElse(
              factory -> tile.setLandAction(factory.createTileAction(destinationTile)),
              () -> {
                throw new BoardParsingException("No registered factory for type: " + actionType);
              }
        );
  }

  /**
   * Parses a tile action without a destination tile from JSON.
   *
   * @param tile is the tile to set the action of.
   * @param actionType is the type of the action in saved in JSON.
   *
   * @throws BoardParsingException if the tile action cannot be parsed due to unregistered factory.
   */
  private void parseNoDestinationTileAction(Tile tile, String actionType) {
    registry.getNoDestinationFactory(actionType)
        .ifPresentOrElse(
              factory -> tile.setLandAction(factory.createTileAction()),
              () -> {
                throw new BoardParsingException("No registered factory for type: " + actionType);
              }
        );
  }

  /**
   * Parses the name and description of a board from JSON.
   *
   * @param board is the board to set the name and description of.
   * @param boardJson is the JSON object representing the board.
   */
  private void parseBoardMetaData(Board board, JsonObject boardJson) {
    if (boardJson.has("name")) {
      board.setName(boardJson.get("name").getAsString());
    }
    if (boardJson.has("description")) {
      board.setDescription(boardJson.get("description").getAsString());
    }
  }
}
