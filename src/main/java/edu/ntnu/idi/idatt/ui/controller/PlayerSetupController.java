package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.service.GameConfigService;
import edu.ntnu.idi.idatt.service.PlayerService;
import edu.ntnu.idi.idatt.utils.ViewManager;
import edu.ntnu.idi.idatt.ui.view.PlayerSetupView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerSetupController {
    private List<PlayerConfig> playerConfigs;
    private final PlayerSetupView view;
    private final GameConfigService gameConfigService;
    private final ViewManager viewManager;
    private final PlayerService playerService;

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
