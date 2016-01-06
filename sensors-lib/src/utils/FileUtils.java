package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FileUtils {

    public static void writeSrtToFile(String str, String file , boolean overwrite) throws FileNotFoundException {

        PrintWriter writer;
        writer = new PrintWriter(new FileOutputStream(file, overwrite));
        writer.write(str+"\n");
        writer.close();

    }
    
}