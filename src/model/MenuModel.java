package model;

import controller.Game;

import java.util.ArrayList;
import java.util.List;

public class MenuModel extends Model {
    private int X, Y, dY;
    public final List<Item> menuItems = new ArrayList<Item>();
    public final MenuJumper jumper = new MenuJumper();
    private boolean inRecords = false;

    public MenuModel() {
        Model.ITEM_WIDTH = 234;
        Model.ITEM_HEIGHT = 63;
        X = Game.getSize().width / 2 - ITEM_WIDTH / 2;
        Y = Game.getSize().height / 7 * 3;
        dY = 50;
        menuItems.add(new Item(this, "Start game"));
        menuItems.add(new Item(this, "Another item"));
        menuItems.add(new Item(this, "Exit"));
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

    public void setInRecords(boolean inRecords) {
        this.inRecords = inRecords;
    }

    public boolean isInRecords() {
        return inRecords;
    }

    public void press(int itemId) {
        if (menuItems.size() > itemId) {
            menuItems.get(itemId).press();
        }
    }

    public void select(int itemId) {
        if (menuItems.size() > itemId)
            menuItems.get(itemId).select();
    }

    public void release() {
        for (Item item : menuItems) {
            item.normal();
        }
    }

    @Override
    public void update(long nanos) {
        double beats = nanos / 10000000;
        for (Item item : menuItems)
            if (item.isSelected()) item.update(beats);
        jumper.update(beats);
    }
}
