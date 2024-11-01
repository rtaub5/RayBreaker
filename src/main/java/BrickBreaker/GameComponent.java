package BrickBreaker;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class GameComponent extends JComponent {
    private Ball ball;
    private ArrayList<Brick> bricks;
    private Paddle paddle;
    private final int paddleY = 415;
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
        ball = new Ball(300, 400, 15, 15);
        paddle = new Paddle(250, paddleY, 120, 10);
        bricks = new ArrayList<>();
        generateBricks();
    }

    private void generateBricks() {
        Random random = new Random();
        int numBricks = 5 + random.nextInt(6); // random num between 5 and 15
        for (int i = 0; i < numBricks; i++) {
            // Randomize location within upper section of component
            int x = 50 + random.nextInt(500);
            int y = random.nextInt(paddleY - 75); // random num between 0 and 350
            bricks.add(new Brick(x, y, 80, 35));
        }
    }

    public void moveBall() {

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
        g.fillRect((int)paddle.getX(), (int)paddle.getY(), (int)paddle.getWidth(), (int)paddle.getHeight()); // Paddle at the bottom

        // Draw ball
        g.setColor(Color.RED);
        g.fillOval((int)ball.getX(), (int)ball.getY(), (int)ball.getWidth(), (int)ball.getHeight()); // Ball

        // Draw bricks
        g.setColor(Color.blue);
        for (Brick b : bricks) {
            g.fillRect(b.x, b.y, b.width, b.height);
        }

        // - randomize number of bricks between 5 and 10
        // - randomize their location somewhere above the paddle
    }

}



