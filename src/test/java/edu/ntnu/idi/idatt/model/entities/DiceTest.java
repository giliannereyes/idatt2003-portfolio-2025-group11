package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.model.entities.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Dice class.
 * <p>
 *  Tests the following:
 *  <ul>
 *    <li>Instantiation of a Dice object, both valid and invalid.</li>
 *    <li>Rolling the dice and checking for valid values and correct total values.</li>
 *    <li>Getting the value of a die with an invalid die number.</li>
 *  </ul>
 * </p>
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public class DiceTest {
  Dice dice;

  /**
   * Instantiates a valid Dice object with two dice before each test.
   */
  @BeforeEach
  public void setUp() {
    dice = new Dice(2);
  }

  // -------------------- Positive tests --------------------
  /**
   * Tests the instantiation of a Dice object.
   *
   * <p>Expected outcome: Dice object is created with
   * the correct number of dice.</p>
   */
  @Test
  public void testDiceInstantiation() {
    assertNotNull(dice);
    assertInstanceOf(Dice.class, dice);
    assertEquals(2, dice.getDice().size());
  }

  /**
   * Tests the rolling of the dice and checks for valid values
   * and correct total values.
   *
   * <p> Expected outcome: Roll values are within 2 to 12 and
   * calculated total values are correct.</p>
   */
  @Test
  public void testRoll() {
    for (int i = 0; i < 100; i++) {
      int rolledValue = dice.roll();
      int die1 = dice.getDie(0);
      int die2 = dice.getDie(1);
      assertEquals(rolledValue, die1 + die2);
      assertTrue(rolledValue >= 2 && rolledValue <= 12);
    }
  }

  // -------------------- Negative tests --------------------
  /**
   * Tests the instantiation of a Dice object with an invalid number of dice.
   *
   * <p>Expected outcome: IllegalArgumentException is thrown.</p>
   */
  @Test
  public void testDiceInstantiationWithInvalidNumberOfDice() {
    assertThrows(IllegalArgumentException.class, () -> new Dice(0));
    assertThrows(IllegalArgumentException.class, () -> new Dice(-1));
  }

  /**
   * Tests getting the value of a die with an invalid die number.
   *
   * <p> Expected outcome: IllegalArgumentException is thrown if the die number
   * is less than 0 or greater than the number of dice.</p>
   */
  @Test
  public void testGetDieWithInvalidDieNumber() {
    assertThrows(IllegalArgumentException.class, () -> dice.getDie(-1));
    assertThrows(IllegalArgumentException.class, () -> dice.getDie(2));
  }
}

