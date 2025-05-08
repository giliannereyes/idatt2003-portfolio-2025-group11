package edu.ntnu.idi.idatt.ui.view;

import edu.ntnu.idi.idatt.ui.controller.FileBoardConfigController;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;

/**
 * A specialized BoardConfigView that adds Load/Save file functionality.
 */
public class FileBoardConfigView extends BoardConfigView {
  private FileBoardConfigController<?> controller;

  public FileBoardConfigView() {
    super();
    Button loadBoardButton = new Button("Load Board from JSON");
    Button saveBoardButton = new Button("Save Board to JSON");
    loadBoardButton.setOnAction(e -> onLoadFromFile());
    saveBoardButton.setOnAction(e -> onSaveToFile());
    root.getChildren().addAll(loadBoardButton, saveBoardButton);
  }

  public void setController(FileBoardConfigController<?> controller) {
    this.controller = controller;
  }

  public void updateSelectedBoard(String boardName) {
    boardToggleGroup.selectToggle(null);
    selectedBoardLabel.setText("Selected: " + boardName);
  }

  private void onLoadFromFile() {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Load Board JSON File");
    chooser.getExtensionFilters().add(
          new FileChooser.ExtensionFilter("JSON Files", "*.json")
    );
    File file = chooser.showOpenDialog(getRoot().getScene().getWindow());
    if (file != null) {
      controller.loadBoardConfiguration(file);
    }
  }

  private void onSaveToFile() {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Save Board Configuration");
    chooser.getExtensionFilters().add(
          new FileChooser.ExtensionFilter("JSON Files", "*.json")
    );
    File file = chooser.showSaveDialog(getRoot().getScene().getWindow());
    if (file != null) {
      controller.saveBoardConfiguration(file);
    }
  }
}