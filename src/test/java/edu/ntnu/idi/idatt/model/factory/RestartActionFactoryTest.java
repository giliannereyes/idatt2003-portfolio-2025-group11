package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.ResetAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the RestartActionFactory class.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class RestartActionFactoryTest {
    RestartActionFactory restartActionFactory;

    /**
     * Sets up the RestartActionFactory before each test.
     */
    @BeforeEach
    public void setUp() {
        restartActionFactory = new RestartActionFactory();
    }

    // ------- Positive tests -------

    /**
     * Tests the creation of a ResetAction instance.
     *
     * <p>Expected: The factory should return a new instance of ResetAction.</p>
     */
    @Test
    public void testCreateResetAction() {
        TileAction action = restartActionFactory.createTileAction();
        assertNotNull(action, "Reset action should not be null");
        assertInstanceOf(ResetAction.class, action, "The factory should return an instance of ResetAction");
    }

    /**
     * Tests that the factory creates independent instances of ResetAction.
     *
     * <p>Expected: The factory should return a new instance of ResetAction each time it is called.</p>
     */
    @Test
    public void testRestartActionFactoryCreatesNewInstances() {
        TileAction action1 = restartActionFactory.createTileAction();
        TileAction action2 = restartActionFactory.createTileAction();
        assertInstanceOf(ResetAction.class, action1, "The factory should return an instance of ResetAction");
        assertInstanceOf(ResetAction.class, action2, "The factory should return an instance of ResetAction");
        assertNotSame(action1, action2, "Factory should return a new instance each time.");
    }
}
