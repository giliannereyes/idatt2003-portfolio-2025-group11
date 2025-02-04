package edu.ntnu.idi.idatt.model.entities;

import java.util.Random;

/**
 * Represents a six-sided die.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Die {
  private int lastRolledValue;
  private static final Random random = new Random();

  /**
   * Rolls the die.
   *
   * @return the value of the die after rolling.
   */
  public int roll() {
    lastRolledValue = random.nextInt(6) + 1;
    return lastRolledValue;
  }

  /**
   * Returns the value of the last rolled die.
   *
   * @return the value of the last rolled die.
   *
   * @throws IllegalStateException if the die has not been rolled yet.
   */
  public int getValue() {
    if (lastRolledValue == 0) {
      throw new IllegalStateException("The die has not been rolled yet.");
    }
    return lastRolledValue;
  }
}

