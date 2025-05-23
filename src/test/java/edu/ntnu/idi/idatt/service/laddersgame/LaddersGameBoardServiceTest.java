package edu.ntnu.idi.idatt.service.laddersgame;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.factory.DestinationTileActionFactory;
import edu.ntnu.idi.idatt.domain.factory.NoDestinationTileActionFactory;
import edu.ntnu.idi.idatt.domain.factory.TileActionFactoryRegistry;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.SnakesAndLaddersFactory;
import edu.ntnu.idi.idatt.persistence.reader.BoardFileReader;
import edu.ntnu.idi.idatt.persistence.writer.BoardFileWriter;
import edu.ntnu.idi.idatt.utils.Validation;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link LaddersGameBoardService}, verifying its behavior when interacting
 * with different implementations of {@link SnakesAndLaddersFactory}.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class LaddersGameBoardServiceTest {

    //------------ Positive test ------------

    /**
     * Verifies that a board is successfully loaded from a file when the factory returns a valid board.
     */
    @Test
    void testLoadBoardConfigurationSuccessfully() {
        SnakesAndLaddersFactory stubFactory = new SnakesAndLaddersFactory(new DummyReader(), new DummyWriter(), new DummyRegistry()) {
            @Override
            public Optional<Board> loadBoardFromFile(Path path) {
                return Optional.of(new Board(10, 10));
            }

            @Override
            public void saveBoardToFile(Path path, Board board) {}

            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                return new HashMap<>();
            }
        };

        LaddersGameBoardService service = new LaddersGameBoardService(stubFactory);
        File file = new File("dummy.txt");
        Validation.validateNonNull(file, "Board file");
        Optional<Board> board = service.loadBoardConfiguration(file);

        assertTrue(board.isPresent(), "Board should be loaded successfully");
        Board loadedBoard = board.get();
        Validation.validatePositiveNum(loadedBoard.getRows(), "Loaded board rows");
        Validation.validatePositiveNum(loadedBoard.getColumns(), "Loaded board columns");
    }

    /**
     * Verifies that saving a board configuration does not throw an exception when the factory simulates success.
     */
    @Test
    void testSaveBoardConfigurationSuccessfully() {
        SnakesAndLaddersFactory stubFactory = new SnakesAndLaddersFactory(new DummyReader(), new DummyWriter(), new DummyRegistry()) {
            @Override
            public Optional<Board> loadBoardFromFile(Path path) {
                return Optional.empty();
            }

            @Override
            public void saveBoardToFile(Path path, Board board) {
                // do nothing (simulate success)
            }

            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                return new HashMap<>();
            }
        };

        LaddersGameBoardService service = new LaddersGameBoardService(stubFactory);
        Board board = new Board(5, 5);
        Validation.validatePositiveNum(board.getRows(), "Board rows");
        Validation.validatePositiveNum(board.getColumns(), "Board columns");
        File file = new File("dummy.txt");
        Validation.validateNonNull(file, "Board file");
        assertDoesNotThrow(() -> service.saveBoardConfiguration(file, board));
    }

    /**
     * Verifies that predefined boards are returned correctly from the factory.
     */
    @Test
    void testGetPredefinedBoardsSuccessfully() {
        SnakesAndLaddersFactory stubFactory = new SnakesAndLaddersFactory(new DummyReader(), new DummyWriter(), new DummyRegistry()) {
            @Override
            public Optional<Board> loadBoardFromFile(Path path) {
                return Optional.empty();
            }

            @Override
            public void saveBoardToFile(Path path, Board board) {}

            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                Map<String, Board> boards = new HashMap<>();
                boards.put("basic", new Board(5, 5));
                return boards;
            }
        };

        LaddersGameBoardService service = new LaddersGameBoardService(stubFactory);
        Map<String, Board> boards = service.getPredefinedBoards();
        Board basic = boards.get("basic");
        Validation.validatePositiveNum(basic.getRows(), "basic board rows");
        Validation.validatePositiveNum(basic.getColumns(), "basic board columns");

        assertEquals(1, boards.size(), "Should return 1 predefined board");
        assertTrue(boards.containsKey("basic"));
    }

    //------------ Negative test ------------

    /**
     * Verifies that an exception is thrown when the factory fails to load a board from a file.
     */
    @Test
    void testLoadBoardThrowsException() {
        SnakesAndLaddersFactory failingFactory = new SnakesAndLaddersFactory(new DummyReader(), new DummyWriter(), new DummyRegistry()) {
            @Override
            public Optional<Board> loadBoardFromFile(Path path) {
                throw new RuntimeException("File error");
            }

            @Override
            public void saveBoardToFile(Path path, Board board) {}

            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                return new HashMap<>();
            }
        };

        LaddersGameBoardService service = new LaddersGameBoardService(failingFactory);
        assertThrows(RuntimeException.class, () -> service.loadBoardConfiguration(new File("invalid.txt")));
    }

    /**
     * Verifies that an exception is thrown when the factory fails to save a board to a file.
     */
    @Test
    void testSaveBoardThrowsException() {
        SnakesAndLaddersFactory failingFactory = new SnakesAndLaddersFactory(new DummyReader(), new DummyWriter(), new DummyRegistry()) {
            @Override
            public Optional<Board> loadBoardFromFile(Path path) {
                return Optional.empty();
            }

            @Override
            public void saveBoardToFile(Path path, Board board) {
                throw new RuntimeException("Save failed");
            }

            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                return new HashMap<>();
            }
        };

        LaddersGameBoardService service = new LaddersGameBoardService(failingFactory);
        Board board = new Board(3, 3);
        Validation.validatePositiveNum(board.getRows(), "Board rows");
        Validation.validatePositiveNum(board.getColumns(), "Board columns");
        File file = new File("invalid.txt");
        Validation.validateNonNull(file, "Board file");
        assertThrows(RuntimeException.class, () -> service.saveBoardConfiguration(file, board));
    }

    /**
     * Verifies that an exception is thrown when the factory fails to return predefined boards.
     */
    @Test
    void testGetPredefinedBoardsThrowsException() {
        SnakesAndLaddersFactory failingFactory = new SnakesAndLaddersFactory(new DummyReader(), new DummyWriter(), new DummyRegistry()) {
            @Override
            public Optional<Board> loadBoardFromFile(Path path) {
                return Optional.empty();
            }

            @Override
            public void saveBoardToFile(Path path, Board board) {}

            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                throw new IllegalStateException("Factory failure");
            }
        };

        LaddersGameBoardService service = new LaddersGameBoardService(failingFactory);
        assertThrows(RuntimeException.class, service::getPredefinedBoards);
    }

    //------------ Edge cases ------------

    /**
     * Verifies that an empty {@link Optional} is returned when no board is found in the file.
     */
    @Test
    void testLoadBoardReturnsEmptyOptional() {
        SnakesAndLaddersFactory factory = new SnakesAndLaddersFactory(new DummyReader(), new DummyWriter(), new DummyRegistry()) {
            @Override
            public Optional<Board> loadBoardFromFile(Path path) {
                return Optional.empty(); // Simulate no board found
            }

            @Override
            public void saveBoardToFile(Path path, Board board) {}

            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                return Collections.emptyMap();
            }
        };

        LaddersGameBoardService service = new LaddersGameBoardService(factory);
        Optional<Board> board = service.loadBoardConfiguration(new File("not_found.txt"));

        assertFalse(board.isPresent(), "Expected empty Optional when board not found");
    }

    /**
     * Verifies that an empty map is returned when no predefined boards are available.
     */
    @Test
    void testGetPredefinedBoardsReturnsEmptyMap() {
        SnakesAndLaddersFactory factory = new SnakesAndLaddersFactory(new DummyReader(), new DummyWriter(), new DummyRegistry()) {
            @Override
            public Optional<Board> loadBoardFromFile(Path path) {
                return Optional.empty();
            }

            @Override
            public void saveBoardToFile(Path path, Board board) {}

            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                return Collections.emptyMap(); // no presets
            }
        };

        LaddersGameBoardService service = new LaddersGameBoardService(factory);
        Map<String, Board> boards = service.getPredefinedBoards();

        Validation.validateNonNull(boards, "boards should not be null");
        assertTrue(boards.isEmpty(), "Expected empty map of predefined boards");
    }

    //------------ Dummy ------------

    /**
     * Dummy implementation of {@link BoardFileReader} that returns a basic board.
     */
    private static class DummyReader implements BoardFileReader {
        @Override
        public Board readBoard(Path path) {
            return new Board(10, 10);
        }
    }

    /**
     * Dummy implementation of {@link BoardFileWriter} that does nothing.
     */
    private static class DummyWriter implements BoardFileWriter {
        @Override
        public void writeBoard(Path path, Board board) {
        }
    }

    /**
     * Dummy implementation of {@link TileActionFactoryRegistry} that returns empty factories.
     */
    private static class DummyRegistry extends TileActionFactoryRegistry {
        @Override public Optional<DestinationTileActionFactory> getDestinationFactory(String type) { return Optional.empty(); }
        @Override public Optional<NoDestinationTileActionFactory> getNoDestinationFactory(String type) { return Optional.empty(); }
    }
}
