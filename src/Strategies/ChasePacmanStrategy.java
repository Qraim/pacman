package Strategies;

import Agent.*;
import Modele.Maze;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChasePacmanStrategy extends Strategie {

    private ArrayList<PositionAgent> pacmans_pos;

    public ChasePacmanStrategy(ArrayList<PositionAgent> pacmansPos) {
        this.pacmans_pos = pacmansPos;
    }

    @Override
    public AgentAction getAction(Agent agent, Maze maze) {
        PositionAgent ghostPosition = agent.getPosition();

        // Trouver le Pac-Man le plus proche
        PositionAgent closestPacman = findClosestPacman(ghostPosition, this.pacmans_pos);

        List<AgentAction> possibleActions = getPossibleActions(ghostPosition, maze);

        AgentAction bestAction = null;
        double minDistance = Double.MAX_VALUE;
        for (AgentAction action : possibleActions) {
            PositionAgent newPosition = getNewPosition(ghostPosition, action);

            // Vérifiez si le nouveau mouvement mène à une impasse
            if (isDeadEnd(newPosition, maze)) {
                continue; // ignore les mouvements qui mènent à des impasses
            }

            // Calculer la distance entre la nouvelle position et le Pac-Man le plus proche
            double distance = calculateDistance(newPosition, closestPacman);

            // Choisir l'action qui minimise la distance
            if (distance < minDistance) {
                minDistance = distance;
                bestAction = action;
            }
        }

        // Si toutes les actions possibles mènent à une impasse, choisir une au hasard
        if (bestAction == null && !possibleActions.isEmpty()) {
            bestAction = possibleActions.get(new Random().nextInt(possibleActions.size()));
        }

        return bestAction;
    }

    private boolean isDeadEnd(PositionAgent position, Maze maze) {
        int x = position.getX();
        int y = position.getY();

        // Compter les directions possibles où l'agent peut se déplacer
        int countPossibleDirections = 0;

        // Vérifier chaque direction possible
        if (!maze.isWall(x, y - 1)) countPossibleDirections++; // Nord
        if (!maze.isWall(x, y + 1)) countPossibleDirections++; // Sud
        if (!maze.isWall(x - 1, y)) countPossibleDirections++; // Ouest
        if (!maze.isWall(x + 1, y)) countPossibleDirections++; // Est

        // S'il n'y a qu'une seule direction possible, c'est une impasse
        return countPossibleDirections <= 1;
    }



    public void setPacmansPos(ArrayList<PositionAgent> pacmansPos) {
        this.pacmans_pos = pacmansPos;
    }

    private List<AgentAction> getPossibleActions(PositionAgent current, Maze maze) {
        List<AgentAction> actions = new ArrayList<>();

        if (!maze.isWall(current.getX(), current.getY() - 1)) actions.add(new AgentAction(Maze.NORTH));
        if (!maze.isWall(current.getX(), current.getY() + 1)) actions.add(new AgentAction(Maze.SOUTH));
        if (!maze.isWall(current.getX() - 1, current.getY())) actions.add(new AgentAction(Maze.WEST));
        if (!maze.isWall(current.getX() + 1, current.getY())) actions.add(new AgentAction(Maze.EAST));

        return actions;
    }

    private PositionAgent getNewPosition(PositionAgent current, AgentAction action) {
        return new PositionAgent(
                current.getX() + action.get_vx(),
                current.getY() + action.get_vy(),
                action.get_direction()
        );
    }

    private double calculateDistance(PositionAgent a, PositionAgent b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private PositionAgent findClosestPacman(PositionAgent ghostPosition, List<PositionAgent> pacmanPositions) {
        PositionAgent closest = null;
        double minDistance = Double.MAX_VALUE;

        for (PositionAgent pacmanPosition : pacmanPositions) {
            double distance = calculateDistance(ghostPosition, pacmanPosition);

            if (distance < minDistance) {
                closest = pacmanPosition;
                minDistance = distance;
            }
        }

        return closest;
    }
}
