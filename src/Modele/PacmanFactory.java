package Modele;

public class PacmanFactory implements AgentFactory {
    @Override
    public Agent createAgent(PositionAgent position) {
        return new PacmanAgent(position);
    }
}
