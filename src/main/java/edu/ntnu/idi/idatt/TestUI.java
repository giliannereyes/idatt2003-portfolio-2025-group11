package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.io.BoardFileReader;
import edu.ntnu.idi.idatt.io.BoardFileReaderGson;
import edu.ntnu.idi.idatt.io.BoardFileWriter;
import edu.ntnu.idi.idatt.io.BoardFileWriterGson;
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
import edu.ntnu.idi.idatt.model.factory.*;
import edu.ntnu.idi.idatt.model.game.SnakesAndLadders;

import java.nio.file.Path;
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

        // Create event bus and register handlers
        DefaultEventBus eventBus = new DefaultEventBus();
        eventBus.register(PlayerMovedEvent.class, new PlayerMovedHandler());
        eventBus.register(TileActionEvent.class, new TileActionHandler());
        eventBus.register(DiceRolledEvent.class, new DiceRolledHandler());

        // Add factories to registry
        TileActionFactoryRegistry registry = new TileActionFactoryRegistry();
        registry.registerDestinationFactory(new LadderActionFactory());
        registry.registerNoDestinationFactory(new SkipTurnActionFactory());
        registry.registerNoDestinationFactory(new ResetActionFactory());
        registry.registerDestinationFactory(new SnakeActionFactory());

        // Create players
        List<Player> players = new ArrayList<>();
        System.out.println("Enter number of players:");
        int playerCount = scanner.nextInt();
        scanner.nextLine();
        for (int i = 1; i <= playerCount; i++) {
            System.out.println("Enter name for Player " + i + ":");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }

        Dice dice = new Dice(1);

        BoardFileWriter writer = new BoardFileWriterGson();
        BoardFileReader reader = new BoardFileReaderGson(registry);

        // Path to the file
        // Path path = Path.of("src/main/resources/testboard.json");

        BoardGameFactory boardFactory = new BoardGameFactory(reader, writer, registry);
        // Board easyBoard = boardFactory.loadEasyBoard();
        // Board mediumBoard = boardFactory.loadMediumBoard();
        Board hardBoard = boardFactory.loadHardBoard();
        game = new SnakesAndLadders(hardBoard, players, dice, eventBus);
    }
}
