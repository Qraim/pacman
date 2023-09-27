import Modele.Maze;
import Modele.PacmanGame;
import Vue.ViewCommand;
import Vue.ViewPacmanGame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Test {
    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame();

        JFileChooser fileChooser = new JFileChooser("./layouts");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Layout files", "lay");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(frame);

        String str = null;
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            str = fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            System.out.println("Aucun fichier choisi.");
            return;
        }

        Maze maze = new Maze(str);
        PacmanGame game = new PacmanGame(5000, maze);

        ViewCommand Test = new ViewCommand(game);
        ViewPacmanGame view = new ViewPacmanGame(game);



//        game.initializeGame();
//
//
//        game.launch();

    }

}