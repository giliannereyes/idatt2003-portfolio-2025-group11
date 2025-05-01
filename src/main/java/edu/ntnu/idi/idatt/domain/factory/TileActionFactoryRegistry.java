package edu.ntnu.idi.idatt.domain.factory;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A registry for tile action factories. Maintains a map of factories for creating
 * tile actions with and without destination.
 *
 * @version 0.1
 * @since 0.2
 * @author Gilianne Reyes
 */
public class TileActionFactoryRegistry {
    private final Map<String, DestinationTileActionFactory> destinationFactories;
    private final Map<String, NoDestinationTileActionFactory> noDestinationFactories;

    /**
     * Constructs the registry and initializes the maps.
     */
    public TileActionFactoryRegistry() {
        destinationFactories = new HashMap<>();
        noDestinationFactories = new HashMap<>();
    }

    /**
     * Retrieves the factory for creating tile action without destination.
     *.
     * @param factory is the factory to use.
     */
    public void registerNoDestinationFactory(NoDestinationTileActionFactory factory) {
        noDestinationFactories.put(factory.getActionType(), factory);
    }

    /**
     * Registers a factory for creating tile action with destination.
     *
     * @param factory is the factory to use.
     */
    public void registerDestinationFactory(DestinationTileActionFactory factory) {
        destinationFactories.put(factory.getActionType(), factory);
    }

    /**
     * Retrieves the factory for creating tile action with destination.
     *
     * @param actionName is the name of the action.
     *
     * @return the factory for creating tile action with destination, if found.
     */
    public Optional<DestinationTileActionFactory> getDestinationFactory(String actionName) {
        return Optional.ofNullable(destinationFactories.get(actionName));
    }

    /**
     * Retrieves the factory for creating tile action without destination.
     *
     * @param actionName is the name of the action.
     * @return the factory for creating tile action without destination, if found.
     */
    public Optional<NoDestinationTileActionFactory> getNoDestinationFactory(String actionName) {
        return Optional.ofNullable(noDestinationFactories.get(actionName));
    }
}
