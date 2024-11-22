package brickbreaker.neuralnetworks;


import basicneuralnetwork.NeuralNetwork;

public class NeuralNetworkScore
{
    private NeuralNetwork neuralNetwork;
    private int score;
    public NeuralNetworkScore(NeuralNetwork network, int score)
    {
        neuralNetwork = network;
        this.score = score;
    }

    public NeuralNetwork getNeuralNetwork()
    {
        return neuralNetwork;
    }

    public int getScore()
    {
        return score;
    }
}
