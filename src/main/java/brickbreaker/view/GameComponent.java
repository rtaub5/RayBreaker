package brickbreaker.view;

import brickbreaker.model.Ball;
import brickbreaker.model.Brick;
import brickbreaker.model.Game;
import brickbreaker.model.Paddle;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameComponent extends JComponent {
    private Game game;
    private final Color[] colors = new Color[]{Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.GREEN, Color.RED, Color.WHITE};
    Random rand = new Random();

    public GameComponent() {
        setBackground(Color.BLACK);
        this.setOpaque(true);
        setBorder(new LineBorder(Color.DARK_GRAY, 1));
        initializeGame();
    }

    public void initializeGame() {
        game = new Game();
        game.ball = new Ball(200, 200, 15);
        game.bricks = new ArrayList<Brick>();
        game.paddle = new Paddle(250, 415, 120, 10);
    }

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
        g.setColor(Color.CYAN);
        for (int i = 0; i < game.getBricks().size(); i++) {
            Brick curr = game.getBricks().get(i);
            g2.draw(curr);
        }
    }
}

