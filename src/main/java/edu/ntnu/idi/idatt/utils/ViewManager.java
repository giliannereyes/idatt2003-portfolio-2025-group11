package edu.ntnu.idi.idatt.utils;

import edu.ntnu.idi.idatt.view.View;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The DisplayManager class is responsible for managing the displays
 * of the application. It stores and switches between different displays.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class ViewManager {
    Map<String, View> displays;
    Stage primaryStage;
    int displayWidth = 1200;
    int displayHeight = 700;

    /**
     * Constructs a DisplayManager instance.
     *
     * @param primaryStage is the primary stage of the application.
     */
    public ViewManager(Stage primaryStage) {
        Validation.validateNonNull(primaryStage, "Primary stage");
        this.primaryStage = primaryStage;
        this.displays = new HashMap<>();
    }

    /**
     * Adds a display to the manager.
     *
     * @param view is the display to add.
     *
     * @throws IllegalArgumentException if the display is null.
     */
    public void add(View view) {
        Validation.validateNonNull(view, "Display object");
        displays.put(view.getClass().getName(), view);
    }

    /**
     * Switches to the display with the provided class name.
     *
     * @param displayClassName is the class name of the display to switch to.
     *
     * @throws IllegalArgumentException if the display is not found or the class name is null/empty.
     */
    public void switchTo(String displayClassName) {
        Validation.validateNonEmptyStr(displayClassName, "Display class name");
        View view = displays.get(displayClassName);
        if (view == null) {
            throw new IllegalArgumentException("Display not found.");
        }
        Parent root = view.getRoot();
        Scene scene = new Scene(root, displayWidth, displayHeight);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(
              Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm()
        );
        primaryStage.setScene(scene);
        view.initializeView();
    }

}
