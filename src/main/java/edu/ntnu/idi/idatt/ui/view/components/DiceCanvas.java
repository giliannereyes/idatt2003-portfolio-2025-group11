package edu.ntnu.idi.idatt.ui.view.components;

import edu.ntnu.idi.idatt.domain.enums.DiceImage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import java.util.Objects;
import java.util.Random;

/**
 * A visual component for displaying and animating two dice.
 * This class handles the layout, image updates, and rolling animation of dice.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */

public class DiceCanvas extends StackPane {
    private final ImageView dice1;
    private final ImageView dice2;
    private final Random random = new Random();

    /**
     * Constructs a DiceCanvas with specified width and height for the dice images.
     *
     * @param width the total width of the dice canvas
     * @param height the total height of the dice canvas
     */
    public DiceCanvas(double width, double height) {
        dice1 = new ImageView();
        dice2 = new ImageView();
        dice1.setFitWidth(width / 2);
        dice1.setFitHeight(height / 2);
        dice2.setFitWidth(width / 2);
        dice2.setFitHeight(height / 2);
        HBox diceBox = new HBox(10);
        diceBox.setAlignment(Pos.CENTER);
        diceBox.getChildren().addAll(dice1, dice2);
        this.getChildren().add(diceBox);
    }

    /**
     * Updates the dice display with the final rolled values, using an animation.
     *
     * @param diceValue1 the value of the first die (1–6)
     * @param diceValue2 the value of the second die (1–6)
     */
    public void updateDice(int diceValue1, int diceValue2) {
        rollDiceAnimation(diceValue1, diceValue2);
    }

    /**
     * Uses the DiceImage enum to set the images for each die.
     *
     * @param diceValue1 the value of the first die
     * @param diceValue2 the value of the second die
     */
    private void setDiceImages(int diceValue1, int diceValue2) {
        DiceImage image1 = DiceImage.getDiceImage(diceValue1);
        DiceImage image2 = DiceImage.getDiceImage(diceValue2);
        dice1.setImage(new Image(Objects
                .requireNonNull(getClass().getResourceAsStream(Objects.requireNonNull(image1).getImagePath()))));
        dice2.setImage(new Image(Objects
                .requireNonNull(getClass().getResourceAsStream(Objects.requireNonNull(image2).getImagePath()))));
    }

    /**
     * Animate the dice rolling by rapidly cycling through random dice faces from the enum.
     *
     * @param diceValue1 the final value of the first die
     * @param diceValue2 the final value of the second
     */
    private void rollDiceAnimation(int diceValue1, int diceValue2) {
        int rollCycles = 10;
        int frameDurationMs = 100;
        Timeline timeline = new Timeline();
        for (int i = 0; i < rollCycles; i++) {
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(i * frameDurationMs),
                    event -> {
                        int randomVal1 = random.nextInt(6) + 1;
                        int randomVal2 = random.nextInt(6) + 1;
                        setDiceImages(randomVal1, randomVal2);
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(e -> setDiceImages(diceValue1, diceValue2));
        timeline.play();
    }
}
