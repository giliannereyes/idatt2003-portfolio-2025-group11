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
  Map<String, Board> getPredefinedBoards();
}
