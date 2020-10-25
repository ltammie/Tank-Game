package tanks.server;

import java.io.Serializable;

public class PackageToClient  implements Serializable {
    int shiftX;
    int hp;
    int enemyHp;
    boolean gameStarted;

    public PackageToClient(int shiftX, int hp, int enemyHp) {
        this.shiftX = shiftX;
        this.hp = hp;
        this.enemyHp = enemyHp;
    }
}
