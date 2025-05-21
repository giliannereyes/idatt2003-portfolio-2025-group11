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
  Optional<Board> loadBoardConfiguration(File file);

  /**
   * Saves the given board configuration to the specified file.
   *
   * @param file the file to which the board configuration should be saved
   * @param board the {@link Board} object to save
   */
  void saveBoardConfiguration(File file, Board board);
}
