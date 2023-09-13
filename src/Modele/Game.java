package Modele;

import java.lang.Runnable;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public abstract class Game implements Runnable{

    private int turn;
    private int maxturn;
    private PropertyChangeSupport support;

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private boolean isrunning;

    private Thread thread;

    private long time = 1000;


    abstract void initializeGame();

    abstract void takeTurn();

    abstract boolean gameContinue();

    abstract void gameOver();

    public Game(int maxturn) {
        this.maxturn = maxturn;
        isrunning = true;
        turn = 0;
        this.support = new PropertyChangeSupport(this);

    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getMaxturn() {
        return maxturn;
    }

    public void setMaxturn(int maxturn) {
        this.maxturn = maxturn;
    }

    public boolean isIsrunning() {
        return isrunning;
    }

    public void setIsrunning(boolean isrunning) {
        this.isrunning = isrunning;
    }

    public void init(){
        turn = 0;
        isrunning = true;
        initializeGame();
    }

    public void step(){
        int oldTurn = this.turn;

        turn++;
        System.out.println("Tour");
        takeTurn();
        if(gameContinue() && maxturn == turn){
            isrunning = false;
            gameOver();
        }

        support.firePropertyChange("turn", oldTurn, turn);

    }

    public void pause(){
        isrunning = false;
    }

    public void run(){
        while (isrunning) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            step();
        }
    }

    public void launch(){
        isrunning = true;
        thread = new Thread(this);
        thread.start();

    }

}
