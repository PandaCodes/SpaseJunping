package controller.sceneController;

import controller.Game;
import model.PlayModel;
import view.View;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PlayController extends Controller {
    private PlayModel playModel;

    PlayController(PlayModel model) {
        playModel = model;
    }

    @Override
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                playModel.moveJumperRight();
                break;
            case KeyEvent.VK_LEFT:
                playModel.moveJumperLeft();
                break;
            case KeyEvent.VK_SPACE:
                if (!playModel.isStarted()) {
                    playModel.Go();
                } else if (isRunning()) {
                    pause();
                } else {
                    startAfter(null);
                }
                break;
        }
    }

    @Override
    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                //if (playModel.jumper.)
                playModel.stopJumper();
                break;
            case KeyEvent.VK_LEFT:
                playModel.stopJumper();
                break;
        }
    }

    @Override
    public void someFunction() {
        playModel.jumper.setEyesClosed(true);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playModel.jumper.setEyesClosed(false);
    }

    @Override
    public void run() {
        waitForPrev();
        long previousIterationTime = System.nanoTime();
        while (isRunning()) {
            long now = System.nanoTime();
            long nanosPassed = now - previousIterationTime;
            previousIterationTime = now;
            playModel.update(nanosPassed);
            View.draw();
            if (playModel.isGameOver()) {
                Game.currentScore = playModel.getScore();
                setScene(GAME_OVER_SCENE, this);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        if (isRunning()) {
            stop();
            playModel.setPaused(true);
            View.draw();
        }
    }

    @Override
    public void startAfter(Controller prevController) {
        if (!isRunning()) {
            super.startAfter(prevController);
            playModel.setPaused(false);
            new BlinkThread(this).start();
        }
    }

    private void updateRecords() {
        if (playModel.getScore() != 0) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File("src/resources/records")));
                List<Long> records = new ArrayList<Long>();
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.equals("")) {
                        records.add(Long.valueOf(line));
                    }
                }
                br.close();
                records.add(playModel.getScore());
                Collections.sort(records, Collections.reverseOrder());
                if (records.size() > 8) {
                    records.remove(records.size() - 1);
                }
                FileWriter fw = new FileWriter(new File("src/resources/records"));
                for (Long record : records) {
                    fw.write(String.valueOf(record) + "\n");
                }
                fw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        if (isRunning()) {
            super.stop();
            updateRecords();
            View.draw();
        }
    }

}

