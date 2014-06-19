package controller.sceneController;

import model.GameOverModel;
import view.View;

import java.awt.*;

class GameOverController extends Controller {
    private GameOverModel gameOverModel;
    private int x;
    private int y;
    private int dx;
    private int dy;

    public GameOverController(GameOverModel model) {
        gameOverModel = model;
        x = gameOverModel.getX();
        y = gameOverModel.getY();
        dx = gameOverModel.getDX();
        dy = gameOverModel.getDY();
    }

    @Override
    public void mousePressed(Point p) {
        if (p.x > gameOverModel.getX() && p.x < gameOverModel.getX() + GameOverModel.ITEM_WIDTH &&
                p.y > gameOverModel.getY()){
            int itemId = (p.y - gameOverModel.getY()) / (gameOverModel.getDY() + GameOverModel.ITEM_HEIGHT);
            if ((p.y - gameOverModel.getY()) % (gameOverModel.getDY() + GameOverModel.ITEM_HEIGHT) <
                    GameOverModel.ITEM_HEIGHT)
                gameOverModel.press(itemId);
        }
    }

    @Override
    public void mouseReleased(Point p) {
        gameOverModel.release();
        if (p.x > gameOverModel.getX() && p.x < gameOverModel.getX() + GameOverModel.ITEM_WIDTH &&
                p.y > gameOverModel.getY()){
            int itemId = (p.y - gameOverModel.getY()) / (gameOverModel.getDY() + GameOverModel.ITEM_HEIGHT);
            if ((p.y - gameOverModel.getY()) % (gameOverModel.getDY() + GameOverModel.ITEM_HEIGHT) <
                    GameOverModel.ITEM_HEIGHT) {
                gameOverModel.select(itemId);
                switch (itemId) {
                    case 0:
                        setScene(PLAY_SCENE, this);
                        break;
                    case 1:
                        setScene(MENU_SCENE, this);
                        break;

                }
            }
        }
    }

    @Override
    public void mouseMoved(Point p) {
        if (p.x > gameOverModel.getX() && p.x < gameOverModel.getX() + GameOverModel.ITEM_WIDTH &&
                p.y > gameOverModel.getY()){
            int itemId = (p.y - gameOverModel.getY()) / (gameOverModel.getDY() + GameOverModel.ITEM_HEIGHT);
            if ((p.y - gameOverModel.getY()) % (gameOverModel.getDY() + GameOverModel.ITEM_HEIGHT)
                    < GameOverModel.ITEM_HEIGHT)
                gameOverModel.select(itemId);
            else gameOverModel.release();
        }
        else gameOverModel.release();
    }


    @Override
    public void run() {
        waitForPrev();
        long previousIterationTime = System.nanoTime();
        while (isRunning()) {
            long now = System.nanoTime();
            long nanosPassed = now - previousIterationTime;
            previousIterationTime = now;
            gameOverModel.update(nanosPassed);
            View.draw();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void stop() {
        if(isRunning()) {
            super.stop();
            View.draw();
        }
    }
}
