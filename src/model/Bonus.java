package model;

public class Bonus {
    private int x;
    private int y;
    private int type;
    public Bonus(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    protected void moveVertical(int dy) {
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }
}
