public class Config {
    static String type;
    static String filePath;
    String DEFAULT_TYPE = "txt";
    String DEFAULT_FILE_PATH = "todo.txt";

    Config(){
        this.type = DEFAULT_TYPE;
        this.filePath = DEFAULT_FILE_PATH;
    }

    Config(String type, String filePath){
        this.type = type;
        this.filePath = filePath;
    }

}