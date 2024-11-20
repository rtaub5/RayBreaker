package brickbreaker.neuralnetworks;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.GameController;
import brickbreaker.model.Game;
import brickbreaker.view.GameComponent;

import java.util.*;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

public class AI {
    // private final GameFrame frame = new GameFrame();
    private final GameComponent component = new GameComponent();
    private final Game game = new Game();
    private final GameController controller = new GameController(game, component);

    private Random random;
    int count;

    public AI(int count) {
        this.count = count;
    }

    public ArrayList<NeuralNetwork> createNetworks() {
        ArrayList<NeuralNetwork> neuralNetworks = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            neuralNetworks.add(new NeuralNetwork(2, 10, 1));
        }

        return neuralNetworks;
    }


    private int play(NeuralNetwork neuralNetwork) {
        controller.startGame();

        while(controller.getGame().isInProgress()) {
            double[] input = new double[1];
            input[0] = controller.getGame().getBallToPaddleAngle();
            double[] answer = neuralNetwork.guess(input);

            if(answer[0] > answer[1]) {
                controller.movePaddle(VK_LEFT);
            } else {
                controller.movePaddle(VK_RIGHT);
            }
        }

        return controller.getGame().getScore();
    }
    // Treemap has to become a list that can be sorted. We DO want duplicates, even if two neural networks get the same score
    // integer of treemap is score, we need to make a datastructure that combines scores and neural network
  /*  private ArrayList<NeuralNetwork> getBestPerforming(TreeMap<Integer, NeuralNetwork> neuralNetworks) {

       List<Map.Entry<Integer, NeuralNetwork>> entries = new ArrayList<>(neuralNetworks.entrySet());
        ArrayList<NeuralNetwork> newList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            newList.add(entries.get(i).getValue());
        }

        return newList;
    } */
    private ArrayList<NeuralNetwork> getBestPerforming(List<Integer> scores, List<List<NeuralNetwork>>groupedNN)
    {
        List<Integer> sortedScores = new ArrayList<>(scores);
        ArrayList<NeuralNetwork> newNeuralNetworks = new ArrayList<>();
        Collections.sort(sortedScores);
        for (int ix = 0; ix < 10; ++ix)
        {
            int currHighestScore = sortedScores.get(ix);
            int indexHighestScore = scores.get(currHighestScore);
            newNeuralNetworks.addAll(groupedNN.get(indexHighestScore));
        }
        return newNeuralNetworks;
    }

    // make yael's changes from screenshot
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
     //   TreeMap<Integer, NeuralNetwork> scores = new TreeMap<>(Comparator.reverseOrder());
        List<Integer> scores = new ArrayList<>();
        List<List<NeuralNetwork>> groupedNN = new ArrayList<>();

        for (int i = 0; i < neuralNetworks.size(); i++) {
            //   scores.put(play(neuralNetworks.get(i)), neuralNetworks.get(i));
            //  scores.get(i).set(0, play(neuralNetworks.get(i)));
            //  scores.get(i).set(1, neuralNetworks.get(i));
            int currScore = play(neuralNetworks.get(i));
            int currIndex = scores.get(currScore);
            if (currIndex == -1)
            {
                scores.add(currScore);
                List<NeuralNetwork> network = new ArrayList<>();
                network.add(neuralNetworks.get(i));
                groupedNN.add(network);
            }
            else
            {
                List<NeuralNetwork> networks = groupedNN.get(currIndex);
                networks.add(neuralNetworks.get(i));
                groupedNN.set(currIndex, networks);
            }
        }

       // ArrayList<NeuralNetwork> newNeuralNetworks = getBestPerforming(scores);
        ArrayList<NeuralNetwork> newNeuralNetworks = getBestPerforming(scores, groupedNN);
        merge(newNeuralNetworks);
        return newNeuralNetworks;

    }

}
