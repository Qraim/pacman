package Factory;

import Agent.Agent;
import Agent.FantomeAgent;
import Agent.PositionAgent;
import Strategies.Strategie;

public class GhostFactory implements AgentFactory {
    @Override
    public Agent createAgent(PositionAgent position, Strategie strategie) {
        return new FantomeAgent(position, strategie);
    }
}
