package edu.ntnu.idi.idatt.domain.factory;

import edu.ntnu.idi.idatt.domain.entity.Player;

@FunctionalInterface
public interface PlayerFactory {
  Player createPlayer(String playerName);
}

