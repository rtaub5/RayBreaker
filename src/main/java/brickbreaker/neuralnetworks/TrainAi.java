package brickbreaker.neuralnetworks;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.model.Game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TrainAi {
    int agents;
    Simulation sim;
    Random rand = new Random();
    private NeuralNetworkScore topNetwork;
    private int topScore = Integer.MIN_VALUE;

    public TrainAi(int agents, Simulation sim) {
        this.agents = agents;
        this.sim = sim;
    }

    public ArrayList<NeuralNetwork> createNetworks() {
        ArrayList<NeuralNetwork> neuralNetworks = new ArrayList<>();
        for(int i = 0; i < agents; i++) {
            neuralNetworks.add(new NeuralNetwork(2, 2, 4, 2));
        }
        return neuralNetworks;
    }

    public NeuralNetworkScore play(NeuralNetwork neuralNetwork, long seed) {
        sim.getGame().resetRand(seed);
        sim.getGame().start();
        for (int count = 0; count < 10000 && sim.advance(neuralNetwork); count++) {
        }

        return new NeuralNetworkScore(neuralNetwork, sim.getGame().getScore(), seed);
    }

    public ArrayList<NeuralNetwork> learnGame(ArrayList<NeuralNetwork> neuralNetworks) {
        List<NeuralNetworkScore> networkScores = new ArrayList<>();

        for (NeuralNetwork neuralNetwork : neuralNetworks) {
            long seed = new Random(System.currentTimeMillis()).nextLong();
            NeuralNetworkScore neuralNetworkScore = play(neuralNetwork, seed);
            networkScores.add(neuralNetworkScore);
        }

        ArrayList<NeuralNetwork> newNeuralNetworks = getBestPerforming(networkScores);
        newNeuralNetworks = merge(newNeuralNetworks);
        return newNeuralNetworks;
    }

    private ArrayList<NeuralNetwork> getBestPerforming(List<NeuralNetworkScore> networkScores)
    {
        // sorts list by scores in descending order
        networkScores.sort(Comparator.comparingDouble(NeuralNetworkScore::getScore).reversed());
        ArrayList<NeuralNetwork> topNeuralNetworks = new ArrayList<>();

        NeuralNetworkScore ns = networkScores.get(0);

        if (topScore < ns.getScore()) {
            topNetwork = ns;
        }
        System.out.println("Score: " + ns.getScore() + " Seed: " + ns.getSeed());

        for (int i = 0; i < 10; i++) {
            if (networkScores.get(i).getScore() > 0)
            {
                topNeuralNetworks.add(networkScores.get(i).getNeuralNetwork());
            }
        }
        return topNeuralNetworks;
    }

    private ArrayList<NeuralNetwork> merge(List<NeuralNetwork> newNeuralNetworks) {
        ArrayList<NeuralNetwork> nextGen = new ArrayList<>();
        for (int i = 0; i < agents; i++) {
            NeuralNetwork parent1 = newNeuralNetworks.get(rand.nextInt(newNeuralNetworks.size()));
            NeuralNetwork parent2 = newNeuralNetworks.get(rand.nextInt(newNeuralNetworks.size()));
            NeuralNetwork merged = parent1.merge(parent2);
            merged.mutate(0.1);
            nextGen.add(merged);
        }


        return nextGen;
    }

    public static void main(String[] args)
    {
        Simulation simulation = new Simulation(new Game(new Random()));
        TrainAi trainAi = new TrainAi(1000, simulation);
        ArrayList<NeuralNetwork> list = trainAi.createNetworks();
        for (int i = 0; i < 5; i++) {
            list = trainAi.learnGame(list);
        }
        NeuralNetwork winner = list.get(0);

        System.out.println();
        System.out.println("Winner: " + winner.toString());

        trainAi.topNetwork.getNeuralNetwork().writeToFile("trained.json");
    }
}
