package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.domain.entity.Board;
import java.util.Map;

public interface BoardPresetService {
  Map<String, Board> getPredefinedBoards();
}
