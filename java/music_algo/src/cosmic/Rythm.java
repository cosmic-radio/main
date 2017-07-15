package cosmic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by konra on 15.07.2017.
 */
public class Rythm {

    private static List<char[]> patterns;
    private char[] currPattern;
    int position;

    static {
        patterns = new ArrayList<>();
    }

    public Rythm(){}

    public Rythm(int id){
        currPattern = patterns.get(id);
    }

    public static void loadFile(String path) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(path));

        String line = br.readLine();
        while (line != null){
            patterns.add(line.toCharArray());
            line = br.readLine();
        }
    }

    public void loadPattern(int id){

        currPattern = patterns.get(id);
        position = 0;
    }

    public char nextNote(){

        if(position == 32) position = 0;
        return currPattern[position++];
    }

    public boolean nextBool(){

        if(position == 32) position = 0;
        return currPattern[position++] != '0';
    }
}
