package brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.neuralnetworks.AI;
import brickbreaker.view.GameFrame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {


     /*   GameFrame game = new GameFrame();
        game.setVisible(true);
*/
        AI ai = new AI(1000);
      ArrayList<NeuralNetwork> neuralNetworks = ai.createNetworks();
      for (int i = 0; i < 1000; i++) {
           neuralNetworks = ai.learnGame(neuralNetworks);
       }

     //   adinaMainChanges(); - Doesn't work because trying to access private methods. Try again later


    }


    private static void adinaMainChanges()
    {
        AI ai = new AI(1000);

        ArrayList<NeuralNetwork> neuralNetworks = ai.createNetworks();

        TreeMap<Integer, NeuralNetwork> scores = new TreeMap<>(Comparator.reverseOrder());

//        for (int i = 0; i < neuralNetworks.size(); i++) {
//            scores.put(ai.play(neuralNetworks.get(i)), neuralNetworks.get(i));
//        }

 //       ArrayList<NeuralNetwork> newNeuralNetworks = ai.getBestPerforming(scores);

     //   ai.merge(newNeuralNetworks);

        //repeat process
    }

}
