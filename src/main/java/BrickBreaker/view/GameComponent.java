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

public class GameComponent extends JComponent {
  //  private Ball ball;
  //  private ArrayList<Brick> bricks;
  //   private Paddle paddle;
     private Game game;
     private final int paddleY = 415;
     private int currX = 300;
     private int currY = 400;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Paddle
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int)game.paddle.getX(), (int)game.paddle.getY(), (int)game.paddle.getWidth(), (int)game.paddle.getHeight()); // Paddle at the bottom
        // Draw ball
        g.setColor(Color.RED);
        g.fillOval((int)game.ball.getX(), (int)game.ball.getY(), (int)game.ball.getWidth(), (int)game.ball.getHeight()); // Ball
        // Draw bricks
        // - randomize number of bricks between 5 and 10
        // - randomize their location somewhere above the paddle
    }

}



