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
 * <p>Supports two predefined layouts ("Quick board" and "Normal board"), each with its own
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
  public static final String NORMAL_MODE = "Normal mode";
  public static final String QUICK_MODE  = "Quick mode";
  private final MonopolyLayoutStrategy layoutStrategy;
  private final List<Supplier<Board>>  predefinedBoards;
  private final Map<String, Map<Integer, Property>> propertyMaps;

  /**
   * Constructs a MonopolyBoardFactory instance.
   */
  public MonopolyBoardFactory() {
    propertyMaps = new LinkedHashMap<>();
    layoutStrategy = new MonopolyLayoutStrategy();
    predefinedBoards = List.of(this::loadNormalBoard, this::loadQuickBoard);
    initialisePropertyMaps();
  }

  /**
   * Retrieves a map of all predefined boards, where the key is the board name
   * and the value is the corresponding {@link Board} object.
   *
   * <p>Note: The property registry is not included in this map, and
   * must be created separately using {@link #createPropertyRegistryForBoard(Board)}.</p>
   *
   * @return a map of board names to board objects.
   */
  public Map<String, Board> getAllPredefinedBoards() {
    return predefinedBoards.stream()
          .map(Supplier::get)
          .collect(Collectors.toMap(Board::getName, b -> b));
  }

  /**
   * Creates a property registry for the specified board.
   *
   * <p>Note: The board must be one of the predefined boards.</p>
   *
   * @param board is the board for which to create the property registry.
   *
   * @return a {@link PropertyRegistry} containing the properties for the board.
   *
   * @throws IllegalArgumentException if the board is null or not a predefined board.
   */
  public PropertyRegistry createPropertyRegistryForBoard(Board board) {
    Validation.validateNonNull(board, "Board");
    Map<Integer, Property> map = propertyMaps.get(board.getName());
    if (map == null) {
      throw new IllegalArgumentException("Unknown board: " + board.getName());

    }
    PropertyRegistry reg = new PropertyRegistry();
    map.forEach(reg::registerProperty);
    return reg;
  }

  /**
   * Loads the normal board configuration.
   *
   * @return a {@link Board} object representing the normal board.
   */
  public Board loadNormalBoard() {
    Board b = new Board(7, 7);
    b.setName(NORMAL_MODE);
    b.setDescription("24-space perimeter (7×7). Rent = 10 % of cost.");
    layoutStrategy.buildLayout(b);
    b.addTileAction(7,  new GoToJailAction(b.getTile(7)));   // Jail
    b.addTileAction(19, new GoToJailAction(b.getTile(7)));   // Go-to-Jail
    return b;
  }

  /**
   * Loads the quick board configuration.
   *
   * @return a {@link Board} object representing the quick board.
   */
  public Board loadQuickBoard() {
    Board b = new Board(7, 7);
    b.setName(QUICK_MODE);
    b.setDescription("24-space perimeter (7×7). Rent = 20 % of cost (quick mode).");
    layoutStrategy.buildLayout(b);
    b.addTileAction(7,  new GoToJailAction(b.getTile(7)));   // Jail
    b.addTileAction(19, new GoToJailAction(b.getTile(7)));   // Go-to-Jail
    return b;
  }

  /**
   * Initialises the property maps for the two predefined boards.
   */
  private void initialisePropertyMaps() {
    propertyMaps.put(NORMAL_MODE, buildPropertyMap(0.10));   // 10 %
    propertyMaps.put(QUICK_MODE,  buildPropertyMap(0.20));   // 20 %
  }

  /**
   * Creates a map for the 24-square perimeter.
   *
   * @param rentPercent is fraction of cost charged as base rent (e.g. 0.10 = 10 %)
   */
  private Map<Integer, Property> buildPropertyMap(double rentPercent) {
    java.util.function.BiFunction<Integer, Double, Integer> rent =
          (cost, pct) -> (int) Math.round(cost * pct);

    Map<Integer, Property> propertyMap = new LinkedHashMap<>();

    propertyMap.put(2, new Property("Evans Ave.", 50, rent.apply(50, rentPercent)));
    propertyMap.put(3, new Property("Downing St.", 50, rent.apply(50, rentPercent)));
    propertyMap.put(4, new Property("Union Station", 100, rent.apply(100, rentPercent)));
    propertyMap.put(5, new Property("Hill Way", 80, rent.apply(80, rentPercent)));
    propertyMap.put(6, new Property("Jackson Ave.", 100, rent.apply(100, rentPercent)));

    propertyMap.put(8, new Property("Pajaro St.", 120, rent.apply(120, rentPercent)));
    propertyMap.put(9, new Property("Blanco Rd.", 140, rent.apply(140, rentPercent)));
    propertyMap.put(10, new Property("Grand Central Station", 100, rent.apply(100, rentPercent)));
    propertyMap.put(11, new Property("Kentucky Ave.", 180, rent.apply(180, rentPercent)));
    propertyMap.put(12, new Property("Broadway", 200, rent.apply(200, rentPercent)));

    propertyMap.put(14, new Property("Main St.", 220, rent.apply(220, rentPercent)));
    propertyMap.put(15, new Property("Giggling Way", 240, rent.apply(240, rentPercent)));
    propertyMap.put(16, new Property("South Station", 100, rent.apply(100, rentPercent)));
    propertyMap.put(17, new Property("Flatlands Ave.", 260, rent.apply(260, rentPercent)));
    propertyMap.put(18, new Property("Atlantic Ave.", 280, rent.apply(280, rentPercent)));

    propertyMap.put(20, new Property("Reynolds Ave.", 320, rent.apply(320, rentPercent)));
    propertyMap.put(21, new Property("Columbia Rd.", 350, rent.apply(350, rentPercent)));
    propertyMap.put(22, new Property("King St. Station", 100, rent.apply(100, rentPercent)));
    propertyMap.put(23, new Property("17 Mile Drive", 350, rent.apply(350, rentPercent)));
    propertyMap.put(24, new Property("Lombard St.", 400, rent.apply(400, rentPercent)));

    return propertyMap;
  }
}
