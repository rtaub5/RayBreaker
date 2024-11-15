package brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.controller.GameController;
import brickbreaker.view.GameComponent;
import brickbreaker.view.GameFrame;

import java.util.*;

public class AI {
    private final GameFrame frame = new GameFrame();
    private final GameComponent component = new GameComponent();
    private final GameController controller = new GameController(frame, component);
    int count;

    public AI(int count) {
        this.count = count;
    }

    public ArrayList<NeuralNetwork> createNetworks() {
        ArrayList<NeuralNetwork> neuralNetworks = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            neuralNetworks.add(new NeuralNetwork(2, 2, 1));
        }

        return neuralNetworks;
    }


    public int play(NeuralNetwork neuralNetwork) {
        controller.startGame();

        while(controller.getGame().isInProgress()) {
            double[] input = new double[1];
            input[0] = controller.getGame().getBallToPaddleAngle();
            double[] answer = neuralNetwork.guess(input);

            if(answer[0] > answer[1]) {
                controller.movePaddleLeft();
            } else {
                controller.movePaddleRight();
            }
        }

        return controller.getGame().getScore();
    }

    public ArrayList<NeuralNetwork> getBestPerforming(TreeMap<Integer, NeuralNetwork> neuralNetworks) {
        List<Map.Entry<Integer, NeuralNetwork>> entries = new ArrayList<>(neuralNetworks.entrySet());
        ArrayList<NeuralNetwork> newList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            newList.add(entries.get(i).getValue());
        }

        return newList;
    }

    public void merge(List<NeuralNetwork> neuralNetworks) {
        ArrayList<NeuralNetwork> newNeuralNetworks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            NeuralNetwork parent1 = neuralNetworks.get(i % count);
            NeuralNetwork parent2 = neuralNetworks.get((i + 1) % count);

            NeuralNetwork merged = parent1.merge(parent2);
            merged.mutate(0.1);
            newNeuralNetworks.add(merged);
        }
    }


}
