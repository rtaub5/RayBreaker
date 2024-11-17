package brickbreaker.controller;

import brickbreaker.model.Game;
import brickbreaker.view.GameComponent;
import brickbreaker.view.GameFrame;

import javax.swing.*;
import java.awt.event.*;

public class GameController
{
    private final GameFrame model;
    private final GameComponent view;
    private final Game game;
    private boolean isRunning = false;

    public GameController(GameFrame model, GameComponent view)
    {
        this.model = model;
        this.view = view;
        game = view.getGame();
    }

    public Game getGame()
    {
        return game;
    }


    private void initializeGameState()
    {
        view.getGame().getBall().setX(200);
        view.getGame().getBall().setY(200);
        initializePaddle();
        game.initializeBricks(view.getWidth(), view.getHeight(), 40, 20);
    }

    public void startGame()
    {
        game.restartGame();
        if (!isRunning)
        {
            initializeGameState();
            startTimer();
            isRunning = true;
        } else
        {
            resumeGame();
        }

    }

    private void resumeGame()
    {
        initializePaddle();
        startTimer();
    }

    public void initializePaddle()
    {
        view.setFocusable(true);
        view.requestFocusInWindow();
    }

//    public void movePaddleRight () {
//    movePaddle(new KeyEvent(new JButton(), KeyEvent.KEY_PRESSED,
//            System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'R'));
//    }
//
//    public void movePaddleLeft () {
//    movePaddle(new KeyEvent(new JButton(), KeyEvent.KEY_PRESSED,
//            System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'L'));
//    }

    public void movePaddle(int keyCode) { // send
        if (keyCode == KeyEvent.VK_RIGHT) {
            game.getPaddle().setDirection(true);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            game.getPaddle().setDirection(false);
        }
        startPaddleTimer();
    }

//    public void movePaddle (KeyEvent e){
//        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
//        {
//            game.getPaddle().setDirection(true);
//        } else if (e.getKeyCode() == KeyEvent.VK_LEFT)
//        {
//
//            game.getPaddle().setDirection(false);
//        }
//    startPaddleTimer();
//    }

    public void stopMovingPaddle () {
    model.paddleTimer.stop();
    }

        private void startPaddleTimer () {
        if (model.paddleTimer == null || !model.paddleTimer.isRunning())
        {
            model.paddleTimer = new Timer(5, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    double paddleX = game.getPaddle().getX();
                    if (paddleX <= 0 || paddleX + game.getPaddle().getWidth() >= view.getWidth())
                    {
                        game.getPaddle().changeDirection();
                    }
                    game.getPaddle().move();
                    view.repaint();
                }
            });
        }
        model.paddleTimer.start();
    }


    public void moveBall ( int x, int y){
    game.nextMove(x, y);
    if (!game.isInProgress())
    {
        gameOver();
    }
    view.repaint();
    }

    public void gameOver () {
    //stop timers, display popup
    isRunning = false;
    stopTimer();
    view.repaint();
    int restart = JOptionPane.showConfirmDialog(model,
            "Would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
    if (restart == JOptionPane.YES_OPTION)
    {
        startGame();
    }
    }

    public void startTimer () {
    model.timer = new Timer(100, new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            moveBall((int) view.getGame().getBall().getX(), (int) view.getGame().getBall().getY());
        }
    });

    model.timer.start();
    }

    public void stopTimer () {
        if (model.timer != null && model.timer.isRunning())
        {
            model.timer.stop();
        }
    }
        /*
        Game logic:
            - Ball begins somewhere pointed in some direction and keeps moving
            - when ball hits either: a wall (edge of screen), a brick, or the paddle, it changes its angle
            - if the ball hits a brick, the brick vanishes
            - if the ball hits the bottom wall, game is over

        Process (not necessarily in order):
            - void startGame(): perhaps a button in GameFrame that starts the ball moving (starts timer)
            - mouseEvent to drag paddle along the bottom of the screen
            - void moveBall(int x, int y): moves the ball along the screen one pixel (or more) in the ball's current
              angle
            - timer event calls moveBall() and checks after or before each move whether it has/will bump into something
            - this can use Ellipse2D.intersects(x, y, width, height)
                 - If true: checkObject(): switch case if object is wall, brick, or paddle
            - intersects probably won't check for walls though, so perhaps first check that the ball is still in bounds.
                 - If it's below bounds, game over;
                 otherwise, calculateAngle() and call moveBall()
            - double calculateAngle(double angle): decides which angle to reflect based on the angle the ball is
              as it hits. reset ball.angle.
                 - perhaps based on the surface type it hits
                   (ie if it hits a brick, reflect by 180; if a wall, by 90 etc)
        */

    }
