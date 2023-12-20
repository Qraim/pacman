package Modele;

import Agent.*;
import Factory.AgentFactory;
import Factory.GhostFactory;
import Factory.PacmanFactory;
import Strategies.ChasePacmanStrategy;
import Strategies.FleePacmanStrategy;
import Strategies.KeyboardStrat;
import Strategies.Strategie;

import java.util.ArrayList;
import java.util.List;

public class PacmanGame extends Game {

    private final Maze maze;

    private final ArrayList<Agent> agents;

    public PacmanGame(int maxturn, Maze maze) {
        super(maxturn);
        this.maze = maze;
        this.agents = new ArrayList<Agent>();
        initializeGame();
    }

    public Maze getMaze() {
        return maze;
    }

    @Override
    public void initializeGame() {

        agents.clear();

        AgentFactory pacmanFactory = new PacmanFactory();
        AgentFactory ghostFactory = new GhostFactory();

        Strategie pacmanStrat = new KeyboardStrat();
        Strategie ghostStrat = new ChasePacmanStrategy(GetPacmanPos());

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

    synchronized void takeTurn() {
        List<Agent> toRemove = new ArrayList<>();

        List<Agent> agentsClone = new ArrayList<>(agents);
        for (Agent agent : agentsClone) {
            Agent potentialRemove = moveAgent(agent);
            if (potentialRemove != null) {
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
        System.exit(0);
    }

    private Agent moveAgent(Agent agent) {
        AgentAction action = agent.move(maze);
        int x = agent.getPosition().getX();
        int y = agent.getPosition().getY();

        ghostScared(getCapsuleTimer() > 0);

        if (GetPacmanPos().isEmpty()) {
            gameOver();
            return null;
        }

        if (agent instanceof PacmanAgent) {
            return handlePacmanActions(x, y);
        } else {
            return handleGhostActions(x, y);
        }


    }

    private Agent handlePacmanActions(int x, int y) {
        if (maze.isFood(x, y)) {
            maze.setFood(x, y, false);
            Points++;
            if (!maze.StillFood()) {
                System.out.println("Vous avez gagné");
            }
        } else if (maze.isCapsule(x, y)) {
            maze.setCapsule(x, y, false);
            Points = Points + 5;
            resetCapsuleTimer();
        }

        Agent ghost = getGhostAtPosition(x, y);
        if (ghost != null) {
            return getAgent(x, y, ghost);
        }
        return null;
    }

    private Agent getAgent(int x, int y, Agent ghost) {
        if (getCapsuleTimer() > 0) {
            System.out.println("Manger fantome");
            Points = Points + 10;
            return ghost;
        } else {
            if (GetPacmanPos().isEmpty()) {
                System.out.println("Pacman est mort");
                gameOver();
            }
            return getPacmanAtPosition(x, y);
        }
    }

    private Agent handleGhostActions(int x, int y) {
        List<PositionAgent> pacmanPositions = GetPacmanPos();
        Agent ghost = getGhostAtPosition(x, y);

        for (PositionAgent pacmanPos : pacmanPositions) {
            if (x == pacmanPos.getX() && y == pacmanPos.getY()) {
                return getAgent(x, y, ghost);
            }
        }
        return null;
    }

    private Agent getGhostAtPosition(int x, int y) {
        for (Agent agent : agents) {
            if (agent instanceof FantomeAgent && agent.getPosition().getX() == x && agent.getPosition().getY() == y) {
                return agent;
            }
        }
        return null;
    }

    private Agent getPacmanAtPosition(int x, int y) {
        for (Agent agent : agents) {
            if (agent instanceof PacmanAgent && agent.getPosition().getX() == x && agent.getPosition().getY() == y) {
                return agent;
            }
        }
        return null;
    }

    private void ghostScared(Boolean scared) {

        for (Agent agent : agents) {

            if (agent instanceof FantomeAgent) {


                ArrayList<PositionAgent> pacman_pos = GetPacmanPos();
                agent.getStrategie().setPacmansPos(pacman_pos);
                FantomeAgent fantome = (FantomeAgent) agent;

                // Vérifier si l'état actuel est différent de l'état désiré
                if (fantome.isIscared() != scared) {

                    if (scared) {
                        agent.setStrategie(new FleePacmanStrategy(pacman_pos));
                    } else {
                        agent.restorestrategie();
                    }

                    // Mettre à jour l'état du fantôme
                    fantome.setIscared(scared);
                }
            }
        }
    }


    public ArrayList<PositionAgent> GetPacmanPos() {

        ArrayList<PositionAgent> pacman_pos = new ArrayList<PositionAgent>();
        for (Agent agent : agents) {
            if (agent instanceof PacmanAgent) {
                pacman_pos.add(agent.getPosition());
            }
        }
        return pacman_pos;
    }

    public ArrayList<PositionAgent> GetGhostPos() {
        ArrayList<PositionAgent> ghosts_pos = new ArrayList<PositionAgent>();
        for (Agent agent : agents) {
            if (agent instanceof FantomeAgent) {
                ghosts_pos.add(agent.getPosition());
            }
        }
        return ghosts_pos;
    }

}


