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

    //TODO: move all this to be default game initialization in constructor?






    public Game getGame() {
        return game;
    }



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

        g.setColor(Color.BLUE);
        for (int i = 0; i < game.getBricks().size(); i++) {
            Brick curr = game.getBricks().get(i);
            g.drawRect((int) curr.getX(), (int) curr.getY(), brickWidth, brickHeight);
        }
    }
}

