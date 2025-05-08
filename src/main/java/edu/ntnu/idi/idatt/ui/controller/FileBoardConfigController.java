package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.service.FileBoardService;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.PredefinedBoardService;
import edu.ntnu.idi.idatt.ui.view.FileBoardConfigView;
import edu.ntnu.idi.idatt.utils.ViewManager;
import java.io.File;
import java.util.Optional;

public class FileBoardConfigController<B extends Board> extends BoardConfigController<B> {
  private final FileBoardService<B> fileBoardService;
  private final FileBoardConfigView view;

  public FileBoardConfigController(FileBoardConfigView view, PredefinedBoardService<B> predefinedBoardService,
                                   ViewManager viewManager, GameConfigService<B> gameConfigService,
                                   FileBoardService<B> fileBoardService
  ) {
    super(view, predefinedBoardService, viewManager, gameConfigService);
    this.fileBoardService = fileBoardService;
    this.view = view;
    view.setController(this);
  }

  public void loadBoardConfiguration(File file) {
    try {
      Optional<B> boardOpt = fileBoardService.loadBoardConfiguration(file);
      boardOpt.ifPresentOrElse(board -> {
        selectedBoard = board;
        view.updateSelectedBoard(board.getName() + "(Loaded from file)");
      }, () -> view.showAlert("Error", "The file does not contain a valid board configuration"));
    } catch (Exception e) {
      view.showAlert("Error", "An error occurred while loading the board: " + e.getMessage());
    }
  }

  public void saveBoardConfiguration(File file) {
    if (selectedBoard != null) {
      try {
        fileBoardService.saveBoardConfiguration(file, selectedBoard);
        view.showAlert("Success", "Board saved successfully!");
      } catch (Exception e) {
        view.showAlert("Error", "An error occurred while saving the board: " + e.getMessage());
      }
    } else {
      view.showAlert("No board to save", "You must select a board before saving.");
    }
  }
}
