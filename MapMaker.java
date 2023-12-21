import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MapMaker {
    // This class has the role of creating the initial WordMap and FileMap.

    MyMap<String, MyMap<File, ArrayList<Integer>>> wordMap = new MyMap(1000); // Master word map
    MyMap<File, String> fileToString = new MyMap(100); //
}
