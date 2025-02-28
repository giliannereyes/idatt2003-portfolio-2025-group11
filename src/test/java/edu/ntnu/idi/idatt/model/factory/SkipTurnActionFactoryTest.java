package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.SkipTurnAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for the {@link SkipTurnAction} class.
 * This test ensures that the factory correctly creates a {@link SkipTurnAction}
 * and that it performs the expected behavior when executed.
 *
 * @author Trang Duong
 * @author Gilianne Reyes
 * @version 0.2
 * @since 0.1
 */
public class SkipTurnActionFactoryTest {
    private SkipTurnActionFactory skipTurnActionFactory;

    /**
     * Initialises a destination tile and a {@link SkipTurnActionFactory} instance
     */
    @BeforeEach
    public void setUp() {
        skipTurnActionFactory = new SkipTurnActionFactory();
    }

    // -------------- Positive tests --------------

    /**
     * Tests if the factory creates a {@link SkipTurnActionFactory} instance.
     *
     * <p>Expected: The factory should create an instance of {@link SkipTurnActionFactory}.</p>
     */
    @Test
    public void testCreateSkipTurnActionFactory() {
        SkipTurnActionFactory skipTurnActionFactory = new SkipTurnActionFactory();
        assertNotNull(skipTurnActionFactory, "SkipTurnActionFactory should not be null");
        assertInstanceOf(SkipTurnActionFactory.class, skipTurnActionFactory,
                "SkipTurnActionFactory should be an instance of SkipTurnActionFactory"
        );
    }

    /**
     * Tests that the factory creates independent instances of {@link SkipTurnAction}.
     *
     * <p>Expected: The factory should create a new instance of
     * {@link SkipTurnAction} each time it is called.</p>
     */
    @Test
    public void testSkipTurnFactoryCreatesNewInstances() {
        TileAction action1 = skipTurnActionFactory.createTileAction();
        TileAction action2 = skipTurnActionFactory.createTileAction();
        assertNotSame(action1, action2, "Factory should return a new instance each time.");
    }
}