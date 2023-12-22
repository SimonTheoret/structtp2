import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

// This class has the role of parsing the queries and calling the right functions for the queries.
public class QueryMaker {
    ArrayList<String> queries;
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
                System.out.println("Error, wrong query encoutered");
                System.out.println(rawQuery);
                return null;
            }
        }
    }

    // Search queries with many words
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

    // This class corrects the words in a query.
    protected class Corrector {
        MyMap<String, MyMap<File, ArrayList<Integer>>> wordMap;
        HashSet<String> words;

        Corrector(MyMap<String, MyMap<File, ArrayList<Integer>>> wordMap) {
            this.wordMap = wordMap;
            this.words = wordMap.keySet();
        }

        protected String correctWord(String word) {
            String corr = word;
            double dist = 1000.0;
            for (String s : this.words) {
                double d = distance(word, s, 20);
                if (d < dist) {
                    dist = d;
                    corr = s;
                }
            }
            return corr;
        }

        //Measures the distance between the two strings. This function comes from
        // https://github.com/tdebatty/java-string-similarity
        public final double distance(final String s1, final String s2,
                                     final int limit) {
            if (s1 == null) {
                throw new NullPointerException("s1 must not be null");
            }

            if (s2 == null) {
                throw new NullPointerException("s2 must not be null");
            }

            if (s1.equals(s2)) {
                return 0;
            }

            if (s1.length() == 0) {
                return s2.length();
            }

            if (s2.length() == 0) {
                return s1.length();
            }

            // create two work vectors of integer distances
            int[] v0 = new int[s2.length() + 1];
            int[] v1 = new int[s2.length() + 1];
            int[] vtemp;

            // initialize v0 (the previous row of distances)
            // this row is A[0][i]: edit distance for an empty s
            // the distance is just the number of characters to delete from t
            for (int i = 0; i < v0.length; i++) {
                v0[i] = i;
            }

            for (int i = 0; i < s1.length(); i++) {
                // calculate v1 (current row distances) from the previous row v0
                // first element of v1 is A[i+1][0]
                //   edit distance is delete (i+1) chars from s to match empty t
                v1[0] = i + 1;

                int minv1 = v1[0];

                // use formula to fill in the rest of the row
                for (int j = 0; j < s2.length(); j++) {
                    int cost = 1;
                    if (s1.charAt(i) == s2.charAt(j)) {
                        cost = 0;
                    }
                    v1[j + 1] = Math.min(
                            v1[j] + 1,              // Cost of insertion
                            Math.min(
                                    v0[j + 1] + 1,  // Cost of remove
                                    v0[j] + cost)); // Cost of substitution

                    minv1 = Math.min(minv1, v1[j + 1]);
                }

                if (minv1 >= limit) {
                    return limit;
                }

                // copy v1 (current row) to v0 (previous row) for next iteration
                //System.arraycopy(v1, 0, v0, 0, v0.length);

                // Flip references to current and previous row
                vtemp = v0;
                v0 = v1;
                v1 = vtemp;

            }

            return v0[s2.length()];
        }
    }
}