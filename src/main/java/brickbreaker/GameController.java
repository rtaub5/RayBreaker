package brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.model.Direction;
import brickbreaker.model.Game;
import brickbreaker.neuralnetworks.Simulation;
import brickbreaker.view.GameComponent;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class GameController {
    private final Simulation model;
    private final GameComponent view;
    private boolean isRunning;
    private Timer timer;
    private Timer paddleTimer;
    private NeuralNetwork network;

    public GameController(Simulation model, GameComponent view) {
        this.model = model;
        this.view = view;
        isRunning = false;
        try {
            network = NeuralNetwork.readFromFile("trained.json.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
    public Simulation getModel() { return model; }

    public void startGame() {
        model.start();
        view.setFocusable(true);
        view.requestFocusInWindow();
        startTimer();
    }

    public void movePaddle(int keyCode) {
        if (keyCode == KeyEvent.VK_RIGHT) {
            model.getPaddle().setDirection(Direction.RIGHT);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            model.getPaddle().setDirection(Direction.LEFT);
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
                    model.getPaddle().move();
                    view.repaint();
                }
            });
        }
        paddleTimer.start();
    }

    public void moveBall(int x, int y) {
        model.nextMove();
        model.makeMove(network);

        if (!model.started()) { // if game ended
            gameOver();
        }
        view.repaint();
    }

    public void gameOver() {
        stopTimer();
        view.repaint();
    }

    public void startTimer() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveBall((int) view.getGame().getBall().getX(), (int) view.getGame().getBall().getY());
            }
        });
        isRunning = true;
        timer.start();
    }

    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        isRunning = false;
    }
}
