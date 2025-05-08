package edu.ntnu.idi.idatt.domain.factory.monopoly;

import edu.ntnu.idi.idatt.domain.action.monopoly.JailTileAction;
import edu.ntnu.idi.idatt.domain.action.monopoly.PropertyAction;
import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.entity.monopoly.MonopolyBoard;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.strategy.monopoly.MonopolyLayoutStrategy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MonopolyBoardFactory {
  private static final int GRID_SIZE = 7;
  private final MonopolyLayoutStrategy layoutStrategy;
  private final List<Supplier<MonopolyBoard>> predefinedBoards;

  public MonopolyBoardFactory() {
    this.layoutStrategy = new MonopolyLayoutStrategy();
    this.predefinedBoards = List.of(
          this::loadSmallBoard,
          this::loadLargeBoard
    );
  }

  public Map<String, MonopolyBoard> getAllPredefinedBoards() {
    return predefinedBoards.stream()
        .map(Supplier::get)
        .collect(Collectors.toMap(
              MonopolyBoard::getName, board -> board
        ));
  }

  /**
   * Loads a "small" Monopoly board with a 24-space perimeter.
   * Includes properties with rent 10% the property cost.
   *
   * @return a MonopolyBoard object with properties and actions.
   */
  public MonopolyBoard loadSmallBoard() {
    MonopolyBoard board = new MonopolyBoard(7,7);
    board.setName("Small board");
    board.setDescription("24-space perimeter. Properties have rent 10% of the property value.");
    layoutStrategy.buildLayout(board);
    board.addTileAction(7, new JailTileAction(board.getTile(7)));
    board.addTileAction(19, new JailTileAction(board.getTile(7)));
    getSmallBoardPropertyMap().forEach(board::addMonopolyProperty);
    return board;
  }

  /**
   * Loads a "large" Monopoly board with a 36-space perimeter.
   * Includes properties with rent 10% the property cost.
   *
   * @return a MonopolyBoard object with properties and actions.
   */
  public MonopolyBoard loadLargeBoard() {
    MonopolyBoard board = new MonopolyBoard(10,10);
    board.setName("Large board");
    board.setDescription("36-space perimeter. Properties have rent 10% of the property value.");
    layoutStrategy.buildLayout(board);
    board.addTileAction(10, new JailTileAction(board.getTile(10)));
    board.addTileAction(28, new JailTileAction(board.getTile(10)));
    getLargeBoardPropertyMap().forEach(board::addMonopolyProperty);
    return board;
  }

  public Board loadBoard() {
    Board board = new Board(GRID_SIZE, GRID_SIZE);
    board.setName("Monopoly Lite");
    board.setDescription("24-space perimeter with custom properties");
    layoutStrategy.buildLayout(board);
    Tile jailTile = board.getTile(7);
    board.addTileAction(7, new JailTileAction(jailTile));
    board.addTileAction(jailTile.getTileId(), new JailTileAction(jailTile));
    getSmallBoardPropertyMap().forEach((tileId, prop) ->
          board.addTileAction(tileId, new PropertyAction(prop))
    );
    board.addTileAction(28, new JailTileAction(jailTile));
    return board;
  }

  private Map<Integer, Property> getSmallBoardPropertyMap() {
    Map<Integer, Property> props = new LinkedHashMap<>();
    props.put(2,  new Property("Evans Ave.",      50,  5));
    props.put(3,  new Property("Downing St.",     50,  5));
    props.put(4,  new Property("Union Station",  100, 10));
    props.put(5,  new Property("Hill Way",        80,  8));
    props.put(6,  new Property("Jackson Ave.",   100, 10));

    props.put(8,  new Property("Pajaro St.",     120, 12));
    props.put(9,  new Property("Blanco Rd.",     140, 14));
    props.put(10, new Property("Grand Central Station",100,10));
    props.put(11, new Property("Kentucky Ave.",  180, 18));
    props.put(12, new Property("Broadway",       200, 20));

    props.put(14, new Property("Main St.",       220, 22));
    props.put(15, new Property("Giggling Way",   240, 24));
    props.put(16, new Property("South Station",  100, 10));
    props.put(17, new Property("Flatlands Ave.", 260, 26));
    props.put(18, new Property("Atlantic Ave.",  280, 28));

    props.put(20, new Property("Reynolds Ave.",  320, 32));
    props.put(21, new Property("Columbia Rd.",   350, 35));
    props.put(22, new Property("King St. Station",100,10));
    props.put(23, new Property("17 Mile Drive",  350, 35));
    props.put(24, new Property("Lombard St.",    400, 40));

    return props;
  }

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
    props.put(32, new Property("...", 150, 15));
    props.put(33, new Property("... name", 100, 10));
    props.put(34, new Property("...", 100, 10));
    props.put(35, new Property("...", 200, 20));
    props.put(36, new Property("...", 350, 35));

    return props;
  }
}
