package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class View {
    public static final int JUMPER_WIDTH = 70;
    public static final int JUMPER_HEIGHT = 105;
    public static final int STONE_WIDTH = 70;
    public static final int STONE_HEIGHT = 21;
    public static final int BONUS_WIDTH = 38;
    public static final int BONUS_HEIGHT = 38;
    public static final int TURBO_WIDTH = 129;
    public static final int TURBO_HEIGHT = 169;

    static private Graphics2D g2d = null;
    private static Scene scene;

    public static JPanel createWnd(Dimension screenSize) {
        final JPanel window = new JPanel();
        window.setFocusable(true);
        window.setSize(screenSize);
        JFrame frame = new JFrame();
        frame.setFocusable(false);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.add(window, BorderLayout.CENTER);
        frame.setSize(screenSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeiconified(WindowEvent e) {
                draw();
            }
        });
        g2d = (Graphics2D) window.getGraphics();
        return window;
    }

    public static void setScene(Scene scene) {
        View.scene = scene;
    }

    public static void draw() {
        if (g2d == null) return;
        g2d.drawImage(scene.getStage(), null, 0, 0);
    }
}
