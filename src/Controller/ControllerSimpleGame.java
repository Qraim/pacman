package Controller;
import Modele.*;
import Vue.*;
public class ControllerSimpleGame extends AbstractController {

    private Game game;
    public ControllerSimpleGame() {
        game = new SimpleGame(50);
        ViewCommand vc = new ViewCommand(game);
    }
}
