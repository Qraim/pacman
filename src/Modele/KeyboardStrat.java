package Modele;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardStrat extends Strategie {
    private AgentAction currentAction = null; // Action pour le contrôle du clavier

    public KeyboardStrat() {
        // Attachez le gestionnaire d'événements du clavier à un JFrame
        // Pour que cela fonctionne correctement, le JFrame doit être visible et actif
        JFrame frame = new JFrame();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        frame.setSize(400, 400); // Taille de base pour la démonstration
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }

    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                currentAction = new AgentAction(AgentAction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                currentAction = new AgentAction(AgentAction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                currentAction = new AgentAction(AgentAction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                currentAction = new AgentAction(AgentAction.EAST);
                break;
        }
    }

    @Override
    public AgentAction getAction(Agent agent, Maze maze) {
        return currentAction;
    }
}
