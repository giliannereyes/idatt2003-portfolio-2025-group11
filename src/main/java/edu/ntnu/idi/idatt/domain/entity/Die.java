package edu.ntnu.idi.idatt.domain.entity;

import java.util.Random;

/**
 * Represents a six-sided die with standard fair faces 1–6.
 * It can be rolled to generate a random value between 1 and 6, in
 * which the last rolled value can be retrieved.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Die {
  private int lastRolledValue;
  private static final Random random = new Random();

  /**
   * Rolls the die, generating a random value between 1 and 6.
   *
   * @return the value of the die after rolling.
   */
  public int roll() {
    lastRolledValue = random.nextInt(6) + 1;
    return lastRolledValue;
  }

  /**
   * Returns the value of from the most recent roll of the die.
   *
   * @return the last rolled value of the die, between 1 and 6.
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

