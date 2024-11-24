package brickbreaker.neuralnetworks;

import basicneuralnetwork.NeuralNetwork;
import brickbreaker.model.Game;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AI {
    private final Game game = new Game();
    private ArrayList<Integer[]> scoresPerGen = new ArrayList<>();

    private Random random = new Random();
    private boolean isRunning = false;
    private Timer timer;
    private Timer paddleTimer;
    int count;
    int rounds = 10000;
    int testRounds = 10;

    public AI(int count) {
        this.count = count;

    }

    public ArrayList<NeuralNetwork> createNetworks() {
        ArrayList<NeuralNetwork> neuralNetworks = new ArrayList<>();

        for(int i = 0; i < count; i++) {
           // neuralNetworks.add(new NeuralNetwork(1, 2, 4, 2));
            neuralNetworks.add(new NeuralNetwork(2, 2, 4, 2));
        }

        return neuralNetworks;
    }

    public ArrayList<Integer[]> getScoresPerGen() {
        return scoresPerGen;
    }


    public int movePaddle(NeuralNetwork neuralNetwork) {
        int retVal = 0;
        double[] input = new double[2];
        input[0] = game.getBallToPaddleAngle();
        input[1] = game.ball.y;
        double[] answer = neuralNetwork.guess(input);

        if (answer[0] > answer[1]) {
            // System.out.println("Move paddle left " + game.getPaddle().getX() + " " + game.getPaddle().getY());
            //game.getPaddle().moveLeft();
            return 1;
        } else  {
            //System.out.println("Move paddle right " + game.getPaddle().getX() + " " + game.getPaddle().getY());
            //game.getPaddle().moveRight();
            return 0;
        }

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
        startGame();
     //   for (int i = 0; i < rounds; i++) {
        for (int i = 0; i < 10_000 && game.isInProgress(); i++) {
          //     moveBall((int) game.getBall().getX(), (int) game.getBall().getY());
                game.nextMove();
             //   movePaddleForAi(neuralNetwork);
                int paddleDecision = movePaddle(neuralNetwork);
                if(paddleDecision == 1) { //&& game.paddle.legalMove()) {
                    game.getPaddle().moveLeft();
                }
                else if (paddleDecision == 0)
                { //&& game.paddle.legalMove()) {
                    game.getPaddle().moveRight();
                }
        }

        System.out.println(game.getScore());
        return game.getScore();
    }

    private void movePaddleForAi(NeuralNetwork currNeuralNetwork)
    {
        paddleTimer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int curr = movePaddle(currNeuralNetwork);
                if (curr == 1)
                {
                    int x =  game.getPaddle().moveLeft();
                    //    model.paddle.x = x;
                }
                else
                {
                    int x =  game.getPaddle().moveRight();
                    // model.paddle.x = x;
                }


            }
        });
        paddleTimer.start();

    }

    private ArrayList<NeuralNetwork> getBestPerforming(List<NeuralNetworkScore> networkScores)
    {
        networkScores.sort(Comparator.comparingDouble(NeuralNetworkScore::getScore).reversed());
        ArrayList<NeuralNetwork> topNeuralNetworks = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            topNeuralNetworks.add(networkScores.get(i).getNeuralNetwork());
        }

        return topNeuralNetworks;
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
        List<NeuralNetworkScore> networkScores = new ArrayList<>();

        for (NeuralNetwork neuralNetwork : neuralNetworks)
        {
            System.out.println("new play");
            int currScore = play(neuralNetwork);
            NeuralNetworkScore neuralNetworkScore = new NeuralNetworkScore(neuralNetwork, currScore);
            networkScores.add(neuralNetworkScore);
        }

    //    scoresPerGen.add(scores.toArray(new Integer[scores.size()]));
        ArrayList<NeuralNetwork> newNeuralNetworks = getBestPerforming(networkScores);
        newNeuralNetworks = merge(newNeuralNetworks);
        return newNeuralNetworks;

    }



    public double getAvgScorePerGen(int gen) {
        double sum = 0;

        for (int i = 0; i < scoresPerGen.get(gen).length; i++) {
            sum += scoresPerGen.get(gen)[i];
        }

        return sum / scoresPerGen.get(gen).length;
    }

//    public void moveBall(int x, int y) {
//        System.out.println("MOVE BALL: " + x + ", " + y);
//        game.nextMove();
//
//
//    }

    public void startGame() {
      //  System.out.println("STARTING GAME");
        game.restartGame();
        if (!isRunning) {
            initializeGameState();
            startTimer();
            isRunning = true;
        } else {
            resumeGame();
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
   //     System.out.println("TIMER STARTED");
    }

    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    private void resumeGame() {
    //    initializePaddle();
        startTimer();
    }

    private void initializeGameState() {
        game.getBall().setX(200);
        game.getBall().setY(200);
    }

    public static void main(String[] args)
    {
        AI ai = new AI(1000);
        ArrayList<NeuralNetwork> list = ai.createNetworks();
        for (int i=0 ; i< 5; i++)
        {
            list = ai.learnGame(list);
        }
        NeuralNetwork winner = list.get(0);
    }


}
