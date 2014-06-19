package model;

import controller.Game;
import view.View;

import java.util.Random;

public class Stone {
    private int x;
    private int y;
    protected int vVelocity = 0;
    private int hVelocity = 0;
    private int type;

    public Stone(int x, int y) {
        this.x = x;
        this.y = y;
        type = new Random().nextInt(7);
        if (type == 6) hVelocity = 2;
    }

    public int getType() { return type; }

    public void move() {
        moveVertical(vVelocity);
        x += hVelocity;
        if (x < 5 ) hVelocity = 2;
        if (x + View.STONE_WIDTH > Game.getSize().width - 5 ) hVelocity = -2;
    }

    public void moveVertical(int dy) {
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
