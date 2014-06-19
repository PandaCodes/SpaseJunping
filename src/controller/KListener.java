package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class KListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        Game.controller.keyPressed(e.getKeyCode());
    }

    public  void keyReleased(KeyEvent e) {
        Game.controller.keyReleased(e.getKeyCode());
    }

}