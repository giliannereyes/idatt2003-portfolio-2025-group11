package edu.ntnu.idi.idatt.service.snakesandladders;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.EventHandler;
import edu.ntnu.idi.idatt.domain.event.GameEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link LaddersGameService}, verifying its behavior under various game configurations and actions.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class LaddersGameServiceTest {
    private GameConfig validConfig;
    private EventBus dummyEventBus;

    /**
     * Sets up a minimal valid game configuration and a dummy event bus before each test.
     */
    @BeforeEach
    public void setUp() {
        // Minimal valid setup
        Board board = new Board(5, 5);
        board.addTile(new Tile(1, 0, 0));

        PlayerConfig playerConfig = new PlayerConfig(new Player("P1"), "image1.png");
        validConfig = new GameConfig() {
            @Override
            public boolean isComplete() {
                return true;
            }
        };
        validConfig.setBoard(board);
        validConfig.setPlayerConfigs(List.of(playerConfig));

        dummyEventBus = new DummyEventBus();
    }

    //------------ Positive test ------------

    /**
     * Verifies that the game starts successfully with a valid configuration.
     */
    @Test
    void testStartGameWithValidConfig() {
        LaddersGameService service = new LaddersGameService(validConfig, dummyEventBus);
        assertDoesNotThrow(service::startGame);
    }

    /**
     * Verifies that a player can take a turn after the game has started.
     */
    @Test
    void testPlayTurnAfterStart() {
        LaddersGameService service = new LaddersGameService(validConfig, dummyEventBus);
        service.startGame();
        assertDoesNotThrow(service::onDiceClicked);
    }

    //------------ Negative test ------------

    /**
     * Verifies that starting the game with an incomplete configuration throws an exception.
     */
    @Test
    void testStartGameWithIncompleteConfigThrows() {
        GameConfig incomplete = new GameConfig() {
            @Override
            public boolean isComplete() {
                return false;
            }
        };
        incomplete.setBoard(new Board(3, 3));
        incomplete.setPlayerConfigs(List.of(new PlayerConfig(new Player("Bob"), "bob.png")));

        LaddersGameService service = new LaddersGameService(incomplete, dummyEventBus);
        assertThrows(IllegalStateException.class, service::startGame);
    }

    /**
     * Verifies that attempting to play a turn before the game has started throws a NullPointerException.
     */
    @Test
    void testPlayTurnBeforeStartThrows() {
        LaddersGameService service = new LaddersGameService(validConfig, dummyEventBus);
        assertThrows(NullPointerException.class, service::onDiceClicked);  // game is null
    }

    /**
     * Verifies that constructing the service with a null configuration throws a NullPointerException.
     */
    @Test
    void testConstructorWithNullConfigThrows() {
        assertThrows(NullPointerException.class, () -> new LaddersGameService(null, new DummyEventBus()));
    }

    /**
     * Verifies that constructing the service with a null event bus throws a NullPointerException
     */
    @Test
    void testConstructorWithNullEventBusThrows() {
        assertThrows(NullPointerException.class, () -> new LaddersGameService(validConfig, null));
    }

    //------------ Edge cases ------------

    /**
     * Verifies that the game can start with a minimal board configuration (1x1 board with one tile).
     */
    @Test
    void testStartGameWithMinimalBoard() {
        GameConfig minimal = new GameConfig() {
            @Override
            public boolean isComplete() {
                return true;
            }
        };

        Board board = new Board(1, 1);
        board.addTile(new Tile(1, 0, 0));
        Player player = new Player("Tiny");
        minimal.setBoard(board);
        minimal.setPlayerConfigs(List.of(new PlayerConfig(player, "tiny.png")));

        LaddersGameService service = new LaddersGameService(minimal, dummyEventBus);
        assertDoesNotThrow(service::startGame);
    }

    /**
     * Verifies that the game can start even if no players are configured.
     */
    @Test
    void testStartGameWithNoPlayers() {
        GameConfig config = new GameConfig() {
            @Override public boolean isComplete() { return true; }
        };
        config.setBoard(new Board(1, 1));
        config.setPlayerConfigs(List.of()); // no players

        LaddersGameService service = new LaddersGameService(config, dummyEventBus);
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
