package brickbreaker.view;

import brickbreaker.GameController;
import brickbreaker.neuralnetworks.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {
    private Simulation sim;
    private GameComponent component;
    private GameController controller;

    // Constructor
    public GameFrame() {
        component = new GameComponent();
        sim = component.getGame();
        controller = new GameController(sim, component);
        setFrame();
        setVisible(true);
    }

    public void setFrame() {
        setSize(600, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up Layout Manager
        setLayout(new BorderLayout());
        Container pane = getContentPane();

        // Create pre-set fonts
        Font mediumFont = new Font("SansSerif", Font.PLAIN, 25);

        // Set up Views
        JLabel title = new JLabel("Welcome to Brick Breaker!", JLabel.CENTER);
        title.setFont(mediumFont);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setVerticalTextPosition(SwingConstants.CENTER);
        pane.add(title, BorderLayout.PAGE_START);
        pane.add(component, BorderLayout.CENTER);

        // Panels to hold buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Buttons to play and pause
        JButton playAndPause = new JButton();
        playAndPause.setFont(new Font("SansSerif", Font.PLAIN, 20));
        playAndPause.setPreferredSize(new Dimension(100, 50));
        playAndPause.setText("Start");

        bottomPanel.add(playAndPause);
        buttonPanel.add(bottomPanel, BorderLayout.CENTER);
        pane.add(buttonPanel, BorderLayout.PAGE_END);
        pane.revalidate();
        pane.repaint();

        //add action listeners for buttons
        playAndPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sim.started()) {
                    if (sim.getRounds() == 0) {
                        controller.startGame();
                        sim.nextRound();
                        setPauseButton(playAndPause);
                    }
                    else { // game is over
                        askToReplay(playAndPause);
                        sim.setRounds(0);
                    }
                } else if (controller.isRunning()) {
                    controller.stopTimer();
                    setPlayButton(playAndPause);
                } else {
                    component.requestFocusInWindow();
                    controller.startTimer();
                    setPauseButton(playAndPause);
                }
            }
        });

        KeyListener keyListener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                controller.movePaddle(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                controller.stopMovingPaddle();
            }
        };

        component.addKeyListener(keyListener);
    }

    public void setPlayButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.PLAIN, 35));
        button.setPreferredSize(new Dimension(80, 50));
        button.setText("\u25B6"); // Unicode play symbol
    }

    public void setPauseButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.PLAIN, 55));
        button.setPreferredSize(new Dimension(80, 50));
        button.setText("\u23F8"); // Unicode pause symbol
    }

    public void askToReplay (JButton button) {
        button.setFont(new Font("SansSerif", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(200, 50));
        button.setText("Play again?");
    }
}
