package Modele;

import java.util.Random;

public class RandomStrat extends Strategie {
    @Override
    public AgentAction getAction(Agent agent, Maze maze) {
        AgentAction randomAction = new AgentAction(new Random().nextInt(4));

        if(!isLegalMove(agent, randomAction, maze)){
            return new AgentAction(4);
        }
        return randomAction;
    }
}
