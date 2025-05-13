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

public class DiceCanvas extends StackPane {
    private final ImageView dice1;
    private final ImageView dice2;
    private final Random random = new Random();

    public DiceCanvas(double width, double height) {
        dice1 = new ImageView();
        dice2 = new ImageView();
        // Resize the dice images
        dice1.setFitWidth(width / 2);
        dice1.setFitHeight(height);
        dice2.setFitWidth(width / 2);
        dice2.setFitHeight(height);
        HBox diceBox = new HBox(10);
        diceBox.setAlignment(Pos.CENTER);
        diceBox.getChildren().addAll(dice1, dice2);
        this.getChildren().add(diceBox);
    }

    public void updateDice(int diceValue1, int diceValue2) {
        rollDiceAnimation(diceValue1, diceValue2);
    }

    /**
     * Uses the DiceImage enum to set the images for each die.
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
