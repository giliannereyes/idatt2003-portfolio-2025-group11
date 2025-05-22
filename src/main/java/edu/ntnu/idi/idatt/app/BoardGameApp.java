package edu.ntnu.idi.idatt.app;

import edu.ntnu.idi.idatt.config.initializer.AppInitializer;
import edu.ntnu.idi.idatt.utils.ViewManager;
import edu.ntnu.idi.idatt.ui.view.GameSelectionView;
import javafx.application.Application;
import javafx.stage.Stage;

public class BoardGameApp extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    AppInitializer appInitializer = new AppInitializer(primaryStage);
    ViewManager viewManager = appInitializer.getViewManager();
    viewManager.switchTo(GameSelectionView.class);
    primaryStage.setTitle("Board Games");
    primaryStage.show();
  }
}
