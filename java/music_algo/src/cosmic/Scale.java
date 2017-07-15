package cosmic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by konra on 15.07.2017.
 */
public class Scale {

    private static List<Set<Integer>> scales;

    static {
        scales = new ArrayList<>();
    }

    public static void loadFile(String path) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(path));

        String line = br.readLine();
        while (line != null){

            String[] lineArr = line.split("/");
            Set<Integer> scale = new TreeSet<>();

            for(String s: lineArr){
                scale.add(Integer.parseInt(s));
            }

            scales.add(scale);
            line = br.readLine();
        }
    }

    public static Set<Integer> get(int id){
        return scales.get(id);
    }
}
