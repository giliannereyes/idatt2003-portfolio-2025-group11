package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.service.BoardPersistenceService;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.BoardPresetService;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.utils.ViewManager;
import java.io.File;
import java.util.Map;
import java.util.Optional;

public class BoardConfigController {
  private final BoardConfigView view;
  private final Map<String, Board> predefinedBoards;
  private final ViewManager viewManager;
  private final BoardPersistenceService fileHandler;
  protected final GameConfigService gameConfigService;
  protected Board selectedBoard;

  public BoardConfigController(
        BoardConfigView view,
        BoardPresetService boardPresetService,
        ViewManager viewManager,
        GameConfigService gameConfigService,
        BoardPersistenceService boardPersistenceService
  ) {
    this.view = view;
    this.predefinedBoards = boardPresetService.getPredefinedBoards();
    this.viewManager = viewManager;
    this.gameConfigService = gameConfigService;
    this.fileHandler = boardPersistenceService;
    view.setController(this);
    loadPredefinedBoards();
  }

  public void loadPredefinedBoards() {
    try {
      predefinedBoards.values().forEach(
            board -> view.addBoardOption(board.getName(), board.getDescription())
      );
    } catch (Exception e) {
      view.showAlert("Error", "Failed to load predefined boards: " + e.getMessage());
    }
  }

  public void registerBoardSelection() {
    if (selectedBoard != null) {
      try {
        gameConfigService.updateBoard(selectedBoard);
        viewManager.switchToNextView();
      } catch (Exception e) {
        view.showAlert("Error", "Failed to register board selection: " + e.getMessage());
      }
    } else {
      view.showAlert("Error", "No board selected");
    }
  }

  public void selectPredefinedBoard(String boardName) {
    selectedBoard = predefinedBoards.get(boardName);
    if (selectedBoard == null) {
      view.showAlert("Error", "Invalid board selection");
    }
  }

  public void loadBoardConfiguration(File file) {
    if (fileHandler == null) {
      view.showAlert("Unavailable service", "File handling service is not available");
    } else {
      try {
        Optional<Board> boardOpt = fileHandler.loadBoardConfiguration(file);
        boardOpt.ifPresentOrElse(
              board -> {
                selectedBoard = board;
                view.updateSelectedBoard(board.getName() + " (Loaded from file)");
              },
              () -> view.showAlert("Error", "The file does not contain a valid board configuration")
        );
      } catch (Exception e) {
        view.showAlert("Error", "An error occurred while loading the board: " + e.getMessage());
      }
    }
  }

  public void saveBoardConfiguration(File file) {
    if (fileHandler == null) {
      view.showAlert("Unavailable service", "File handling service is not available");
    } else {
      if (selectedBoard != null) {
        try {
          fileHandler.saveBoardConfiguration(file, selectedBoard);
          view.showAlert("Success", "Board saved successfully!");
        } catch (Exception e) {
          view.showAlert("Error", "An error occurred while saving the board: " + e.getMessage());
        }
      } else {
        view.showAlert("No board to save", "You must select a board before saving.");
      }
    }
  }
}
