package edu.ntnu.idi.idatt.persistence.writer;

import edu.ntnu.idi.idatt.domain.entity.Board;
import java.nio.file.Path;
import java.io.IOException;

/**
 * An interface for writing a board to a file.
 *
 * @version 0.1
 * @since 0.2
 * @author Gilianne Reyes
 */
public interface BoardFileWriter {
    /**
     * Writes a board to a file.
     *
     * @param path is the path to the file to write in.
     * @param board is the board to write to the file.
     *
     * @throws IOException if an I/O error occurs.
     */
    void writeBoard(Path path, Board board) throws IOException;
}
