package Vue;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Objects;

public class ViewCommand implements PropertyChangeListener {
    private final JFrame frame;
    private final JButton initButton;
    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton stepbutton;
    private final JSlider speedSlider;
    private final JLabel turnLabel;

    private final JLabel pointlabel;
    private final Game simpleGame;

    public ViewCommand(Game simpleGame) {
        this.simpleGame = simpleGame;

        frame = new JFrame("Commandes");
        frame.setSize(new Dimension(600, 400));
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initButton = new JButton("Initialiser");
        startButton = new JButton("Lancer");
        pauseButton = new JButton("Pause");
        stepbutton = new JButton("Step");

        initButton.setIcon(new ImageIcon("./Icons/icon_restart.png"));
        startButton.setIcon(new ImageIcon("./Icons/icon_run.png"));
        stepbutton.setIcon(new ImageIcon("./Icons/icon_step.png"));
        pauseButton.setIcon(new ImageIcon("./Icons/icon_pause.png"));

        pauseButton.setEnabled(false);
        initButton.setEnabled(false);

        speedSlider = new JSlider(1, 10, 5);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        turnLabel = new JLabel("Tour: 0");

        pointlabel = new JLabel("Point : " + simpleGame.Points);


        frame.add(initButton);
        frame.add(startButton);
        frame.add(pauseButton);
        frame.add(stepbutton);
        frame.add(speedSlider);
        frame.add(turnLabel);
        frame.add(pointlabel);

        initButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simpleGame.init();

            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simpleGame.launch();
                pauseButton.setEnabled(true);
                initButton.setEnabled(true);
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simpleGame.pause();
            }
        });

        stepbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simpleGame.step();
            }
        });

        ActionListener updateLabel = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                turnLabel.setText("Tour: " + simpleGame.getTurn());
                simpleGame.setTime(1000 / speedSlider.getValue());
            }
        };

        ActionListener updatepoint = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pointlabel.setText("Points : " + simpleGame.Points);
            }
        };

        new Timer(100, updateLabel).start();
        new Timer(100, updatepoint).start();

        frame.setVisible(true);

        simpleGame.addPropertyChangeListener(this);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("turn".equals(evt.getPropertyName())) {
            turnLabel.setText("Tour: " + evt.getNewValue());
        }
    }
}
