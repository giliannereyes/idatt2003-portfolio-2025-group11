package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.persistence.reader.PlayerFileReader;
import edu.ntnu.idi.idatt.persistence.writer.PlayerFileWriter;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.enums.PlayerToken;
import edu.ntnu.idi.idatt.domain.factory.PlayerFactory;
import edu.ntnu.idi.idatt.utils.Validation;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A service class for managing player-related operations such
 * as loading and saving player data.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerService {
  private final PlayerFileReader fileReader;
  private final PlayerFileWriter fileWriter;
  private final int maxPlayers = 5;
  private final int minPlayers = 2;
  private final PlayerFactory playerFactory;

  /**
   * Constructs a PlayerService instance.
   *
   * @param fileReader is the player file reader.
   * @param fileWriter is the player file writer.
   *
   * @throws IllegalArgumentException if any of the parameters is null.
   */
  public PlayerService(PlayerFileReader fileReader, PlayerFileWriter fileWriter, PlayerFactory playerFactory) {
    Validation.validateNonNull(fileReader, "File reader");
    Validation.validateNonNull(fileWriter, "File writer");
    Validation.validateNonNull(playerFactory, "Player factory");
    this.fileReader = fileReader;
    this.fileWriter = fileWriter;
    this.playerFactory = playerFactory;
  }

  public List<String[]> loadPlayersFromCsv(File file) {
    try {
      List<String[]> playerData = fileReader.readFromCSV(file);
      if (playerData.size() > maxPlayers) {
        throw new IllegalArgumentException("Too many players");
      } else if (playerData.size() < minPlayers) {
        throw new IllegalArgumentException("Too few players");
      }
      return playerData;
    } catch (Exception e) {
      throw new RuntimeException("Error reading from file: " + e.getMessage());
    }
  }

  public void savePlayersToCsv(File file, List<String[]> playerData) {
    try {
      fileWriter.writeToCSV(playerData, file, null);
    } catch (Exception e) {
      throw new RuntimeException("Error writing to file: " + e.getMessage());
    }
  }

  public List<PlayerConfig> createPlayerConfigs(List<String> playerNames, List<String> tokenNames) {
    try {
      return IntStream.range(0, playerNames.size())
            .mapToObj(i -> createPlayerConfig(playerNames.get(i), tokenNames.get(i)))
            .collect(Collectors.toList());
    } catch (Exception e) {
      throw new RuntimeException("Error creating player configs: " + e.getMessage());
    }
  }

  public boolean isPlayerConfigDataValid(List<String> playerNames, List<String> tokenNames) {
    if (playerNames.size() != tokenNames.size()) {
      return false;
    }
    Set<String> seenNames = new HashSet<>();
    Set<String> seenTokens = new HashSet<>();
    return IntStream.range(0, playerNames.size()).allMatch(i ->
          isValidEntry(playerNames.get(i), tokenNames.get(i), seenNames, seenTokens)
    );
  }

  private PlayerConfig createPlayerConfig(String playerName, String tokenName) {
    PlayerToken playerToken = PlayerToken.fromName(tokenName);
    if (playerToken == null) {
      throw new IllegalArgumentException("Invalid token name: " + tokenName);
    }
    String tokenPath = playerToken.getImagePath();
    Player player = playerFactory.createPlayer(playerName);
    return new PlayerConfig(player, tokenPath);
  }

  private boolean isValidEntry(String name, String tokenName, Set<String> seenNames, Set<String> seenTokens) {
    if (!isValidPlayerName(name)) {
      return false;
    }
    PlayerToken token = PlayerToken.fromName(tokenName);
    if (token == null || !isValidPlayerToken(tokenName)) {
      return false;
    }
    if (!isUniqueName(name, seenNames)) {
      return false;
    }
    return isUniqueToken(token.getImagePath(), seenTokens);
  }

  private boolean isUniqueName(String name, Set<String> seenNames) {
    return seenNames.add(name.toLowerCase()); // returns false if already present
  }

  private boolean isUniqueToken(String imagePath, Set<String> seenTokens) {
    return seenTokens.add(imagePath.toLowerCase()); // returns false if already present
  }

  private boolean isValidPlayerName(String playerName) {
    return playerName != null && !playerName.trim().isEmpty();
  }

  private boolean isValidPlayerToken(String tokenName) {
    return tokenName != null && !tokenName.trim().isEmpty() && PlayerToken.fromName(tokenName) != null;
  }
}
