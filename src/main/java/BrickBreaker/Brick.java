package BrickBreaker;

import java.awt.*;

public class Brick extends Rectangle {

    private boolean isAlive = false;

    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive() {
        isAlive = true;
    }

    public void setDead() {
        isAlive = false;
    }




}
