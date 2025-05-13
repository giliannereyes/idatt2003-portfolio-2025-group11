package edu.ntnu.idi.idatt.service.snakesandladders;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.SnakesAndLaddersFactory;
import edu.ntnu.idi.idatt.service.BoardPersistenceService;
import edu.ntnu.idi.idatt.service.BoardPresetService;
import java.io.File;
import java.util.Map;
import java.util.Optional;

public class LaddersGameBoardService implements BoardPersistenceService, BoardPresetService {
  private final SnakesAndLaddersFactory factory;

  public LaddersGameBoardService(SnakesAndLaddersFactory factory) {
    this.factory = factory;
  }

  @Override
  public Optional<Board> loadBoardConfiguration(File file) {
    try {
      return factory.loadBoardFromFile(file.toPath());
    } catch (Exception e) {
      throw new RuntimeException("Failed to load board: " + e.getMessage(), e);
    }
  }

  @Override
  public void saveBoardConfiguration(File file, Board board) {
    try {
      factory.saveBoardToFile(file.toPath(), board);
    } catch (Exception e) {
      throw new RuntimeException("Failed to save board: " + e.getMessage(), e);
    }
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
