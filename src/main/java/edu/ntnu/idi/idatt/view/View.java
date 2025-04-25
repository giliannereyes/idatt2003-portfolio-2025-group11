package edu.ntnu.idi.idatt.view;

import javafx.scene.Parent;

/**
 * The Display interface is used to define the behavior of a display.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface View {
    /**
     * The getRoot method is responsible for retrieving the root node
     * of the display.
     */
    Parent getRoot();

    /**
     * The initializeView method is responsible for initializing the display.
     */
    void initializeView();

}
