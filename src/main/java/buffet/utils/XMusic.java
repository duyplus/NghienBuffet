package buffet.utils;

import buffet.ui.MainUI;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class XMusic {

    public FileInputStream fis;
    public BufferedInputStream bis;
    public Player player;
    public long pauseLocate;
    public long totalLength;
    public String fileName;

    public void Play(String path) {
        try {
            fis = new FileInputStream(path);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            totalLength = fis.available();
            fileName = path + "";
        } catch (IOException | JavaLayerException ex) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                    if (player.isComplete() && MainUI.count == 1) {
                        Play(fileName);
                    }
                    if (player.isComplete()) {
                        MainUI.songName.setText("");
                    }
                } catch (JavaLayerException ex) {
                    return;
                }
            }
        }.start();
    }

    public void Resume() {
        try {
            fis = new FileInputStream(fileName);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            fis.skip(totalLength - pauseLocate);
        } catch (IOException | JavaLayerException ex) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                    return;
                }
            }
        }.start();
    }

    public void Pause() {
        if (player != null) {
            try {
                pauseLocate = fis.available();
                player.close();
            } catch (IOException ex) {
                return;
            }
        }
    }

    public void Stop() {
        try {
            if (player != null) {
                player.close();
                pauseLocate = 0;
                totalLength = 0;
            }
        } catch (Exception e) {
            return;
        }
    }
}
