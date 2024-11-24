package brickbreaker.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Component
{
    private Ball ball;
    private List<Brick> bricks;
    private Paddle paddle;
    private int brickPadding = 5;
    private int score;
    private boolean inProgress;
    private final Random rand = new Random();

    public Game() {
        ball = new Ball(300, 400, 15);
        paddle = new Paddle(250, 415, 120, 10);
        bricks = new ArrayList<>();
        inProgress = true;
        score = 0;
    }

    public void setBall(Ball ball) { this.ball = ball; }
    public Ball getBall() { return ball; }
    public void setPaddle(Paddle paddle) { this.paddle = paddle; }
    public Paddle getPaddle() { return paddle; }
    public void setBricks(ArrayList<Brick> bricks) { this.bricks = bricks; }
    public List<Brick> getBricks() { return bricks; }
    public void addBrick(Brick brick) { bricks.add(brick); }
    public void removeBrick(int ix) { bricks.remove(ix); }
    public void clearBricks() { bricks.clear(); }
    public boolean isInProgress() { return inProgress; }
    public int getScore() { return score; }

    public void restartGame() {
        ball.setX(15);
        ball.setY(385);
        inProgress = true;
    }

    public void initializeBricks(int width, int height, int brickWidth, int brickHeight) {
        int rows = width / brickWidth; // how many bricks can fit across the frame
        int cols = height / brickHeight / 2; // how many bricks can fit in top half of frame

        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                addBrick(new Brick(x * brickWidth, y * brickHeight, brickWidth, brickHeight));
                x += rand.nextInt(brickPadding);
            }
            y += rand.nextInt(brickPadding);
        }
    }

    // For AI - gets angle between ball center and paddle center
    public double getBallToPaddleAngle() {
        double deltaX = paddle.getX() - ball.getX();
        double deltaY = paddle.getY() - ball.getY();
        return Math.toDegrees(Math.atan2(deltaY, deltaX));
    }

    // Determines what kind of object the ball hit and responds
    public Intersection intersects(int x, int y) {
        // First check if ball hit wall/ceiling or floor
        if (x < 1 || y < 1 || x >= 600) {
            return Intersection.WALL;
        } else if (y >= 525) {
            return Intersection.FLOOR;
        } // If not, check if ball hit paddle
        else if (ball.intersects(paddle)) {
            return Intersection.PADDLE;
        } else { // Check if ball hit brick
            for (int i = 0; i < bricks.size(); i++) {
                if (ball.intersects(bricks.get(i))) {
                    removeBrick(i);
                    return Intersection.BRICK;
                }
            }
        }
        return Intersection.NONE;
    }

    public void nextMove() {
        int x = (int) ball.getX();
        int y = (int) ball.getY();

        switch (intersects(x, y)) {
            case NONE:
                ball.moveBall();
                break;
            case WALL:
                ball.reflectOffWall(x, y);
                break;
            case FLOOR:
                gameOver();
                break;
            case BRICK:
                if (bricks.isEmpty()) { gameOver(); }
                ball.reverseBallAngle();
                break;
            case PADDLE:
                ball.reverseBallAngle();
                setAngleFromPaddle(x);
                ball.moveBall();
                score++;
                break;
            default:
                break;
        }
    }

    // Reset the angle of the ball based on where it hits the paddle
    public void setAngleFromPaddle(int x)
    {
        int distance = Math.abs(x - paddle.x); // distance between paddle's left edge and ball
        double angle = distance;
        int quarter = paddle.width / 4;
        // angle narrows based on area of paddle edge ball hit
        if (distance < quarter) {
            angle += 10;
        }
        else if (distance > quarter && distance < (quarter * 3)) {
            angle += 30;
        } else {
            angle += 60;
        }
        ball.setAngle(angle);
    }

    private void gameOver() {
        clearBricks();
        inProgress = false;
        score = 0;
    }
}

