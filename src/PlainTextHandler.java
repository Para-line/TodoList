import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class PlainTextHandler implements FileHandler {
    @Override
    public List<String> readFile(String path) throws IOException {
        File file = new File(path);
        if(!file.exists())
        {
            file.createNewFile();
        }
        Scanner fileIn = new Scanner(file);
        List<String> info = new LinkedList<>();
        int idx = 0;
        while(fileIn.hasNextLine())
        {
            String s = fileIn.nextLine();
            if(s!="") {
                info.add(s);
            }
        }
        return info;
    }

    @Override
    public void saveFile(String path, List<String> todoInfo) throws IOException {
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            for (int i = 0; i < todoInfo.size(); i++) {
                writer.println(todoInfo.get(i));
            }
            writer.flush();
    }
}
