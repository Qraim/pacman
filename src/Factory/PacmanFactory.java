package Factory;

import Agent.Agent;
import Agent.PacmanAgent;
import Agent.PositionAgent;
import Strategies.Strategie;

public class PacmanFactory implements AgentFactory {
    @Override
    public Agent createAgent(PositionAgent position, Strategie strategie) {
        return new PacmanAgent(position,strategie);
    }
}
