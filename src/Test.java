import Modele.Maze;
import Modele.PacmanGame;
import Vue.PanelPacmanGame;
import Vue.ViewPacmanGame;

import javax.swing.*;

public class Test {
    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame();

        String str = "./layouts/mediumClassic.lay";

        Maze maze = new Maze(str);
        PacmanGame game = new PacmanGame(10000,maze);

        game.initializeGame();

        ViewPacmanGame view = new ViewPacmanGame(game);

        game.launch();

    }

}