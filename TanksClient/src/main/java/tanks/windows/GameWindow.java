package tanks.windows;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameWindow {

    public static Canvas getGameCanvas() {
        Canvas canvas = new Canvas(640, 480);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(new Image("/images/field.png"), 640, 480);
        return canvas;
    }
}
