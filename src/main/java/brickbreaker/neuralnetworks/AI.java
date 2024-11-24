package brickbreaker.neuralnetworks;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.model.Direction;
import brickbreaker.model.Game;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AI {
    private Game game;
    private ArrayList<Integer[]> scoresPerGen = new ArrayList<>();

    private Random random = new Random();
    private boolean isRunning;
    private Timer timer;
    private Timer paddleTimer;
    int count;
    int rounds = 10000;
    int testRounds = 10;

    public AI(int count) {
        this.count = count;
        isRunning = false;
    }

    public ArrayList<Integer[]> getScoresPerGen() {
        return scoresPerGen;
    }

    public ArrayList<NeuralNetwork> createNetworks() {
        ArrayList<NeuralNetwork> neuralNetworks = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            neuralNetworks.add(new NeuralNetwork(1, 2, 4, 2));
            // neuralNetworks.add(new NeuralNetwork(2, 2, 4, 2));
        }
        return neuralNetworks;
    }

    public ArrayList<NeuralNetwork> learnGame(ArrayList<NeuralNetwork> neuralNetworks) {
        List<NeuralNetworkScore> networkScores = new ArrayList<>();

        for (NeuralNetwork neuralNetwork : neuralNetworks) {
            int currScore = play(neuralNetwork);
            NeuralNetworkScore neuralNetworkScore = new NeuralNetworkScore(neuralNetwork, currScore);
            networkScores.add(neuralNetworkScore);
        }

        //    scoresPerGen.add(scores.toArray(new Integer[scores.size()]));
        ArrayList<NeuralNetwork> newNeuralNetworks = getBestPerforming(networkScores);
        newNeuralNetworks = merge(newNeuralNetworks);
        return newNeuralNetworks;
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
//        game.restartGame();
//        game.getBall().setX(200);
//        game.getBall().setY(200);
        game = new Game();
        startGame();
        //   for (int i = 0; i < rounds; i++) {
        for (int i = 0; i < 10_000 && game.started(); i++) {
            game.nextMove();
            //   movePaddleForAi(neuralNetwork);
            int paddleDecision = movePaddle(neuralNetwork);
            if (paddleDecision == 0) {
                game.getPaddle().setDirection(Direction.LEFT);
            } else {
                game.getPaddle().setDirection(Direction.RIGHT);
            }
            game.getPaddle().move();
        }
        System.out.println("Score: " + game.getScore());
        return game.getScore();
    }

    public int movePaddle(NeuralNetwork neuralNetwork) {
        double[] input = new double[1];
        input[0] = getBallToPaddleAngle();
        double[] answer = neuralNetwork.guess(input);

        if (answer[0] > answer[1]) {
            // move paddle left
            return 0;
        } else  {
            // move paddle right
            return 1;
        }
    }

    private void movePaddleForAi(NeuralNetwork currNeuralNetwork)
    {
        paddleTimer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int curr = movePaddle(currNeuralNetwork);
                if (curr == 1) {
                    game.getPaddle().setDirection(Direction.LEFT);
                } else {
                    game.getPaddle().setDirection(Direction.RIGHT);
                }
                game.getPaddle().move();
            }
        });
        paddleTimer.start();

    }

    private ArrayList<NeuralNetwork> getBestPerforming(List<NeuralNetworkScore> networkScores)
    {
        // sorts list by scores in descending order
        networkScores.sort(Comparator.comparingDouble(NeuralNetworkScore::getScore).reversed());
        ArrayList<NeuralNetwork> topNeuralNetworks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            topNeuralNetworks.add(networkScores.get(i).getNeuralNetwork());
        }
        return topNeuralNetworks;
    }

    private ArrayList<NeuralNetwork> merge(List<NeuralNetwork> newNeuralNetworks) {
        ArrayList<NeuralNetwork> nextGen = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            NeuralNetwork parent1 = newNeuralNetworks.get(random.nextInt(newNeuralNetworks.size()));
            NeuralNetwork parent2 = newNeuralNetworks.get(random.nextInt(newNeuralNetworks.size()));
            NeuralNetwork merged = parent1.merge(parent2);
            merged.mutate(0.1);
            nextGen.add(merged);
        }
        return nextGen;
    }

    public void startGame() {
        game.start();
        if (!isRunning) {
            startTimer();
            isRunning = true;
        }
    }

    public void startTimer() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  moveBall((int) game.getBall().getX(), (int) game.getBall().getY());
                game.nextMove();
            }
        });
        timer.start();
    }

    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    // For AI - gets angle between ball center and paddle center
    public double getBallToPaddleAngle() {
        double deltaX = game.getPaddle().getX() - game.getBall().getX();
        double deltaY = game.getPaddle().getY() - game.getBall().getY();
        return Math.toDegrees(Math.atan2(deltaY, deltaX));
    }

    public double getAvgScorePerGen(int gen) {
        double sum = 0;

        for (int i = 0; i < scoresPerGen.get(gen).length; i++) {
            sum += scoresPerGen.get(gen)[i];
        }

        return sum / scoresPerGen.get(gen).length;
    }

    public static void main(String[] args)
    {
        AI ai = new AI(10);
        ArrayList<NeuralNetwork> list = ai.createNetworks();
        for (int i = 0; i < 5; i++) {
            list = ai.learnGame(list);
        }
        NeuralNetwork winner = list.get(0);

        System.out.println();
        System.out.println("Winner: " + winner.toString());
    }
}
