package brickbreaker.model;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double
{

    private double angle;
    private int speed;

    public Ball(double x, double y, double radius)
    {
        super(x, y, radius, radius);
        angle = 45;
        speed = 25;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public void reverseBallAngle() {
        setAngle(angle * -1);
    }

    public void moveBall()
    {
         /*
         subtract 90 from current angle to account for Java's inverted axis and align angle with trigonometric convention
         multiply by speed to calculate distance the ball will travel within that frame
         */
        float xDirection = (float) (Math.sin((float) Math.toRadians(angle - 90))
                * speed);
        float yDirection = (float) (Math.cos((float) Math.toRadians(angle - 90))
                * -speed);
        // multiply by -speed since y-axis is inverted
        x = (int) (x + xDirection);
        y = (int) (y + yDirection);
    }

    public void reflectOffWall(int x, int y)
    {
        // If the ball hit the wall to its left or right
        if (x <= 1 || x >= 600)
        {
            setAngle(180 - angle); // rotates evenly in opposite direction
            moveBall();
        }
        // If the ball hit the ceiling
        else if (y <= 1)
        {
            reverseBallAngle(); // bounce off ceiling
            moveBall();
        }
    }
}
