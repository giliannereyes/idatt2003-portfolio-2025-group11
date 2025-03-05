    package edu.ntnu.idi.idatt.model.actions;

    import edu.ntnu.idi.idatt.model.entities.Player;
    import edu.ntnu.idi.idatt.utils.Validation;

    /**
     * SnakeAction class is a class that represents the action of a tile with a skip-turn effect.
     * When a player lands on a tile with a skip-turn effect, the player will skip their next turn.
     *
     * @version 0.2
     * @since 0.1
     * @author Trang Duong
     * @author Gilianne Reyes
     */
    public class SkipTurnAction implements TileAction {
        public static final String actionType = "SkipTurnAction";

        /**
         * Constructs a SkipTurnAction instance.
         */
        public SkipTurnAction() {
        }

        /**
         * Sets the player to skip their next turn.
         *
         * @param player is the player that landed on the tile.
         *
         * @throws IllegalArgumentException if the player is null.
         */
        public void perform(Player player) {
            Validation.validateNonNull(player, "Player");
            player.setSkipTurn(true);
        }

        /**
         * Retrieves the type of the action, which is "SkipTurnAction".
         *
         * @return the type of the action.
         */
        @Override
        public String getActionType() {
            return actionType;
        }
    }