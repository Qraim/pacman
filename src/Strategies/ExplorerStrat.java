package Strategies;

import Agent.Agent;
import Agent.AgentAction;
import Modele.Maze;
import Agent.PositionAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExplorerStrat extends Strategie {
    private boolean[][] visited;
    private AgentAction lastAction = null;

    public ExplorerStrat(int mazeSizeX, int mazeSizeY) {
        visited = new boolean[mazeSizeX][mazeSizeY];
    }

    @Override
    public AgentAction getAction(Agent agent, Maze maze) {
        PositionAgent position = agent.getPosition();
        int x = position.getX();
        int y = position.getY();

        visited[x][y] = true;

        List<AgentAction> possibleActions = getPossibleActions(agent, maze);
        AgentAction chosenAction = chooseNextAction(possibleActions);

        lastAction = chosenAction;
        return chosenAction;
    }

    @Override
    public void setPacmansPos(ArrayList<PositionAgent> pacmansPos) {
        // This strategy doesn't use Pac-Man positions, so you can leave this empty or log an unsupported operation exception.
    }

    private List<AgentAction> getPossibleActions(Agent agent, Maze maze) {
        List<AgentAction> actions = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            AgentAction action = new AgentAction(i);
            if (isLegalMove(agent, action, maze) && !isBacktracking(action)) {
                actions.add(action);
            }
        }

        return actions;
    }


    private PositionAgent getNewPosition(PositionAgent current, AgentAction action) {
        return new PositionAgent(
                current.getX() + action.get_vx(),
                current.getY() + action.get_vy(),
                current.getDir()
        );
    }

    private boolean isBacktracking(AgentAction action) {
        if (lastAction == null) {
            return false;
        }
        return action.get_vx() == -lastAction.get_vx() && action.get_vy() == -lastAction.get_vy();
    }

    private AgentAction chooseNextAction(List<AgentAction> possibleActions) {
        if (possibleActions.isEmpty()) {
            // No options left, must backtrack
            return new AgentAction(-lastAction.get_direction()); // assuming the opposite direction is represented this way
        } else {
            // Randomly select a valid move
            return possibleActions.get(new Random().nextInt(possibleActions.size()));
        }
    }
}
