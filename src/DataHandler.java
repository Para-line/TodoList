import java.io.IOException;
import java.util.List;

public class DataHandler {
    String type;
    String configPath;

    Config conf = new Config();

    public DataHandler() throws IOException{
        ConfigManager parser = new ConfigManager();
        Config conf = parser.parseConfig();
        this.conf = conf;
    }

    public List<String> parseTodo() throws IOException {
        PlainTextHandler textHandler = new PlainTextHandler();
        List<String> todoInfo = textHandler.readFile(this.conf.filePath);
        return todoInfo;
    }

    public void saveFile(List<String> todoInfo) throws IOException{
        if(conf.type=="txt") {
            PlainTextHandler textHandler = new PlainTextHandler();
            textHandler.saveFile(conf.filePath,todoInfo);
        }
        else
        {
            JsonHandler jsonHandler = new JsonHandler();
            jsonHandler.saveFile(conf.filePath,todoInfo);
        }
    }
}
