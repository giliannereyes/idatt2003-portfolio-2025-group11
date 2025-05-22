package edu.ntnu.idi.idatt.utils;

/**
 * Utility class for validation of method parameters.
 * It Provides common checks for nullity, emptiness, and numeric range
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Validation {
  /**
   * Prevents instantiation of the utility class.
   *
   * @throws UnsupportedOperationException if the utility class is instantiated.
   */
  private Validation() {
    throw new UnsupportedOperationException("Utility class cannot be instantiated");
  }

  /**
   * Validates that the provided string is not null or empty.
   *
   * @param str is the string to validate.
   * @param fieldName is the name of the field being validated.
   */
  public static void validateNonEmptyStr(String str, String fieldName) {
    if (str == null || str.isBlank()) {
      throw new IllegalArgumentException(fieldName + " cannot be empty or null");
    }
  }

  /**
   * Validates that the provided number is positive.
   *
   * @param number is the number to validate.
   * @param fieldName is the name of the field being validated.
   */
  public static void validatePositiveNum(double number, String fieldName) {
    if (number <= 0) {
      throw new IllegalArgumentException(fieldName + " must be positive.");
    }
  }

  /**
   * Validates that the provided number is non-negative.
   *
   * @param number is the number to validate.
   * @param fieldName is the name of the field being validated.
   */
  public static void validateNonNegativeNum(double number, String fieldName) {
    if (number < 0) {
      throw new IllegalArgumentException(fieldName + " cannot be negative.");
    }
  }

  /**
   * Validates that the provided object is not null.
   *
   * @param obj is the object to validate.
   * @param fieldName is the name of the field being validated.
   */
  public static void validateNonNull(Object obj, String fieldName) {
    if (obj == null) {
      throw new IllegalArgumentException(fieldName + " cannot be null.");
    }
  }
}
