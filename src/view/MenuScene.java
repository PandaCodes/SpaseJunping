package view;

import controller.Game;
import model.Item;
import model.MenuModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MenuScene implements Scene {
    private MenuModel menuModel;
    private ArrayList<BufferedImage> itemImages = new ArrayList<BufferedImage>();
    private BufferedImage background;
    private BufferedImage cosmosJump;
    private BufferedImage recordsBack;
    private List<BufferedImage> jumperImages = new ArrayList<BufferedImage>();
    private List<String> records = new ArrayList<String>();

    public MenuScene(MenuModel model) {
        menuModel = model;
        try {
            itemImages.add(ImageIO.read(getClass().getResource("/resources/menu_start.png")));
            itemImages.add(ImageIO.read(getClass().getResource("/resources/menu_RECORDS.png")));
            itemImages.add(ImageIO.read(getClass().getResource("/resources/menu_EXIT.png")));
            background = ImageIO.read(getClass().getResource("/resources/background.png"));
            cosmosJump = ImageIO.read(getClass().getResource("/resources/Cosmos Jump w-t back.png"));
            recordsBack = ImageIO.read(getClass().getResource("/resources/recordsBack.png"));

            jumperImages.add(ImageIO.read(getClass().getResource("/resources/jumper_mini.png")));
            jumperImages.add(ImageIO.read(getClass().getResource("/resources/jumper_CompactSprings_mini.png")));
            jumperImages.add(ImageIO.read(getClass().getResource("/resources/jumper_closedEyes_mini.png")));
            jumperImages.add(ImageIO.read(getClass().getResource("/resources/jumper_ClosedEyes_CompressedSprings_mini.png")));

            BufferedReader bf = new BufferedReader(new FileReader(new File("src/resources/records")));
            String line;
            while ((line = bf.readLine()) != null) {
                records.add(line);
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawRecords(Graphics2D g2d) {
        int x = menuModel.getX() * 3 / 2;
        int y = menuModel.getY() *2/3;
        g2d.setFont(new Font("Un Pilgi", Font.BOLD, 40));
        g2d.setColor(Color.LIGHT_GRAY);
        for (String record : records) {
            g2d.drawString(record, x, y);
            y += menuModel.getDY();
        }
    }

    private void drawMenu(Graphics2D g2d) {
        int y = menuModel.getY();
        int x = menuModel.getX();
        int deltaY = menuModel.getDY() + MenuModel.ITEM_HEIGHT;
        int w = MenuModel.ITEM_WIDTH;
        int h = MenuModel.ITEM_HEIGHT;
        int dx, dy, wi, hi, i;

        for (Item item : menuModel.menuItems) {
            i = menuModel.menuItems.indexOf(item);
            wi = item.getWidth();
            hi = item.getHeight();
            dx = (w - wi) / 2;
            dy = (h - hi) / 2;
            g2d.drawImage(itemImages.get(i), x + dx, y + dy, wi, hi, null);
            y += deltaY;
        }
    }

    private void drawJumper(Graphics2D g2d) {
        double angle = menuModel.jumper.getAngle();
        int x = menuModel.jumper.getX();
        int y = menuModel.jumper.getY();
        if (angle != 0) {
            AffineTransform at = new AffineTransform();
            at.setToRotation(angle, x, y);
            at.translate(x, y);
            g2d.drawImage(jumperImages.get(menuModel.jumper.getStage()), at, null);
        } else
            g2d.drawImage(jumperImages.get(menuModel.jumper.getStage()), null, x, y);
    }

    @Override
    public BufferedImage getStage() {
        Dimension size = Game.getSize();
        BufferedImage bufferedImage = new BufferedImage(size.width,
                size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        if (menuModel.isInRecords()) {
            g2d.drawImage(recordsBack, null, 0, 0);
            drawRecords(g2d);
        } else {
            g2d.drawImage(background, null, 0, 0);
            g2d.drawImage(cosmosJump, null, background.getWidth()/2 - cosmosJump.getWidth() / 2, background.getHeight()/10);
            drawMenu(g2d);
        }
        drawJumper(g2d);
        return bufferedImage;
    }
}
