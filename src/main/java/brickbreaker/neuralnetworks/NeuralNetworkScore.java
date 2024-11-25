package brickbreaker.neuralnetworks;


import basicneuralnetwork.NeuralNetwork;

public class NeuralNetworkScore
{
    private NeuralNetwork neuralNetwork;
    private int score;

    private long seed;
    public NeuralNetworkScore(NeuralNetwork network, int score, long seed)
    {
        neuralNetwork = network;
        this.score = score;
        this.seed = seed;
    }

    public NeuralNetwork getNeuralNetwork()
    {
        return neuralNetwork;
    }

    public int getScore()
    {
        return score;
    }

    public long getSeed()
    {
        return seed;
    }
}
