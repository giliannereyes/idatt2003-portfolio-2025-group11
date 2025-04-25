package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.factory.BoardGameFactory;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public class BoardService {
    BoardGameFactory boardGameFactory;

    public BoardService(BoardGameFactory boardGameFactory) {
        this.boardGameFactory = boardGameFactory;
    }

    public Optional<Board> loadBoardFromJson(File file) {
        try {
            return boardGameFactory.loadBoardFromFile(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load board: " + e.getMessage(), e);
        }
    }

    public void saveBoardToJson(File file, Board board) {
        try {
            boardGameFactory.saveBoardToFile(file.toPath(), board);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save board: " + e.getMessage(), e);
        }
    }

    public Map<String, Board> getPredefinedBoards() {
        try {
            return boardGameFactory.getAllPredefinedBoards();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load predefined boards", e);
        }
    }

}
