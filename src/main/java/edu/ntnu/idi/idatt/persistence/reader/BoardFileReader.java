package edu.ntnu.idi.idatt.persistence.reader;

import edu.ntnu.idi.idatt.domain.entity.Board;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Interface for reading a {@link Board} from a file.
 *
 * @author Gilianne Reyes
 * @version 0.1
 */
public interface BoardFileReader {
  /**
   * Reads and deserializes a board from the specified file path.
   *
   * @param path is the file path to read from.
   *
   * @return the {@link Board} deserialized from the file.
   */
  Board readBoard(Path path) throws IOException;
}
