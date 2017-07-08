package cosmic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by konra on 08.07.2017.
 */
public class MusicFinder {

    List<Scale> scales;
    int sampleRate;
    int bufferSize;
    int[] buffer;

    public MusicFinder(int initSampleRate, int bufferSize){
        this.sampleRate = initSampleRate;
        this.bufferSize = bufferSize;
        buffer = new int[bufferSize];
        scales = new ArrayList<>();
    }

    public void addScale(Scale scale){
        scales.add(scale);
    }

    public void addScale(int[] scale){

        Scale sc = new Scale(scale);
        scales.add(sc);
    }

    public int bufferData(double[] data, int offset){

        int[] normalized = new int[data.length];

        for(int i = offset; i < offset + bufferSize*sampleRate; i++){

            int n = (int) data[i];
            n = n % 12;
            normalized[i] = n;
        }

        for(int i = 0; i < bufferSize; i++){

            try{
                buffer[i] = normalized[offset+i*sampleRate];

            } catch (ArrayIndexOutOfBoundsException e){
                return -1;
            }
        }
        return offset + bufferSize*sampleRate;
    }

    public int[] find(){

        int[] notes = new int[bufferSize];

        for(int i = 0; i < bufferSize; i++){

            if(scales.get(0).steps.contains(buffer[i])) notes[i] = buffer[i];
            else notes[i] = 0;
        }
        return notes;
    }


    class Scale {

        Set<Integer> steps;

        public Scale(int[] steps){

            this.steps = new TreeSet<>();
            for(int step: steps){
                this.steps.add(step);
            }
        }
    }
}
