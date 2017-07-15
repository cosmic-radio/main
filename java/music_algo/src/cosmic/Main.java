package cosmic;

import org.json.JSONObject;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {

    static int i;
    static String PRESET = "nz";

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {

        double[][] data = loadData(Rounding.UP);
        double[] firstFrame = data[0];

       Rythm.loadFile("rythm");
       Scale.loadFile("scales");

       Rythm kick = new Rythm(0);
       Rythm hat = new Rythm(1);
       Rythm r1 = new Rythm(6);
       Rythm r2 = new Rythm(5);


        MusicFinder mf = new MusicFinder(2, 3200);

        mf.setScale(Scale.get(3));

        int offset = mf.bufferData(data, 40, 0);

        mf.normalizeChannels();

        int[] notes1 = mf.findMelody(r1);
        int[] notes2 = mf.findMelody(r2);

        mf.setScale(Scale.get(2));
        int[] notes3 = mf.findMelody(kick);

        SamplePlayer synth = new SamplePlayer("sounds/" + PRESET);
        SamplePlayer synth2 = new SamplePlayer("sounds/" + PRESET);
        SamplePlayer drums = new SamplePlayer("sounds/drums");

        Integer[] ch = {5,7,2,0,9};
        List<Integer> chord = Arrays.asList(ch);

        i = 0;
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {

            System.out.print("[" + notes1[i] + ", " + notes2[i] + "]");
            if (i++ % 10 == 0) System.out.print("\n");

            if(notes1[i] != 0) synth.playChordRandom(notes1[i], 0.2, 7);
            if(notes2[i] != 0 && chord.contains(notes1[i] - notes2[i])) synth2.playNote(notes2[i]);

            if(notes3[i] != 0) drums.playClip("hatl");

            }
        }, 0, 100);
    }

    private static double[][] loadData(Rounding rounding) throws FileNotFoundException {

        File in = new File("out.txt");
        Scanner scan = new Scanner(new FileInputStream(in));

        String headerStr = scan.nextLine();
        JSONObject header = new JSONObject(headerStr);

        int numOfFrames = header.getInt("number_of_half_frames");

        double[][] data = new double[numOfFrames][];

        int frame = 0;
        String line = scan.nextLine();

        while(line != null){

            String[] strArr = line.split(";");
            double[] valueArr = new double[strArr.length];

            for(int i = 0; i < strArr.length; i++){

                double value = Double.parseDouble(strArr[i]);
                valueArr[i] = rounding.exec(value);
            }

            data[frame++] = valueArr;

            try{
                line = scan.nextLine();
            } catch (NoSuchElementException e){
                line = null;
            }
        }

        return data;
    }


    enum Rounding {
        NONE{
            double exec(double in){return in;}
        }, UP{
            double exec(double in){return Math.ceil(in);}
        }, DOWN{
            double exec(double in){return Math.floor(in);}
        };

        abstract double exec(double in);
    }
}
