package Strategies;

import Modele.Agent;
import Modele.AgentAction;
import Modele.Maze;
import java.util.ArrayList;

public class FindFoodStrat extends Strategie {

    private AgentAction previousAction = null;

    @Override
    public AgentAction getAction(Agent agent, Maze maze) {
        ArrayList<AgentAction> possibleMoves = new ArrayList<>();
        AgentAction backwardMove = null;

        for (int i = 0; i < 4; i++) {
            AgentAction potentialMove = new AgentAction(i);
            int newX = agent.getPosition().getX() + potentialMove.get_vx();
            int newY = agent.getPosition().getY() + potentialMove.get_vy();

            if (isLegalMove(agent, potentialMove, maze)) {
                if (previousAction != null && isOpposite(potentialMove, previousAction)) {
                    backwardMove = potentialMove; // Gardez la trace du mouvement en arrière
                } else {
                    possibleMoves.add(potentialMove);
                }
            }
        }

        for (AgentAction move : possibleMoves) {
            int newX = agent.getPosition().getX() + move.get_vx();
            int newY = agent.getPosition().getY() + move.get_vy();
            if (maze.isFood(newX, newY)) {
                previousAction = move; // Mettez à jour le dernier mouvement
                return move;           // Retournez le mouvement qui mène à la nourriture
            }
        }

        if (!possibleMoves.isEmpty()) {
            AgentAction chosenMove = possibleMoves.get((int) (Math.random() * possibleMoves.size()));
            previousAction = chosenMove;
            return chosenMove;
        } else if (backwardMove != null) {
            previousAction = backwardMove;
            return backwardMove; // Si Pacman est bloqué, autorisez-le à revenir en arrière
        }

        return null; // Si rien d'autre ne fonctionne
    }

    private boolean isOpposite(AgentAction action1, AgentAction action2) {
        return (action1.get_vx() == -action2.get_vx()) && (action1.get_vy() == -action2.get_vy());
    }
}
