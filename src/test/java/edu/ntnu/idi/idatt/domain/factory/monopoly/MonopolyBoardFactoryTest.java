package edu.ntnu.idi.idatt.domain.factory.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.domain.action.monopoly.GoToJailAction;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.monopoly.PropertyRegistry;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link MonopolyBoardFactory} class.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class MonopolyBoardFactoryTest {
  private MonopolyBoardFactory factory;

  /**
   * Set up a fresh factory before each test.
   */
  @BeforeEach
  void setUp() {
    factory = new MonopolyBoardFactory();
  }

  // ------- Positive tests -------

  /**
   * Test loading the hardcoded small board.
   *
   * <p>Expected: The board should have the correct name and tile count.
   * Tiles 7 and 19 should each have a GoToJailAction.</p>
   */
  @Test
  void testLoadSmallBoard() {
    Board board = factory.loadSmallBoard();

    assertEquals(MonopolyBoardFactory.SMALL_BOARD, board.getName());
    assertEquals(24, board.getTiles().size(), "Small board should have 24 tiles");
    assertTrue(board.getTile(7).getLandAction().isPresent(), "Tile 7 should have an action");
    assertInstanceOf(GoToJailAction.class, board.getTile(7).getLandAction().get());
    assertTrue(board.getTile(19).getLandAction().isPresent(), "Tile 19 should have an action");
    assertInstanceOf(GoToJailAction.class, board.getTile(19).getLandAction().get());
  }

  /**
   * Test loading the hardcoded large board.
   *
   * <p>Expected: The board should have the correct name and tile count.
   * Tiles 10 and 28 should each have a GoToJailAction.</p>
   */
  @Test
  void testLoadLargeBoard() {
    Board board = factory.loadLargeBoard();

    assertEquals(MonopolyBoardFactory.LARGE_BOARD, board.getName());
    assertEquals(36, board.getTiles().size(), "Large board should have 36 tiles");
    assertTrue(board.getTile(10).getLandAction().isPresent(), "Tile 10 should have an action");
    assertInstanceOf(GoToJailAction.class, board.getTile(10).getLandAction().get());
    assertTrue(board.getTile(28).getLandAction().isPresent(), "Tile 28 should have an action");
    assertInstanceOf(GoToJailAction.class, board.getTile(28).getLandAction().get());
  }

  /**
   * Test retrieving all predefined boards.
   *
   * <p>Expected: The factory should return a map of the two boards keyed by name,
   * each with the correct tile count.</p>
   */
  @Test
  void testGetAllPredefinedBoards() {
    Map<String, Board> boards = factory.getAllPredefinedBoards();
    assertEquals(2, boards.size(), "Should return exactly two predefined boards");
    assertTrue(boards.containsKey(MonopolyBoardFactory.SMALL_BOARD));
    assertTrue(boards.containsKey(MonopolyBoardFactory.LARGE_BOARD));

    Board small = boards.get(MonopolyBoardFactory.SMALL_BOARD);
    Board large = boards.get(MonopolyBoardFactory.LARGE_BOARD);
    assertEquals(24, small.getTiles().size(), "Small board should have 24 tiles");
    assertEquals(36, large.getTiles().size(), "Large board should have 36 tiles");
  }

  /**
   * Test creating the property registry for the small board.
   *
   * <p>Expected: The registry should contain 20 properties.</p>
   */
  @Test
  void testCreatePropertyRegistryForSmallBoard() {
    Board small = factory.loadSmallBoard();
    PropertyRegistry registry = factory.createPropertyRegistryForBoard(small);
    assertEquals(20, registry.getAllProperties().size(), "Small board should register 20 properties");
  }

  /**
   * Test creating the property registry for the large board.
   *
   * <p>Expected: The registry should contain 32 properties.</p>
   */
  @Test
  void testCreatePropertyRegistryForLargeBoard() {
    Board large = factory.loadLargeBoard();
    PropertyRegistry registry = factory.createPropertyRegistryForBoard(large);
    assertEquals(32, registry.getAllProperties().size(), "Large board should register 33 properties");
  }

  // ------- Negative tests -------

  /**
   * Test creating a property registry with a null board.
   *
   * <p>Expected: Passing null should throw {@link IllegalArgumentException}.</p>
   */
  @Test
  void testCreatePropertyRegistryWithNullBoard() {
    assertThrows(IllegalArgumentException.class,
          () -> factory.createPropertyRegistryForBoard(null),
          "Passing null board should throw NullPointerException");
  }

  /**
   * Test creating a property registry for an unknown board.
   *
   * <p>Expected: An unrecognized board name should throw {@link IllegalArgumentException}.</p>
   */
  @Test
  void testCreatePropertyRegistryWithUnknownBoard() {
    Board dummy = new Board(1, 1);
    dummy.setName("Unknown board");
    assertThrows(IllegalArgumentException.class,
          () -> factory.createPropertyRegistryForBoard(dummy),
          "Unrecognized board name should throw IllegalArgumentException");
  }
}
