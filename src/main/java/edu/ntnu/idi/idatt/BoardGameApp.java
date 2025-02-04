package edu.ntnu.idi.idatt;

import javafx.application.Application;
import javafx.stage.Stage;

public class BoardGameApp extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Board Game");
    primaryStage.show();
  }
}
