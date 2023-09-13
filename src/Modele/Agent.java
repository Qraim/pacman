package Modele;

public abstract class Agent {
    protected PositionAgent position;
    protected AgentAction currentAction;

    public Agent(PositionAgent position) {
        this.position = position;
        this.currentAction = new AgentAction(AgentAction.STOP);
    }

    public PositionAgent getPosition() {
        return position;
    }

    public void setPosition(PositionAgent position) {
        this.position = position;
    }

    public AgentAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(AgentAction currentAction) {
        this.currentAction = currentAction;
    }

}
