package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.domain.entity.Board;
import java.io.File;
import java.util.Optional;

/**
 * Interface for services that handle the persistence of board configurations.
 * Provides methods to load and save {@link Board} objects from and to files.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface BoardPersistenceService {

  /**
   * Loads a board configuration from the specified file.
   *
   * @param file the file from which the board configuration should be loaded
   * @return an {@link Optional} containing the loaded {@link Board}, or empty if loading fails
   */
  Optional<Board> loadBoardConfiguration(File file);

  /**
   * Saves the given board configuration to the specified file.
   *
   * @param file the file to which the board configuration should be saved
   * @param board the {@link Board} object to save
   */
  void saveBoardConfiguration(File file, Board board);
}
