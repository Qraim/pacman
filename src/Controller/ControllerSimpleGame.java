package Controller;
import Modele.*;
import Vue.*;
public class ControllerSimpleGame extends AbstractController {

    private final Game game;
    public ControllerSimpleGame() {
        game = new SimpleGame(50);
        ViewCommand vc = new ViewCommand(game);
    }
}
