package brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.view.GameFrame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {

        AI ai = new AI(1000);
        ArrayList<NeuralNetwork> neuralNetworks = ai.createNetworks();

        for (int i = 0; i < 1000; i++) {
            neuralNetworks = ai.learnGame(neuralNetworks);
        }

    }


}
