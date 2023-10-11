package Modele;

import Strategies.Strategie;

public interface AgentFactory {
    Agent createAgent(PositionAgent position, Strategie strategie);
}

