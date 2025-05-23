package edu.ntnu.idi.idatt.utils;

import edu.ntnu.idi.idatt.ui.view.View;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages application views by registering View instances, creating corresponding Scenes,
 * and handling navigation between them. Transitions can also be registered to
 * define navigation flows between views.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class ViewManager {
  private final Stage stage;
  private final Map<Class<? extends View>, View> viewMap;
  private final Map<Class<? extends View>, Scene> scenes;
  private final Map<Class<? extends View>, Class<? extends View>> transitions;
  private final double width;
  private final double height;
  private final String styleCss;
  private Class<? extends View> currentViewClass;

  /**
   * Constructs a ViewManager for the given primary stage, stylesheet, and dimensions.
   *
   * @param primaryStage is the main JavaFX Stage to set scenes on.
   * @param styleCss is the classpath resource path to the CSS stylesheet.
   * @param width is the width to use for all Scenes.
   * @param height is the height to use for all Scenes.
   *
   * @throws IllegalArgumentException if {@code primaryStage} is null.
   * @throws IllegalArgumentException if {@code styleCss} is null.
   */
  public ViewManager(Stage primaryStage, String styleCss, double width, double height) {
    Validation.validateNonNull(primaryStage, "Primary stage");
    Validation.validateNonEmptyStr(styleCss, "Style css");
    this.stage = primaryStage;
    this.styleCss = Objects.requireNonNull(
          getClass().getResource(styleCss)).toExternalForm();
    this.viewMap = new HashMap<>();
    this.scenes = new HashMap<>();
    this.transitions = new HashMap<>();
    this.width = width;
    this.height = height;
  }

  /**
   * Registers a View instance, creates its Scene with the configured dimensions
   * and stylesheet, and caches both for later navigation.
   *
   * @param view is the View to register and display.
   * @param <V>  is the concrete View type.
   *
   * @throws IllegalArgumentException if {@code view} is null.
   */
  public <V extends View> void add(V view) {
    Validation.validateNonNull(view, "View");
    viewMap.put(view.getClass(), view);
    Scene scene = new Scene(view.getRoot(), width, height);
    scene.getStylesheets().add(styleCss);
    scenes.put(view.getClass(), scene);
  }

  /**
   * Switches the primary stage to display the Scene associated with the given View class.
   *
   * @param viewClass is the class of the View to display.
   * @param <V> is the concrete View type
   *
   * @throws IllegalArgumentException if no Scene is registered for {@code viewClass}.
   * @throws IllegalArgumentException if the {@code viewClass} is null.
   */
  public <V extends View> void switchTo(Class<V> viewClass) {
    Validation.validateNonNull(viewClass, "View class");
    Scene scene = scenes.get(viewClass);
    if (scene == null) {
      throw new IllegalArgumentException("Unknown view: " + viewClass);
    }
    stage.setScene(scene);
    viewMap.get(viewClass).initializeView();
    currentViewClass = viewClass;
  }

  /**
   * Registers a navigation transition from one View class to another.
   * After calling {@link #switchToNextView()}, the manager will move from
   * the current view to the registered next view.
   *
   * @param from is the class of the current View
   * @param to is the class of the next View.
   * @param <F> is the current View type.
   * @param <T> is the next View type.
   *
   * @throws IllegalArgumentException if either {@code from} or {@code to} is null.
   */
  public <F extends View, T extends View> void registerTransition(Class<F> from, Class<T> to) {
    Validation.validateNonNull(from, "From-view");
    Validation.validateNonNull(to, "To-view");
    transitions.put(from, to);
  }

  /**
   * Switches to the View registered as next after the currently active one.
   *
   * @throws IllegalStateException if no current view is set or no transition is registered.
   */
  public void switchToNextView() {
    if (currentViewClass == null) {
      throw new IllegalStateException("No current view set");
    }
    Class<? extends View> next = transitions.get(currentViewClass);
    if (next == null) {
      throw new IllegalStateException("No transition from " + currentViewClass);
    }
    switchTo(next);
  }
}
