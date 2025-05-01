package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;

public interface GameEventListener {
  void onDiceRolled(Player player, int dice1, int dice2);
  void onPlayerMoved(Player player, Tile fromTile, Tile destinationTile);
  void onTileAction(Player player, TileAction tileAction);
  void onPlayerWon(Player player);
}

