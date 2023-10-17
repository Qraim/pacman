package Strategies;

import java.util.ArrayList;
import java.util.Random;
import Agent.Agent;
import Agent.AgentAction;
import Modele.Maze;
import Agent.PositionAgent;

public class RandomStrat extends Strategie {
    @Override
    public AgentAction getAction(Agent agent, Maze maze) {
        AgentAction randomAction = new AgentAction(new Random().nextInt(4));

        if(!isLegalMove(agent, randomAction, maze)){
            return new AgentAction(4);
        }
        return randomAction;
    }

    @Override
    public void setPacmansPos(ArrayList<PositionAgent> pacmansPos) {

    }
}
