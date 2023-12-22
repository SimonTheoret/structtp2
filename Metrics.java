import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Metrics {
    // Returns the most probable following word of firstW
    // from all the documents combined (the stringBlob)
    protected static String biGram(String firstW, String stringBlob){
        MyMap<String, Integer> countMap = new MyMap(10);
        String[] allWord  = stringBlob.split(" ");
        boolean takeNext = false;
        for (String word : allWord) {
            if (takeNext){
                if (countMap.containsKey(word)) { // if map contains  the word, increment
                    Integer i = countMap.get(word);
                     i = i + 1;
                } else{// insert word in map if the word is not already present
                    countMap.put(word, 1);
                }
            }
            if (word.equals(firstW)){
                takeNext = true;
            } else{
                takeNext = false;
            }
        }
        int max = 0;
        String bestString = ""; //empty strings always compare as last
        for (Maps.Entry<String, Integer> e : countMap.entrySet()){
            Integer count = e.getValue();
            String word = e.getKey();
            if(count >= max) { // possible new best string
                if (count == max){ // decide which is best string based on lex. order
                    String[] comp =  {bestString, word};
                    Arrays.sort(comp);
                    bestString = comp[0];
                }
                else {
                    bestString = word;
                }
            }
        }
        return bestString;
    }
}
