package Vue;

import Modele.PacmanGame;
import Agent.PositionAgent;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class ViewPacmanGame implements PropertyChangeListener {

    private final JFrame frame;
    private JLabel label;

    private final PanelPacmanGame panel;
    private final PacmanGame game;

    public ViewPacmanGame(PacmanGame PacmanGame) {
        this.game = PacmanGame;

        panel = new PanelPacmanGame(game.getMaze());

        frame = new JFrame("Pacman Game");
        frame.setSize(game.getMaze().getSizeX() * 50, game.getMaze().getSizeY() * 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel);
        frame.setVisible(true);

        game.addPropertyChangeListener(this);
    }

    public void update(){

        ArrayList<PositionAgent> pacmans_pos = game.GetPacmanPos();

        panel.setPacmans_pos(pacmans_pos);

        panel.setGhosts_pos(game.GetGhostPos());

        panel.setGhostsScarred(game.getCapsuleTimer() > 0);

        panel.repaint();

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("turn".equals(evt.getPropertyName())) {
            update();
        }
    }
}
