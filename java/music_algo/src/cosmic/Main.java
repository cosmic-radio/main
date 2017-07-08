package cosmic;



import org.json.JSONObject;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private static int FRAME_TIME = 60;
    private static int[] steps = {1, 3, 5, 7, 8};

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {

        double[][] data = loadData(Rounding.UP);
        double[] firstFrame = data[0];

        SamplePlayer sp = new SamplePlayer("samples");

        MusicFinder mf = new MusicFinder(4, 1280);
        int offset = mf.bufferData(firstFrame, 0);
        mf.addScale(steps);

        int[] notes = mf.find();

        int coun = 0;
        for(int i : notes){

            System.out.print(i + " ");
            if (coun++ % 10 == 0) System.out.print("\n");

            if(i != 0) sp.playClip(i);
            Thread.sleep(230);
        }
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
