package edu.ntnu.idi.idatt.domain.entity;

import edu.ntnu.idi.idatt.utils.Validation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a collection of six-sided dice.
 * It can be rolled to generate a random value between 1 and 6 for each die,
 * and the last rolled value of each die can be retrieved.
 *
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Dice {
  private final List<Die> dice;

  /**
   * Constructs a Dice object with a specified number of dice.
   *
   * @param numberOfDice is the number of dice to create.
   *
   * @throws IllegalArgumentException if the number of dice is less than or equal to 0.
   */
  public Dice(int numberOfDice) {
    Validation.validatePositiveNum(numberOfDice, "Number of dice");
    dice = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(new Die());
    }
  }

  /**
   * Rolls the dice.
   *
   * @return the sum of the values of the last rolled dice.
   */
  public int roll() {
    return dice.stream()
          .mapToInt(Die::roll)
          .sum();
  }

  /**
   * Returns the value of a selected die.
   *
   * @param dieNumber is the index of the die to get the value of.
   *
   * @return the value of the selected die.
   *
   * @throws IndexOutOfBoundsException if the die number is invalid.
   * @throws IllegalStateException if the die has not been rolled yet.
   */
  public int getDie(int dieNumber) {
    if (dieNumber < 0 || dieNumber >= dice.size()) {
      throw new IndexOutOfBoundsException(
            String.format("dieNumber must be between 0 and %d (inclusive)", dice.size() - 1)
      );
    }
    return dice.get(dieNumber).getValue();
  }

  /**
   * Returns the list of dice. This method is used for testing purposes.
   *
   * @return the list of dice.
   */
  public List<Die> getDice() {
    return Collections.unmodifiableList(dice);
  }
}

