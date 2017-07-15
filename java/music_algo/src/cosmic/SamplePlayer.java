package cosmic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        File dir = new File(samplesPath);
        File[] sampleFiles = dir.listFiles();

        for(File f: sampleFiles){

            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            Clip c = AudioSystem.getClip();
            c.open(ais);

            if(samplesPath.equals("sounds/" + Main.PRESET)){
                int index = Integer.parseInt(f.getName().substring(0,f.getName().indexOf(".")))-1;
                clipNotes[index] = c;
            } else {
                clips.put(f.getName().substring(0,f.getName().indexOf(".")), c);
            }
        }
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
