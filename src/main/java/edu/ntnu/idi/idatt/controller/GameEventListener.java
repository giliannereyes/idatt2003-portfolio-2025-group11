package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;

public interface GameEventListener {
  void onDiceRolled(Player player, int dice1, int dice2);
  void onPlayerMoved(Player player, Tile fromTile, Tile destinationTile);
  void onTileAction(Player player, TileAction tileAction);
  void onPlayerWon(Player player);
}

