package tanks.windows;

import java.io.Serializable;

public class PackageToServer implements Serializable {
    public int shiftX;
    public int x;
    public int y;

    public PackageToServer(int shiftX, int x, int y) {
        this.shiftX = shiftX;
        this.x = x;
        this.y = y;
    }
}
