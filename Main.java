import java.util.ArrayList;
import java.io.File;


public class Main {
    public static void main(String[] args) {
        MyMap<String, MyMap<File, ArrayList<Integer>>> wordMap = MapMaker.createWordMap();
        //QueryMaker.parseQueries(wordMap);
    }
}
