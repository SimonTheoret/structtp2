import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.util.HashSet;
import java.util.Map;

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
    protected static double tf(String word, String document){
        double counter = 0.0;
        String[] wordsArray = document.split(" ");
        for (String s: wordsArray){
            if (s.equals(word)) {
                counter = counter + 1.0 ;
            }
        }
        return counter / wordsArray.length;
    }

    protected static double idf(String word, MapMaker mapMaker){
        int D = mapMaker.fileToStringMap.size();
        MyMap<File, ArrayList<Integer>> fileMap =  mapMaker.wordMap.get(word);
        ArrayList<ArrayList<Integer>> values = fileMap.values();
        double count = 0.0;
        for (ArrayList<Integer> content : values){
            if (!content.isEmpty()){
                count = count + 1.0;
            }
        }
        return 1.0 + Math.log((1.0+D)/(1+count));
    }
    // Returns the tf-idf score for the given word,
    // when considered in the document 'document'.
    protected static double tfidf(String word, String document, MapMaker mapMaker){
        return tf(word, document) * idf(word, mapMaker);
    }
    //Calculates the most pertinentFile for a given word
    public static MyMap<File, Double> calculateScores(String word,
                                                        MapMaker mapMaker,
                                                        MyMap<File, Double> fileToScoreMap )
    {
        ArrayList<File> files = mapMaker.listOfFiles;
        for (File f: files){
            String document = mapMaker.fileToStringMap.get(f);
            double score = tfidf(word, document, mapMaker );
            double newScore = fileToScoreMap.get(f);
            newScore = newScore + score;

        }
        return fileToScoreMap;
    }
    public static File mostPertinentFile(ArrayList<String> words, MapMaker mapMaker){
        ArrayList<File> files = mapMaker.listOfFiles;
        MyMap<File, Double> fileToScoreMap = new MyMap<>(files.size());
        for (File f: files){
            fileToScoreMap.put(f, 0.0);
        }
        for (String w : words){
            calculateScores(w, mapMaker, fileToScoreMap);
        }
        File bestFile =  files.get(0);
        Double bestScore = 0.0;
        for (MyMap.Entry<File, Double> e : fileToScoreMap.entrySet()){
            double score = e.getValue();
            File file = e.getKey();
            if (score >= bestScore){
                if (score == bestScore){
                    File[] comp = {file, bestFile};
                    Arrays.sort(comp);
                    bestFile = comp[0];
                    bestScore = score;
                } else{
                    bestFile = file;
                    bestScore = score;
                }
            }
        }
        System.out.println(bestScore);
        return bestFile;
    }
}
