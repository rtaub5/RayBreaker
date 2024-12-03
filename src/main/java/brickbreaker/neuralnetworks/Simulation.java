package brickbreaker.neuralnetworks;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.model.Game;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Simulation {
    private Game game;
    private boolean isRunning;

    public Simulation(Game game) {
        this.game = game;
        isRunning = false;
    }

    public Game getGame() {
        return game;
    }

    public boolean advance(NeuralNetwork neuralNetwork) {
        game.makeMove(neuralNetwork);
        game.nextMove();
        return game.started();
    }
}
