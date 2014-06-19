package controller;

import controller.sceneController.Controller;
import view.View;

import javax.swing.*;
import java.awt.*;

public class Game {

    static public Controller controller = null;
    private static Dimension size;
    public static Long currentScore;

    public static Dimension getSize() {
        return size;
    }

    private static void createGame(Dimension size) {
        Game.size = size;
        JPanel window = View.createWnd(size);
        window.addKeyListener(new KListener());
        //window.add...
        Controller.setScene(Controller.MENU_SCENE, null);
        MListener mListener = new MListener();
        window.addMouseListener(mListener);
        window.addMouseMotionListener(mListener);
        System.out.println();

    }

    public static void main(String[] args) {
        createGame(new Dimension(500, 700));
    }
}