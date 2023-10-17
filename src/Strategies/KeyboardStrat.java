package Strategies;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Agent.PositionAgent;

import Agent.Agent;
import Agent.AgentAction;
import Modele.Maze;

public class KeyboardStrat extends Strategie {

    private AgentAction currentAction = new AgentAction(4); // Action for keyboard control
    private AgentAction lastaction = new AgentAction(4); // Action for keyboard control

    public KeyboardStrat() {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton upButton = new JButton("^");
        JButton downButton = new JButton("v");
        JButton leftButton = new JButton("<");
        JButton rightButton = new JButton(">");

        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentAction = new AgentAction(AgentAction.NORTH);
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentAction = new AgentAction(AgentAction.SOUTH);
            }
        });

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentAction = new AgentAction(AgentAction.WEST);
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentAction = new AgentAction(AgentAction.EAST);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(upButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(leftButton, gbc);

        gbc.gridx = 1;
        buttonPanel.add(downButton, gbc);

        gbc.gridx = 2;
        buttonPanel.add(rightButton, gbc);

        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });

        frame.setSize(400, 400);
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
        if(isLegalMove(agent, currentAction, maze)){
            lastaction = currentAction;
            return currentAction;
        }
        else if(isLegalMove(agent, lastaction, maze))
            return lastaction;
        else
            return new AgentAction(4);
    }

    @Override
    public void setPacmansPos(ArrayList<PositionAgent> pacmansPos) {

    }
}
