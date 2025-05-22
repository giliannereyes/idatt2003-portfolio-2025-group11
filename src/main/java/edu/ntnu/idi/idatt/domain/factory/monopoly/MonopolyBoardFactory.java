package edu.ntnu.idi.idatt.domain.factory.monopoly;

import edu.ntnu.idi.idatt.domain.action.monopoly.GoToJailAction;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.entity.monopoly.PropertyRegistry;
import edu.ntnu.idi.idatt.domain.strategy.monopoly.MonopolyLayoutStrategy;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Factory for creating and configuring Monopoly {@link Board} instances.
 *
 * <p>Supports two predefined layouts ("Small board" and "Large board"), each with its own
 * dimension, tile actions (e.g. Go-to-Jail), and a property registry mapping.</p>
 *
 * <p>Clients can call {@link #getAllPredefinedBoards()} to retrieve ready-to-use boards,
 * and then pass them to {@link #createPropertyRegistryForBoard(Board)} for property setup.</p>
 *
 * @version 0.1
 * @author Gilianne Reyes
 * @see Board
 * @see MonopolyLayoutStrategy
 * @see PropertyRegistry
 */
public class MonopolyBoardFactory {
  private final MonopolyLayoutStrategy layoutStrategy;
  private final List<Supplier<Board>> predefinedBoards;
  private final Map<String, Map<Integer, Property>> boardPropertyMaps;
  public static final String SMALL_BOARD = "Small board";
  public static final String LARGE_BOARD = "Large board";

  /**
   * Constructs a new MonopolyBoardFactory, initializing layout strategy,
   * predefined boards, and property mappings.
   */
  public MonopolyBoardFactory() {
    this.layoutStrategy = new MonopolyLayoutStrategy();
    this.predefinedBoards = List.of(
          this::loadSmallBoard,
          this::loadLargeBoard
    );
    this.boardPropertyMaps = new LinkedHashMap<>();
    initializePropertyMaps();
  }

  /**
   * Retrieves all predefined boards keyed by their names.
   *
   * @return an unmodifiable Map of board name to Board instance.
   */
  public Map<String, Board> getAllPredefinedBoards() {
    return predefinedBoards.stream()
        .map(Supplier::get)
        .collect(Collectors.toMap(
              Board::getName, board -> board
        ));
  }

  /**
   * Creates a {@link PropertyRegistry} for the given board.
   * This associates properties with the board's tiles based on the board name.
   *
   * @param board is the board to create a property registry for.
   *
   * @return a {@link PropertyRegistry} configured for the given board.
   *
   * @throws IllegalArgumentException if the board name is not recognized.
   */
  public PropertyRegistry createPropertyRegistryForBoard(Board board) {
    Validation.validateNonNull(board, "Board");
    PropertyRegistry registry = new PropertyRegistry();
    Map<Integer, Property> propertyMap = boardPropertyMaps.get(board.getName());
    if (propertyMap != null) {
      propertyMap.forEach(registry::registerProperty);
    } else {
      throw new IllegalArgumentException("Invalid Monopoly board: " + board.getName()
            + ". Unable to create property registry."
      );
    }
    return registry;
  }

  /**
   * Loads the "Small board" layout (7×7 grid, 24-space perimeter) and
   * configures GoToJail actions on tiles 7 and 19.
   * <br>
   * <b>Note:</b> Properties must be configured separately
   * using {@link #createPropertyRegistryForBoard(Board)}.
   *
   * @return a configured Board named "Small board" with its tile actions set.
   */
  public Board loadSmallBoard() {
    Board board = new Board(7, 7);
    board.setName(SMALL_BOARD);
    board.setDescription("24-space perimeter. Properties have rent 10% of the property value.");
    layoutStrategy.buildLayout(board);
    board.addTileAction(7, new GoToJailAction(board.getTile(7)));
    board.addTileAction(19, new GoToJailAction(board.getTile(7)));
    return board;
  }

  /**
   * Loads the "Large board" layout (10×10 grid, 36-space perimeter) and
   * configures GoToJail actions on tiles 10 and 28.
   * <br>
   * <b>Note:</b> Properties must be configured separately
   * using {@link #createPropertyRegistryForBoard(Board)}.
   *
   * @return a configured Board named "Large board" with its tile actions set.
   */
  public Board loadLargeBoard() {
    Board board = new Board(10, 10);
    board.setName(LARGE_BOARD);
    board.setDescription("36-space perimeter. Properties have rent 10% of the property value.");
    layoutStrategy.buildLayout(board);
    board.addTileAction(10, new GoToJailAction(board.getTile(10)));
    board.addTileAction(28, new GoToJailAction(board.getTile(10)));
    return board;
  }

  /**
   * Initializes the boardPropertyMaps by mapping board names to their
   * respective property maps.
   */
  private void initializePropertyMaps() {
    boardPropertyMaps.put(SMALL_BOARD, getSmallBoardPropertyMap());
    boardPropertyMaps.put(LARGE_BOARD, getLargeBoardPropertyMap());
  }

  /**
   * Creates a map of {@link Property}  for the small Monopoly board.
   *
   * @return a map of tile IDs to Property instances for the small board.
   */
  private Map<Integer, Property> getSmallBoardPropertyMap() {
    Map<Integer, Property> props = new LinkedHashMap<>();
    props.put(2,  new Property("Evans Ave.",      50,  5));
    props.put(3,  new Property("Downing St.",     50,  5));
    props.put(4,  new Property("Union Station",  100, 10));
    props.put(5,  new Property("Hill Way",        80,  8));
    props.put(6,  new Property("Jackson Ave.",   100, 10));

    props.put(8,  new Property("Pajaro St.",     120, 12));
    props.put(9,  new Property("Blanco Rd.",     140, 14));
    props.put(10, new Property("Grand Central Station", 100, 10));
    props.put(11, new Property("Kentucky Ave.",  180, 18));
    props.put(12, new Property("Broadway",       200, 20));

    props.put(14, new Property("Main St.",       220, 22));
    props.put(15, new Property("Giggling Way",   240, 24));
    props.put(16, new Property("South Station",  100, 10));
    props.put(17, new Property("Flatlands Ave.", 260, 26));
    props.put(18, new Property("Atlantic Ave.",  280, 28));

    props.put(20, new Property("Reynolds Ave.",  320, 32));
    props.put(21, new Property("Columbia Rd.",   350, 35));
    props.put(22, new Property("King St. Station", 100, 10));
    props.put(23, new Property("17 Mile Drive",  350, 35));
    props.put(24, new Property("Lombard St.",    400, 40));

    return props;
  }

  /**
   * Creates a map of {@link Property} for the large Monopoly board.
   *
   * @return a map of tile IDs to Property instances for the large board.
   */
  private Map<Integer, Property> getLargeBoardPropertyMap() {
    Map<Integer, Property> props = new LinkedHashMap<>();
    props.put(2, new Property("Park Place", 200, 20));
    props.put(3, new Property("Boardwalk", 400, 40));
    props.put(4, new Property("Baltic Ave.", 60, 6));
    props.put(5, new Property("Mediterranean Ave.", 50, 5));
    props.put(6, new Property("Reading Railroad", 100, 10));
    props.put(7, new Property("Oriental Ave.", 100, 10));
    props.put(8, new Property("Vermont Ave.", 120, 12));
    props.put(9, new Property("Connecticut Ave.", 140, 14));

    props.put(11, new Property("St. Charles Place", 150, 15));
    props.put(12, new Property("States Ave.", 160, 16));
    props.put(13, new Property("Virginia Ave.", 180, 18));
    props.put(14, new Property("St. James Place", 200, 20));
    props.put(15, new Property("Tennessee Ave.", 220, 22));
    props.put(16, new Property("New York Ave.", 240, 24));
    props.put(17, new Property("Kentucky Ave.", 260, 26));
    props.put(18, new Property("Indiana Ave.", 280, 28));

    props.put(20, new Property("Illinois Ave.", 300, 30));
    props.put(21, new Property("B&O Railroad", 100, 10));
    props.put(22, new Property("Atlantic Ave.", 320, 32));
    props.put(23, new Property("Ventnor Ave.", 340, 34));
    props.put(24, new Property("Marvin Gardens", 360, 36));
    props.put(25, new Property("Pacific Ave.", 380, 38));
    props.put(26, new Property("North Carolina Ave.", 400, 40));
    props.put(27, new Property("Pennsylvania Ave.", 420, 42));

    props.put(29, new Property("Short Line", 100, 10));
    props.put(30, new Property("Water Works", 150, 15));
    props.put(31, new Property("Electric Company", 150, 15));
    props.put(32, new Property("Liberty Ave.", 150, 15));
    props.put(33, new Property("Heritage Way", 100, 10));
    props.put(34, new Property("Prospect Court", 100, 10));
    props.put(35, new Property("Founder's Plaza", 200, 20));
    props.put(36, new Property("Capital Circle", 350, 35));

    return props;
  }
}
