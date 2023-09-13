package Modele;

import java.util.ArrayList;
import java.util.Random;

public class PacmanGame extends Game {

    private Maze maze;
    private ArrayList<Agent> agents;

    public PacmanGame(int maxturn, Maze maze) {
        super(maxturn);
        this.maze = maze;
        this.agents = new ArrayList<>();
        initializeGame();
    }

    public Maze getMaze() {
        return maze;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    @Override
    public void initializeGame() {
        agents.clear();

        AgentFactory pacmanFactory = new PacmanFactory();
        AgentFactory ghostFactory = new GhostFactory();

        ArrayList<PositionAgent> pacmanStartPositions = maze.getPacman_start();
        for (PositionAgent pos : pacmanStartPositions) {
            Agent pacman = pacmanFactory.createAgent(pos);
            agents.add(pacman);
        }

        ArrayList<PositionAgent> ghostStartPositions = maze.getGhosts_start();
        for (PositionAgent pos : ghostStartPositions) {
            Agent ghost = ghostFactory.createAgent(pos);
            agents.add(ghost);
        }
    }

    @Override
    void takeTurn() {
        for (Agent agent : agents) {
            System.out.println("Randommove");
            randomMoveAgents(agent);
        }
    }

    @Override
    boolean gameContinue() {
        return true;
    }

    @Override
    void gameOver() {
    }

    public void addAgent(Agent agent) {
        agents.add(agent);
    }

    private boolean isLegalMove(Agent agent, AgentAction action){

        int x = agent.getPosition().getX() + action.get_vx();
        int y = agent.getPosition().getY() + action.get_vy();

        return !maze.isWall(x,y);
    }


    private void moveAgent(Agent agent, AgentAction action){
        if (isLegalMove(agent, action)) {
            int x = agent.getPosition().getX() + action.get_vx();
            int y = agent.getPosition().getY() + action.get_vy();

            agent.setPosition(new PositionAgent(x, y, action.get_direction()));

            if (maze.isFood(x, y) && agent instanceof PacmanAgent) {
                maze.setFood(x, y, false);
            }
        }
    }

    public void randomMoveAgents(Agent agent) {

        AgentAction randomAction = new AgentAction(new Random().nextInt(5));

        while (!isLegalMove(agent, randomAction)) {
            randomAction = new AgentAction(new Random().nextInt(5));
        }
        System.out.println("Trouvé déplacement");
        moveAgent(agent, randomAction);

    }


    public PositionAgent GetPacmanPos(){
        return agents.get(0).getPosition();
    }

    public ArrayList<PositionAgent> GetGhostPos(){
        ArrayList<PositionAgent> ghost_pos = new ArrayList<>();
        for (Agent agent : agents) {
            ghost_pos.add(agent.getPosition());
        }
        ghost_pos.remove(0);
        return ghost_pos;
    }

}
