package cosmic;


import org.apache.commons.io.IOUtils;

import java.io.*;
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
            int n = i + 1;
            InputStream is = Main.class.getClassLoader().getResourceAsStream("sounds/nz/" + n + ".wav");
//            System.out.println(is);
//            byte[] arr = IOUtils.toByteArray(Main.class.getClassLoader().getResourceAsStream("sounds/nz/" + i + 1 + ".wav"));
//            ByteArrayInputStream s = new ByteArrayInputStream(arr);
            BufferedInputStream bis = new BufferedInputStream(is);
            AudioInputStream as = AudioSystem.getAudioInputStream(bis);
            Clip c = AudioSystem.getClip();
            c.open(as);
            clipNotes[i] = c;
        }

        InputStream is = getClass().getClassLoader().getResourceAsStream("sounds/drums/hatl.wav");
        AudioInputStream as = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
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
