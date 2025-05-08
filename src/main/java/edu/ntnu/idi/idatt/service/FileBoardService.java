package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.domain.entity.Board;
import java.io.File;
import java.util.Optional;

public interface FileBoardService<B extends Board> {
  Optional<B> loadBoardConfiguration(File file);

  void saveBoardConfiguration(File file, B board);
}
