package BrickBreaker.model;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double
{

    private double angle;
    private int speed;

    public Ball(double x, double y, double radius)
    {
        super(x, y, radius, radius);
        this.angle = 90;
        speed = 10;
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
}
