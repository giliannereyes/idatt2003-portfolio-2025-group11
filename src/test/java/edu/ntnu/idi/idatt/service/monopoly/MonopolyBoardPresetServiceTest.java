package edu.ntnu.idi.idatt.service.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.factory.monopoly.MonopolyBoardFactory;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MonopolyBoardPresetService}.
 * These tests verify the behavior of the service when interacting with different implementations of {@link MonopolyBoardFactory}.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class MonopolyBoardPresetServiceTest {

    //------------ Positive test ------------

    /**
     * Verifies that the service correctly returns a map containing predefined boards
     * when the factory provides valid board data.
     */
    @Test
    public void testReturnsPredefinedBoards() {
        MonopolyBoardFactory stubFactory = new MonopolyBoardFactory() {
            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                Map<String, Board> boards = new HashMap<>();
                boards.put("classic", new Board(11, 11));
                boards.put("custom", new Board(13, 13));
                return boards;
            }
        };

        MonopolyBoardPresetService service = new MonopolyBoardPresetService(stubFactory);
        Map<String, Board> result = service.getPredefinedBoards();

        assertEquals(2, result.size(), "Expected 2 boards");
        assertTrue(result.containsKey("classic"), "Missing 'classic' board");
        assertTrue(result.containsKey("custom"), "Missing 'custom' board");
    }

    //------------ Negative test ------------

    /**
     * Verifies that the service throws a {@link RuntimeException} when the factory fails to provide board data.
     */
    @Test
    public void testThrowsRuntimeException() {
        MonopolyBoardFactory failingFactory = new MonopolyBoardFactory() {
            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                throw new IllegalStateException("Database unavailable");
            }
        };

        MonopolyBoardPresetService service = new MonopolyBoardPresetService(failingFactory);

        RuntimeException exception = assertThrows(RuntimeException.class, service::getPredefinedBoards);
        assertTrue(exception.getMessage().contains("Failed to load predefined boards"));
    }

    //------------ Edge cases ------------

    /**
     * Verifies that the service returns an empty map when the factory provides no predefined boards.
     */
    @Test
    public void testReturnsEmptyMap() {
        MonopolyBoardFactory emptyFactory = new MonopolyBoardFactory() {
            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                return new HashMap<>();
            }
        };

        MonopolyBoardPresetService service = new MonopolyBoardPresetService(emptyFactory);
        Map<String, Board> result = service.getPredefinedBoards();

        assertNotNull(result, "Returned map should not be null");
        assertTrue(result.isEmpty(), "Expected an empty map");
    }

    /**
     * Verifies that the service correctly returns a board with minimal dimensions,
     * simulating an incomplete or undersized board.
     */
    @Test
    public void testReturnsIncompleteBoard() {
        MonopolyBoardFactory incompleteBoardFactory = new MonopolyBoardFactory() {
            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                Map<String, Board> boards = new HashMap<>();
                boards.put("incomplete", new Board(3, 1));
                return boards;
            }
        };

        MonopolyBoardPresetService service = new MonopolyBoardPresetService(incompleteBoardFactory);
        Map<String, Board> result = service.getPredefinedBoards();

        assertTrue(result.containsKey("incomplete"), "Expected 'incomplete' board to be returned");
        assertNotNull(result.get("incomplete"), "Returned board should not be null");
    }

    /**
     * Verifies that the service throws a {@link RuntimeException} when the factory returns {@code null}
     * instead of a valid map of predefined boards.
     */
    @Test
    public void testFactoryReturnsNull() {
        MonopolyBoardFactory nullFactory = new MonopolyBoardFactory() {
            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                return null;
            }
        };

        MonopolyBoardPresetService service = new MonopolyBoardPresetService(nullFactory);

        RuntimeException exception = assertThrows(RuntimeException.class, service::getPredefinedBoards);
        assertTrue(exception.getMessage().contains("Failed to load predefined boards"));
    }

    /**
     * Verifies that the service handles a map containing a {@code null} board value without throwing an exception.
     * Ensures that the key is still present and the value is {@code null}.
     */
    @Test
    public void testFactoryReturnsMapWithNullBoard() {
        MonopolyBoardFactory factoryWithNullBoard = new MonopolyBoardFactory() {
            @Override
            public Map<String, Board> getAllPredefinedBoards() {
                Map<String, Board> boards = new HashMap<>();
                boards.put("broken", null);
                return boards;
            }
        };

        MonopolyBoardPresetService service = new MonopolyBoardPresetService(factoryWithNullBoard);
        Map<String, Board> result = service.getPredefinedBoards();

        assertTrue(result.containsKey("broken"));
        assertNull(result.get("broken"), "Expected null board value to be returned");
    }
}
