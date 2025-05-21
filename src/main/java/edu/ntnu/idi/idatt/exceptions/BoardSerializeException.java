package edu.ntnu.idi.idatt.exceptions;

/**
 * A domain-specific exception that represents an error that occurs during the
 * serialization of a board.
 *
 * @version 0.1
 * @since 0.2
 * @author Gilianne Reyes
 */
public class BoardSerializeException extends RuntimeException {
  /**
   * Constructs a new BoardSerializeException with the specified detail message
   * and cause.
   *
   * @param message is the detail message.
   * @param cause is the cause.
   */
  public BoardSerializeException(String message, Throwable cause) {
    super(message, cause);
  }
}
