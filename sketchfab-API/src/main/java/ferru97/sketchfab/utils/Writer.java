package ferru97.sketchfab.utils;

import ferru97.sketchfab.api.Model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

public class Writer {
    
    public static void appendRowsTable (File dst, Stream<Model> res) throws FileNotFoundException {
        PrintStream ps = new PrintStream(new FileOutputStream(dst,true));
        res.forEach(p -> ps.println(p.getCommasLine()));
        ps.close();
    }
    
    public static File createTable (String file_name, String commas_header) throws FileNotFoundException, UnsupportedEncodingException {
        File f = new File(file_name+".xls");
        PrintWriter writer = new PrintWriter(f);
        writer.println(commas_header);
        writer.close();
        return f;
    }
}
