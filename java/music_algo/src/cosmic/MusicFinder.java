package cosmic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by konra on 08.07.2017.
 */
public class MusicFinder {

    Set<Integer> scale;
    int sampleRate;
    int bufferSize;
    double[][] buffer;
    int[][] normalizedBuffer;

    public MusicFinder(int initSampleRate, int bufferSize){
        this.sampleRate = initSampleRate;
        this.bufferSize = bufferSize;
    }

    public void setScale(Set<Integer> scale){
        this.scale = scale;
    }

    public int bufferData(double[][] data, int channels, int offset){

        buffer = new double[channels][bufferSize];

        for(int c = 0; c < channels; c++) System.arraycopy(data[c], offset, this.buffer[c], 0, bufferSize);
        return offset + bufferSize;
    }

    public void normalizeChannels(){

        normalizedBuffer = new int[buffer.length][bufferSize];

        for(int j = 0; j < buffer.length; j++){
            for(int i = 0; i < bufferSize; i++){

                int n = (int) buffer[j][i];
                n = n % 12;
                normalizedBuffer[j][i] = n;
            }
        }
    }

    public int[] findMelody(Rythm rythm){

        int best = 0;
        int[] bestNotes = null;

        for(int j = 0; j < normalizedBuffer.length; j++){

            int score = 0;
            int[] notes = new int[bufferSize];

            for(int i = 0; i < bufferSize; i++){

                int note;
                if(rythm.nextBool()) note = normalizedBuffer[j][i];
                else note = 0;

                if(!scale.contains(note)) note = 0;
                else score += 1;

                notes[i] = note;
            }

            if(score > best){
                bestNotes = notes;
                best = score;
            }
        }

        return bestNotes;
    }
}
