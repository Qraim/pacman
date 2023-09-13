package Controller;
import Modele.*;
public abstract class AbstractController {
    Game game;

    void restart(){
        game.pause();
        game.init();
    }

    void step(){
        game.step();
    }

    void play(){
        game.run();
    }

    void pause(){
        game.pause();
    }

    void setSpeed(double speed){
        game.setTime((long) speed);
    }
}
