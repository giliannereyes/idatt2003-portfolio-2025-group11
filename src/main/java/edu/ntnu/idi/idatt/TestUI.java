package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.model.actions.LadderAction;
import edu.ntnu.idi.idatt.model.actions.ResetAction;
import edu.ntnu.idi.idatt.model.actions.SkipTurnAction;
import edu.ntnu.idi.idatt.model.actions.SnakeAction;
import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.entities.Dice;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.events.bus.DefaultEventBus;
import edu.ntnu.idi.idatt.model.events.handlers.DiceRolledHandler;
import edu.ntnu.idi.idatt.model.events.handlers.PlayerMovedHandler;
import edu.ntnu.idi.idatt.model.events.handlers.TileActionHandler;
import edu.ntnu.idi.idatt.model.events.types.DiceRolledEvent;
import edu.ntnu.idi.idatt.model.events.types.PlayerMovedEvent;
import edu.ntnu.idi.idatt.model.events.types.TileActionEvent;
import edu.ntnu.idi.idatt.model.game.SnakesAndLadders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class for testing the Snakes and Ladders game.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class TestUI {
    private static SnakesAndLadders game;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        setupGame();
        game.setUpGame();
        game.playGame();
        System.out.println("Game Over! Winner: " + game.getWinner().getName());
    }

    private static void setupGame() {
        System.out.println("Setting up Snakes and Ladders...");
        Board board = new Board();
        Dice dice = new Dice(1);
        List<Player> players = new ArrayList<>();

        // Create event bus and register handlers
        DefaultEventBus eventBus = new DefaultEventBus();
        eventBus.register(PlayerMovedEvent.class, new PlayerMovedHandler());
        eventBus.register(TileActionEvent.class, new TileActionHandler());
        eventBus.register(DiceRolledEvent.class, new DiceRolledHandler());

        // Create players
        System.out.println("Enter number of players:");
        int playerCount = scanner.nextInt();
        scanner.nextLine();
        for (int i = 1; i <= playerCount; i++) {
            System.out.println("Enter name for Player " + i + ":");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }

        // Ladders
        board.initializeBoard(50);
        board.addTileAction(3, new LadderAction(board.getTile(8)));
        board.addTileAction(15, new LadderAction(board.getTile(25)));
        // Snakes
        board.addTileAction(5, new SnakeAction(board.getTile(2)));
        board.addTileAction(17, new SnakeAction(board.getTile(13)));
        // Skip turns
        board.addTileAction(10, new SkipTurnAction());
        board.addTileAction(20, new SkipTurnAction());
        // Reset actions
        board.addTileAction(30, new ResetAction());
        board.addTileAction(40, new ResetAction());

        // Initialize game
        game = new SnakesAndLadders(board, players, dice, eventBus);
    }
}
