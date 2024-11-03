package BrickBreaker.model;

import java.awt.*;

public class Paddle extends Rectangle {

    private boolean direction;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height);
        direction = true;
    }

    public void changeDirection() {
        direction = !direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public void move() {
        if(direction) {
            x += 1;
        }
        else {
            x -= 1;
        }
    }

}
