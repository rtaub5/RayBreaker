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

    public void movePaddle(int keyCode) { // send
        if (keyCode == KeyEvent.VK_RIGHT) {
            game.getPaddle().setDirection(true);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            game.getPaddle().setDirection(false);
        }
        startPaddleTimer();
    }

    public void stopMovingPaddle () {
    model.paddleTimer.stop();
    }

    private void startPaddleTimer() {
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


    public void moveBall(int x, int y) {
    game.nextMove(x, y);
    if (!game.isInProgress())
    {
        gameOver();
    }
    view.repaint();
    }

    public void gameOver() {
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

    public void startTimer() {
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

    public void stopTimer() {
        if (model.timer != null && model.timer.isRunning()) {
            model.timer.stop();
        }
    }

}
