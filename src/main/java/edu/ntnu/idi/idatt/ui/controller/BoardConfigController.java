package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.service.BoardPersistenceService;
import edu.ntnu.idi.idatt.service.BoardPresetService;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.ui.view.BoardConfigView;
import edu.ntnu.idi.idatt.utils.ViewManager;
import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Controller responsible for managing board configuration in the UI.
 * Handles loading predefined boards, selecting and saving board configurations,
 * and updating the game configuration accordingly.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 * @see BoardConfigView
 */
public class BoardConfigController {
  private final BoardConfigView view;
  private final Map<String, Board> predefinedBoards;
  private final ViewManager viewManager;
  private final BoardPersistenceService fileHandler;
  protected final GameConfigService gameConfigService;
  protected Board selectedBoard;

  /**
   * Constructs a new BoardConfigController.
   *
   * @param view the view responsible for displaying board configuration options
   * @param boardPresetService the service providing predefined board configurations
   * @param viewManager the manager for switching between views
   * @param gameConfigService the service for updating the game configuration
   * @param boardPersistenceService the service for loading and saving board configurations
   */
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
    loadPredefinedBoards();
    if (fileHandler == null) {
      view.disableFileHandling();
    }
  }

  /**
   * Loads predefined boards into the view for user selection.
   * Displays an alert if loading fails.
   */
  public void loadPredefinedBoards() {
    try {
      predefinedBoards.values().forEach(
            board -> view.addBoardOption(board.getName(), board.getDescription())
      );
    } catch (Exception e) {
      view.showAlert("Error", "Failed to load predefined boards: " + e.getMessage());
    }
  }


  /**
   * Registers the currently selected board into the game configuration.
   * Switches to the next view if successful, otherwise shows an error alert.
   */
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

  /**
   * Selects a predefined board by its name.
   * Shows an alert if the board name is invalid.
   *
   * @param boardName the name of the board to select
   */
  public void selectPredefinedBoard(String boardName) {
    selectedBoard = predefinedBoards.get(boardName);
    if (selectedBoard == null) {
      view.showAlert("Error", "Invalid board selection");
    }
  }

  /**
   * Loads a board configuration from a file.
   * Updates the selected board and view if successful, otherwise shows an error alert.
   *
   * @param file the file containing the board configuration
   */
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

  /**
   * Saves the currently selected board configuration to a file.
   * Shows a success or error alert depending on the outcome.
   *
   * @param file the file to save the board configuration to
   */
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
