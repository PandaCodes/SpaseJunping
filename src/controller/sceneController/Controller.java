package controller.sceneController;


import controller.Game;
import model.GameOverModel;
import model.MenuModel;
import model.PlayModel;
import view.GameOverScene;
import view.MenuScene;
import view.PlayScene;
import view.View;

import java.awt.*;

abstract public class Controller implements Runnable {
    private Thread gameThread;
    private boolean isRunning = false;
    private Controller prevController = null;

    public final static int MENU_SCENE = 0;
    public final static int PLAY_SCENE = 1;
    public final static int GAME_OVER_SCENE = 2;

    public boolean isRunning() {
        return  isRunning;
    }
    public void keyPressed(int keyCode) {
    }
    public void keyReleased(int keyCode) {
    }
    public void someFunction() {
    }
    public void mouseClicked(Point p) {
    }
    public void mousePressed(Point p) {
    }
    public void mouseReleased(Point p) {
    }
    public void mouseMoved(Point p) {
    }

    public void startAfter(Controller prevController) {
        this.prevController = prevController;
        if (!isRunning) {
            isRunning = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            try {
                gameThread.join();
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    protected void waitForPrev() {
        if (prevController != null)
            prevController.stop();
        prevController = null;
    }   //return boolean?

    public static void setScene(int scene, Controller prevController) {
        //0 = menu 1 = game 2 = game over
        switch (scene) {
            case MENU_SCENE :
                MenuModel menuModel = new MenuModel();
                View.setScene(new MenuScene(menuModel));
                Game.controller = new MenuController(menuModel);
                break;
            case PLAY_SCENE :
                PlayModel playModel = new PlayModel();
                View.setScene(new PlayScene(playModel));
                Game.controller = new PlayController(playModel);
                break;
            case GAME_OVER_SCENE :
                GameOverModel gameOverModel = new GameOverModel();
                View.setScene(new GameOverScene(gameOverModel));
                Game.controller = new GameOverController(gameOverModel);
                break;
        }
        Game.controller.startAfter(prevController);
    }
}
