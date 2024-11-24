package brickbreaker.model;

import java.awt.*;

public class Paddle extends Rectangle {

    //add speed as variable
    private Direction direction;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height);
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

    public int moveLeft() {
    //    if (legalMove())
     //   {
            x -= 1;
     //   }
     //   else
     //   {
     //       x += 1;
      //  }
        return x;
    }

    public int moveRight() {
       if (legalMove())
      {
            x += 1;
        }
       else {
           x -= 1;
        }
        return x;
    }

    public void move() {
        if(!legalMove())
        {
            changeDirection();
        }
        if (direction == Direction.RIGHT) {
            x += 1;
        }
        else {
            x -= 1;
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

    public double getQuarter()
    {
        return (double) this.width / 4;
    }

}
