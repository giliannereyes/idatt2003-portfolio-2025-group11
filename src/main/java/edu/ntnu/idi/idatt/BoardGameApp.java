package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.config.AppInitializer;
import edu.ntnu.idi.idatt.utils.ViewManager;
import edu.ntnu.idi.idatt.view.PlayerSetupView;
import javafx.application.Application;
import javafx.stage.Stage;

public class BoardGameApp extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    AppInitializer appInitializer = new AppInitializer(primaryStage);
    ViewManager viewManager = appInitializer.getDisplayManager();
    viewManager.switchTo(PlayerSetupView.class.getName());
    primaryStage.setTitle("Snakes & Ladders");
    primaryStage.show();
  }
}
