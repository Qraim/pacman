package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import Modele.*;
public class ViewSimpleGame implements PropertyChangeListener {
    private final JFrame frame;
    private final JLabel label;
    private final Game simpleGame;

    public ViewSimpleGame(Game simpleGame) {
        this.simpleGame = simpleGame;
        frame = new JFrame("Modele.Game");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        label = new JLabel("Tour: " + simpleGame.getTurn());
        frame.add(label, BorderLayout.CENTER);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                label.setText("Tour: " + simpleGame.getTurn());
            }
        };

        new Timer(10, taskPerformer).start();

        frame.setVisible(true);

        simpleGame.addPropertyChangeListener(this);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("turn".equals(evt.getPropertyName())) {
            label.setText("Tour: " + evt.getNewValue());
        }
    }
}
