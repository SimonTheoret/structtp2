import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

// This class has the role of parsing the queries and calling the right functions for the queries.
public class QueryMaker {
    ArrayList<String> queries;
    ArrayList<String> output;
    Corrector corrector;

    // Default constructor
    public QueryMaker() {
    }

    public void parseQueries(MyMap<String, MyMap<File, ArrayList<Integer>>> wordMap) {
        try {
            File myObj = new File("query.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String query = myReader.nextLine();
                queries.add(query);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // General class to represent any query
    protected class Query {
        String rawQuery;
        String out;

        protected Query(String rawQuery) {
            this.rawQuery = rawQuery;
        }

        public Query parseQuery(String rawQuery, Corrector corr) {
            if (rawQuery.charAt(0) == 't') {
                return new BigramQuery(rawQuery, corr);
            }
            if (rawQuery.charAt(0) == 's') {
                return new SearchQuery(rawQuery, corr);
            } else {
                System.out.println("Error, wrong query encountered");
                System.out.println(rawQuery);
                return null;
            }
        }
    }

    // Search queries with possibly many words
    protected class SearchQuery extends Query {
        ArrayList<String> words;

        protected SearchQuery(String rawQuery, Corrector corr) {
            super(rawQuery);
            String[] arr = rawQuery.split(" ");
            words = new ArrayList<>();
            int counter = 0; // make sure to not correct 'search'
            for (String word : arr) {
                if (counter != 0) {
                    words.add(corr.correctWord(word));
                }
                counter += 1;
            }
        }
        protected void go(){
            //this.out =
        }
    }

    // Bigram queries with a single word
    protected class BigramQuery extends Query {
        String word;

        protected BigramQuery(String rawQuery, Corrector corr) {
            super(rawQuery);
            String[] arr = rawQuery.split(" ");
            word = corr.correctWord(arr[arr.length - 1]);
        }
    }
}