package brickbreaker.neuralnetworks;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.GameController;
import brickbreaker.model.Game;
import brickbreaker.view.GameComponent;

import java.util.*;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

public class AI {
    private final Game game = new Game();
    private ArrayList<Integer[]> scoresPerGen = new ArrayList<>();

    private Random random = new Random();
    int count;
    int rounds = 10000;

    public AI(int count) {
        this.count = count;
    }

    public ArrayList<NeuralNetwork> createNetworks() {
        ArrayList<NeuralNetwork> neuralNetworks = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            neuralNetworks.add(new NeuralNetwork(1, 2, 4, 2));
        }

        return neuralNetworks;
    }

    public ArrayList<Integer[]> getScoresPerGen() {
        return scoresPerGen;
    }

    private int play(NeuralNetwork neuralNetwork) {

        /*while(game.isInProgress()) {
           // System.out.println("Move paddle " + game.getPaddle().getX() + ", " + game.getPaddle().getY());
           // System.out.println("move"); uncommenting either statement leads to statement printing
           //infinitely? no other debug statements print.
            double[] input = new double[1];
            input[0] = controller.getGame().getBallToPaddleAngle();
            double[] answer = neuralNetwork.guess(input);

            if(answer[0] > answer[1]) {
               // System.out.println("Move paddle left " + game.getPaddle().getX() + " " + game.getPaddle().getY());
                controller.movePaddle(VK_LEFT);
            } else {
                //System.out.println("Move paddle right " + game.getPaddle().getX() + " " + game.getPaddle().getY());
                controller.movePaddle(VK_RIGHT);
            }
        } */
        for (int i = 0; i < rounds; i++) {
            double[] input = new double[1];
            input[0] = game.getBallToPaddleAngle();
            double[] answer = neuralNetwork.guess(input);

            if (answer[0] > answer[1]) {
                // System.out.println("Move paddle left " + game.getPaddle().getX() + " " + game.getPaddle().getY());
                game.getPaddle().moveLeft();
            } else {
                //System.out.println("Move paddle right " + game.getPaddle().getX() + " " + game.getPaddle().getY());
                game.getPaddle().moveRight();
            }

        }

        System.out.println(game.getScore());
        return game.getScore();
    }

    private ArrayList<NeuralNetwork> getBestPerforming(List<Integer> scores, List<List<NeuralNetwork>>groupedNN)
    {
        List<Integer> sortedScores = new ArrayList<>(scores); //all scores
        ArrayList<NeuralNetwork> newNeuralNetworks = new ArrayList<>(); //best performing networks
        Collections.sort(sortedScores, Collections.reverseOrder());
        for (int ix = 0; ix < sortedScores.size(); ++ix)
        {
            int currHighestScore = sortedScores.get(ix);
            int indexHighestScore = scores.get(currHighestScore);
            newNeuralNetworks.addAll(groupedNN.get(indexHighestScore));
        }
        return newNeuralNetworks;
    }

    private ArrayList<NeuralNetwork> merge(List<NeuralNetwork> neuralNetworks) {
        ArrayList<NeuralNetwork> newNeuralNetworks = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            NeuralNetwork parent1 = neuralNetworks.get(random.nextInt(neuralNetworks.size()));
            NeuralNetwork parent2 = neuralNetworks.get(random.nextInt(neuralNetworks.size()));

            NeuralNetwork merged = parent1.merge(parent2);
            merged.mutate(0.1);
            newNeuralNetworks.add(merged);
        }

        return newNeuralNetworks;
    }

    public ArrayList<NeuralNetwork> learnGame(ArrayList<NeuralNetwork> neuralNetworks) {
        List<Integer> scores = new ArrayList<>();
        List<List<NeuralNetwork>> groupedNN = new ArrayList<>();

        for (int i = 0; i < neuralNetworks.size(); i++) {
            System.out.println("new play");
            int currScore = play(neuralNetworks.get(i));

            if (scores.size() <= currScore)
            {
                scores.add(currScore);
                List<NeuralNetwork> network = new ArrayList<>();
                network.add(neuralNetworks.get(i));
                groupedNN.add(network);
            }
            else
            {
                int currIndex = scores.get(currScore);
                List<NeuralNetwork> networks = groupedNN.get(currIndex);
                networks.add(neuralNetworks.get(i));
                groupedNN.set(currIndex, networks);
            }
        }

        scoresPerGen.add(scores.toArray(new Integer[scores.size()]));
        ArrayList<NeuralNetwork> newNeuralNetworks = getBestPerforming(scores, groupedNN);
        merge(newNeuralNetworks);
        return newNeuralNetworks;

    }

    public double getAvgScorePerGen(int gen) {
        double sum = 0;

        for (int i = 0; i < scoresPerGen.get(gen).length; i++) {
            sum += scoresPerGen.get(gen)[i];
        }

        return sum / scoresPerGen.get(gen).length;
    }

}
