package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.model.entities.Die;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Die class.
 * <p>
 *  Tests the following:
 *  <ul>
 *    <li> Instantiation of a Die object. </li>
 *    <li> Rolling the die and getting the correct value. </li>
 *    <li> Getting the correct value of the last rolled die. </li>
 *    <li> Preventing getting the value of the last rolled die without rolling the die. </li>
 *  </ul>
 * </p>
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public class DieTest {
  Die die;

  /**
   * Instantiates a Die object before each test.
   */
  @BeforeEach
  public void setUp() {
    die = new Die();
  }

  // -------------------- Positive tests --------------------
  /**
   * Tests the instantiation of a Die object.
   *
   * <p>Expected outcome: Die object is created. </p>
   */
  @Test
  public void testDieInstantiation() {
    assertNotNull(die);
    assertInstanceOf(Die.class, die);
  }

  /**
   * Tests the rolling of a die and checks for valid values.
   *
   * <p>Expected outcome: Roll values are within 1 to 6.</p>
   */
  @Test
  public void testRoll() {
    for (int i = 0; i < 100; i++) {
      int rolledValue = die.roll();
      assertTrue(rolledValue >= 1 && rolledValue <= 6);
    }
  }

  /**
   * Tests the getting of the correct value of the last rolled die.
   *
   * <p>Expected outcome: The value of the last rolled die is returned.</p>
   */
  @Test
  public void testGetValue() {
    int rolledValue1 = die.roll();
    assertEquals(rolledValue1, die.getValue());
    int rolledValue2 = die.roll();
    assertEquals(rolledValue2, die.getValue());
  }

  // -------------------- Negative tests --------------------
  /**
   * Tests getting the value of the last rolled die without rolling the die.
   *
   * <p>Expected outcome: IllegalStateException is thrown.</p>
   */
  @Test
  public void testGetValueWithoutRoll() {
    assertThrows(IllegalStateException.class, () -> die.getValue());
  }
}
