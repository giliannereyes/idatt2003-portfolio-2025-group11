package edu.ntnu.idi.idatt.service.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.factory.monopoly.MonopolyBoardFactory;
import edu.ntnu.idi.idatt.service.BoardPresetService;
import java.util.Map;

public class MonopolyBoardPresetService implements BoardPresetService {
  private final MonopolyBoardFactory factory;

  public MonopolyBoardPresetService(MonopolyBoardFactory factory) {
    this.factory = factory;
  }

  @Override
  public Map<String, Board> getPredefinedBoards() {
    try {
      return factory.getAllPredefinedBoards();
    } catch (Exception e) {
      throw new RuntimeException("Failed to load predefined boards", e);
    }
  }
}
