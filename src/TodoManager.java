import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TodoManager {
    public List<TodoItem> todos = new LinkedList<>();
    public int idx = 0;

    void init() throws IOException {
        DataHandler handler = new DataHandler();
        List<String> todoInfo = handler.parseTodo();
        if (todoInfo.size() != 0) {
            for (String info : todoInfo) {
                String[] split = info.split(" ");
                LocalDate date = LocalDate.parse(split[0]);
                String task = split[1].replaceAll("\\(.*?\\)", "");
                String idstr = split[2].substring(split[2].indexOf("：") + 1);
                int id = Integer.parseInt(idstr);
                String priorStr = split[3].substring(split[3].indexOf("：") + 1);
                int priority = Integer.parseInt(priorStr);
                int state;
                if (split[4].equals("x"))
                    state = 0;
                else
                    state = 1;
                TodoItem todo = new TodoItem(date, task, priority, state, id);
                todos.add(todo);
            }
        }
    }


    public List<TodoItem> getAllTodo() {
        return todos;
    }

    //加入todoItem,并且在加入之后排序
    public void addTodo(LocalDate date, String task, int priority, int state) {
        TodoItem todo = new TodoItem(date, task, priority, state, -1);
        int flag = 0;
        for (int i = 0; i < todos.size(); i++) {
            if (todo.compareTo(todos.get(i)) <= 0) {
                todos.add(i, todo);
                flag = 1;
                break;
            }
        }
        if (flag == 0)
            todos.add(todo);
    }

    //通过task查找todo
    public List<Integer> findItem(String s) {
        List<Integer> sub = new LinkedList<>();
        for (int i = 0; i < todos.size(); i++) {
            if (s.equals(todos.get(i).getTask())) {
                sub.add(i);
            }
        }
        return sub;
    }

    public int findTodoById(int id) {
        int cnt = 0;
        int sub = -1;
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == id) {
                sub = i;
                break;
            }
        }
        return sub;
    }

    //通过任务id删除todo
    public void removeTodo(int id) {
        int sub = findTodoById(id);
        todos.remove(sub);
    }

    public List<Integer> findTodoInRange(LocalDate start, LocalDate end) {
        List<Integer> subs = new LinkedList<>();
        for (int i = 0; i < todos.size(); i++) {
            if (start.compareTo(todos.get(i).getDate()) <= 0 && end.compareTo(todos.get(i).getDate()) >= 0) {
                subs.add(i);
            }
        }
        return subs;
    }

    //通过名称查找todo并修改
    public void changeTodo(int id, LocalDate date, String task, int priority, int state) {
        int sub = findTodoById(id);
        todos.remove(sub);
        addTodo(date, task, priority, state);
    }

    //通过名称查找todo
    public List<TodoItem> findTodoByTask(List<Integer> subs) {
        int cnt = 1;
        List<TodoItem> todoList = new LinkedList<>();
        for (int i = 0; i < subs.size(); i++) {
            int sub = subs.get(i);
            TodoItem todo = todos.get(sub);
            todoList.add(todo);
        }
        return todoList;
    }


    //通过时间查找todo
    public List<TodoItem> findTodoByTime(LocalDate start, LocalDate end) {
        int cnt = 1;
        List<TodoItem> todoList = new LinkedList<>();
        for (int i = 0; i < idx; i++) {
            TodoItem todo = todos.get(i);
            if (start.compareTo(todo.getDate()) <= 0 && end.compareTo(todo.getDate()) >= 0) {
                todoList.add(todo);
            }
        }
        return todoList;
    }

    //删除某些时间段的todo
    public void removeTodoInRange(LocalDate start, LocalDate end) {
        List<Integer> subs = findTodoInRange(start, end);
        //输入并判断格式是否正确
        for (int i = 0; i < subs.size(); i++) {
            todos.remove(subs.get(i));
        }
    }

    //改变一个时间段的todo状态
    public void changeTodoStateInRange(LocalDate start, LocalDate end, int state) {
        List<Integer> subs = findTodoInRange(start, end);
        //输入并判断格式是否正确
        for (int i = 0; i < subs.size(); i++) {
            todos.get(subs.get(i)).setState(state);
        }
    }

    public void changeTodoStateById(int id) {
        int sub = findTodoById(id);
        todos.get(sub).setState(~todos.get(sub).getState());
    }

    public List<String> todoListToString() {
        List<String> todoInfo = new LinkedList<>();
        for (TodoItem todo : todos) {
            todoInfo.add(todo.toString());
        }
        return todoInfo;
    }

    public void saveAsFile() throws IOException {
        List<String> todoInfo = todoListToString();
        DataHandler handler = new DataHandler();
        handler.saveFile(todoInfo);
    }
}
