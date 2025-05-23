package edu.ntnu.idi.idatt.ui.enums;

import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Represents the tokens that players can use in the game.
 * Each token has a name and an image path associated with it.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public enum PlayerToken {
  BLUE("Blue", "/images/playerTokens/blue_token.png"),
  GREEN("Green", "/images/playerTokens/green_token.png"),
  ORANGE("Orange", "/images/playerTokens/orange_token.png"),
  PURPLE("Purple", "/images/playerTokens/purple_token.png"),
  RED("Red", "/images/playerTokens/red_token.png");

  private final String name;
  private final String imagePath;

  /**
   * Constructs a PlayerToken instance.
   *
   * @param name is the name of the token.
   * @param imagePath is the path to the image of the token.
   */
  PlayerToken(String name, String imagePath) {
    Validation.validateNonNull(name, "Token name");
    Validation.validateNonEmptyStr(imagePath, "Image path");
    this.name = name;
    this.imagePath = imagePath;
  }

  /**
   * Retrieves the image path of the token.
   *
   * @return a string representing the image path.
   */
  public String getImagePath() {
    return imagePath;
  }

  /**
   * Retrieves the name of the token.
   *
   * @return a string representing the name of the token.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the PlayerToken instance based on the name.
   *
   * @param name is the name of the token.
   *
   * @return the PlayerToken instance.
   */
  public static PlayerToken fromName(String name) {
    for (PlayerToken token : values()) {
      if (token.name.equalsIgnoreCase(name)) {
        return token;
      }
    }
    return null;
  }
}
