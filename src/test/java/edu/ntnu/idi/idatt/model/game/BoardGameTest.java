package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.entities.Dice;
import edu.ntnu.idi.idatt.model.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * JUnit 5 test class for {@link BoardGame}.
 * Tests cover game initialisation, get player algorithm and dice eyes sum.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class BoardGameTest {
    private Board board;
    private List<Player> players;
    private Dice dice;
    private BoardGame game;

    @BeforeEach
    public void setUp() {
        board = new Board();
        board.initializeBoard(90);
        dice = new Dice(2);
        players = Arrays.asList(new Player("A"), new Player("B"), new Player("C"));
        game = new BoardGame(board, players, dice) {
            @Override
            protected void playTurn(Player player) {
            }

            @Override
            protected boolean isGameOver() {
                return false;
            }
        };
    }

    /*
    @Test
    public void testSetUpGame() {
        game.setUpGame();

        for (Player player : players) {
            assertEquals(board.getTile(0), player.getCurrentTile(), "Player should be placed on the start tile.");
        }
    }
     */

    /**
     * Test that getNextPlayer() correctly cycles through players.
     */
    @Test
    public void testGetNextPlayer() {
        assertEquals(players.get(1), game.getNextPlayer());
        assertEquals(players.get(2), game.getNextPlayer());
        assertEquals(players.get(0), game.getNextPlayer());
    }

    /**
     * Test that rollDice() returns a sum between 2 and 12 (2 six-sided dice).
     */
    @Test
    public void testRollDice() {
        for (int i = 0; i < 50; i++) {
            int roll = game.rollDice();
            assertTrue(roll >= 2 && roll <= 12, "Roll should be between 2 and 12 for two dice");
        }
    }
}
