package Strategies;

import Agent.Agent;
import Agent.AgentAction;
import Agent.PositionAgent;

import Modele.Maze;

import java.util.ArrayList;

public abstract class Strategie {
    abstract public AgentAction getAction(Agent agent, Maze maze);

    protected boolean isLegalMove(Agent agent, AgentAction action, Maze maze){

        int x = agent.getPosition().getX() + action.get_vx();
        int y = agent.getPosition().getY() + action.get_vy();

        return !maze.isWall(x,y);
    }

    abstract public void setPacmansPos(ArrayList<PositionAgent> pacmansPos);


}
