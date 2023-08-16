import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
public class Display {
    TodoManager todo = new TodoManager();
    Scanner cin = new Scanner(System.in);
    int n;

    public void show() throws IOException {
        todo.init();
        while (true) {
            System.out.println("欢迎使用todoList");
            System.out.println("请输入以下数字使用相应功能");
            System.out.println("1.展示所有todo");
            System.out.println("2.增加todo");
            System.out.println("3.按id删除todo");
            System.out.println("4.查询某一个时间范围的todo");
            System.out.println("5.按名字查询todo");
            System.out.println("6.删除某一个时间范围的todo");
            System.out.println("7.按id修改todo");
            System.out.println("8.修改一段时间todo的完成状态");
            System.out.println("9.按id修改todo的完成状态");
            System.out.println("10.退出todoList");
            n = intInputHelper();
            if (n == 1) {
                showAllTodoUI();
            } else if (n == 2) {
                addTodoUI();
            } else if (n == 3) {
                removeTodoByIdUI();
            } else if (n == 4) {
                findTodoInRangeUI();
            } else if (n == 5) {
                findTodoByTaskUI();
            } else if (n == 6) {
                removeTodoInRangeUI();
            } else if (n == 7) {
                changeTodoByIdUI();
            } else if (n == 8) {
                changeTodoStateInRangeUI();
            } else if (n == 9) {
                changeTodoStateByIdUI();
            } else if (n == 10) {
                break;
            } else {
                System.out.println("输入错误，请输入正确的整数");
            }
            System.out.println("请按回车键继续");
            pause();
        }
        todo.saveAsFile();
    }

    private void repeat(Runnable f) {
        String line = cin.findInLine("\n");
        if (line != null) {
            cin.nextLine();
        }
        String s = cin.nextLine();
        if (s.equals("1")) {
            f.run();
        }
    }

    public void showAllTodoUI() {
        List<TodoItem> todoList = todo.getAllTodo();
        for (TodoItem todo : todoList) {
            System.out.println(todo);
        }
    }

    //添加todo的UI
    public void addTodoUI() {
        LocalDate date;
        while (true) {
            System.out.println("请输入todo的时间（例如：2023-08-05）");
            try {
                String datestr = cin.nextLine();
                date = LocalDate.parse(datestr);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("格式输入错误，请重新输入");
            }
        }
        System.out.println("请输入任务（中间不要有空格）");
        String task = cin.nextLine();

        System.out.println("请输入该todo的优先级");
        int priority = intInputHelper();
        todo.addTodo(date, task, priority, 0);
        System.out.println("添加成功");
    }

    public void findTodoInRangeUI() {
        LocalDate[] range = dateInputPrompt();
        if(range == null)
        {
            return;
        }
        LocalDate start = range[0];
        LocalDate end = range[1];
        List<TodoItem> todoList = todo.findTodoByTime(start, end);
        for (TodoItem todo : todoList) {
            System.out.println(todo);
        }
        System.out.println("查询成功");
    }

    public void findTodoByTaskUI() {
        System.out.println("请输入你要查询的todo名称");
        String s = cin.nextLine();
        List<Integer> subs = todo.findItem(s);
        if (subs.size() == 0)
            System.out.println("没有查询到相应的todo");
        else {
            List<TodoItem> todoList = todo.findTodoByTask(subs);
            for (TodoItem todo : todoList) {
                System.out.println(todo);
            }
            System.out.println("查询成功");
        }
    }

    public LocalDate[] dateInputPrompt() {
        LocalDate start;
        LocalDate end;
        while (true) {
            while (true) {
                System.out.println("请输入todo的时间段,中间用空格隔开（例如：2023-08-05 2023-08-06）");
                String datestr = cin.nextLine();
                datestr = datestr.replaceAll("\\s+", " ");
                String split[] = datestr.split(" ");
                try {
                    start = LocalDate.parse(split[0]);
                    end = LocalDate.parse(split[1]);
                    if(start.compareTo(end)>0)
                    {
                        System.out.println("您输入的开始时间晚于结束时间，请重新输入");
                        continue;
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("格式输入错误，请重新输入");
                }

            }
            List<Integer> subs = todo.findTodoInRange(start, end);
            if (subs.size() == 0) {
                System.out.println("您输入的时间范围内没有todo");
                return null;
            } else {
                break;
            }
        }

        LocalDate[] range = new LocalDate[2];
        range[0] = start;
        range[1] = end;
        return range;
    }

    public void removeTodoInRangeUI() {
        LocalDate[] range = dateInputPrompt();
        if(range == null)
        {
            return;
        }
        LocalDate start = range[0];
        LocalDate end = range[1];
        todo.removeTodoInRange(start, end);
        System.out.println("删除成功，按回车键继续");

    }

    public int idInput() {
        int sub;
        int id = -1;
        while (true) {
            id = intInputHelper();
            sub = todo.findTodoById(id);
            if (sub == -1) {
                System.out.println("没有查询到相应的todo，按1退出当前界面，输入其他数字继续查询");
                int operation = cin.nextInt();
                cin.nextLine();
                if (operation == 1) {
                    id = -1;
                    break;
                } else {
                    System.out.println("请重新输入要查询的id");
                    continue;
                }
            }
            break;
        }
        return id;
    }

    public void removeTodoByIdUI() {
        System.out.println("请输入你要删除todo的id");
        int id = idInput();
        if(id==-1)
        {
            return;
        }
        todo.removeTodo(id);
        System.out.println("删除成功，按回车键继续");
    }

    public void changeTodoByIdUI() {
        System.out.println("请输入你要修改todo的id");
        int id = idInput();
        LocalDate date;
        System.out.println("请输入你要修改的todo的日期");
        String datestr = cin.nextLine();
        date = LocalDate.parse(datestr);
        System.out.println("请输入你要修改todo的名字");
        String task = cin.nextLine();
        System.out.println("请输入你要修改的todo的优先级");
        int priority = intInputHelper();
        System.out.println("请输入该todo的完成情况（输入一个整数，0表示未完成，1表示已完成");
        int state = intInputHelper();
        todo.changeTodo(id, date, task, priority, state);
        System.out.println("修改成功，按回车键继续");
    }

    public void changeTodoStateInRangeUI() {
        LocalDate[] range = dateInputPrompt();
        LocalDate start = range[0];
        LocalDate end = range[1];
        System.out.println("请输入todo的完全情况（0代表未完成，1代表已完成）");
        int state = intInputHelper();
        todo.changeTodoStateInRange(start, end, state);
        System.out.println("修改成功，按回车键继续");
    }

    public void changeTodoStateByIdUI() {
        int id;
        System.out.println("请输入你要修改todo的id");
        id = idInput();
        if(id==-1)
        {
            return;
        }
        todo.changeTodoStateById(id);
        System.out.println("修改成功，按回车键继续");
    }

    public void pause() {
        cin.nextLine();
    }

    public int intInputHelper() {
        int num;
        while (true) {
            try {
                num = cin.nextInt();
                cin.nextLine();
                break;
            } catch (InputMismatchException e) {
                cin.nextLine();
                System.out.println("输入无效，请输入一个整数");
            }
        }
        return num;
    }
}

