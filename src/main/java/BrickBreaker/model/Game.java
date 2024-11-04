package BrickBreaker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public Ball ball;
    public List<Brick> bricks;
    public Paddle paddle;

    private int ballX = 300;
    private int ballY = 400;
    private int ballRadius = 15;
    private int paddleY = 415;

    private final Random rand = new Random();


    public Game() {
        ball = new Ball(0, 0, 0);
        paddle = new Paddle(0, 0, 0, 0);
        bricks = new ArrayList<>();
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    public void addBrick(Brick brick) {
        bricks.add(brick);
    }

    public void removeBrick(int ix) {
        bricks.remove(ix);
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void moveBall() {
        if ((!(ball.getX() == 0)) && (!(ball.getX() == 600)) && (!(ball.getY() == 600))&& (!(ball.getY() == 0)))
            ball.moveBall();
        else if(ball.getY() > paddleY) {
            endGame();
        }
    }

    public void setBallAngle() {
        //TODO: replace this with something that makes sense
        ball.setAngle(ball.getAngle() * -1);
    }

    private void endGame() {

    }

    //TODO: should this go in ball and pass in list of bricks?
    public int intersects(int x, int y) {
        int retVal = bricks.size() + 1;
        if(ball.intersects(paddle)) {
            retVal = -1;
        }
        else {
            for (int i = 0; i < bricks.size(); i++) {
                if(ball.intersects(bricks.get(i))) {
                    retVal = i;
                }
            }
        }

        return retVal;
    }

    public void initializeBricks(int width, int height, int brickWidth, int brickHeight) {
        int rows = width / brickWidth;
        int cols = height / brickHeight / 2;

        for (int y = 0; y < cols; y++) {
            for(int x = 0; x < rows; x++) {
                addBrick(new Brick(x * brickWidth, y * brickHeight, brickWidth, brickHeight));
                x += rand.nextInt(3);
            }
            y += rand.nextInt(3);
        }

    }

}

