package model;

import controller.Game;
import view.View;

public class Jumper {

    protected int x = 180;
    protected int y = 595;
    protected int dx = 0;
    protected boolean springsCompressed = false;
    protected boolean eyesClosed = false;
    protected double vVelocity = 0;
    protected double jumpVelocity = 12;
    protected boolean turbo = false;

    Jumper() {
        y = Game.getSize().height - View.JUMPER_HEIGHT;
        x = Game.getSize().width/2 - View.JUMPER_WIDTH/2;
    }

    protected void moveHorizontal() {
        x += dx;
    }

    protected void moveVertical(int dy) {
        y -= dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public void setEyesClosed(boolean ec) {
        eyesClosed = ec;
    }

    public void setSpringsCompressed(boolean sc) {
        springsCompressed = sc;
    }

    public int getStage()
    {
        int ret = 0;
        if (eyesClosed) ret += 2;
        if (springsCompressed) ret += 1;
        return ret;
    }
}
