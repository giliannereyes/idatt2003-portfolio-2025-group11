package edu.ntnu.idi.idatt.exceptions;

/**
 * Thrown to indicate that an error occurred while serializing player data.
 *
 *
 * @author Gilianne Reyes
 * @version 0.1
 */
public class PlayerDataSerializeException extends RuntimeException {
  /**
   * Constructs a new PlayerDataSerializeException with the specified detail
   * message and cause.
   *
   * @param message a detailed message explaining the reason for the exception
   * @param cause   the underlying exception that caused this error
   */
  public PlayerDataSerializeException(String message, Throwable cause) {
    super(message, cause);
  }
}
