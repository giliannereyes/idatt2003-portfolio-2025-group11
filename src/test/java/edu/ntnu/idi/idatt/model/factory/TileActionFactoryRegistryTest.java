package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.LadderAction;
import edu.ntnu.idi.idatt.model.actions.SkipTurnAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class for the TileActionFactoryRegistry.
 *
 * @version 0.1
 * @since 0.2
 * @author Gilianne Reyes
 */
public class TileActionFactoryRegistryTest {
    private TileActionFactoryRegistry registry;
    private TestDestinationTileActionFactory destinationFactory;
    private TestNoDestinationTileActionFactory noDestinationFactory;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    public void setUp() {
        registry = new TileActionFactoryRegistry();
        destinationFactory = new TestDestinationTileActionFactory();
        noDestinationFactory = new TestNoDestinationTileActionFactory();
    }

    // ------- Positive tests -------

    /**
     * Test registering and retrieving a factory with a destination tile.
     *
     * <p>Expected: The factory should be registered and retrievable.</p>
     */
    @Test
    public void testRegisterAndRetrieveDestinationFactory() {
        registry.registerDestinationFactory(destinationFactory);
        Optional<DestinationTileActionFactory> factory = registry
                .getDestinationFactory("destinationAction");
        assertTrue(factory.isPresent());
        assertEquals(factory.get(), destinationFactory);
    }

    /**
     * Test registering and retrieving a factory without a destination tile.
     *
     * <p>Expected: The factory should be registered and retrievable.</p>
     */
    @Test
    public void testRegisterAndRetrieveNoDestinationFactory() {
        registry.registerNoDestinationFactory(noDestinationFactory);
        Optional<NoDestinationTileActionFactory> retrievedFactory = registry
                .getNoDestinationFactory("noDestinationAction");
        assertTrue(retrievedFactory.isPresent());
        assertEquals(noDestinationFactory, retrievedFactory.get());
    }

    // ------- Negative tests -------

    /**
     * Test retrieving a non-existent factory with a destination tile.
     *
     * <p>Expected: The factory should not be present.</p>
     */
    @Test
    public void testRetrieveNonExistentDestinationFactory() {
        Optional<DestinationTileActionFactory> retrievedFactory = registry
                .getDestinationFactory("nonExistentAction");
        assertFalse(retrievedFactory.isPresent(), "Factory should not be present");
    }

    /**
     * Test retrieving a non-existent factory without a destination tile.
     *
     * <p>Expected: The factory should not be present.</p>
     */
    @Test
    public void testRetrieveNonExistentNoDestinationFactory() {
        Optional<NoDestinationTileActionFactory> retrievedFactory = registry
                .getNoDestinationFactory("nonExistentAction");
        assertFalse(retrievedFactory.isPresent(), "Factory should not be present");
    }


    // Implementations of the factory interfaces for testing
    /**
     * Test implementation of a factory with a destination tile.
     */
    private static class TestDestinationTileActionFactory implements DestinationTileActionFactory {
        @Override
        public TileAction createTileAction(Tile destinationTile) {
            return new LadderAction(destinationTile);
        }

        @Override
        public String getActionType() {
            return "destinationAction";
        }
    }

    /**
     * Test implementation of a factory without a destination tile.
     */
    private static class TestNoDestinationTileActionFactory implements NoDestinationTileActionFactory {
        @Override
        public String getActionType() {
            return "noDestinationAction";
        }

        @Override
        public TileAction createTileAction() {
            return new SkipTurnAction();
        }
    }
}


