package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.PlayerService;
import edu.ntnu.idi.idatt.utils.ViewManager;
import edu.ntnu.idi.idatt.ui.view.PlayerSetupView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing player setup in the UI.
 * Handles loading and saving player data from/to CSV files,
 * validating input, and registering player configurations.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerSetupController {
    private List<PlayerConfig> playerConfigs;
    private final PlayerSetupView view;
    private final GameConfigService gameConfigService;
    private final ViewManager viewManager;
    private final PlayerService playerService;

    /**
     * Constructs a new PlayerSetupController.
     *
     * @param view the view responsible for player setup UI
     * @param playerService the service for handling player-related logic
     * @param gameConfigService the service for managing game configuration
     * @param viewManager the manager for switching between views
     */
    public PlayerSetupController(
            PlayerSetupView view,
            PlayerService playerService,
            GameConfigService gameConfigService,
            ViewManager viewManager
    ) {
        this.playerConfigs = new ArrayList<>();
        this.view = view;
        this.playerService = playerService;
        this.gameConfigService = gameConfigService;
        this.viewManager = viewManager;
    }

    /**
     * Loads player data from a CSV file and populates the view.
     *
     * @param file the CSV file containing player data
     */
    public void loadPlayersFromCsv(File file) {
        List<String[]> playerData;
        try {
            playerData = playerService.loadPlayersFromCsv(file);
            view.autoFillPlayersFromCSV(playerData);
            view.onSuccessfulCsvLoad();
        } catch (Exception e) {
            view.onErrorLoadingCsv(e.getMessage());
        }
    }


    /**
     * Saves the given player names and token names to a CSV file.
     *
     * @param playerNames list of player names
     * @param tokenNames list of token names
     * @param file the file to save the data to
     */
    public void savePlayersToCsv(List<String> playerNames, List<String> tokenNames, File file) {
        List<String[]> playerData = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            String[] data = {playerNames.get(i), tokenNames.get(i)};
            playerData.add(data);
        }
        try {
            playerService.savePlayersToCsv(file, playerData);
            view.onSuccessfulCsvSave();
        } catch (Exception e) {
            view.onErrorSavingCsv(e.getMessage());
        }
    }

    /**
     * Validates and registers player configurations based on the provided names and tokens.
     * Updates the game configuration and transitions to the next view if successful.
     *
     * @param playerNames list of player names
     * @param tokenNames list of token names
     */
    public void registerPlayerConfigs(List<String> playerNames, List<String> tokenNames) {
        playerConfigs.clear();
        if (!playerService.isPlayerConfigDataValid(playerNames, tokenNames)) {
            view.onErrorLoadingCsv("Invalid player names or tokens. " +
                  "Make sure there are no empty values or duplicates.");
        } else {
            try {
                playerConfigs = playerService.createPlayerConfigs(playerNames, tokenNames);
                gameConfigService.updatePlayerConfigs(playerConfigs);
                viewManager.switchToNextView();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                view.onErrorLoadingCsv("An error occurred while registering the player configurations.");
            }
        }
    }
}
