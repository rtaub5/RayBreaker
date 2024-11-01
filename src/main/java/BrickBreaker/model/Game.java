package BrickBreaker.model;

import java.util.ArrayList;
import java.util.List;

public class Game
{
    public Ball ball;
    public List<Brick> bricks;
    public Paddle paddle;
    private int ballX = 300;
    private int ballY = 400;
    private int ballRadius = 15;
    private int paddleY = 415;


    public Game()
    {
        ball = new Ball(0, 0, 0);
        paddle = new Paddle(0, 0, 0, 0);
        bricks = new ArrayList<>();

    }



    public void moveBall()
    {
        if((!(ball.x == 0)) && (!(ball.x == 600)) && (!(ball.y == 0)) && (!(ball.y == 600)))
            ball.moveBall();
        else
        {
            // need to make logic for when it hits things
        }
    }







}
