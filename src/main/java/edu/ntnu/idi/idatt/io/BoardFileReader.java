package edu.ntnu.idi.idatt.io;

import edu.ntnu.idi.idatt.model.entities.Board;

import java.io.IOException;
import java.nio.file.Path;

/**
 * An interface for reading a board from a file.
 *
 * @version 0.1
 * @since 0.2
 * @author Gilianne Reyes
 */
public interface BoardFileReader {
    /**
     * Reads a board from a file.
     *
     * @param path is the path to the file.
     *
     * @return the board read from the file.
     *
     * @throws IOException if an I/O error occurs.
     */
    Board readBoard(Path path) throws IOException;
}
