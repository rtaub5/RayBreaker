package brickbreaker.model;

import java.awt.*;

public class Paddle extends Rectangle {

    //add speed as variable
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

    public int moveLeft() {
        if (legalMove())
        x -= 1;
        return x;
    }

    public int moveRight() {
        if (legalMove())
        x += 1;
        return x;
    }

    public void move() {
        if(direction && legalMove()) {
            x += 1; //replace with speed
        }
        else if (!direction && legalMove()){
            x -= 1;
        }
    }

    public boolean legalMove()
    {
        boolean legal = true;
        if (x >= (600 - width) || x <= 0)
        {
            legal = false;
        }
        return legal;
    }

    public double getQuarter()
    {
        return (double) this.width / 4;
    }

}
