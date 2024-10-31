package BrickBreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {

    private double angle;

    public Ball(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.angle = 90;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }




}
