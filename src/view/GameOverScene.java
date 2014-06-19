package view;

import controller.Game;
import model.GameOverModel;
import model.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class GameOverScene implements Scene {
    private GameOverModel gameOverModel;
    private ArrayList<BufferedImage> itemImages = new ArrayList<BufferedImage>();
    private BufferedImage background;

    public GameOverScene(GameOverModel model) {
        gameOverModel = model;
        try {
            itemImages.add(ImageIO.read(getClass().getResource("/resources/restart.png")));
            itemImages.add(ImageIO.read(getClass().getResource("/resources/menu.png")));
            background = ImageIO.read(getClass().getResource("/resources/game_over.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void drawGameOver(Graphics2D g2d) {
        int y = gameOverModel.getY();
        int x = gameOverModel.getX();
        int deltaY = gameOverModel.getDY() + GameOverModel.ITEM_HEIGHT;
        int w = GameOverModel.ITEM_WIDTH;
        int h = GameOverModel.ITEM_HEIGHT;
        int dx, dy, wi, hi, i;

        for (Item item : gameOverModel.gameOverItems) {
            i = gameOverModel.gameOverItems.indexOf(item);
            wi = item.getWidth();
            hi = item.getHeight();
            dx = (w - wi) / 2;
            dy = (h - hi) / 2;
            g2d.drawImage(itemImages.get(i), x + dx, y + dy, wi, hi, null);
            y += deltaY;
        }
        g2d.setFont(new Font("Un Pilgi", Font.BOLD, 40));
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawString(String.valueOf(Game.currentScore), 284, 300);
    }

    @Override
    public BufferedImage getStage() {
        Dimension size = Game.getSize();
        BufferedImage bufferedImage = new BufferedImage(size.width,
                size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(background, null, 0, 0);
        drawGameOver(g2d);
        return bufferedImage;
    }
}
