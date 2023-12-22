import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MapMaker {
    // This class has the role of creating the initial WordMap and FileMap.

    // Creates file map for a word.
    public static MyMap<File, ArrayList<Integer>> createFileMap(String word) {
        MyMap<File, String> fileToStringMap = Cleaner.sendStrings();
        MyMap<File, ArrayList<Integer>> fileMap = new MyMap<>(4);
        for (MyMap.Entry<File, String> e : fileToStringMap.entrySet()) {
            int count = 0;
            fileMap.put(e.getKey(), new ArrayList<Integer>());
            //System.out.println(fileMap);
            for (String w : e.getValue().split(" ")) {
                if (w.equals(word)) {
                    fileMap.get(e.getKey()).add((Integer) count);
                }
                count++;
            }
        }
        return fileMap;
    }

    public static String makeStringBlob() {
        MyMap<File, String> fileToStringMap = Cleaner.sendStrings();
        StringBuilder blob = new StringBuilder();
        for (String str : fileToStringMap.values()) {
            blob.append(str);
        }
        return blob.toString();
    }
    public static MyMap<String, MyMap<File, ArrayList<Integer>>> createWordMap() {
        String allWords = makeStringBlob();
        MyMap<String, MyMap<File, ArrayList<Integer>>> wordMap = new MyMap<>(332);
        for (String w : allWords.split(" ")) {
            wordMap.put(w, createFileMap(w));
        }

        return wordMap;
    }
}
