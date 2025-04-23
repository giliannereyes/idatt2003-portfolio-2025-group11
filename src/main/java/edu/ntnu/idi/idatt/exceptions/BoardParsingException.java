package edu.ntnu.idi.idatt.exceptions;

/**
 * Domain-specific exception for parsing a board. Signals
 * that an error occurred while parsing a board.
 *
 * @version 0.1
 * @since 0.2
 * @author Gilianne Reyes
 */
public class BoardParsingException extends RuntimeException {
    /**
     * Constructs a BoardParsingException with the specified detail message.
     *
     * @param message is the detail message.
     */
    public BoardParsingException(String message) {
        super(message);
    }

    /**
     * Constructs a BoardParsingException with the specified detail message and cause.
     *
     * @param message is the detail message.
     * @param cause is the cause.
     */
    public BoardParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
