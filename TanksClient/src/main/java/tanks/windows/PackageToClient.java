package tanks.windows;

import java.io.Serializable;

public class PackageToClient implements Serializable {
    int shiftX;
    int hp;
    int enemyHp;

    public PackageToClient(int shiftX, int hp, int enemyHp) {
        this.shiftX = shiftX;
        this.hp = hp;
        this.enemyHp = enemyHp;
    }
}
