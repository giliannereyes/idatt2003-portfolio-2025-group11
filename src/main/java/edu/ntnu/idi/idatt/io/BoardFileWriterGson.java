package edu.ntnu.idi.idatt.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.ntnu.idi.idatt.exceptions.BoardSerializeException;
import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.utils.Validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A GSON-based file writer for writing board files.
 *
 * @version 0.1
 * @since 0.2
 * @author Gilianne Reyes
 */
public class BoardFileWriterGson implements BoardFileWriter {
    /**
     * Writes a board to a file.
     *
     * @param path is the path to the file to write in.
     * @param board is the board to write to the file.
     */
    @Override
    public void writeBoard(Path path, Board board) {
        Validation.validateNonNull(board, "Board");
        Validation.validateNonNull(path, "Path to file");
        try {
            JsonObject boardJson = new JsonObject();
            boardJson.addProperty("name", board.getName());
            boardJson.addProperty("description", board.getDescription());
            boardJson.add("tiles", serializeTiles(board));
            Files.writeString(path, boardJson.toString());
        } catch (IOException e) {
            throw new BoardSerializeException("Error writing board to file: " + path, e);
        }
    }

    /**
     * Serializes the tiles of a board to JSON.
     *
     * @param board is the board containing the tiles.
     * @return a JSON array representing the tiles.
     *
     * <br><strong>GitHub Copilot helped implement stream in this method.</strong>
     */
    private JsonArray serializeTiles(Board board) {
        Validation.validateNonNull(board, "Board");
        return board.getTiles().values().stream().map(tile -> {
            JsonObject tileJson = new JsonObject();
            tileJson.addProperty("id", tile.getTileId());
            tile.getNextTile().ifPresent(nextTile -> tileJson.addProperty("nextTile", nextTile.getTileId()));
            if (tile.getLandAction().isPresent()) {
                tileJson.add("action", serializeTileAction(tile));
            }
            return tileJson;
        }).collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
    }

    /**
     * Serializes the action of a tile to JSON.
     *
     * @param tile is the tile containing the action.
     * @return a JSON object representing the action.
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