package Vue;

import Modele.Game;
import Modele.PacmanGame;
import Modele.PositionAgent;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class ViewPacmanGame implements PropertyChangeListener {

    private JFrame frame;
    private JLabel label;

    private PanelPacmanGame panel;
    private PacmanGame game;

    public ViewPacmanGame(PacmanGame PacmanGame) {
        this.game = PacmanGame;
        //game.launch();
        panel = new PanelPacmanGame(game.getMaze());

        frame = new JFrame("Pacman Game");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(panel);

        frame.setVisible(true);


        game.addPropertyChangeListener(this);
    }

    public void update(){

        ArrayList<PositionAgent> pacmans_pos = new ArrayList<>();
        pacmans_pos.add(game.GetPacmanPos());
        panel.setPacmans_pos(pacmans_pos);

        panel.setGhosts_pos(game.GetGhostPos());

        panel.repaint();

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("turn".equals(evt.getPropertyName())) {
            update();
        }
    }
}
