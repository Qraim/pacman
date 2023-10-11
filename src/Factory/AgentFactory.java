package Factory;

import Agent.Agent;
import Agent.PositionAgent;
import Strategies.Strategie;

public interface AgentFactory {
    Agent createAgent(PositionAgent position, Strategie strategie);
}

