package model;


import controller.Game;
import view.View;

import java.awt.*;
import java.util.Random;

public class MenuJumper extends Jumper {
    private final int JUMP = 0, LOOK = 1, FLY = 2, FREE = 3;
    //For looking:
    private final int LOOK_APPEAR = 1, LOOKING = 0, LOOK_HIDE = -1;
    private double angle;
    private int lookState;
    private int lookSide;
    private int maxCoord;

    private int state;
    private int counter;

    private Random rnd = new Random();

    private final double G = 0.2;
    private final int WAIT_TIME = 200;


    MenuJumper() {
        free();
    }
    public double getAngle() { return  angle; }

    protected void update(double beats) {
       switch (state) {
           case FREE :
               if (counter == 0) {
                   state = rnd.nextInt(2);
                   if (state == JUMP) {
                       counter = rnd.nextInt(4) + 1;
                       vVelocity = jumpVelocity + 1 + rnd.nextInt(7);
                       dx =  rnd.nextInt(7) - 3;
                       y = Game.getSize().height;
                       x = rnd.nextInt(Game.getSize().width - 2* View.JUMPER_WIDTH) + View.JUMPER_WIDTH/2 ;
                   }
                   else if (state == LOOK) {
                       counter = rnd.nextInt(400) + 200;
                       lookSide = rnd.nextInt(4);
                       angle = lookSide * Math.PI/2;
                       lookState = LOOK_APPEAR;
                       switch (lookSide) {
                           case 0 :
                               x = rnd.nextInt(Game.getSize().width - 2* View.JUMPER_WIDTH) + View.JUMPER_WIDTH/2 ;
                               y = Game.getSize().height;
                               maxCoord = y - View.JUMPER_HEIGHT*3/4;
                               break;
                           case 3 :
                               y = rnd.nextInt(Game.getSize().height - 2* View.JUMPER_HEIGHT) + View.JUMPER_HEIGHT*3/2;
                               x = Game.getSize().width;
                               maxCoord = x - View.JUMPER_WIDTH*3/4;
                               break;
                           case 2 :
                               x = rnd.nextInt(Game.getSize().width - 2* View.JUMPER_WIDTH) + View.JUMPER_WIDTH*3/2 ;
                               y = 0;
                               maxCoord = View.JUMPER_HEIGHT/2;
                               break;
                           case 1 :
                               y = rnd.nextInt(Game.getSize().height - 2* View.JUMPER_HEIGHT) + View.JUMPER_HEIGHT/2 ;
                               x = 0;
                               maxCoord = View.JUMPER_WIDTH*3/4;
                               break;

                       }
                   }
               }
               else counter--;
               break;
           case JUMP :
               jump(beats);
               break;
           case LOOK:
               look(beats);
               break;
           case FLY :
               //fly(beats);
               //y =counter;
               break;
       }
    }
    private void jump(double beats) {
        moveVertical((int) Math.round(vVelocity * beats));
        moveHorizontal();
        vVelocity -= G * beats;
        if (y + View.JUMPER_HEIGHT > Game.getSize().height && vVelocity < 0) {
            if (counter != 0) {
                vVelocity = jumpVelocity + rnd.nextInt(6);          //пружинки
                dx = rnd.nextInt(7) - 3;
                counter--;
            }
            else if (y > Game.getSize().height){
                free();
            }
        }
    }
    private void look(double beats) {

        switch (lookSide) {
            case 0:
                y -= lookState;
                if (y <= maxCoord) lookState = LOOKING;
                break;
            case 1:
                x += lookState;
                if (x >= maxCoord) lookState = LOOKING;
                break;
            case 2:
                y += lookState;
                if (y >= maxCoord) lookState = LOOKING;
                break;
            case 3:
                x -= lookState;
                if (x <= maxCoord) lookState = LOOKING;
                break;
        }
        if (lookState == LOOKING) {
            if (counter > 0) {
                counter--;
            } else {
                lookState = LOOK_HIDE;
            }
        }  else if (lookState == LOOK_HIDE)
            if (x < 0 || x > Game.getSize().width || y < 0 || y > Game.getSize().height) {
                free();
            }

    }
    private void free() {
        state = FREE;
        counter = rnd.nextInt(WAIT_TIME);
        angle = 0;
        lookSide = 0;
        x = -1000;
        y = -1000;
    }

    public void click(Point point) {
        if (!isOnJumper(point)) return;
        if (state == JUMP)
            vVelocity = 20;
        else if (state == LOOK)
            lookState = LOOK_HIDE;
    }

    private boolean isOnJumper(Point point) {

        switch (lookSide) {
            case 0:
                if (point.x > x && point.x < x + View.JUMPER_WIDTH && point.y > y && point.y < y + View.JUMPER_HEIGHT)
                    return true;
                break;
            case 1:
                if (point.x > x - View.JUMPER_HEIGHT && point.x < x && point.y > y && point.y < y + View.JUMPER_WIDTH)
                    return true;
                break;
            case 2:
                if (point.x > x - View.JUMPER_WIDTH && point.x < x && point.y > y - View.JUMPER_HEIGHT && point.y < y)
                    return  true;
                break;
            case 3:
                if (point.x > x && point.x < x + View.JUMPER_HEIGHT && point.y > y - View.JUMPER_WIDTH && point.y < y)
                    return  true;
                break;
        }
        return false;
    }
}
