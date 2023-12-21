import java.util.ArrayList;
import java.io.File;


public class Main {
    public static void main(String[] args) {
        MyMap<Integer, Double> mapTest = new MyMap(10);
        System.out.println(mapTest.table);
        mapTest.put(10, 10.0);
        mapTest.put(11, 11.0);
        mapTest.put(12, 12.0);
        mapTest.put(13, 13.0);
        mapTest.put(14, 14.0);
        mapTest.put(15, 15.0);
        mapTest.put(16, 16.0);
        mapTest.put(17, 17.0);
        mapTest.put(10, 18.0);
        mapTest.put(19, 19.0);
        mapTest.put(20, 20.0);
        for (Maps.MapEntry<Integer, Double> entry: mapTest.table){
            System.out.println(entry.getValue());

            //System.out.println(entry.getValue());
        }
        //MyMap<File, String> map = Cleaner.sendStrings();
        //for (String s : map.values()){
            //System.out.println(s);
        }

    }
