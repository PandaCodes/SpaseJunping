package controller.sceneController;

import controller.Game;

class BlinkThread extends Thread {
    private Controller controller;
    BlinkThread (Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while (controller.isRunning()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.someFunction();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.someFunction();
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.someFunction();
        }
    }
}