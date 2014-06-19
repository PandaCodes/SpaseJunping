package model;


public abstract class Model {
    public static int ITEM_WIDTH;
    public static int ITEM_HEIGHT;
    public abstract void update(long nanos);

}
