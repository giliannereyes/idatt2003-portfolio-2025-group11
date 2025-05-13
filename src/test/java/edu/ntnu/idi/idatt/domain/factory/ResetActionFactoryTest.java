package edu.ntnu.idi.idatt.domain.factory;

import edu.ntnu.idi.idatt.domain.action.snakesandladders.ResetAction;
import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.factory.snakesandladders.ResetActionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the ResetActionFactory class.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class ResetActionFactoryTest {
    ResetActionFactory resetActionFactory;

    /**
     * Sets up the ResetActionFactory before each test.
     */
    @BeforeEach
    public void setUp() {
        resetActionFactory = new ResetActionFactory();
    }

    // ------- Positive tests -------

    /**
     * Tests the creation of a ResetAction instance.
     *
     * <p>Expected: The factory should return a new instance of ResetAction.</p>
     */
    @Test
    public void testCreateResetAction() {
        TileAction action = resetActionFactory.createTileAction();
        assertNotNull(action, "Reset action should not be null");
        assertInstanceOf(ResetAction.class, action, "The factory should return an instance of ResetAction");
    }

    /**
     * Tests that the factory returns the correct action type.
     *
     * <p>Expected: The factory should return the action type
     * of the {@link ResetAction}.</p>
     */
    @Test
    public void testGetActionType() {
        assertEquals(ResetAction.actionType, resetActionFactory.getActionType());
    }

    /**
     * Tests that the factory creates independent instances of ResetAction.
     *
     * <p>Expected: The factory should return a new instance of ResetAction each time it is called.</p>
     */
    @Test
    public void testRestartActionFactoryCreatesNewInstances() {
        TileAction action1 = resetActionFactory.createTileAction();
        TileAction action2 = resetActionFactory.createTileAction();
        assertInstanceOf(ResetAction.class, action1, "The factory should return an instance of ResetAction");
        assertInstanceOf(ResetAction.class, action2, "The factory should return an instance of ResetAction");
        assertNotSame(action1, action2, "Factory should return a new instance each time.");
    }
}
