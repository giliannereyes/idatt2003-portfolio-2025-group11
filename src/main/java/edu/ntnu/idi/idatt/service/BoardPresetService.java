package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.domain.entity.Board;
import java.util.Map;


/**
 * Interface for services that provide predefined board configurations.
 * Implementations of this interface are responsible for supplying a collection
 * of named {@link Board} instances that can be used as presets.
 *
 *  @version 0.1
 *  @since 0.1
 *  @author Gilianne Reyes
 */
public interface BoardPresetService {

  /**
   * Retrieves a collection of predefined board configurations.
   *
   * @return a map where the key is the name of the preset and the value is the corresponding {@link Board}
   */
  Map<String, Board> getPredefinedBoards();
}
