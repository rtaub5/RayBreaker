package BrickBreaker.view;

import BrickBreaker.model.Ball;
import BrickBreaker.model.Brick;
import BrickBreaker.model.Game;
import BrickBreaker.model.Paddle;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;





public class GameComponent extends JComponent {
    //  private Ball ball;
    //  private ArrayList<Brick> bricks;
    //   private Paddle paddle;
    private Game game;
    private final int paddleY = 415;
    private int currX = 200;
    private int currY = 200;
    //TODO: put this in game?
    private final int brickNo = 20;
    Random rand = new Random();

    private final int brickHeight = 20;
    private final int brickWidth = 40;


    /*
    Has:
        - paddle: brick that moves with cursor along horizontal line only
            make this a separate class or just use brick?
        - ball
        - array of bricks (however many fit on specified portion of screen)

     */
    public GameComponent() {
        setBackground(Color.BLACK);
        this.setOpaque(true);

        setBorder(new LineBorder(Color.DARK_GRAY, 1)); //
        initMouseListener();

        game = new Game();
        game.ball = new Ball(currX, currY, 15);
        game.bricks = new ArrayList<Brick>();
        game.paddle = new Paddle(250, paddleY, 120, 10);

    }

    public void moveBall() {
        game.moveBall();
    }

    private void initMouseListener() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

//        setBorder(new LineBorder(Color.DARK_GRAY, 1));
//        game = new Game();
//        //TODO: move all this to be default game initialization in constructor?
//        game.setBall(new Ball(currX, currY, 15));
//        game.setBricks(new ArrayList<Brick>());
//        game.setPaddle(new Paddle(250, paddleY, 120, 10));





    public Game getGame() {
        return game;
    }

  /*  public void initializeBricks() {
        int rows = getWidth() / brickWidth;
        int cols = getHeight() / brickHeight / 2;

        for (int y = 0; y < cols; y++) {
            for(int x = 0; x < rows; x++) {
                game.addBrick(new Brick(x * brickWidth, y * brickHeight, brickWidth, brickHeight));
                x += rand.nextInt(brickWidth);
            }
            y += rand.nextInt(brickHeight);
        }

    } */



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Paddle
        g.setColor(Color.DARK_GRAY);

        g.fillRect((int) game.getPaddle().getX(), (int) game.getPaddle().getY(),
                (int) game.getPaddle().getWidth(), (int) game.getPaddle().getHeight()); // Paddle at the bottom
        // Draw ball
        g.setColor(Color.RED);
        g.fillOval((int) game.getBall().getX(), (int) game.getBall().getY(),
                (int) game.getBall().getWidth(), (int) game.getBall().getHeight()); // Ball
        // Draw bricks
        // - randomize number of bricks between 5 and 10
       // initializeBricks();
        // - randomize their location somewhere above the paddle

        g.setColor(Color.BLUE);
        for (int i = 0; i < game.getBricks().size(); i++) {
            Brick curr = game.getBricks().get(i);
            g.drawRect((int) curr.getX(), (int) curr.getY(), brickWidth, brickHeight);
        }
    }
}

