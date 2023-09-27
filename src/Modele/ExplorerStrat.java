package Modele;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class ExplorerStrat extends Strategie{
    private Stack<AgentAction> actionStack = new Stack<>();
    private boolean[][] visited;
    private AgentAction previousAction = null;

    @Override
    public AgentAction getAction(Agent agent, Maze maze) {
        int x = agent.getPosition().getX();
        int y = agent.getPosition().getY();

        if (visited == null) {
            visited = new boolean[maze.getSizeX()][maze.getSizeX()];
        }

        visited[x][y] = true;

        ArrayList<AgentAction> possibleMoves = new ArrayList<>();
        AgentAction backwardMove = null;

        for (int i = 0; i < 4; i++) {
            AgentAction potentialMove = new AgentAction(i);
            int newX = x + potentialMove.get_vx();
            int newY = y + potentialMove.get_vy();

            if (isLegalMove(agent, potentialMove, maze)) {
                if (previousAction != null && isOpposite(potentialMove, previousAction)) {
                    backwardMove = potentialMove;
                } else {
                    possibleMoves.add(potentialMove);
                }
            }
        }

        if (!possibleMoves.isEmpty()) {
            AgentAction chosenMove = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
            previousAction = chosenMove;
            return chosenMove;
        }
        else if (backwardMove != null) {
            previousAction = backwardMove;
            return backwardMove;
        }
        return null;
    }

    private boolean isOpposite(AgentAction action1, AgentAction action2) {
        return (action1.get_vx() == -action2.get_vx()) && (action1.get_vy() == -action2.get_vy());
    }
}

