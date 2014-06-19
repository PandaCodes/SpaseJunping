package model;

import controller.Game;

import java.util.ArrayList;
import java.util.List;

public class GameOverModel extends Model{
    private int X, Y, dY, dX;
    public final List<Item> gameOverItems = new ArrayList<Item>();

    public GameOverModel() {
        Model.ITEM_WIDTH = 215;
        Model.ITEM_HEIGHT = 58;
        X = Game.getSize().width / 2 - ITEM_WIDTH / 2;
        Y = Game.getSize().height / 5 * 3;
        dY = 30;
        dX = Game.getSize().width / 5;
        gameOverItems.add(new Item(this, "Try again"));
        gameOverItems.add(new Item(this, "Back to menu"));
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getDY() {
        return dY;
    }

    public int getDX() {
        return dX;
    }

    public void press(int itemId) {
        if (gameOverItems.size() > itemId) {
            gameOverItems.get(itemId).press();
        }
    }

    public void select(int itemId) {
        if (gameOverItems.size() > itemId)
            gameOverItems.get(itemId).select();
    }

    public void release() {
        for (Item item : gameOverItems) {
            item.normal();
        }
    }

    @Override
    public void update(long nanos) {
        double beats = nanos / 10000000;
        for (Item item : gameOverItems)
            if (item.isSelected()) item.update(beats);
    }
}
