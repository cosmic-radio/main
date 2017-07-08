package cosmic;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * Created by konra on 02.07.2017.
 */
public class SamplePlayer {

    private Clip[] clips;

    public SamplePlayer(String samplesPath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {


        clips = new Clip[12];

        File dir = new File(samplesPath);
        File[] sampleFiles = dir.listFiles();

        for(File f: sampleFiles){

            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            Clip c = AudioSystem.getClip();
            c.open(ais);

            int index = Integer.parseInt(f.getName().substring(0,f.getName().indexOf(".")))-1;
            clips[index] = c;
        }
    }

    public void playClip(int clipNo) throws InterruptedException {

        clips[clipNo].start();
    }
}
