package Strategies;

import Agent.Agent;
import Agent.AgentAction;
import Modele.Maze;
import Agent.PositionAgent;

import java.util.ArrayList;
import java.util.List;

public class FleePacmanStrategy extends Strategie {

    private ArrayList<PositionAgent> pacmans_pos;

    private int tours = 1;

    public FleePacmanStrategy(ArrayList<PositionAgent> pacmansPos) {
        this.pacmans_pos = pacmansPos;
    }

    @Override
    public AgentAction getAction(Agent agent, Maze maze) {
        tours++;
        if(tours%2==0){
            return new AgentAction(Maze.STOP);
        }

        PositionAgent ghostPosition = agent.getPosition();

        PositionAgent closestPacman = findClosestPacman(ghostPosition, this.pacmans_pos);

        List<AgentAction> possibleActions = getPossibleActions(ghostPosition, maze);

        AgentAction bestAction = null;
        double maxDistance = -1;
        for (AgentAction action : possibleActions) {
            PositionAgent newPosition = getNewPosition(ghostPosition, action);
            double distance = calculateDistance(newPosition, closestPacman);

            if (distance > maxDistance) {
                maxDistance = distance;
                bestAction = action;
            }
        }

        return bestAction;
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
