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
    Random rand = new Random(-7760456958180441894L);
    // private final Color[] colors = new Color[]{Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.GREEN, Color.RED, Color.WHITE};

    public GameComponent() {
        game = new Game(rand);
        setBackground(Color.BLACK);
        this.setOpaque(true);
        setBorder(new LineBorder(Color.DARK_GRAY, 1));
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
            Brick brick = game.getBricks().get(i);
            g2.draw(brick);
        }
    }
}

