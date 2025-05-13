package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.domain.entity.Board;
import java.io.File;
import java.util.Optional;

public interface BoardPersistenceService {
  Optional<Board> loadBoardConfiguration(File file);

  void saveBoardConfiguration(File file, Board board);
}
