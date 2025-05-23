package edu.ntnu.idi.idatt.service.monopoly;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.config.GameConfig;
import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Dice;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.entity.monopoly.PropertyRegistry;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.EventHandler;
import edu.ntnu.idi.idatt.domain.event.GameEvent;
import edu.ntnu.idi.idatt.domain.factory.monopoly.MonopolyBoardFactory;
import edu.ntnu.idi.idatt.domain.game.monopoly.Monopoly;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MonopolyGameService}.
 *
 * @version 0.1
 * @author Trang Duong
 */
class MonopolyGameServiceTest {
  private MonopolyGameService service;
  private GameConfig config;
  private final EventBus eventBus = new DummyEventBus();
  private final MonopolyBoardFactory boardFactory = new StubBoardFactory();

  /**
   * Sets up a minimal game configuration for testing.
   *
   * <p>Creates a 1x1 board with a single tile and a player configuration.</p>
   */
  @BeforeEach
  void setUp() {
    config = new GameConfig() {
      @Override
      public boolean isComplete() {
        return true;
      }
    };
    Board board = new Board(1, 1);
    board.addTile(new Tile(0, 0, 0));
    config.setBoard(board);
    config.setPlayerConfigs(List.of(new PlayerConfig(new Player("P1"), "p1.png")));
    service = new MonopolyGameService(config, eventBus, boardFactory);
  }

  /**
   * Validates null-guard clauses in the constructor.
   *
   * <p>Expected: an {@link IllegalArgumentException} is thrown for each null argument.</p>
   */
  @Test
  void testConstructorWithNullArguments() {
    assertAll(
          () -> assertThrows(
                IllegalArgumentException.class,
                () -> new MonopolyGameService(null, eventBus, boardFactory)),
          () -> assertThrows(
                IllegalArgumentException.class,
                () -> new MonopolyGameService(config, null, boardFactory)),
          () -> assertThrows(
                IllegalArgumentException.class,
                () -> new MonopolyGameService(config, eventBus, null))
    );
  }

  /**
   * Starts the game with a configuration whose {@code isComplete()} returns false.
   *
   * <p>Expected: {@link IllegalStateException} is thrown from {@code startGame()}.</p>
   */
  @Test
  void testStartGameWithIncompleteConfig() {
    GameConfig bad = new GameConfig() {
      @Override
      public boolean isComplete() {
        return false;
      }
    };
    MonopolyGameService svc = new MonopolyGameService(bad, eventBus, boardFactory);
    assertThrows(IllegalStateException.class, svc::startGame);
  }

  @Nested
  class OnDiceClicked {
    /**
     * Invokes {@code onDiceClicked()} before {@code startGame()}.
     *
     * <p>Expected: {@link IllegalStateException} is thrown.</p>
     */
    @Test
    void beforeStart_throws() {
      assertThrows(IllegalStateException.class, service::onDiceClicked);
    }

    /**
     * Injects a recording Monopoly and verifies the call is forwarded.
     *
     * <p>Expected: {@code playNextTurn()} is invoked exactly once and no exception is thrown.</p>
     */
    @Test
    void delegatesToGame() throws Exception {
      RecordingMonopoly recorder = injectRecordingGame();
      assertDoesNotThrow(service::onDiceClicked);
      assertTrue(recorder.nextTurnCalled);
    }
  }

  /**
   * Calls {@code buyProperty()} after injecting a recording Monopoly.
   *
   * <p>Expected: the same player and property objects reach
   * {@link Monopoly#buyProperty(Player, Property)}.</p>
   */
  @Test
  void buyProperty_delegates() throws Exception {
    RecordingMonopoly recorder = injectRecordingGame();
    Player player = new Player("Alice");
    Property property = new Property("Boardwalk", 400, 50);
    service.buyProperty(player, property);
    assertSame(player, recorder.recordedBuyPlayer);
    assertSame(property, recorder.recordedBuyProperty);
  }

  /**
   * Retrieves the property registry when an instance has been set via reflection.
   *
   * <p>Expected: {@code getPropertyRegistry()} returns the injected instance.</p>
   */
  @Test
  void getPropertyRegistry_returnsInjectedInstance() throws Exception {
    PropertyRegistry registry = new PropertyRegistry();
    injectRegistry(registry);
    assertSame(registry, service.getPropertyRegistry());
  }

  // ------------------ Helper Methods And Classes ------------------
  private RecordingMonopoly injectRecordingGame() throws Exception {
    RecordingMonopoly recorder = new RecordingMonopoly();
    Field field = MonopolyGameService.class.getDeclaredField("game");
    field.setAccessible(true);
    field.set(service, recorder);
    return recorder;
  }

  private void injectRegistry(PropertyRegistry registry) throws Exception {
    Field field = MonopolyGameService.class.getDeclaredField("propertyRegistry");
    field.setAccessible(true);
    field.set(service, registry);
  }

  private static final class DummyEventBus implements EventBus {
    @Override
    public <E extends GameEvent> void register(Class<E> type, EventHandler<E> handler) {}

    @Override
    public <E extends GameEvent> void unregister(Class<E> type, EventHandler<E> handler) {}

    @Override
    public void publish(GameEvent event) {}
  }

  private static final class StubBoardFactory extends MonopolyBoardFactory {
    @Override
    public PropertyRegistry createPropertyRegistryForBoard(Board board) {
      return new PropertyRegistry();
    }
  }

  private static final class RecordingMonopoly extends Monopoly {
    boolean nextTurnCalled;
    Player recordedBuyPlayer;
    Property recordedBuyProperty;

    RecordingMonopoly() {
      super(createMinimalBoard(), List.of(new Player("stub")), new Dice(2),
            new DummyEventBus(), new PropertyRegistry());
    }

    @Override
    public void playNextTurn() {
      nextTurnCalled = true;
    }

    @Override
    public void buyProperty(Player player, Property property) {
      recordedBuyPlayer = player;
      recordedBuyProperty = property;
    }

    private static Board createMinimalBoard() {
      Board board = new Board(1, 1);
      board.addTile(new Tile(0, 0, 0));
      return board;
    }
  }
}