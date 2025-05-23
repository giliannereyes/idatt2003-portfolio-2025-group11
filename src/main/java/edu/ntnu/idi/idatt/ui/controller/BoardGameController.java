package edu.ntnu.idi.idatt.ui.controller;


/**
 * Interface for controllers managing board game logic and interactions.
 * Provides methods for initializing the game and handling dice click events.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface BoardGameController {

  /**
   * Initializes the board game. This method should set up the game state,
   * prepare the board, and perform any necessary setup before gameplay begins.
   */
  void initialize();

  /**
   * Handles the event when the dice is clicked.
   * This method should trigger the dice roll and any subsequent game logic.
   */
  void onDiceClicked();
}