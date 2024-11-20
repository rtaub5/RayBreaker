package brickbreaker.model;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double
{

    private double angle;
    private int speed;

    public Ball(double x, double y, double radius)
    {
        super(x, y, radius, radius);
        this.angle = 45;
        speed = 20;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }


    public void moveBall()
    {
        float xDirection = (float) (Math.sin((float) Math.toRadians(angle - 90))
                * speed);
        float yDirection = (float) (Math.cos((float) Math.toRadians(angle - 90))
                * -speed);

        x = (int) (x + xDirection);
        y = (int) (y + yDirection);


    }

    public void reflectOffWall(int x, int y)
    {
        if (x <= 1 || x >= 600)
        {
            setAngle(180 - angle);
            moveBall();
        }
        else if (y <= 1)
        {
            setAngle(angle * -1);
            moveBall();
        }
    }


}
