package edu.ntnu.idi.idatt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link GameConfigService}, verifying its behavior
 * when updating and retrieving game configuration.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class GameConfigServiceTest {
  private GameConfig gameConfig;
  private GameConfigService service;

  /**
   * Sets up a minimal valid game configuration before each test.
   */
  @BeforeEach
  public void setUp() {
    gameConfig = new GameConfig() {
      @Override
      public boolean isComplete() {
        return true;
      }
    };
    gameConfig.setBoard(new Board(2, 2));
    gameConfig.getBoard().addTile(new Tile(1, 0, 0));
    gameConfig.setPlayerConfigs(List.of(new PlayerConfig(new Player("A"), "a.png")));
    Validation.validatePositiveNum(gameConfig.getBoard().getRows(), "Board rows");
    Validation.validatePositiveNum(gameConfig.getBoard().getColumns(), "Board columns");
    Validation.validateNonEmptyStr("A", "Player name");
    Validation.validateNonEmptyStr("a.png", "Token path");

    service = new GameConfigService(gameConfig);
  }

  //------------ Positive test ------------

  /**
   * Verifies that {@code build()} returns the same {@link GameConfig} instance.
   */
  @Test
  void testGetGameConfigReturnsSameGameConfig() {
    GameConfig result = service.getGameConfig();
    assertSame(gameConfig, result, "build() should return the original GameConfig instance");
  }

  /**
   * Verifies that the board can be updated successfully.
   */
  @Test
  void testUpdateBoardSuccessfully() {
    Board newBoard = new Board(3, 3);
    newBoard.addTile(new Tile(1, 0, 0));
    service.updateBoard(newBoard);

    assertEquals(newBoard, service.getGameConfig().getBoard(), "Board should be updated correctly");
  }

  /**
   * Verifies that the player configurations can be updated successfully.
   */
  @Test
  void testUpdatePlayerConfigsSuccessfully() {
    List<PlayerConfig> newPlayers = List.of(
          new PlayerConfig(new Player("B"), "b.png"),
          new PlayerConfig(new Player("C"), "c.png")
    );
    service.updatePlayerConfigs(newPlayers);
    for (PlayerConfig config : newPlayers) {
      Validation.validateNonEmptyStr(config.getPlayer().getName(), "Player name");
      Validation.validateNonEmptyStr(config.getTokenImagePath(), "Token path");
    }

    assertEquals(newPlayers, service.getGameConfig().getPlayerConfigs(),
          "PlayerConfigs should be updated correctly");
  }

  /**
   * Verifies that {@code isConfigComplete()} returns true for a complete configuration.
   */
  @Test
  void testIsConfigCompleteReturnsTrue() {
    assertTrue(service.isConfigComplete(), "Expected config to be complete");
  }

  //------------ Negative test ------------

  /**
   * Verifies that updating the board with {@code null} throws an {@link IllegalArgumentException}.
   */
  @Test
  void testUpdateBoardWithNull() {
    assertThrows(IllegalArgumentException.class, () -> service.updateBoard(null),
          "Expected exception when setting board to null");
  }

  /**
   * Verifies that updating the player configs
   * with {@code null} throws an {@link IllegalArgumentException}.
   */
  @Test
  void testUpdatePlayerConfigsWithNull() {
    assertThrows(IllegalArgumentException.class, () -> service.updatePlayerConfigs(null),
          "Expected exception when setting player configs to null");
  }

  //------------ Edge cases ------------

  /**
   * Verifies that updating the player configs with an empty list results in an empty configuration.
   */
  @Test
  void testUpdatePlayerConfigsWithEmptyList() {
    service.updatePlayerConfigs(List.of());
    assertTrue(service.getGameConfig().getPlayerConfigs().isEmpty(),
          "Player configs should be empty after setting to empty list");
  }

  /**
   * Verifies that a minimal board (1x1 with one tile) can be set and retrieved correctly.
   */
  @Test
  void testUpdateBoardWithMinimalBoard() {
    Board tinyBoard = new Board(1, 1);
    tinyBoard.addTile(new Tile(1, 0, 0));
    Validation.validatePositiveNum(tinyBoard.getRows(), "Tiny board rows");
    Validation.validatePositiveNum(tinyBoard.getColumns(), "Tiny board columns");
    Validation.validateNonNegativeNum(0, "Tile X");
    Validation.validateNonNegativeNum(0, "Tile Y");
    service.updateBoard(tinyBoard);

    Board result = service.getGameConfig().getBoard();
    assertEquals(1, result.getTiles().size(), "Minimal board should have exactly one tile");
    assertEquals(1, result.getTile(1).getTileId());
  }

  /**
   * Verifies that the board can be updated with no tiles.
   * This test ensures that the service accepts an empty board layout without throwing an exception.
   */
  @Test
  void testUpdateBoardWithNoTiles() {
    Board emptyBoard = new Board(2, 2);
    service.updateBoard(emptyBoard);

    assertTrue(service.getGameConfig().getBoard().getTiles().isEmpty(),
          "Expected board with no tiles");
  }
}
