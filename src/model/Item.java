package model;

public class Item {
    String name;
    private Model model;
    private final int NORMAL = 0, SELECTED = 1, PRESSED = 2;
    private int state;
    private int width, height;
    private double pulseVelocity, dw, dh;
    private final double K = 0.0005;
    private int naxWidth;
    private int maxHeight;

    public Item(Model model, String name) {
        normal();
        this.model = model;
        this.name = name;
        naxWidth = (int) Math.round(MenuModel.ITEM_WIDTH * 1.1);
        maxHeight = (int) Math.round(MenuModel.ITEM_HEIGHT * 1.1);
    }

    void press() {
        state = PRESSED;
        width = (int) Math.round(Model.ITEM_WIDTH / 1.23);
        height = (int) Math.round(Model.ITEM_HEIGHT / 1.23);
    }

    void normal() {
        width = Model.ITEM_WIDTH;
        height = Model.ITEM_HEIGHT;
        state = NORMAL;
        dw = dh = pulseVelocity = 0;
    }

    void select() {
        if (state != SELECTED)
            pulseVelocity = 0.1;
        state = SELECTED;
    }

    void update(double beats) {
        dw += Model.ITEM_WIDTH * pulseVelocity / 8 * beats;
        dh += Model.ITEM_HEIGHT * pulseVelocity / 8 * beats;
        if (width > naxWidth) {
            pulseVelocity = 0;
            width = naxWidth;
            height = maxHeight;
        } else
        {
            pulseVelocity -= K * dw * beats;
            width = Model.ITEM_WIDTH + (int) Math.round(dw);
            height = Model.ITEM_HEIGHT + (int) Math.round(dh);
        }


    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    boolean isSelected() {
        return state == SELECTED;
    }
}
