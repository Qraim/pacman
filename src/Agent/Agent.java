package Agent;

import Modele.Maze;
import Strategies.Strategie;

public abstract class Agent {
    protected PositionAgent position;
    protected AgentAction currentAction;

    protected Strategie strategie;
    protected Strategie oldStrategie;


    public Agent(PositionAgent position, Strategie strategie) {
        this.strategie = strategie;
        oldStrategie = strategie;
        this.position = position;
        this.currentAction = new AgentAction(AgentAction.STOP);
    }

    public void restorestrategie(){
        this.strategie = oldStrategie;
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

    public Strategie getStrategie() {
        return strategie;
    }

    public AgentAction move(Maze maze){
        AgentAction action = strategie.getAction(this, maze);

        if(action != null){
            int newX = position.getX() + action.get_vx();
            int newY = position.getY() + action.get_vy();

            position.setX(newX);
            position.setY(newY);
            position.setDir(action.get_direction());
        }

        return action;
    }


    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }
}

