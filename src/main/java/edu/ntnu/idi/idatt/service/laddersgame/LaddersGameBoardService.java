package edu.ntnu.idi.idatt.service.laddersgame;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.factory.laddersgame.LaddersGameBoardFactory;
import edu.ntnu.idi.idatt.service.BoardPersistenceService;
import edu.ntnu.idi.idatt.service.BoardPresetService;
import edu.ntnu.idi.idatt.utils.Validation;
import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for managing Snakes and Ladders game boards.
 * Provides functionality to load, save, and retrieve predefined board configurations.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class LaddersGameBoardService implements BoardPersistenceService, BoardPresetService {
  private final LaddersGameBoardFactory factory;

  /**
   * Constructs a new {@code LaddersGameBoardService} with the given factory.
   *
   * @param factory the factory used to create and manage Snakes and Ladders boards
   */
  public LaddersGameBoardService(LaddersGameBoardFactory factory) {
    this.factory = factory;
  }

  /**
   * Loads a board configuration from the specified file.
   *
   * @param file the file containing the board configuration
   * @return an {@link Optional} containing the loaded {@link Board}, or empty if loading fails
   * @throws IllegalArgumentException if the file is null.
   * @throws RuntimeException if an error occurs during loading
   */
  @Override
  public Optional<Board> loadBoardConfiguration(File file) {
    Validation.validateNonNull(file, "file");
    try {
      return factory.loadBoardFromFile(file.toPath());
    } catch (Exception e) {
      throw new RuntimeException("Failed to load board: " + e.getMessage(), e);
    }
  }

  /**
   * Saves the given board configuration to the specified file.
   *
   * @param file the file to save the board to
   * @param board the board to be saved
   * @throws IllegalArgumentException if the file or board is null.
   * @throws RuntimeException if an error occurs during saving.
   */
  @Override
  public void saveBoardConfiguration(File file, Board board) {
    Validation.validateNonNull(file, "file");
    Validation.validateNonNull(board, "board");
    try {
      factory.saveBoardToFile(file.toPath(), board);
    } catch (Exception e) {
      throw new RuntimeException("Failed to save board: " + e.getMessage(), e);
    }
  }

  /**
   * Retrieves all predefined Snakes and Ladders boards.
   *
   * @return a map of board names to {@link Board} instances
   * @throws RuntimeException if an error occurs while retrieving the boards
   */
  @Override
  public Map<String, Board> getPredefinedBoards() {
    try {
      return factory.getAllPredefinedBoards();
    } catch (Exception e) {
      throw new RuntimeException("Failed to load predefined boards", e);
    }
  }
}
