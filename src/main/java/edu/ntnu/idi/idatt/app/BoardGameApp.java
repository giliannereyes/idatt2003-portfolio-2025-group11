package edu.ntnu.idi.idatt.app;

import edu.ntnu.idi.idatt.config.initializer.AppInitializer;
import edu.ntnu.idi.idatt.ui.view.GameSelectionView;
import edu.ntnu.idi.idatt.utils.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main application class for the Board Game application.
 *
 * <p>This class initializes the JavaFX application and sets up the initial view.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class BoardGameApp extends Application {
  /**
   * The main method to launch the JavaFX application.
   *
   * @param args the command line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * The start method is called when the JavaFX application is launched.
   *
   * @param primaryStage the primary stage for this application, onto which
   *                     the application scene can be set.
   */
  @Override
  public void start(Stage primaryStage) {
    AppInitializer appInitializer = new AppInitializer(primaryStage);
    ViewManager viewManager = appInitializer.getViewManager();
    viewManager.switchTo(GameSelectionView.class);
    primaryStage.setTitle("Board Games");
    primaryStage.show();
  }
}
