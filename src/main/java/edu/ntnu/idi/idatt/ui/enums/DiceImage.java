package edu.ntnu.idi.idatt.ui.enums;

/**
 * Represents the images of the dice used in the game.
 * Each die has a value and an associated image path.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public enum DiceImage {
  ONE(1, "/images/die1.png"),
  TWO(2, "/images/die2.png"),
  THREE(3, "/images/die3.png"),
  FOUR(4, "/images/die4.png"),
  FIVE(5, "/images/die5.png"),
  SIX(6, "/images/die6.png");

  private final int value;
  private final String imagePath;

  /**
   * Constructs a DiceImage instance.
   *
   * @param value the value of the die.
   * @param imagePath the path to the image of the die.
   */
  DiceImage(int value, String imagePath) {
    this.value = value;
    this.imagePath = imagePath;
  }

  /**
   * Retrieves the image path of the die.
   *
   * @return the image path as a string.
   */
  public String getImagePath() {
    return imagePath;
  }

  /**
   * Retrieves the DiceImage instance based on the value.
   *
   * @return the DiceImage instance.
   */
  public static DiceImage getDiceImage(int value) {
    for (DiceImage diceImage : DiceImage.values()) {
      if (diceImage.value == value) {
        return diceImage;
      }
    }
    return null;
  }
}
