package Modele;

import Strategies.Strategie;

public class PacmanFactory implements AgentFactory {
    @Override
    public Agent createAgent(PositionAgent position, Strategie strategie) {
        return new PacmanAgent(position,strategie);
    }


}
