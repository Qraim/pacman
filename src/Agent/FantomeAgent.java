package Agent;

import Strategies.Strategie;

public class FantomeAgent extends Agent {
    private boolean iscared = false;

    public boolean isIscared() {
        return iscared;
    }

    public void setIscared(boolean iscared) {
        this.iscared = iscared;
    }

    public FantomeAgent(PositionAgent position, Strategie strategie) {
        super(position, strategie);
    }


}