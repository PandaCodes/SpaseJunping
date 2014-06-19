package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class MListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        Game.controller.mouseClicked(e.getPoint());
    }

    public  void  mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            Game.controller.mousePressed(e.getPoint());
    }

    public  void  mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            Game.controller.mouseReleased(e.getPoint());
    }

    public void mouseMoved(MouseEvent e) {
        Game.controller.mouseMoved(e.getPoint());
    }
}

