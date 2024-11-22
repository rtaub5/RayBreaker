package brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.model.Game;
import brickbreaker.neuralnetworks.AI;
import brickbreaker.view.GameComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AIControllerNoGui
{
    private Game model;
    private GameComponent view;
    private AI ai;
    private boolean isRunning = false;
    private Timer timer;
    private Timer paddleTimer;
    ArrayList<NeuralNetwork> neuralNetworks;
    NeuralNetwork lastNeuralNetwork;

    public AIControllerNoGui(Game game, GameComponent component, AI ai)
    {
        model = game;
        view = component;
        this.ai = ai;
        neuralNetworks = ai.createNetworks();
        learn();
    }

    private void learn()
    {
        for (int i = 0; i < 1000; i++) {
            neuralNetworks = ai.learnGame(neuralNetworks);
        }
        lastNeuralNetwork = neuralNetworks.get(0);
    }

    public Game getGame() {
        return model;
    }

    private void initializeGameState() {
        System.out.println("INITIALIZING GAME STATE");
        model.getBall().setX(200);
        model.getBall().setY(200);
        initializePaddle();
        startPaddleTimerForAi();
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


    private void startPaddleTimerForAi()
    {
        paddleTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int curr = ai.movePaddle(lastNeuralNetwork);
                if (curr == 1)
                {
                    int x =  model.getPaddle().moveLeft();
                    //    model.paddle.x = x;
                }
                else
                {
                    int x =  model.getPaddle().moveRight();
                    // model.paddle.x = x;
                }

            }
        });
        paddleTimer.start();
    }

    private void startPaddleTimer() {
        if (paddleTimer == null || !paddleTimer.isRunning()) {
            paddleTimer = new Timer(5, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double paddleX = model.getPaddle().getX();
                    if (paddleX <= 0 || paddleX + model.getPaddle().getWidth() >= view.getWidth()) {
                        model.getPaddle().changeDirection();
                    }
                    model.getPaddle().move();
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
    }

    public void gameOver() {
        //  stop timers, display popup
        isRunning = false;
        stopTimer();
        view.repaint();
        System.out.println("Game over.");
      //  int restart = JOptionPane.showConfirmDialog(model, "Would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
       // if (restart == JOptionPane.YES_OPTION) {
      //      startGame();
      //  }
   //     else
   //     {
            stopTimer();
            paddleTimer.stop();
      //  }
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
