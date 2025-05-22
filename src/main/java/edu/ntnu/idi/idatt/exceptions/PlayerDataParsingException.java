package edu.ntnu.idi.idatt.exceptions;

/**
 * Thrown to indicate that an error occurred while parsing player data from a source.
 *
 * @author Gilianne Reyes
 * @version 0.1
 */
public class PlayerDataParsingException extends RuntimeException {
  /**
   * Constructs a new PlayerDataParsingException with the specified detail
   * message and cause.
   *
   * @param message a detailed message explaining the reason for the exception
   * @param cause   the underlying exception that caused this error
   */
  public PlayerDataParsingException(String message, Throwable cause) {
    super(message, cause);
  }
}
