package edu.ntnu.idi.idatt.service.monopoly;

import edu.ntnu.idi.idatt.domain.entity.monopoly.MonopolyBoard;
import edu.ntnu.idi.idatt.domain.factory.monopoly.MonopolyBoardFactory;
import edu.ntnu.idi.idatt.service.PredefinedBoardService;
import java.util.Map;

public class MonopolyPredefinedBoardService implements PredefinedBoardService<MonopolyBoard> {
  private final MonopolyBoardFactory factory;

  public MonopolyPredefinedBoardService(MonopolyBoardFactory factory) {
    this.factory = factory;
  }

  @Override
  public Map<String, MonopolyBoard> getPredefinedBoards() {
    try {
      return factory.getAllPredefinedBoards();
    } catch (Exception e) {
      throw new RuntimeException("Failed to load predefined boards", e);
    }
  }
}
