package Modele;

import java.lang.Runnable;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public abstract class Game implements Runnable{

    private int turn;

    private int maxturn;

    public int Points = 0 ;

    private final PropertyChangeSupport support;

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

    private long time = 50;

    private int capsuleTimer = 0;

    private static final int CAPSULE_EFFECT_DURATION = 20;

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
        takeTurn();
        System.out.println("Tour: " + turn);
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

    public int getCapsuleTimer() {
        return capsuleTimer;
    }

    public void decrementCapsuleTimer() {
        if(capsuleTimer > 0) {
            capsuleTimer--;
        }
    }

    public void resetCapsuleTimer() {
        capsuleTimer = CAPSULE_EFFECT_DURATION;
    }

}
