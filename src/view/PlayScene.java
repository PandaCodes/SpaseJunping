package view;

import controller.Game;
import model.Bonus;
import model.PlayModel;
import model.Stone;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PlayScene implements Scene {
    private ArrayList<BufferedImage> jumperImages = new ArrayList<BufferedImage>();
    private BufferedImage background;
    private BufferedImage pauseImage;
    private ArrayList<BufferedImage> bonusImages = new ArrayList<BufferedImage>();

    private ArrayList<BufferedImage> stoneImages = new ArrayList<BufferedImage>();
    private PlayModel playModel;

    public PlayScene(PlayModel playModel) {
        this.playModel = playModel;
        try {
            jumperImages.add(ImageIO.read(getClass().getResource("/resources/jumper_mini.png")));
            jumperImages.add(ImageIO.read(getClass().getResource("/resources/jumper_CompactSprings_mini.png")));
            jumperImages.add(ImageIO.read(getClass().getResource("/resources/jumper_closedEyes_mini.png")));
            jumperImages.add(ImageIO.read(getClass().getResource("/resources/jumper_ClosedEyes_CompressedSprings_mini.png")));
            jumperImages.add(ImageIO.read(getClass().getResource("/resources/jumper_turbo.png")));

            bonusImages.add(ImageIO.read(getClass().getResource("/resources/banan.png")));
            bonusImages.add(ImageIO.read(getClass().getResource("/resources/banan2.png")));
            bonusImages.add(ImageIO.read(getClass().getResource("/resources/rocket.png")));

            stoneImages.add(ImageIO.read(getClass().getResource("/resources/stone5_70red.png")));
            stoneImages.add(ImageIO.read(getClass().getResource("/resources/stone_green.png")));
            stoneImages.add(ImageIO.read(getClass().getResource("/resources/stone1_70.png")));
            stoneImages.add(ImageIO.read(getClass().getResource("/resources/stone2_70.png")));
            stoneImages.add(ImageIO.read(getClass().getResource("/resources/stone3_70.png")));
            stoneImages.add(ImageIO.read(getClass().getResource("/resources/stone4_70.png")));
            stoneImages.add(ImageIO.read(getClass().getResource("/resources/stone5_70.png")));
            background = ImageIO.read(getClass().getResource("/resources/background.png"));
            pauseImage = ImageIO.read(getClass().getResource("/resources/foreground_pause.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, null);
    }

    private void drawModel(Graphics2D g) {
        synchronized (playModel.stones) {
            for (Stone stone : playModel.stones) {
                g.drawImage(stoneImages.get(stone.getType()), stone.getX(), stone.getY(), null);
            }
            for (Bonus bonus : playModel.bonuses) {
                if (bonus.getType() == 2) {
                    g.drawImage(bonusImages.get(bonusImages.size() - 1), bonus.getX(), bonus.getY(), null);
                } else {
                    g.drawImage(bonusImages.get(bonus.getType()), bonus.getX(), bonus.getY(), null);
                }
            }
            if (playModel.jumper.isTurbo()) {
                g.drawImage(jumperImages.get(4), playModel.jumper.getX(), playModel.jumper.getY(), null);
            } else {
                g.drawImage(jumperImages.get(playModel.jumper.getStage()),
                        playModel.jumper.getX(), playModel.jumper.getY(), null);
            }
        }

    }

    @Override
    public BufferedImage getStage() {
        Dimension size = Game.getSize();
        BufferedImage bufferedImage = new BufferedImage(size.width,
                size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        drawBackground(g2d);
        drawModel(g2d);
        g2d.setFont(new Font("Un Pilgi", Font.BOLD, 23));
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawString(String.valueOf(playModel.getScore()), 10, 20);
        if (playModel.isPaused()) {
            g2d.drawImage(pauseImage, 0, 0, null);
        }
        return bufferedImage;
    }
}