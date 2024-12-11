package brickbreaker.neuralnetworks;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.model.*;

import java.awt.*;
import java.util.*;

public class Simulation extends Component {
    private Ball ball;
    // private List<Brick> bricks;
    private Brick brick;
    private Paddle paddle;
    private final int brickPadding = 5;
    private final int brickWidth = 45;
    private final int brickHeight = 20;
    private int score;
    private boolean started;
    private int rounds;
    private final Random rand;
    private boolean isRunning;
    private boolean hitBrick = false;
    private boolean hitPaddle = false;

    public Simulation(Random rand) {
        this.rand = rand;
        initializeRandVal();
        // bricks = new ArrayList<>();
        brick = new Brick(0,0,0,0);
        started = false;
        rounds = 0;
        score = 0;
        isRunning = false;
    }

    private void initializeRandVal() {
        ball = new Ball(rand.nextInt(300), 300, 15);
        paddle = new Paddle(rand.nextInt(250), 415, 120, 10);
    }

    public void resetRand(long seed) {
        rand.setSeed(seed);
        initializeRandVal();
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }
    public Ball getBall() {
        return ball;
    }
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }
    public Paddle getPaddle() {
        return paddle;
    }
//    public void setBricks(ArrayList<Brick> bricks) {
//        this.bricks = bricks;
//    }
//    public List<Brick> getBricks() {
//        return bricks;
//    }
    public void setBrick(int width, int height) {
        int rows = width / brickWidth; // how many bricks can fit across the frame
        int cols = height / brickHeight / 2; // how many bricks can fit in top half of frame
       // int x = rand.nextInt(0, rows);
       // int y = rand.nextInt(0, cols);
        int x = rand.nextInt(rows);
        int y = rand.nextInt(cols);
        brick = new Brick(x * brickWidth, y * brickHeight, brickWidth, brickHeight);
    }
    public Brick getBrick() {
        return brick;
    }
//    public void removeBrick(int ix) {
//        bricks.remove(ix);
//    }
//    public void clearBricks() {
//        bricks.clear();
//    }
    public void start() {
        started = true;
        rounds = 0;
        setBrick(600, 600);
    }
    public boolean started() {
        return started;
    }
    private void endGame() {
        // clearBricks();
        started = false;
    }
    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
    public int getRounds() {
        return rounds;
    }
    public void nextRound() {
        rounds++;
    }
    public int getScore() {
        return score;
    }

//    public void initializeBricks(int width, int height) {
//        int rows = width / brickWidth; // how many bricks can fit across the frame
//        int cols = height / brickHeight / 2; // how many bricks can fit in top half of frame
//
//        for (int y = 0; y < cols; y++) {
//            for (int x = 0; x < rows; x++) {
//                setBrick(brickWidth, brickHeight));
//                x += rand.nextInt(brickPadding);
//            }
//            y += rand.nextInt(brickPadding);
//        }
//    }

    public boolean advance(NeuralNetwork neuralNetwork) {
        makeMove(neuralNetwork);
        nextMove();
        return started();
    }

    // Determines what kind of object the ball hit and responds
    public Intersection intersects(int x, int y) {
        // First check if ball hit wall/ceiling or floor
        if (x < 1 || y < 1 || x >= 600) {
            return Intersection.WALL;
        } else if (y >= 525)
        {
            return Intersection.FLOOR;
            // If not, check if ball hit paddle
        } else if (ball.intersects(paddle)) {
            return Intersection.PADDLE;
        } else { // Check if ball hit brick
//            for (int i = 0; i < bricks.size(); i++) {
//                if (ball.intersects(bricks.get(i))) {
//                    removeBrick(i);
//                    return Intersection.BRICK;
//                }
//            }
            if (ball.intersects(brick)) {
                return Intersection.BRICK;
            }
        }
        return Intersection.NONE;
    }

    // changed input to centerX
    public void makeMove(NeuralNetwork network) {
        double[] input = new double[4];
        input[0] = ball.getCenterX();
        input[1] = paddle.getCenterX();
        input[2] = this.getBrick().getCenterX();
        input[3] = this.getBrick().getCenterY();
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
                break;
            case FLOOR:
                endGame();
                break;
            case BRICK:
                // remove brick and create new
                hitBrick = true;
              //  if (hitPaddle)
               // {
                    score++; // if hit brick after hitting paddle
                System.out.println("Score!! " + score);
                 //   hitPaddle = !hitPaddle;
              //  }
                this.setBrick(600, 600);
                ball.reverseBallAngle();
                break;
            case PADDLE:
                hitPaddle = true;
               // if (hitBrick)
               // {
                //    score++; // if hit paddle after hitting brick
              //      hitBrick = !hitBrick;
              //  }
                ball.reverseBallAngle();
                ball.moveBall();
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
        angle = 45; //to train ai
        ball.setAngle(angle);
    }
}
