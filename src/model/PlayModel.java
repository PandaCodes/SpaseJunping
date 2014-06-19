package model;

import controller.Game;
import view.View;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

public class PlayModel extends Model {

    public final Jumper jumper = new Jumper();
    public final List<Stone> stones = Collections.synchronizedList(new ArrayList<Stone>());
    public final List<Bonus> bonuses = Collections.synchronizedList(new ArrayList<Bonus>());

    private int stoneDistance = 40;
    private final int WIDTH_INDENT = 10;
    private final double G = 0.2;
    private boolean started = false;
    private boolean paused = false;
    private boolean gameOver = false;
    private long scores = 0;
    private long HEIGHT = 0;
    private int compressFlag = 0;
    private int bonusCounter = 0;
    private int turboCounter = 0;

    public PlayModel() {
        createStones();
    }

    public long getScore() {
        return scores;
    }

    public void Go() {
        jumper.vVelocity = jumper.jumpVelocity;
        started = true;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void moveJumperLeft() {
        jumper.dx = -5;
    }

    public void moveJumperRight() {
        jumper.dx = 5;
    }

    public void stopJumper() {
        jumper.dx = 0;
    }

    private void createStones() {
        Dimension size = Game.getSize();
        int visibleStones = (size.height) / (View.STONE_HEIGHT + stoneDistance);
        int currentHeight = size.height - View.STONE_HEIGHT;
        for (int i = 0; i < visibleStones + 1; i++) {
            Random rnd = new Random();
            stones.add(new Stone(rnd.nextInt(size.width - View.STONE_WIDTH - 2 * WIDTH_INDENT) + WIDTH_INDENT,
                    currentHeight));
            currentHeight -= View.STONE_HEIGHT + stoneDistance;
        }
    }

    private Stone isJumperLanded() {
        int jX = jumper.getX();
        int jY = jumper.getY();
        int sX, sY;
        for (Stone stone : stones) {
            sX = stone.getX();
            sY = stone.getY();
            if (sX < jX + View.JUMPER_WIDTH / 2 && sX + View.STONE_WIDTH > jX + View.JUMPER_WIDTH / 2 &&
                    jY + View.JUMPER_HEIGHT > sY && jY + View.JUMPER_HEIGHT < sY + View.STONE_HEIGHT) {
                return stone;
            }
        }
        return null;
    }

    private Bonus isBonus() {
        int jX = jumper.getX();
        int jY = jumper.getY();
        int tX, tY, w, h;
        for (Bonus bonus : bonuses) {
            tX = bonus.getX() + View.BONUS_WIDTH/3;
            tY = bonus.getY() + View.BONUS_HEIGHT/3;
            if (bonus.getType() == 0) {
                w = View.BONUS_WIDTH/3;
                h = View.BONUS_HEIGHT/3;
            } else {
                w = View.TURBO_WIDTH/3;
                h = View.TURBO_HEIGHT/3;
            }
            if (((tX < jX && (tX + w) > jX) || (tX > jX && (tX - View.JUMPER_WIDTH) < jX)) &&
                    ((tY < jY && (tY + h) > jY) || (tY > jY && (tY - View.JUMPER_HEIGHT) < jY))) {
                //bonuses.remove(bonus);
                return bonus;
            }
        }
        return null;
    }


    private void updateObjects() {
        int height = Game.getSize().height;
        Random rnd = new Random();
        int maxStoneHeight = stones.get(stones.size() - 1).getY();
        if (maxStoneHeight >= stoneDistance) {
            //Creating random stone
            int x = rnd.nextInt(Game.getSize().width - View.STONE_WIDTH - 2 * WIDTH_INDENT) + WIDTH_INDENT;
            int y = maxStoneHeight - (stoneDistance + View.STONE_HEIGHT);

            Stone stone = new Stone(x,y);
            if (stone.getType() == 1) {
                stoneDistance = 135;
            } else {
                stoneDistance = 70;
            }
            //Additional stone
            if (rnd.nextInt(100) < 50) {
                x = rnd.nextInt(Game.getSize().width - View.STONE_WIDTH - 2 * WIDTH_INDENT) + WIDTH_INDENT;
                y += rnd.nextInt(stoneDistance - 2*View.STONE_HEIGHT ) - View.STONE_WIDTH ;
                Stone stone1 = new Stone(x,y);
                stones.add(stone1);
            }
            stones.add(stone);
            //Creating bonuses
            if (rnd.nextInt(100) < 50) {
                bonusCounter++;
                bonuses.add(new Bonus(x + View.STONE_WIDTH / 2 - View.BONUS_WIDTH / 2, y - View.BONUS_HEIGHT, new Random().nextInt(2)));
            } else if (rnd.nextInt(50) == 25) {
                turboCounter++;
                bonuses.add(new Bonus(x + View.STONE_WIDTH / 2 - View.TURBO_WIDTH / 2, y - View.TURBO_HEIGHT, 2));
            }
        }

        Stone stone;
        int i = 0;
        while (i < stones.size()) {
            stone = stones.get(i);
            if (stone.getY() > height) {
                stones.remove(i);
            } else {
                stone.move();
                i++;
            }
        }
       /* Bonus bonus;
        while (i < bonuses.size()) {
            bonus = bonuses.get(i);
            if (bonus.getY() > height)
                stones.remove(i);
            else
                i++;
        }      */
    }


    @Override
    public void update(long nanos) {
        if (paused) return;

        Dimension size = Game.getSize();
        //Горизонтальное движение
        jumper.moveHorizontal();
        if (jumper.x < -View.JUMPER_WIDTH / 2) {
            jumper.x = size.width - View.JUMPER_WIDTH / 2;
        }
        if (jumper.x > size.width - View.JUMPER_WIDTH / 2) {
            jumper.x = -View.JUMPER_WIDTH / 2;
        }

        if (!started) {
            updateObjects();
            return;
        }

        if (jumper.getY() > Game.getSize().height) {
            gameOver = true;
        }

        double v = jumper.vVelocity;
        int maxY = size.height / 2;
        compressFlag++;
        if (compressFlag > 10) {
            jumper.setSpringsCompressed(false);
        }
        //Bonus
        Bonus bonus = isBonus();
        if (bonus != null && (bonus.getType() == 0 || bonus.getType() == 1)) {
            scores += 100;
            bonuses.remove(bonus);
        }

        //Вертикальное движение
        if (jumper.getY() > maxY || v < 0) {
            if(v<0) jumper.turbo = false;
            Stone stone = isJumperLanded();
            if (stone != null) {
                jumper.vVelocity = jumper.jumpVelocity;
                jumper.setSpringsCompressed(true);
                compressFlag = 0;
                if (stone.getType() == 0) {
                    stone.vVelocity = 5;
                }
                if (stone.getType() == 1) {
                    jumper.vVelocity += 5;
                }
            }
            //Bonus
            if (bonus != null && bonus.getType() == 2) {
                jumper.turbo = true;
                jumper.vVelocity = jumper.jumpVelocity * 4;
                bonuses.remove(bonus);
            }
            jumper.moveVertical((int) Math.round(v));

        } else {
            for (Stone stone : stones) {
                stone.moveVertical((int) Math.round(v));
            }
            for (Bonus currentBonus : bonuses) {
                currentBonus.moveVertical((int) Math.round(v));
            }
            scores += (Math.abs(Math.round(v))) / 5;
            HEIGHT += Math.abs(Math.round(v))/5;
        }
        //Графитация
        jumper.vVelocity -= G * (nanos / 10000000);

        updateObjects();
    }
}