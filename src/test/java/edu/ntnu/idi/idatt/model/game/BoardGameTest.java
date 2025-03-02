package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.entities.Dice;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.events.bus.DefaultEventBus;
import edu.ntnu.idi.idatt.model.events.bus.EventBus;
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
 * @author Gilianne Reyes
 * @version 0.2
 * @since 0.1
 */
public class BoardGameTest {
    private Board board;
    private List<Player> players;
    private Dice dice;
    private BoardGame game;
    private EventBus eventBus;

    /**
     * Set up a new game with a board, players and dice before each test.
     */
    @BeforeEach
    public void setUp() {
        board = new Board();
        board.initializeBoard(90);
        dice = new Dice(2);
        players = Arrays.asList(new Player("A"), new Player("B"), new Player("C"));
        eventBus = new DefaultEventBus();
        game = new BoardGame(board, players, dice, eventBus) {
            @Override
            protected void playTurn(Player player) {
            }

            @Override
            protected boolean isGameOver() {
                return false;
            }
        };
    }

    // ---------- Positive tests ----------

    /**
     * Test that the game sets up the players on the starting position correctly.
     *
     * <p>Expected outcome: All players are placed on the start tile.</p>
     */
    @Test
    public void testSetUpGame() {
        game.setUpGame();
        for (Player player : players) {
            assertEquals(board.getStartTile(), player.getCurrentTile(), "Player should be placed on the start tile.");
        }
    }

    /**
     * Test that the game correctly cycles through players.
     *
     * <p>Expected outcome: The next player is returned in the correct order.</p>
     */
    @Test
    public void testGetNextPlayer() {
        Player first = game.getNextPlayer();
        assertEquals(players.get(1), first, "First call should return second player.");
        Player second = game.getNextPlayer();
        assertEquals(players.get(2), second, "Second call should return third player.");
        Player third = game.getNextPlayer();
        assertEquals(players.get(0), third, "Third call should wrap around to first player.");
        assertEquals(first, game.getNextPlayer(), "Cycle should repeat after full rotation.");
    }

    /**
     * Tests that the dice rolls a number between 2 and 12
     * for two six-sided dice.
     *
     * <p>Expected outcome: The roll is between 2 and 12.</p>
     */
    @Test
    public void testRollDice() {
        for (int i = 0; i < 50; i++) {
            int roll = game.rollDice();
            assertTrue(roll >= 2 && roll <= 12, "Roll should be between 2 and 12 for two dice");
        }
    }

    /**
     * Test that the game returns the event bus correctly.
     *
     * <p>Expected outcome: The event bus is the same as the one used in the constructor.</p>
     */
    @Test
    public void testGetEventBus() {
        assertEquals(eventBus, game.getEventBus(), "Event bus should be the same as the one used to create the game.");
    }

    // ---------- Negative tests ----------

    /**
     * Test that the constructor throws an IllegalArgumentException when the board is null.
     *
     * <p>Expected outcome: IllegalArgumentException is thrown.</p>
     */
    @Test
    public void testConstructorWithNullBoard() {
        assertThrows(IllegalArgumentException.class, () -> new BoardGame(null, players, dice, eventBus) {
            @Override
            protected void playTurn(Player player) {
            }

            @Override
            protected boolean isGameOver() {
                return false;
            }
        });
    }

    /**
     * Test that the constructor throws an IllegalArgumentException when the players list is null.
     *
     * <p>Expected outcome: IllegalArgumentException is thrown.</p>
     */
    @Test
    public void testConstructorWithNullPlayers() {
        assertThrows(IllegalArgumentException.class, () -> new BoardGame(board, null, dice, eventBus) {
            @Override
            protected void playTurn(Player player) {
            }

            @Override
            protected boolean isGameOver() {
                return false;
            }
        });
    }

    /**
     * Test that the constructor throws an IllegalArgumentException when the dice is null.
     *
     * <p>Expected outcome: IllegalArgumentException is thrown.</p>
     */
    @Test
    public void testConstructorWithNullDice() {
        assertThrows(IllegalArgumentException.class, () -> new BoardGame(board, players, null, eventBus) {
            @Override
            protected void playTurn(Player player) {
            }

            @Override
            protected boolean isGameOver() {
                return false;
            }
        });
    }

    /**
     * Test that the constructor throws an IllegalArgumentException when the event bus is null.
     *
     * <p>Expected outcome: IllegalArgumentException is thrown.</p>
     */
    @Test
    public void testConstructorWithNullEventBus() {
        assertThrows(IllegalArgumentException.class, () -> new BoardGame(board, players, dice, null) {
            @Override
            protected void playTurn(Player player) {
            }

            @Override
            protected boolean isGameOver() {
                return false;
            }
        });
    }
}
