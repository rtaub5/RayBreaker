package brickbreaker.view;

import brickbreaker.model.Ball;
import brickbreaker.model.Brick;
import brickbreaker.model.Paddle;
import brickbreaker.neuralnetworks.Simulation;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameComponent extends JComponent {
    private Simulation sim;
    private final Random rand = new Random(-1157497197998623888L);

    public GameComponent() {
        sim = new Simulation(rand);
        setBackground(Color.BLACK);
        this.setOpaque(true);
        setBorder(new LineBorder(Color.DARK_GRAY, 1));
    }

    public Simulation getGame() {
        return sim;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.WHITE);
        g2.draw(sim.getPaddle());

        g.setColor(Color.RED);
        g2.draw(sim.getBall());

        g.setColor(Color.CYAN);

        // int numBricks = sim.getBricks().size();
        int numBricks = 1;

        for (int i = 0; i < numBricks; i++) {
            // Brick brick = sim.getBricks().get(i);
            Brick brick = sim.getBrick();
            g2.draw(brick);
        }
    }
}

