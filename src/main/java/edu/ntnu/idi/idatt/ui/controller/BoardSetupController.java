package edu.ntnu.idi.idatt.ui.controller;


import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.service.BoardService;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.utils.ViewManager;
import edu.ntnu.idi.idatt.ui.view.BoardSetupView;

import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Controller class for board setup. It handles the logic for updating the
 * BoardSetUpView based on user interactions and manages the selection of
 * predefined boards or loading boards from JSON files.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class BoardSetupController {
    private final BoardSetupView view;
    private final GameConfigService configService;
    private final ViewManager viewManager;
    private final BoardService boardService;
    private Board selectedBoard;

    /**
     * Constructor for BoardSetupController.
     *
     * @param view is the view for board setup.
     * @param boardService is the service for managing boards.
     * @param configService is the service for managing game configuration.
     * @param viewManager is the display manager for switching views.
     */
    public BoardSetupController(
          BoardSetupView view, BoardService boardService,
          GameConfigService configService, ViewManager viewManager
    ) {
        this.view = view;
        this.boardService = boardService;
        this.configService = configService;
        this.viewManager = viewManager;
    }

    /**
     * Loads all predefined boards into the View.
     */
    public void loadPredefinedBoards() {
        try {
            Map<String, Board> predefinedBoards = boardService.getPredefinedBoards();
            predefinedBoards.values().forEach(
                  board -> view.addBoardOption(board.getName(), board.getDescription())
            );
        } catch (RuntimeException e) {
            view.onErrorLoadingJson("Failed to load predefined boards: " + e.getMessage());
        }
    }

    /**
     * Handles a user selecting a predefined board.
     * Stores it but does not register it until the "Start Game" button is clicked.
     */
    public void selectPredefinedBoard(String boardName) {
        try {
            Board board = boardService.getPredefinedBoards().get(boardName);
            if (board != null) {
                this.selectedBoard = board;
            } else {
                view.onErrorLoadingJson("Selected board does not exist.");
            }
        } catch (RuntimeException e) {
            view.onErrorLoadingJson("Failed to select board: " + e.getMessage());
        }
    }

    /**
     * Loads a board from a JSON file.
     * The board is stored but not registered until the user clicks "Start Game".
     */
    public void loadBoardFromJson(File file) {
        try {
            Optional<Board> boardOpt = boardService.loadBoardFromJson(file);
            boardOpt.ifPresentOrElse(
                    board -> {
                        this.selectedBoard = board;
                        view.autoFillBoard(board.getName());
                    },
                    () -> view.onErrorLoadingJson("The board file is empty or invalid.")
            );
        } catch (Exception e) {
            view.onErrorLoadingJson(e.getMessage());
        }
    }

    /**
     * Registers the selected board in GameConfig and proceeds to the next step.
     */
    public void registerBoardSelection() {
        if (selectedBoard != null) {
            configService.updateBoard(selectedBoard);
            viewManager.switchToNextView();
        } else {
            view.onNoBoardSelected();
        }
    }

    /**
     * Saves the currently selected board to a JSON file.
     *
     * @param file is the file to save the board to.
     */
    public void saveBoardToJson(File file) {
        if (selectedBoard != null) {
            try {
                boardService.saveBoardToJson(file, selectedBoard);
                view.onBoardSaved();
            } catch (Exception e) {
                view.onErrorSavingBoard(e.getMessage());
            }
        } else {
            view.onNoBoardSelected();
        }
    }
}
