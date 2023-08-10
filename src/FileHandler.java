import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public interface FileHandler {
    List<String> readFile(String path) throws IOException;
    void saveFile(String path, List<String> info) throws IOException;
}
