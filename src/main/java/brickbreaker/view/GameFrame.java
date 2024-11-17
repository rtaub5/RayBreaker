package brickbreaker.view;

import brickbreaker.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {
    public Timer timer;
    public Timer paddleTimer;
    private GameComponent component;
    private GameController controller;

    // Constructor
    public GameFrame() {
        component = new GameComponent();
        controller = new GameController(this, component);
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
        Font largeFont = new Font("SansSerif", Font.PLAIN, 35);
        Font largerFont = new Font("SansSerif", Font.PLAIN, 55);

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
        JButton play = new JButton("\u25B6"); // Unicode play symbol
        play.setPreferredSize(new Dimension(80, 50));
        JButton pause = new JButton("\u23F8"); // Unicode pause symbol
        pause.setPreferredSize(new Dimension(80, 50));

        // Set larger font to enlarge symbols
        play.setFont(largeFont);
        pause.setFont(largerFont);

        bottomPanel.add(play);
        bottomPanel.add(pause);
        buttonPanel.add(bottomPanel, BorderLayout.CENTER);
        pane.add(buttonPanel, BorderLayout.PAGE_END);
        pane.revalidate();
        pane.repaint();

        //add action listeners for buttons
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startGame();
            }
        });

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.stopTimer();
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

}
