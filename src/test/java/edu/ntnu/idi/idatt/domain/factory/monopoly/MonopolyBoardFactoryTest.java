package edu.ntnu.idi.idatt.domain.factory.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
   * Test retrieving all predefined boards.
   *
   * <p>Expected: The factory should return a map of the two boards keyed by name,
   * each with the correct tile count.</p>
   */
  @Test
  void testGetAllPredefinedBoards() {
    Map<String, Board> boards = factory.getAllPredefinedBoards();
    assertEquals(2, boards.size(), "Should return exactly two predefined boards");
    assertTrue(boards.containsKey(MonopolyBoardFactory.NORMAL_MODE));
    assertTrue(boards.containsKey(MonopolyBoardFactory.QUICK_MODE));

    Board normal = boards.get(MonopolyBoardFactory.NORMAL_MODE);
    Board quick = boards.get(MonopolyBoardFactory.QUICK_MODE);
    assertEquals(24, normal.getTiles().size());
    assertEquals(24, quick.getTiles().size());
  }

  /**
   * Test creating a property registry for a predefined board.
   *
   * <p>Expected: The factory should return a property registry with the correct
   * number of properties.</p>
   */
  @Test
  void testCorrectPropertyRegistry() {
    Board normal = factory.getAllPredefinedBoards().get(MonopolyBoardFactory.NORMAL_MODE);
    PropertyRegistry registry = factory.createPropertyRegistryForBoard(normal);
    assertInstanceOf(PropertyRegistry.class, registry, "Should return a PropertyRegistry");
    assertEquals(20, registry.getAllProperties().size(), "Should have 22 properties");
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
