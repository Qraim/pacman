package Modele;

import java.util.ArrayList;
import java.util.List;
public class PacmanGame extends Game {

    private String backupmaze;

    private int pacmanlife = 3;
    private Maze maze;
    private ArrayList<Agent> agents;


    public PacmanGame(int maxturn, Maze maze) {
        super(maxturn);
        this.maze = maze;
        this.backupmaze = maze.filename;
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

        if(pacmanlife <= 0){
            System.out.println("Vous avez perdu");
            System.exit(0);
        }

        pacmanlife--;

        try {
            maze = new Maze(backupmaze);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Points = 0;
        setTurn(0);
        agents.clear();

        AgentFactory pacmanFactory = new PacmanFactory();
        AgentFactory ghostFactory = new GhostFactory();

        Strategie pacmanStrat = new KeyboardStrat();
        Strategie ghostStrat = new RandomStrat();

        ArrayList<PositionAgent> pacmanStartPositions = maze.getPacman_start();
        for (PositionAgent pos : pacmanStartPositions) {
            Agent pacman = pacmanFactory.createAgent(pos, pacmanStrat);
            agents.add(pacman);
        }

        ArrayList<PositionAgent> ghostStartPositions = maze.getGhosts_start();
        for (PositionAgent pos : ghostStartPositions) {
            Agent ghost = ghostFactory.createAgent(pos, ghostStrat);
            agents.add(ghost);
        }
    }

    void takeTurn() {
        List<Agent> toRemove = new ArrayList<>();

        // Cloner la liste agents pour la boucle
        List<Agent> agentsClone = new ArrayList<>(agents);
        for (Agent agent : agentsClone) {
            Agent potentialRemove = moveAgent(agent);
            if(potentialRemove != null){
                toRemove.add(potentialRemove);
            }
        }
        agents.removeAll(toRemove);
        decrementCapsuleTimer();
    }



    @Override
    boolean gameContinue() {
        return true;
    }

    @Override
    void gameOver() {
        System.out.println("Game Over");
        pause();
        initializeGame();
    }

    public void addAgent(Agent agent) {
        agents.add(agent);
    }


    private Agent moveAgent(Agent agent){
        AgentAction action = agent.move(maze);
        int x = agent.getPosition().getX();
        int y = agent.getPosition().getY();

        if (agent instanceof PacmanAgent) {
            return handlePacmanActions(x, y);
        } else {
            return handleGhostActions(x, y);
        }
    }


    private Agent handlePacmanActions(int x, int y) {
        if(maze.isFood(x, y)){
            maze.setFood(x, y, false);
            Points++;
            if(!maze.StillFood()){
                System.out.println("Vous avez gagné");
                gameOver();
            }
        } else if(maze.isCapsule(x, y)){
            maze.setCapsule(x, y, false);
            Points = Points + 5;
            // TODO : Changer strategie des fantomes
            resetCapsuleTimer();
        }

        Agent ghost = getGhostAtPosition(x, y);
        if(ghost != null){
            if(getCapsuleTimer() > 0) {
                System.out.println("Manger fantome");
                Points = Points + 10;
                return ghost;
            } else {
                System.out.println("Pacman est mort");
                gameOver();
                return null;
            }
        }
        return null;
    }

    private Agent handleGhostActions(int x, int y) {
        PositionAgent pacmanPos = GetPacmanPos();
        Agent ghost = getGhostAtPosition(x, y);

        if(x == pacmanPos.getX() && y == pacmanPos.getY()){
            if(getCapsuleTimer() > 0) {
                System.out.println("Manger fantome");
                Points = Points + 10;
                return ghost;

            } else {
                System.out.println("Pacman est mort");
                gameOver();
                return null;
            }
        }
        return null;
    }


    public boolean StillFood(){
        return maze.StillFood();
    }


    private boolean isGhost(int x, int y){
        for(Agent agent : agents){
            if(agent instanceof FantomeAgent && agent.getPosition().getX() == x && agent.getPosition().getY() == y){
                return true;
            }
        }
        return false;
    }

    private Agent getGhostAtPosition(int x, int y) {
        for(Agent agent : agents){
            if(agent instanceof FantomeAgent && agent.getPosition().getX() == x && agent.getPosition().getY() == y) {
                return agent;
            }
        }
        return null;
    }


    public PositionAgent GetPacmanPos(){
        for(Agent agent : agents){
            if(agent instanceof PacmanAgent){
                return agent.position;
            }
        }
        return null;
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


