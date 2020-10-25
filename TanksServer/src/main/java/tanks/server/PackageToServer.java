package tanks.server;

import java.io.Serializable;

public class PackageToServer implements Serializable {
    public int shiftX;

    public int x;
    public int y;
    public boolean isShot;

    public PackageToServer(int shiftX, int x, int y, boolean isShot) {
        this.shiftX = shiftX;
        this.x = x;
        this.y = y;
        this.isShot = isShot;
    }
}
