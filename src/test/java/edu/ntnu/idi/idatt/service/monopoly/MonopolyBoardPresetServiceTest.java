package edu.ntnu.idi.idatt.service.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.factory.monopoly.MonopolyBoardFactory;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MonopolyBoardPresetService}.
 * These tests verify the behavior of the service when
 * interacting with different implementations of {@link MonopolyBoardFactory}.
 *
 * @version 0.2
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
    Validation.validateNonEmptyStr(result.get("classic").getName(), "Missing 'classic' board");
    Validation.validateNonEmptyStr(result.get("custom").getName(), "Missing 'custom' board");
  }

  /**
   * Verifies that a board is created with positive row and column dimensions.
   * This test ensures that the validation utility accepts valid dimensions.
   */
  @Test
  public void testBoardHasPositiveDimensions() {
    Board board = new Board(5, 7);
    Validation.validatePositiveNum(board.getRows(), "Board rows");
    Validation.validatePositiveNum(board.getColumns(), "Board columns");
  }

  //------------ Negative test ------------

  /**
   * Verifies that the service throws a {@link RuntimeException}
   * when the factory fails to provide board data.
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

    Validation.validateNonNull(result, "Returned map should not be null");
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
    Validation.validateNonNull(result.get("incomplete"), "Returned board should not be null");
  }

  /**
   * Verifies that the service throws a {@link RuntimeException}
   * when the factory returns {@code null}
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
   * Verifies that the service handles a map containing a {@code null}
   * board value without throwing an exception.
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

  /**
   * Verifies that a predefined board has a non-empty name and description.
   * Ensures that the board metadata is valid according to the validation utility.
   */
  @Test
  public void testBoardHasValidNameAndDescription() {
    MonopolyBoardFactory factory = new MonopolyBoardFactory() {
      @Override
      public Map<String, Board> getAllPredefinedBoards() {
        Board board = new Board(7, 7);
        board.setName("Small Board");
        board.setDescription("A test board for minimal layout");
        return Map.of("small", board);
      }
    };

    MonopolyBoardPresetService service = new MonopolyBoardPresetService(factory);
    Map<String, Board> result = service.getPredefinedBoards();

    Board board = result.get("small");
    Validation.validateNonEmptyStr(board.getName(), "Board name");
    Validation.validateNonEmptyStr(board.getDescription(), "Board description");
  }
}
