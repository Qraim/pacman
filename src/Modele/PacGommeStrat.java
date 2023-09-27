package Modele;

public class PacGommeStrat extends Strategie {

    @Override
    public AgentAction getAction(Agent agent, Maze maze) {

        int x = agent.getPosition().getX();
        int y = agent.getPosition().getY();

        boolean[] pacGommeDirections = new boolean[4];

        pacGommeDirections[0] = maze.isFood(x, y - 1);  // Haut
        pacGommeDirections[1] = maze.isFood(x + 1, y);  // Droite
        pacGommeDirections[2] = maze.isFood(x, y + 1);  // Bas
        pacGommeDirections[3] = maze.isFood(x - 1, y);  // Gauche

        for (int i = 0; i < 4; i++) {
            AgentAction potentialMove = new AgentAction(i);
            if (pacGommeDirections[i] && isLegalMove(agent, potentialMove, maze)) {
                return potentialMove;
            }
        }

        AgentAction randomMove = new AgentAction((int) (Math.random() * 4));
        while (!isLegalMove(agent, randomMove, maze)) {
            randomMove = new AgentAction((int) (Math.random() * 4));
        }

        return randomMove;
    }

}
