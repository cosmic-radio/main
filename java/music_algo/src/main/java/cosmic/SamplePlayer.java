package cosmic;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.sound.sampled.*;

/**
 * Created by konra on 02.07.2017.
 */
public class SamplePlayer {

    private Clip[] clipNotes;
    private Map<String, Clip> clips;


    public SamplePlayer(String samplesPath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        clipNotes = new Clip[12];
        clips = new HashMap<>();

        for(int i = 0; i < 12; i++){
            URL url = getClass().getClassLoader().getResource("sounds/nz/" + i + 1 + ".wav");
            AudioInputStream as = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(as);
            clipNotes[i] = c;
        }

        URL url = getClass().getClassLoader().getResource("sounds/drums/hatl.wav");
        AudioInputStream as = AudioSystem.getAudioInputStream(url);
        Clip hat = AudioSystem.getClip();
        hat.open(as);

        clips.put("hatl", hat);
    }

    public void playNote(int clipNo){

        clipNotes[clipNo].setFramePosition(0);
        clipNotes[clipNo].start();
    }

    public void playChord(int clipNo, int type){

        clipNotes[clipNo].setFramePosition(0);
        clipNotes[clipNo].start();
        int chord = (clipNo+type)%12;

        clipNotes[chord].setFramePosition(0);
        clipNotes[chord].start();
    }

    public void playChordRandom(int clipNo, double chance, int type){

        clipNotes[clipNo].setFramePosition(0);
        clipNotes[clipNo].start();
        int chord = (clipNo+type)%12;
        if(Math.random() < chance){
            clipNotes[chord].setFramePosition(0);
            clipNotes[chord].start();
        }

    }

    public void playClip(String clipId){

        clips.get(clipId).setFramePosition(0);
        clips.get(clipId).start();
    }
}
