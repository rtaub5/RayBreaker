package BrickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private Timer timer;
    private GameComponent component;

    // Constructor
    public GameFrame() {
        component = new GameComponent();
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

        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This method will be called every second
                component.moveBall();
                component.repaint();
            }
        });

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
                //controller.startTimer(timer);
            }
        });

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //controller.stopTimer(timer);
            }
        });
    }


}
