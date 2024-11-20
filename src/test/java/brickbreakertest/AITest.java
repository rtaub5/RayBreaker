package brickbreakertest;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.neuralnetworks.AI;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class AITest {

    @Test
    public void learn() {
        AI ai = new AI(1000);
        ArrayList<NeuralNetwork> neuralNetworks = ai.createNetworks();

        for (int i = 0; i < 25; i++) {
            neuralNetworks = ai.learnGame(neuralNetworks);
        }

        ArrayList<Integer[]> scores = ai.getScoresPerGen();

        double scoreFirstGen = ai.getAvgScorePerGen(0);
        double scoreLastGen = ai.getAvgScorePerGen(9);

        assertTrue(scoreFirstGen < scoreLastGen);

    }
}