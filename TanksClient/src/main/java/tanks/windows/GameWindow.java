package tanks.windows;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameWindow {

    public static Canvas getGameCanvas() {

        return new Canvas(640, 480);
    }
}
