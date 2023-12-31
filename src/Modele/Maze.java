package Modele;

import Agent.PositionAgent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;


public class Maze implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	/**
	 * Les differentes directions possibles pour les actions et les orientations des
	 * agents
	 */
	public static int NORTH = 0;
	public static int SOUTH = 1;
	public static int EAST = 2;
	public static int WEST = 3;
	public static int STOP = 4;

	private final int size_x;
	private final int size_y;

	/**
	 * Les elements du labyrinthe
	 */
	private final boolean[][] walls;
	private final boolean[][] food;
	private final boolean[][] capsules;

	public String filename;

	/**
	 * Les positions initiales des agents
	 */
	private ArrayList<PositionAgent> pacman_start;
	private ArrayList<PositionAgent> ghosts_start;

	public Maze(String filename) throws Exception {
		try {
			System.out.println("Layout file is " + filename);
			this.filename = filename;
			// Lecture du fichier pour determiner la taille du labyrinthe
			InputStream ips = new FileInputStream(filename);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			int nbX = 0;
			int nbY = 0;
			while ((ligne = br.readLine()) != null) {
				ligne = ligne.trim();
				if (nbX == 0) {
					nbX = ligne.length();
				} else if (nbX != ligne.length()) {
					ips.close();
					br.close();
					throw new Exception("Wrong Input Format: all lines must have the same size");
				}
				nbY++;
			}
			br.close();
			System.out.println("### Size of maze is " + nbX + ";" + nbY);

			// Initialisation du labyrinthe
			size_x = nbX;
			size_y = nbY;
			walls = new boolean[size_x][size_y];
			food = new boolean[size_x][size_y];
			capsules = new boolean[size_x][size_y];

			pacman_start = new ArrayList<PositionAgent>();
			ghosts_start = new ArrayList<PositionAgent>();

			// Lecture du fichier pour mettre a jour le labyrinthe
			ips = new FileInputStream(filename);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			int y = 0;
			while ((ligne = br.readLine()) != null) {
				ligne = ligne.trim();

				for (int x = 0; x < ligne.length(); x++) {
                    walls[x][y] = ligne.charAt(x) == '%';
                    food[x][y] = ligne.charAt(x) == '.';
                    capsules[x][y] = ligne.charAt(x) == 'o';
					if (ligne.charAt(x) == 'P') {
						pacman_start.add(new PositionAgent(x, y, Maze.NORTH));
					}
					if (ligne.charAt(x) == 'G') {
						ghosts_start.add(new PositionAgent(x, y, Maze.NORTH));
					}
				}
				y++;
			}
			br.close();

			if (pacman_start.isEmpty())
				throw new Exception("Wrong input format: must specify a Pacman start");

			// On verifie que le labyrinthe est clos
			for (int x = 0; x < size_x; x++)
				if (!walls[x][0])
					throw new Exception("Wrong input format: the maze must be closed");
			for (int x = 0; x < size_x; x++)
				if (!walls[x][size_y - 1])
					throw new Exception("Wrong input format: the maze must be closed");
			for (y = 0; y < size_y; y++)
				if (!walls[0][y])
					throw new Exception("Wrong input format: the maze must be closed");
			for (y = 0; y < size_y; y++)
				if (!walls[size_x - 1][y])
					throw new Exception("Wrong input format: the maze must be closed");
			System.out.println("### Modele.Maze loaded.");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Probleme a la lecture du fichier: " + e.getMessage());
		}
	}

	/**
	 * Renvoie la taille X du labyrtinhe
	 */
	public int getSizeX() {
		return (size_x);
	}

	/**
	 * Renvoie la taille Y du labyrinthe
	 */
	public int getSizeY() {
		return (size_y);
	}

	/**
	 * Permet de savoir si il y a un mur
	 */
	public boolean isWall(int x, int y) {
		assert ((x >= 0) && (x < size_x));
		assert ((y >= 0) && (y < size_y));
		return (walls[x][y]);
	}

	/**
	 * Permet de savoir si il y a de la nourriture
	 */
	public boolean isFood(int x, int y) {
		assert ((x >= 0) && (x < size_x));
		assert ((y >= 0) && (y < size_y));
		return (food[x][y]);
	}

	public boolean isGhost(int x, int y) {
		for(PositionAgent pos : ghosts_start){
			if(pos.getX() == x && pos.getY() == y)
				return true;
		}
		return false;
	}


	public void setFood(int x, int y, boolean b) {
		food[x][y] = b;
	}



	public boolean StillFood()
	{
		for(int i=0; i<food.length; i++)
		{
			for(int j=0; j<food[i].length; j++)
			{
				if(food[i][j])
					return true;
			}
		}
        return false;
    }


	/**
	 * Permet de savoir si il y a une capsule
	 */
	public boolean isCapsule(int x, int y) {
		assert ((x >= 0) && (x < size_x));
		assert ((y >= 0) && (y < size_y));
		return (capsules[x][y]);
	}

	public void setCapsule(int x, int y, boolean b) {
		capsules[x][y] = b;
	}
	

	/**
	 * Renvoie le nombre de pacmans
	 * 
	 * @return the number of pacmans at the start
	 */
	public int getInitNumberOfPacmans() {
		return (pacman_start.size());
	}

	/**
	 * Renvoie le nombre de fantomes
	 * 
	 * @return the number of ghost at the start
	 */
	public int getInitNumberOfGhosts() {
		return (ghosts_start.size());
	}

	public ArrayList<PositionAgent> getPacman_start() {
		return pacman_start;
	}

	public void setPacman_start(ArrayList<PositionAgent> pacman_start) {
		this.pacman_start = pacman_start;
	}

	public ArrayList<PositionAgent> getGhosts_start() {
		return ghosts_start;
	}

	public void setGhosts_start(ArrayList<PositionAgent> ghosts_start) {
		this.ghosts_start = ghosts_start;
	}

	public String toString() {
		StringBuilder s = new StringBuilder("Modele.Maze\n");
		s.append(plateauToString());
		s.append("\nPosition agents fantom :");
		for (PositionAgent pa : ghosts_start) {
			s.append(pa).append(" ");
		}
		s.append("\nPosition agents pacman :");
		for (PositionAgent pa : pacman_start) {
			s.append(pa).append(" ");
		}
		return s.toString();
	}

	public String plateauToString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size_x; i++) {
			for (int j = 0; j < size_y; j++) {
				if (walls[i][j])
					s.append("X");
				else if (food[i][j])
					s.append("f");
				else if (capsules[i][j])
					s.append("c");
				else
					s.append(" ");
			}
			s.append("\n");
		}
		return s.toString();
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
