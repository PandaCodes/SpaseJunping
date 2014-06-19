package controller.sceneController;

import model.MenuModel;
import view.View;

import java.awt.*;

class MenuController extends Controller {
    private MenuModel menuModel;

    public MenuController(MenuModel model) {
        menuModel = model;
    }

    @Override
    public void keyPressed(int keyCode) {
        menuModel.setInRecords(false);
    }
    @Override
    public void mousePressed ( Point p ) {
        if(!menuModel.isInRecords()) {
            if (p.x > menuModel.getX() && p.x < menuModel.getX() + MenuModel.ITEM_WIDTH && p.y > menuModel.getY()) {
                int itemId = (p.y - menuModel.getY()) / (menuModel.getDY() + MenuModel.ITEM_HEIGHT);
                if ((p.y - menuModel.getY()) % (menuModel.getDY() + MenuModel.ITEM_HEIGHT) < MenuModel.ITEM_HEIGHT)
                    menuModel.press(itemId);
            }
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if(!menuModel.isInRecords()) {
            menuModel.release();
            View.draw();
            if (p.x > menuModel.getX() && p.x < menuModel.getX() + MenuModel.ITEM_WIDTH && p.y > menuModel.getY()) {
                int itemId = (p.y - menuModel.getY()) / (menuModel.getDY() + MenuModel.ITEM_HEIGHT);
                if ((p.y - menuModel.getY()) % (menuModel.getDY() + MenuModel.ITEM_HEIGHT) < MenuModel.ITEM_HEIGHT) {
                    menuModel.select(itemId);
                    switch (itemId) {
                        case 0:
                            setScene(PLAY_SCENE, this);
                            break;
                        case 1:
                            menuModel.setInRecords(true);
                            break;
                        case 2:
                            System.exit(0);
                            break;
                    }
                }
            }
        }
    }
    @Override
    public void mouseMoved(Point p) {
        if(!menuModel.isInRecords()) {
            if (p.x > menuModel.getX() && p.x < menuModel.getX() + MenuModel.ITEM_WIDTH && p.y > menuModel.getY()) {
                int itemId = (p.y - menuModel.getY()) / (menuModel.getDY() + MenuModel.ITEM_HEIGHT);
                if ((p.y - menuModel.getY()) % (menuModel.getDY() + MenuModel.ITEM_HEIGHT) < MenuModel.ITEM_HEIGHT)
                    menuModel.select(itemId);
                else menuModel.release();
            } else menuModel.release();
        }
    }
    @Override
    public void mouseClicked( Point p ){
        menuModel.jumper.click(p);
    }
    @Override
    public void someFunction() {
        menuModel.jumper.setEyesClosed(true);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        menuModel.jumper.setEyesClosed(false);
    }
    @Override
    public void run(){
        waitForPrev();
        long previousIterationTime = System.nanoTime();
        while (isRunning()) {
            long now = System.nanoTime();
            long nanosPassed = now - previousIterationTime;
            previousIterationTime = now;
            menuModel.update(nanosPassed);
            View.draw();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void startAfter(Controller prevController){
        if (!isRunning())
            new BlinkThread(this).start();
        super.startAfter(prevController);
    }
    @Override
    public void stop() {
        if(isRunning()) {
            super.stop();
            View.draw();
        }
    }
}
