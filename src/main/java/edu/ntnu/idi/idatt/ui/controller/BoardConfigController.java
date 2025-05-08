package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.PredefinedBoardService;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.utils.ViewManager;
import java.util.Map;

public class BoardConfigController<B extends Board> {
  private final BoardConfigView view;
  private final Map<String, B> predefinedBoards;
  private final ViewManager viewManager;
  protected final GameConfigService<B> gameConfigService;
  protected B selectedBoard;

  public BoardConfigController(BoardConfigView view, PredefinedBoardService<B> predefinedBoardService,
                               ViewManager viewManager, GameConfigService<B> gameConfigService
  ) {
    this.view = view;
    this.predefinedBoards = predefinedBoardService.getPredefinedBoards();
    this.viewManager = viewManager;
    this.gameConfigService = gameConfigService;
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
}
