package brickbreaker.model;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double
{
    private double dx;
    private double dy;
    private final int speed;

    public Ball(double x, double y, double radius)
    {
        super(x, y, radius, radius);
        dx = 1;
        dy = 1;
        speed = 5;
    }

    public void reverseBallAngle() {
        dx *= -1;
        dy *= -1;
    }

    public void moveBall() {
        x += dx * speed;
        y += dy * speed;
    }

    public void setAngle(double angle) {
        dx = Math.cos(angle);
        dy = Math.sin(angle);
    }

    public void reflectOffWall(int x, int y)
    {
        // If the ball hit the wall to its left or right
        if (x <= 1 || x >= 600)
        {
            dx *= -1; // rotates evenly in opposite direction
            moveBall();
        }
        // If the ball hit the ceiling
        else if (y <= 1)
        {
            dy *= -1; // bounce off ceiling
            moveBall();
        }
    }
}
