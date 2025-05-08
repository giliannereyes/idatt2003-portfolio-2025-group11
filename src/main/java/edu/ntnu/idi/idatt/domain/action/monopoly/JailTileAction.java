package edu.ntnu.idi.idatt.domain.action.monopoly;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;

public class JailTileAction implements TileAction {
  private final Tile jailTile;

  public JailTileAction(Tile jailTile) {
    this.jailTile = jailTile;
  }

  @Override
  public void perform(Player player) {
    if (!player.getCurrentTile().equals(jailTile)) {
      player.placeOnTile(jailTile);
    }
    player.setSkipTurn(true);
  }

  @Override public String getActionType() { return "Jail"; }
}

