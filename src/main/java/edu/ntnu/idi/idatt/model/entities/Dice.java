package edu.ntnu.idi.idatt.model.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of six-sided dice.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Dice {
  private final List<Die> dice;

  /**
   * Creates a collection of dice.
   *
   * @param numberOfDice the number of dice to create.
   *
   * @throws IllegalArgumentException if the number of dice is less than or equal to 0.
   */
  public Dice(int numberOfDice) {
    if (numberOfDice <= 0) {
      throw new IllegalArgumentException("Number of dice must be greater than 0.");
    }
    dice = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(new Die());
    }
  }

  /**
   * Rolls the dice.
   *
   * @return the total value of the dice after rolling.
   */
  public int roll() {
    int total = 0;
    for (Die die : dice) {
      total += die.roll();
    }
    return total;
  }

  /**
   * Returns the value of a selected die.
   *
   * @param dieNumber is the index of the die to get the value of.
   *
   * @return the value of the selected die.
   *
   * @throws IllegalArgumentException if the die number is invalid.
   */
  public int getDie(int dieNumber) {
    if (dieNumber < 0 || dieNumber >= dice.size()) {
      throw new IllegalArgumentException("Invalid die number: " + dieNumber);
    }
    return dice.get(dieNumber).getValue();
  }

  /**
   * Returns the list of dice. This method is used for testing purposes.
   *
   * @return the list of dice.
   */
  public List<Die> getDice() {
    return dice;
  }
}

