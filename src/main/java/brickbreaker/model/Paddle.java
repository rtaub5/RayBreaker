package brickbreaker.model;

import java.awt.*;

public class Paddle extends Rectangle {

    private int speed;
    private Direction direction;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height);
        speed = 10;
        direction = Direction.RIGHT;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void changeDirection() {
        if (direction == Direction.LEFT) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.LEFT;
        }
    }

    public void move() {
        if(!legalMove())
        {
            changeDirection();
        }
        if (direction == Direction.RIGHT) {
            x += speed / 3;
        }
        else {
            x -= speed / 3;
        }
    }

    public boolean legalMove()
    {
        if (x >= (600 - width) && direction == Direction.RIGHT) {
            return false;
        } else if (x < 0 && direction == Direction.LEFT) {
            return false;
        }
        return true;
    }
}
