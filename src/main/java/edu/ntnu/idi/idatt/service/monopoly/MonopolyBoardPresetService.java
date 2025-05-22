package edu.ntnu.idi.idatt.service.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.factory.monopoly.MonopolyBoardFactory;
import edu.ntnu.idi.idatt.service.BoardPresetService;
import java.util.Map;

/**
 * Service implementation for retrieving predefined Monopoly game boards.
 * This class uses a {@link MonopolyBoardFactory} to load board configurations.
 */
public class MonopolyBoardPresetService implements BoardPresetService {
  private final MonopolyBoardFactory factory;

  /**
   * Constructs a new MonopolyBoardPresetService with the specified factory.
   *
   * @param factory the factory used to create or retrieve predefined Monopoly boards.
   */
  public MonopolyBoardPresetService(MonopolyBoardFactory factory) {
    this.factory = factory;
  }

  /**
   * Retrieves a map of predefined Monopoly boards.
   * The map keys are board names or identifiers, and the values are {@link Board} instances.
   *
   * @return a map containing predefined board configurations
   * @throws RuntimeException if the predefined boards cannot be loaded
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
