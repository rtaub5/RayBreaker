package brickbreaker;

import brickbreaker.model.Game;
import brickbreaker.view.GameComponent;
import brickbreaker.view.GameFrame;

import javax.swing.*;
import java.awt.event.*;

public class GameController {
    private final Game model;
    private final GameComponent view;
    private boolean isRunning = false;
    private Timer timer;
    private Timer paddleTimer;

    public GameController(Game model, GameComponent view) {
        this.model = model;
        this.view = view;
    }

    public Game getGame() {
        return model;
    }

    private void initializeGameState() {
        System.out.println("INITIALIZING GAME STATE");
        model.getBall().setX(200);
        model.getBall().setY(200);
        initializePaddle();
        model.initializeBricks(view.getWidth(), view.getHeight(), 40, 20);
    }

    public void startGame() {
        System.out.println("STARTING GAME");
        model.restartGame();
        if (!isRunning) {
            initializeGameState();
            startTimer();
            isRunning = true;
        } else {
            resumeGame();
        }
    }

    private void resumeGame() {
        initializePaddle();
        startTimer();
    }

    public void initializePaddle() {
        view.setFocusable(true);
        view.requestFocusInWindow();
    }

    public void movePaddle(int keyCode) {
        if (keyCode == KeyEvent.VK_RIGHT) {
            model.getPaddle().setDirection(true);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            model.getPaddle().setDirection(false);
        }
        startPaddleTimer();
    }

    public void stopMovingPaddle() {
        paddleTimer.stop();
    }

    private void startPaddleTimer() {
        if (paddleTimer == null || !paddleTimer.isRunning()) {
            paddleTimer = new Timer(5, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double paddleX = model.getPaddle().getX();
//                    if (paddleX <= 0 || paddleX + model.getPaddle().getWidth() >= view.getWidth()) {
//                        model.getPaddle().changeDirection();
//                    }
                    model.getPaddle().move();
                    view.repaint();
                }
            });
        }
        paddleTimer.start();
    }

    public void moveBall(int x, int y) {
        System.out.println("MOVE BALL: " + x + ", " + y);
        model.nextMove();
        if (!model.isInProgress()) {
            gameOver();
        }
        view.repaint();
    }

    public void gameOver() {
        //stop timers, display popup
        isRunning = false;
        stopTimer();
        view.repaint();
        System.out.println("Game over.");
//        int restart = JOptionPane.showConfirmDialog(model, "Would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
//        if (restart == JOptionPane.YES_OPTION) {
//            startGame();
//        }
    }

    public void startTimer() {
        timer = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                moveBall((int) view.getGame().getBall().getX(), (int) view.getGame().getBall().getY());
            }
        });

        timer.start();
        System.out.println("TIMER STARTED");
    }

    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

}
