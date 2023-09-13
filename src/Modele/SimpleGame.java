package Modele;

import Vue.ViewCommand;

public class SimpleGame extends Game{


    public SimpleGame(int maxturn) {
        super(maxturn);


    }


    @Override
    void initializeGame() {
        System.out.println("Jeu intitialisé");

    }

    @Override
    void takeTurn() {

        System.out.println("Tour " + getTurn() + " du jeu en cours");
    }

    @Override
    boolean gameContinue() {
        return true;
    }

    @Override
    void gameOver() {
        System.out.println("Duper");

    }


}
