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
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 * @author Trang Duong
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

  /**
   * Loads player data from a CSV file.
   *
   * @param file the CSV file containing player data
   * @return a list of string arrays, each representing a player's data
   * @throws RuntimeException if the file cannot be read or the number of players is invalid
   */
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

  /**
   * Saves player data to a CSV file.
   *
   * @param file the file to write to
   * @param playerData the list of player data to save
   * @throws RuntimeException if writing to the file fails
   */
  public void savePlayersToCsv(File file, List<String[]> playerData) {
    try {
      fileWriter.writeToCSV(playerData, file, null);
    } catch (Exception e) {
      throw new RuntimeException("Error writing to file: " + e.getMessage());
    }
  }

  /**
   * Creates a list of {@link PlayerConfig} objects from player and token names.
   *
   * @param playerNames list of player names
   * @param tokenNames list of token names corresponding to each player
   * @return a list of {@link PlayerConfig} objects
   * @throws RuntimeException if creation fails
   */
  public List<PlayerConfig> createPlayerConfigs(List<String> playerNames, List<String> tokenNames) {
    try {
      return IntStream.range(0, playerNames.size())
            .mapToObj(i -> createPlayerConfig(playerNames.get(i), tokenNames.get(i)))
            .collect(Collectors.toList());
    } catch (Exception e) {
      throw new RuntimeException("Error creating player configs: " + e.getMessage());
    }
  }

  /**
   * Validates player and token name lists for consistency and uniqueness.
   *
   * @param playerNames list of player names
   * @param tokenNames list of token names
   * @return true if the data is valid, false otherwise
   */
  public boolean isPlayerConfigDataValid(List<String> playerNames, List<String> tokenNames) {
    try {
      if (playerNames.size() != tokenNames.size()) {
        return false;
      }

      Set<String> seenNames = new HashSet<>();
      Set<String> seenTokens = new HashSet<>();
      return IntStream.range(0, playerNames.size()).allMatch(i ->
              isValidEntry(playerNames.get(i), tokenNames.get(i), seenNames, seenTokens)
      );
    } catch (Exception e) {
      throw new RuntimeException("Error validating player configs: " + e.getMessage());
    }
  }

  /**
   * Creates a single {@link PlayerConfig} from a player name and token name.
   *
   * @param playerName the player's name
   * @param tokenName the name of the token
   * @return a {@link PlayerConfig} object
   * @throws IllegalArgumentException if the token name is invalid
   */
  private PlayerConfig createPlayerConfig(String playerName, String tokenName) {
    PlayerToken playerToken = PlayerToken.fromName(tokenName);
    if (playerToken == null) {
      throw new IllegalArgumentException("Invalid token name: " + tokenName);
    }
    String tokenPath = playerToken.getImagePath();
    Player player = playerFactory.createPlayer(playerName);
    return new PlayerConfig(player, tokenPath);
  }

  /**
   * Validates a single player/token entry for correctness and uniqueness.
   */
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

  /**
   * Checks if a player name is unique.
   */
  private boolean isUniqueName(String name, Set<String> seenNames) {
    return seenNames.add(name.toLowerCase()); // returns false if already present
  }

  /**
   * Checks if a token image path is unique.
   */
  private boolean isUniqueToken(String imagePath, Set<String> seenTokens) {
    return seenTokens.add(imagePath.toLowerCase()); // returns false if already present
  }

  /**
   * Validates that a player name is not null or empty.
   */
  private boolean isValidPlayerName(String playerName) {
    return playerName != null && !playerName.trim().isEmpty();
  }

  /**
   * Validates that a token name is not null, not empty, and corresponds to a valid {@link PlayerToken}.
   */
  private boolean isValidPlayerToken(String tokenName) {
    return tokenName != null && !tokenName.trim().isEmpty() && PlayerToken.fromName(tokenName) != null;
  }
}
