package Controller;
import Modele.*;
import Vue.*;

import java.util.ArrayList;

public class ControllerPacmanGame {

    private Game game;
    public ControllerPacmanGame() throws Exception {
        String str = "./layouts/mediumClassic.lay";

        Maze maze = new Maze(str);

        PacmanGame game = new PacmanGame(50,maze);
        //ViewPacmanGame vc = new ViewPacmanGame(game);
    }
}
