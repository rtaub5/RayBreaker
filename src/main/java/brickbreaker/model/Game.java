package brickbreaker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public Ball ball;
    public List<Brick> bricks;
    public Paddle paddle;
    private boolean inProgress;
    private int spaceBricks = 5;
    private final Random rand = new Random();

    private int score;

    public Game() {
        ball = new Ball(0, 0, 0);
        paddle = new Paddle(0, 0, 0, 0);
        bricks = new ArrayList<>();
        inProgress = true;
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void restartGame() {
        System.out.println("Game.RESTARTGAME");
        inProgress = true;
    }

    public void setSpaceBricks(int spaceBricks) {
        this.spaceBricks = spaceBricks;
    }

    public int getSpaceBricks() {
        return spaceBricks;
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

    public double getBallToPaddleAngle() {
        double deltaX = paddle.getX() - ball.getX();
        double deltaY = paddle.getY() - ball.getY();
        return Math.atan2(deltaY, deltaX);
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void addBrick(Brick brick) {
        bricks.add(brick);
    }

    public void removeBrick(int ix) {
        bricks.remove(ix);
    }

    public void clearBricks() {
        bricks.clear();
    }

    public void initializeBricks(int width, int height, int brickWidth, int brickHeight) {
        int rows = width / brickWidth;
        int cols = height / brickHeight / 2;

      /*  if(rows == 0 || cols == 0) {
            rows = 10;
            cols = 10;
        } */

        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                addBrick(new Brick(x * brickWidth, y * brickHeight, brickWidth, brickHeight));
                x += rand.nextInt(spaceBricks);
            }
            y += rand.nextInt(spaceBricks);
        }
        System.out.println("Bricks: " + bricks.size());
    }

    public void setBallAngle() {
        //TODO: replace this with something that makes sense
        ball.setAngle(ball.getAngle() * -1);
    }

    public Intersection intersects(int x, int y) {
        Intersection result = positionIsWall(x, y);

        if (result == Intersection.NONE) {
            if (ball.intersects(paddle)) {
                result = Intersection.PADDLE;
            } else {
                for (int i = 0; i < bricks.size(); i++) {
                    if (ball.intersects(bricks.get(i))) {
                        result = Intersection.BRICK;
                        removeBrick(i);
                    }
                }
            }
        }

        return result;
    }

    private Intersection positionIsWall(int x, int y)
    {
        System.out.println("Intersects check for ball at: x = " + x + ", y = " + y);

        if (x < 1 || y < 1 || x >= 600) {
            return Intersection.WALL;
        } else if (y >= 525) {
            return Intersection.FLOOR;
        }

        return Intersection.NONE;
    }

    public void setAngleFromPaddle(int x)
    {
        int position = Math.abs(x - paddle.x);
        int half = paddle.width / position;
        double angle = 180 / position * 10;
        if (half < 2)
        {
            angle += 89;
        }
     //   int position = Math.abs(x - paddle.x) + 1;
        double quarter = paddle.getQuarter();
        angle = position;
        if (position < quarter) {
            angle += 10;
        } else if (position > quarter && position < (quarter * 3)) {
            angle += 30;
        } else {
            angle += 60;
        }
        ball.setAngle(angle);
    }

    public void ballHitNone() {
        System.out.println("hit none: " + ball.getX() + " " + ball.getY());
        ball.moveBall();
    }

    public void ballHitWall(int x, int y) {
        ball.reflectOffWall(x, y);
    }

    public void ballHitBrick() {
      /*  if (bricks.isEmpty()) {
            gameOver(); commenting out to train ai without bricks
        } */
        setBallAngle();
    }

    public void ballHitPaddle(int x) {
        setBallAngle();
        setAngleFromPaddle(x);
        ball.moveBall();
        score++;
    }

    public void ballHitFloor() {
        gameOver();
    }

    private void gameOver() {
        clearBricks();
        inProgress = false;
        score = 0;
        System.out.println("game over");
    }

    public void nextMove(int x, int y) {
        Intersection result = intersects(x, y);

        switch (result) {
            case NONE:
                ballHitNone();
                break;
            case WALL:
                ballHitWall(x, y);
                break;
            case FLOOR:
                ballHitFloor();
                break;
            case BRICK:
                ballHitBrick();
                break;
            case PADDLE:
                ballHitPaddle(x);
                break;
            default:
                break;
        }
    }
}

