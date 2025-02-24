package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.entities.*;
import edu.ntnu.idi.idatt.model.events.DiceRolledEvent;
import edu.ntnu.idi.idatt.model.events.GameLogger;
import edu.ntnu.idi.idatt.model.events.PlayerMovedEvent;
import edu.ntnu.idi.idatt.model.events.TileActionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Simple text-based UI prototype for Snakes and Ladders.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class GameUI {
    private SnakesAndLadders game;
    private final Scanner scanner;
    private GameLogger logger;

    public GameUI() {
        scanner = new Scanner(System.in);
        setUpGame();
    }

    /**
     * Initialises the board, players, and game.
     */
    private void setUpGame() {
        System.out.println("Welcome to Snakes and Ladders!");

        Board board = new Board();
        board.initializeBoard(90);

        Dice dice = new Dice(2);

        List<Player> players = new ArrayList<>();
        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }

        game = new SnakesAndLadders(board, players, dice);
        game.setUpGame();

        logger = new GameLogger();
        game.addEventListener(DiceRolledEvent.class, logger);
        game.addEventListener(PlayerMovedEvent.class, logger);
        game.addEventListener(TileActionEvent.class, logger);
    }

    /**
     * Runs the game loop.
     */
    public void startGame() {
        System.out.println("Game is starting...");
        game.playGame();

        game.removeEventListener(DiceRolledEvent.class, logger);
        game.removeEventListener(PlayerMovedEvent.class, logger);
        game.removeEventListener(TileActionEvent.class, logger);

        if (game.winner != null) {
            System.out.println("\n🎉 " + game.winner.getName() + " wins the game! 🎉");
        }
    }

    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        GameUI ui = new GameUI();
        ui.startGame();
    }
}
