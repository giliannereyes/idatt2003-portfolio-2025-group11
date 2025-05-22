package edu.ntnu.idi.idatt.service.monopoly;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.EventHandler;
import edu.ntnu.idi.idatt.domain.event.GameEvent;
import edu.ntnu.idi.idatt.utils.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MonopolyGameService}, verifying its behavior under various game configurations and actions.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class MonopolyGameServiceTest {
    private GameConfig validConfig;
    private EventBus dummyEventBus;

    /**
     * Sets up a minimal valid game configuration and a dummy event bus before each test.
     */
    @BeforeEach
    public void setUp() {
        // Minimal valid setup
        Board board = createValidBoard();

        PlayerConfig playerConfig = new PlayerConfig(new Player("P1"), "image1.png");
        Validation.validateNonEmptyStr(playerConfig.getPlayer().getName(), "Player name");
        Validation.validateNonEmptyStr(playerConfig.getTokenImagePath(), "Player token path");
        validConfig = new GameConfig() {
            @Override
            public boolean isComplete() {
                return true;
            }
        };
        validConfig.setBoard(createValidBoard());
        validConfig.setPlayerConfigs(List.of(playerConfig));

        dummyEventBus = new DummyEventBus();
    }

    /**
     * Creates a minimal valid board with one tile.
     */
    private Board createValidBoard() {
        Board board = new Board(11, 11);
        Validation.validatePositiveNum(board.getRows(), "Board rows");
        Validation.validatePositiveNum(board.getColumns(), "Board columns");

        Tile tile = new Tile(1, 0, 0);
        Validation.validatePositiveNum(tile.getTileId(), "Tile ID");
        Validation.validateNonNegativeNum(tile.getX(), "Tile X position");
        Validation.validateNonNegativeNum(tile.getY(), "Tile Y position");
        board.addTile(tile);

        return board;
    }

    //------------ Positive test ------------

    /**
     * Verifies that the game starts successfully with a valid configuration.
     */
    @Test
    public void testStartGameWithValidConfig() {
        MonopolyGameService service = new MonopolyGameService(validConfig, dummyEventBus);
        assertDoesNotThrow(service::startGame);
    }

    /**
     * Verifies that a player can take a turn after the game has started.
     */
    @Test
    public void testPlayTurnAfterStart() {
        MonopolyGameService service = new MonopolyGameService(validConfig, dummyEventBus);
        service.startGame();
        assertDoesNotThrow(service::onDiceClicked);
    }

    /**
     * Verifies that a player can buy a property after the game has started.
     */
    @Test
    public void testBuyPropertyAfterStart() {
        MonopolyGameService service = new MonopolyGameService(validConfig, dummyEventBus);
        service.startGame();

        Property property = new Property("Park Place", 150, 50);
        Validation.validateNonEmptyStr(property.getName(), "Property name");
        Validation.validatePositiveNum(property.getCost(), "Property cost");
        Validation.validatePositiveNum(property.getRent(), "Property rent");
    }

    //------------ Negative test ------------

    /**
     * Verifies that starting the game with an incomplete configuration throws an exception.
     */
    @Test
    public void testStartGameWithIncompleteConfigThrows() {
        GameConfig incompleteConfig = new GameConfig() {
            @Override
            public boolean isComplete() {
                return false; // explicitly incomplete
            }
        };

        MonopolyGameService service = new MonopolyGameService(incompleteConfig, dummyEventBus);
        assertThrows(IllegalStateException.class, service::startGame);
    }

    /**
     * Verifies that attempting to play a turn before the game has started throws an exception.
     */
    @Test
    public void testPlayTurnBeforeStartThrows() {
        MonopolyGameService service = new MonopolyGameService(validConfig, dummyEventBus);
        assertThrows(IllegalStateException.class, service::onDiceClicked);
    }

    /**
     * Verifies that attempting to buy a property with a null player throws an exception.
     */
    @Test
    public void testBuyPropertyWithNullPlayer() {
        MonopolyGameService service = new MonopolyGameService(validConfig, dummyEventBus);
        service.startGame();
        Property property = new Property("Boardwalk", 400, 50);
        assertThrows(IllegalArgumentException.class, () -> service.buyProperty(null, property));
    }

    /**
     * Verifies that attempting to buy a property with a null property throws an exception.
     */
    @Test
    public void testBuyPropertyWithNullProperty() {
        MonopolyGameService service = new MonopolyGameService(validConfig, dummyEventBus);
        service.startGame();
        Player player = validConfig.getPlayerConfigs().get(0).getPlayer();
        Validation.validateNonNull(player, "Player");
        Validation.validateNonEmptyStr(player.getName(), "Player name");
        assertThrows(IllegalArgumentException.class, () -> service.buyProperty(player, null));
    }

    //------------ Edge cases ------------

    /**
     * Verifies that the game can start with a minimal board configuration (1x1 board with one tile).
     */
    @Test
    public void testStartGameWithMinimalBoard() {
        GameConfig tinyConfig = new GameConfig() {
            @Override
            public boolean isComplete() {
                return true;
            }
        };
        tinyConfig.setBoard(new Board(1, 1));
        tinyConfig.setPlayerConfigs(List.of(
                new PlayerConfig(new Player("Solo"), "solo.png")
        ));

        Board tinyBoard = new Board(1, 1);
        tinyBoard.addTile(new Tile(1, 0, 0));
        tinyConfig.setBoard(tinyBoard);

        MonopolyGameService service = new MonopolyGameService(tinyConfig, dummyEventBus);
        assertDoesNotThrow(service::startGame);
    }

    //------------ Dummy ------------

    /**
     * Dummy implementation of {@link EventBus} used for testing without triggering real events.
     */
    private static class DummyEventBus implements EventBus {
        @Override
        public <E extends GameEvent> void register(Class<E> eventType, EventHandler<E> handler) {}
        @Override
        public <E extends GameEvent> void unregister(Class<E> eventType, EventHandler<E> handler) {}
        @Override
        public void publish(GameEvent event) {}
    }
}
