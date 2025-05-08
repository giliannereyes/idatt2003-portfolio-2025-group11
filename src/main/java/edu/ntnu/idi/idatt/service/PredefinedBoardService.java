package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.domain.entity.Board;
import java.util.Map;

public interface PredefinedBoardService<B extends Board> {
  Map<String, B> getPredefinedBoards();
}
