package brickbreaker.model;

import basicneuralnetwork.NeuralNetwork;

import java.awt.*;
import java.io.IOException;
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
    private boolean started;
    private int rounds;
    private final Random rand;

    // new variable checks to see if it hits walls in between hitting paddle,
    // so don't have weird bug
    private boolean collidesWithBorder = false;

    public Game(Random random) {
        this.rand = random;
        initializeRandVal();
        bricks = new ArrayList<>();
        started = false;
        rounds = 0;
        score = 0;
    }

    private void initializeRandVal() {
        ball = new Ball(rand.nextInt(300), 300, 15);
        paddle = new Paddle(rand.nextInt(250), 415, 120, 10);
    }

    public void resetRand(long seed) {
        rand.setSeed(seed);
        initializeRandVal();
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
    public boolean started() { return started; }
    public int getRounds() { return rounds; }
    public void setRounds(int rounds) { this.rounds = rounds; }
    public void nextRound() { rounds++; }
    public void start() {
        started = true;
        rounds = 0;
    }
    public int getScore() { return score; }

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

    // changed input to centerX
    public void makeMove(NeuralNetwork network) {
        double[] input = new double[2];
        input[0] = ball.getCenterX();
        input[1] = paddle.getCenterX();
        double[] answer = network.guess(input);

        if (answer[0] > answer[1]) {
            // move paddle left
            getPaddle().setDirection(Direction.LEFT);
            getPaddle().move();
        } else  {
            // move paddle right
            getPaddle().setDirection(Direction.RIGHT);
            getPaddle().move();
        }
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
                collidesWithBorder = true;
                break;
            case FLOOR:
                endGame();
                break;
            case BRICK:
                if (bricks.isEmpty()) { endGame(); }
                ball.reverseBallAngle();
                break;
            case PADDLE:
                ball.reverseBallAngle();
                ball.moveBall();
                if (collidesWithBorder)
                {
                    score++;
                    collidesWithBorder = !collidesWithBorder;
                }
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
        /*   if (distance < quarter) {
            angle += 10;
        }
        else if (distance > quarter && distance < (quarter * 3)) {
            angle += 30;
        } else {
            angle += 60;
        } */
        angle = 45; //to train ai
        ball.setAngle(angle);
    }

    private void endGame() {
        clearBricks();
        started = false;
    }

}

