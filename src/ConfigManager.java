import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConfigManager {
    static String configPath;

    ConfigManager() {
        configPath = "config.txt";
    }

    ConfigManager(String configPath) {
        this.configPath = configPath;
    }

    Config parseConfig() throws IOException {
        Config config = new Config();
        //handle exception
        while(true) {
            PlainTextHandler handler = new PlainTextHandler();
            List<String> configInfo = handler.readFile(configPath);
            int index;
            String type = configInfo.get(0);
            type = type.replaceAll("\\s+", "");
            index = type.indexOf(":");
            if(index == -1)
            {
                System.out.println("配置文件中缺少冒号");
            }
            type = type.substring(index+1, type.length());
            String todoFilePath = configInfo.get(1);
            todoFilePath = todoFilePath.replaceAll("\\s+", "");
            index = todoFilePath.indexOf(":");
            if(index == -1)
            {
                System.out.println("配置文件中缺少冒号");
            }
            todoFilePath = todoFilePath.substring(index+1, todoFilePath.length());
            if (!type.equals("json") && !type.equals("txt")) {
                System.out.println("请检查你的配置文件");
                System.out.println("输入1退出");
                System.out.println("输入其他键使用默认配置");
                Scanner in = new Scanner(System.in);
                String operation = in.nextLine();
                if (operation.equals("1")) {
                    throw new IOException();
                } else {
                    break;
                }
            }
            else{
                    todoFilePath += "." + type;
                    config = new Config(type, todoFilePath);
            }
            break;
        }
        return config;
    }
}
