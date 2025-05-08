  package edu.ntnu.idi.idatt.service;

  import edu.ntnu.idi.idatt.config.GameConfig;
  import edu.ntnu.idi.idatt.config.PlayerConfig;
  import edu.ntnu.idi.idatt.domain.entity.Board;

  import java.util.List;

  public class GameConfigService<B extends Board> {
    private final GameConfig<B> gameConfig;

    public GameConfigService(GameConfig<B> gameConfig) {
      this.gameConfig = gameConfig;
    }

    public GameConfig<B> build() {
      return gameConfig;
    }

    public void updateBoard(B board) {
      gameConfig.setBoard(board);
    }

    public void updatePlayerConfigs(List<PlayerConfig> playerConfigs) {
      gameConfig.setPlayerConfigs(playerConfigs);
    }

    public boolean isConfigComplete() {
      return gameConfig.isComplete();
    }
  }
