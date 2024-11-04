package BrickBreaker.controller;

import BrickBreaker.model.Game;
import BrickBreaker.view.GameComponent;
import BrickBreaker.view.GameFrame;

import javax.swing.*;
import java.awt.event.*;

public class GameController {
    private final GameFrame model;
    private final GameComponent view;
    private final Game game;

    public GameController(GameFrame model, GameComponent view) {
        this.model = model;
        this.view = view;
        game = view.getGame();
    }

    public void startGame() {
        //call all starter methods
        //start timer to control view.ball
        initializePaddle();
        game.initializeBricks(view.getWidth(), view.getHeight(), 40, 20);
        startTimer();
    }

    public void initializePaddle() {
        //initialize mouseEvent for view.paddle
        KeyListener keyListener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    game.getPaddle().setDirection(true);
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.getPaddle().setDirection(false);
                }
                startPaddleTimer();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                model.paddleTimer.stop();
            }
        };

        view.addKeyListener(keyListener);
        view.setFocusable(true);
        view.requestFocusInWindow();
    }

    private void startPaddleTimer() {
        if(model.paddleTimer == null || !model.paddleTimer.isRunning()) {
            model.paddleTimer = new Timer(7, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(game.getPaddle().getX() <= 0 || game.getPaddle().getX() - game.getPaddle().getWidth() >= view.getWidth()) {
                        game.getPaddle().changeDirection();
                    }
                    game.getPaddle().move();
                    view.repaint();
                }
            });
        }
        model.paddleTimer.start();
    }


    public void moveBall(int x, int y) {
        int intersect = game.intersects(x, y);
        if(intersect > game.getBricks().size() ) {
            game.getBall().moveBall();
        }
        else if(intersect < 0) { //ball hit paddle
            game.setBallAngle();
        }
        else {
            //recalculate angle if it did intersect
            game.removeBrick(intersect);
            game.setBallAngle();
        }

        view.repaint();
    }

    private double calculateAngleWall() {
        //reset view.ball.angle
        return 0;
    }

    private double calculateAngleBrick(int x, int y) {
        //reset view.ball.angle
        return 0;
    }

    public void gameOver() {
        //stop timers, display popup
    }

    public void startTimer() {
        model.timer = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                moveBall((int) view.getGame().getBall().getX(), (int) view.getGame().getBall().getY());
            }
        });

        model.timer.start();
    }

    public void stopTimer() {
        model.timer.stop();
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