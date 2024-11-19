package brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.neuralnetworks.AI;
import brickbreaker.view.GameFrame;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


       // GameFrame game = new GameFrame();
       // game.setVisible(true);

        AI ai = new AI(1000);
        ArrayList<NeuralNetwork> neuralNetworks = ai.createNetworks();

        for (int i = 0; i < 1000; i++) {
            neuralNetworks = ai.learnGame(neuralNetworks);
        }


    }
}
