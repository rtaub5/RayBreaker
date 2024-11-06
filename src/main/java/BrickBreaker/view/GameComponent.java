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
    private final int brickHeight = 20;
    private final int brickWidth = 40;
    private Color[] colors;
    Random rand = new Random();


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

        game = new Game();
        game.ball = new Ball(currX, currY, 15);
        game.bricks = new ArrayList<Brick>();
        game.paddle = new Paddle(250, paddleY, 120, 10);

        colors = new Color[]{Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.GREEN, Color.RED, Color.WHITE};

    }

    //TODO: move all this to be default game initialization in constructor?

    public Game getGame() {
        return game;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.WHITE);
        g2.draw(game.getPaddle());

        g.setColor(Color.RED);
        g2.draw(game.getBall());

        for (int i = 0; i < game.getBricks().size(); i++) {
            Brick curr = game.getBricks().get(i);
            g.setColor(colors[rand.nextInt(colors.length)]);
            g2.draw(curr);
        }
    }
}

