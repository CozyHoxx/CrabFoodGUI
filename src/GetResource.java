import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class GetResource {
    private Object object;

    public void playAudio(String file) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Media x = new Media(Objects.requireNonNull(classLoader.getResource("Audios/" + file + ".wav")).toString());
            object = new MediaPlayer(x);
            ((MediaPlayer) object).setVolume(0.1);
            ((MediaPlayer) object).play();
        } catch (NullPointerException ex) {
            System.out.println("No audio");

        }
    }


    public void pauseAudio() {
        ((MediaPlayer) object).pause();
    }

    public void continueAudio() {
        ((MediaPlayer) object).play();
    }

    public void stopAudio() {
        ((MediaPlayer) object).stop();
    }

}
