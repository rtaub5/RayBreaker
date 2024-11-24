package brickbreaker.model;

import java.awt.*;

public class Paddle extends Rectangle {

    //add speed as variable
    private boolean direction;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height);
        direction = true;
    }



    public void setDirection(boolean direction) {
        this.direction = direction;
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
            direction = !direction;
        }
        if (direction) {
            x += 1;
        }
        else {
            x -= 1;
        }
    }

    public boolean legalMove()
    {
        if (x >= (600 - width) && direction) {
            return false;
        }
        else if( x < 0 && !direction) {
            return false;
        }

        return true;
    }

    public double getQuarter()
    {
        return (double) this.width / 4;
    }

}
