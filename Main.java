import java.util.ArrayList;
import java.io.File;


public class Main {
    public static void main(String[] args) {
        MapMaker mM = new MapMaker();
        //QueryMaker.parseQueries(wordMap);
        ArrayList<String> query = new ArrayList<String>();
        query.add("planet");
        File f = Metrics.mostPertinentFile(query, mM);
        System.out.println(f);

    }
}
