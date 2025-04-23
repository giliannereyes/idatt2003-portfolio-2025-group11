package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.io.BoardFileReader;
import edu.ntnu.idi.idatt.io.BoardFileWriter;
import edu.ntnu.idi.idatt.model.actions.*;
import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.model.strategies.SnakeLayoutStrategy;
import edu.ntnu.idi.idatt.utils.Validation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * A factory for creating board games. The class contains both hardcoded and
 * file-based board loading methods.
 *
 * @version 0.3
 * @since 0.2
 * @author Gilianne Reyes
 */
public class BoardGameFactory {
    private final BoardFileReader reader;
    private final BoardFileWriter writer;
    private final TileActionFactoryRegistry registry;
    private final SnakeLayoutStrategy layoutStrategy;
    private final List<Supplier<Board>> predefinedBoards;

    /**
     * Constructs a BoardGameFactory instance.
     *
     * @param reader is the board file reader.
     * @param writer is the board file writer.
     * @param registry is the tile action factory registry.
     *
     * @throws IllegalArgumentException if any of the parameters is null.
     */
    public BoardGameFactory(
            BoardFileReader reader, BoardFileWriter writer, TileActionFactoryRegistry registry
    ) {
        Validation.validateNonNull(reader, "Board file reader");
        Validation.validateNonNull(writer, "Board file writer");
        Validation.validateNonNull(registry, "Tile action factory registry");
        this.reader = reader;
        this.writer = writer;
        this.registry = registry;
        this.layoutStrategy = new SnakeLayoutStrategy();
        this.predefinedBoards = List.of(
                this::loadEasyBoard,
                this::loadMediumBoard,
                this::loadHardBoard
        );
    }

    /**
     * Returns all predefined boards as a Map of name → board object.
     */
    public Map<String, Board> getAllPredefinedBoards() {
        return predefinedBoards.stream()
                .map(Supplier::get)
                .collect(Collectors.toMap(
                        Board::getName,
                        board -> board
                ));
    }

    /**
     * Retrieves a board that has an "easy" layout. It contains the following:
     * <ul>
     *     <li>7x8 board</li>
     *     <li>4 ladders</li>
     *     <li>2 snakes</li>
     *     <li>1 skip turn</li>
     * </ul>
     *
     * @return the board.
     */
    public Board loadEasyBoard() {
        Board board = new Board(7,8);
        board.setName("Easy board");
        board.setDescription("Easy board with few snakes and skip turn.");
        layoutStrategy.buildLayout(board);
        Object[][] easyActions = {
                {2, LadderAction.actionType, 12},
                {5, LadderAction.actionType, 20},
                {9, LadderAction.actionType, 18},
                {15, LadderAction.actionType, 30},
                {32, SnakeAction.actionType, 19},
                {45, SnakeAction.actionType, 28},
                {40, SkipTurnAction.actionType}
        };
        addTileActions(board, easyActions);
        return board;
    }

    /**
     * Retrieves a board that has a "medium" layout. It contains the following:
     * <ul>
     *     <li>8x8 board</li>
     *     <li>4 ladders</li>
     *     <li>3 snakes</li>
     *     <li>1 skip turn</li>
     *     <li>1 reset to start</li>
     * </ul>
     *
     * @return the medium board.
     */
    public Board loadMediumBoard() {
        Board board = new Board(8,8);
        board.setName("Medium board");
        board.setDescription("Medium board with 64 tiles, balanced ladders and snakes.");
        layoutStrategy.buildLayout(board);
        Object[][] mediumActions = {
                {3, LadderAction.actionType, 14},
                {8, LadderAction.actionType, 22},
                {19, LadderAction.actionType, 32},
                {25, LadderAction.actionType, 40},
                {30, SnakeAction.actionType, 12},
                {39, SnakeAction.actionType, 24},
                {50, SnakeAction.actionType, 29},
                {52, SkipTurnAction.actionType},
                {34, ResetAction.actionType}
        };
        addTileActions(board, mediumActions);
        return board;
    }

    /**
     * Retrieves a board that has a "hard" layout. It contains the following:
     * <ul>
     *     <li>10x10 board</li>
     *     <li>5 ladders</li>
     *     <li>6 snakes</li>
     *     <li>1 skip turn</li>
     *     <li>1 reset to start</li>
     * </ul>
     *
     * @return the hard board.
     */
    public Board loadHardBoard() {
        Board board = new Board(10,10);
        board.setName("Hard board");
        board.setDescription("Challenging 100 tile board with more snakes and special tiles.");
        layoutStrategy.buildLayout(board);
        Object[][] hardActions = {
                {4, LadderAction.actionType, 16},
                {11, LadderAction.actionType, 28},
                {20, LadderAction.actionType, 42},
                {36, LadderAction.actionType, 55},
                {63, LadderAction.actionType, 81},
                {25, SnakeAction.actionType, 12},
                {30, SnakeAction.actionType, 17},
                {47, SnakeAction.actionType, 34},
                {52, SnakeAction.actionType, 29},
                {78, SnakeAction.actionType, 60},
                {93, SnakeAction.actionType, 75},
                {58, SkipTurnAction.actionType},
                {35, ResetAction.actionType}
        };
        addTileActions(board, hardActions);
        return board;
    }

    /**
     * Loads a board from a file.
     *
     * @param path is the path to the file.
     *
     * @return the board loaded from the file.
     *
     * @throws IOException if an I/O error occurs.
     */
    public Optional<Board> loadBoardFromFile(Path path) throws IOException {
        Validation.validateNonNull(path, "Path to file");
        return Optional.ofNullable(reader.readBoard(path));
    }

    /**
     * Saves a board to a file.
     *
     * @param path is the path to the file.
     * @param board is the board to save.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void saveBoardToFile(Path path, Board board) throws IOException {
        Validation.validateNonNull(path, "Path to file");
        Validation.validateNonNull(board, "Board to save");
        writer.writeBoard(path, board);
    }

    /**
     * Adds a tile action using the factory registry.
     *
     * @param board is board to add the action to.
     * @param tileId is ID of the tile to add the action to.
     * @param actionType is type of action.
     * @param destinationId is the ID of the destination tile.
     */
    private void addTileAction(Board board, int tileId, String actionType, int destinationId) {
        Tile destinationTile = board.getTile(destinationId);
        registry.getDestinationFactory(actionType)
                .ifPresent(factory -> {
                    TileAction action = factory.createTileAction(destinationTile);
                    board.addTileAction(tileId, action);
                });
    }

    /**
     * Adds a tile action using the factory registry.
     *
     * @param board is the board to add the action to.
     * @param tileId is the ID of the tile to add the action to.
     * @param actionType is the type of action.
     */
    private void addTileAction(Board board, int tileId, String actionType) {
        registry.getNoDestinationFactory(actionType)
                .ifPresent(factory -> {
                    TileAction action = factory.createTileAction();
                    board.addTileAction(tileId, action);
                });
    }

    /**
     * Adds multiple tile actions to a board based on a 2D array of actions.
     *
     * @param board is the board to add actions to.
     * @param actions is a 2D array containing tile action data.
     */
    private void addTileActions(Board board, Object[][] actions) {
        Arrays.stream(actions).forEach(action -> {
            if (action.length == 3) {
                addTileAction(board, (int) action[0], (String) action[1], (int) action[2]);
            } else if (action.length == 2) {
                addTileAction(board, (int) action[0], (String) action[1]);
            }
        });
    }
}
