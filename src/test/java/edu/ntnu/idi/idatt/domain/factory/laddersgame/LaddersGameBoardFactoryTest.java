package edu.ntnu.idi.idatt.domain.factory.laddersgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.domain.action.laddersgame.LadderAction;
import edu.ntnu.idi.idatt.domain.action.laddersgame.ResetAction;
import edu.ntnu.idi.idatt.domain.action.laddersgame.SkipTurnAction;
import edu.ntnu.idi.idatt.domain.action.laddersgame.SnakeAction;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.factory.TileActionFactoryRegistry;
import edu.ntnu.idi.idatt.persistence.reader.BoardFileReader;
import edu.ntnu.idi.idatt.persistence.writer.BoardFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the BoardGameFactory class.
 *
 * @version 0.1
 * @since 0.2
 * @author Gilianne Reyes
 */
public class LaddersGameBoardFactoryTest {
  private TestBoardFileReader reader;
  private TestBoardFileWriter writer;
  private LaddersGameBoardFactory factory;

  /**
   * Set up the test environment.
   */
  @BeforeEach
  public void setUp() {
    TileActionFactoryRegistry registry = new TileActionFactoryRegistry();
    registry.registerDestinationFactory(new LadderActionFactory());
    registry.registerDestinationFactory(new SnakeActionFactory());
    registry.registerNoDestinationFactory(new SkipTurnActionFactory());
    registry.registerNoDestinationFactory(new ResetActionFactory());
    reader = new TestBoardFileReader();
    writer = new TestBoardFileWriter();
    factory = new LaddersGameBoardFactory(reader, writer, registry);
  }

  // ------- Positive tests -------

  /**
   * Test loading the hardcoded easy board.
   *
   * <p>Expected: The board should have the correct name and tile count.
   * The board should also have actions in the correct tiles.</p>
   */
  @Test
  public void testLoadEasyBoard() {
    Board board = factory.loadEasyBoard();
    assertEquals("Easy board", board.getName());
    assertEquals(56, board.getTiles().size());
    assertTrue(board.getTile(2).getLandAction().isPresent());
    assertInstanceOf(LadderAction.class, board.getTile(2).getLandAction().get());
    assertTrue(board.getTile(32).getLandAction().isPresent());
    assertInstanceOf(SnakeAction.class, board.getTile(32).getLandAction().get());
  }

  /**
   * Test loading the hardcoded medium board.
   *
   * <p>Expected: The board should have the correct name and tile count.
   * The board should also have actions in the correct tiles.</p>
   */
  @Test
  public void testLoadMediumBoard() {
    Board board = factory.loadMediumBoard();
    assertEquals("Medium board", board.getName());
    assertEquals(64, board.getTiles().size());
    assertTrue(board.getTile(3).getLandAction().isPresent());
    assertInstanceOf(LadderAction.class, board.getTile(3).getLandAction().get());
    assertTrue(board.getTile(52).getLandAction().isPresent());
    assertInstanceOf(SkipTurnAction.class, board.getTile(52).getLandAction().get());
  }

  /**
   * Test loading the hardcoded hard board.
   *
   * <p>Expected: The board should have the correct name and tile count.
   * The board should also have actions in the correct tiles.</p>
   */
  @Test
  public void testLoadHardBoard() {
    Board board = factory.loadHardBoard();
    assertEquals("Hard board", board.getName());
    assertEquals(100, board.getTiles().size());
    assertTrue(board.getTile(4).getLandAction().isPresent());
    assertInstanceOf(LadderAction.class, board.getTile(4).getLandAction().get());
    assertTrue(board.getTile(47).getLandAction().isPresent());
    assertInstanceOf(SnakeAction.class, board.getTile(47).getLandAction().get());
    assertTrue(board.getTile(35).getLandAction().isPresent());
    assertInstanceOf(ResetAction.class, board.getTile(35).getLandAction().get());
  }

  /**
   * Test loading all predefined boards.
   *
   * <p>Expected: The factory should return a map of all predefined boards keyed by their name.</p>
   */
  @Test
  public void testGetAllPredefinedBoards() {
    Map<String, Board> boards = factory.getAllPredefinedBoards();
    assertEquals(3, boards.size());
    assertTrue(boards.containsKey("Easy board"));
    assertTrue(boards.containsKey("Medium board"));
    assertTrue(boards.containsKey("Hard board"));
    Board easy   = boards.get("Easy board");
    Board medium = boards.get("Medium board");
    Board hard   = boards.get("Hard board");
    assertEquals(7 * 8, easy.getTiles().size(),   "Easy board should be 7×8 = 56 tiles");
    assertEquals(8 * 8, medium.getTiles().size(), "Medium board should be 8×8 = 64 tiles");
    assertEquals(10 * 10, hard.getTiles().size(), "Hard board should be 10×10 = 100 tiles");
  }

  /**
   * Test the method that encapsulates loading a board from a file.
   *
   * <p>Expected: The factory should call the read method of the reader
   * which sets the test implementation's read flag to true.</p>
   * (In real implementations, this would load the board from the file)
   *
   * @throws IOException if an I/O error occurs.
   */
  @Test
  public void testReaderWasCalled() throws IOException {
    Path tempFile = Files.createTempFile("test", ".json");
    factory.loadBoardFromFile(tempFile);
    assertTrue(reader.wasReadCalled());
    Files.delete(tempFile);
  }

  /**
   * Test the method that encapsulates saving a board to a file.
   *
   * <p>Expected: The factory should call the write method of the writer
   * which sets the test implementation's write flag to true.</p>
   * (In real implementations, this would save the board to the file)
   *
   * @throws IOException if an I/O error occurs.
   */
  @Test
  public void testWriterWasCalled() throws IOException {
    Path tempFile = Files.createTempFile("test", ".json");
    factory.saveBoardToFile(tempFile, new Board(10, 10));
    assertTrue(writer.wasWriteCalled());
    Files.delete(tempFile);
  }

  // ------- Negative tests -------

  /**
   * Test instantiating the factory with null parameters.
   *
   * <p>Expected: The factory should throw an IllegalArgumentException
   * if any of the parameters are null.</p>
   */
  @Test
  public void testInstantiateWithNullParameters() {
    assertThrows(IllegalArgumentException.class,
          () -> new LaddersGameBoardFactory(null, writer, new TileActionFactoryRegistry()));
    assertThrows(IllegalArgumentException.class,
          () -> new LaddersGameBoardFactory(reader, null, new TileActionFactoryRegistry()));
    assertThrows(IllegalArgumentException.class,
          () -> new LaddersGameBoardFactory(reader, writer, null));
  }

  /**
   * Test loading a board from a file with a null path.
   *
   * <p>Expected: The factory should throw an IllegalArgumentException.</p>
   */
  @Test
  public void testLoadBoardWithNullPath() {
    assertThrows(IllegalArgumentException.class,
          () -> factory.loadBoardFromFile(null));
  }

  /**
   * Test saving a board to a file with a null path.
   *
   * <p>Expected: The factory should throw an IllegalArgumentException.</p>
   */
  @Test
  public void testSaveBoardWithNullPath() {
    assertThrows(IllegalArgumentException.class,
          () -> factory.saveBoardToFile(null, new Board(10, 10)));
  }

  /**
   * Test saving a board to a file with a null board.
   *
   * <p>Expected: The factory should throw an IllegalArgumentException.</p>
   */
  @Test
  public void testSaveBoardWithNullBoard() {
    assertThrows(IllegalArgumentException.class,
          () -> factory.saveBoardToFile(Path.of("test.json"), null));
  }

  // ------- Test classes -------

  /**
   * Test implementation of BoardFileReader for testing. This implementation
   * does not read from a file, but sets a flag when the read method is called.
   * The readers are tested in their own unit tests.
   */
  private static class TestBoardFileReader implements BoardFileReader {
    private boolean readCalled = false;

    @Override
    public Board readBoard(Path path) {
      readCalled = true;
      return new Board(10, 10);
    }

    public boolean wasReadCalled() {
      return readCalled;
    }
  }

  /**
   * Test implementation of BoardFileWriter for testing.
   * This implementation does not write to a file, but sets a flag when the write method is called.
   * The writers are tested in their own unit tests.
   */
  private static class TestBoardFileWriter implements BoardFileWriter {
    private boolean writeCalled = false;

    @Override
    public void writeBoard(Path path, Board board) {
      writeCalled = true;
    }

    public boolean wasWriteCalled() {
      return writeCalled;
    }
  }
}

