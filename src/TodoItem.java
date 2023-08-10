import java.time.LocalDate;
import java.util.Scanner;

public class TodoItem implements Comparable<TodoItem>{
    public static int size = 1000000;
    private LocalDate date;
    private String task;
    private int priority;
    private int state = 0;

    private int id;
    private static boolean[] idUsed = new boolean[size];
    private Scanner in = new Scanner(System.in);

    //如果传入的id为-1，表示还没有分配好id，由构造函数来分配
    public TodoItem(LocalDate date, String task, int priority, int state,int id)
    {
        this.date = date;
        this.task = task;
        this.priority = priority;
        this.state = state;
        if(id==-1)
        {
            for(int i = 0; i < size; i++)
            {
                if(idUsed[i]==false)
                {
                    this.id = i;
                    idUsed[i] = true;
                    break;
                }
            }
        }
        else{
            this.id = id;
            idUsed[id] = true;
        }
    }

    public void changeItem()
    {
        System.out.println("请输入修改之后的时间");
        String time = in.nextLine();
        this.date = LocalDate.parse(time);

        System.out.println("请输入修改之后的任务");
        String task = in.nextLine();
        this.task = task;

        System.out.println("请输入修改之后的优先级");
        int priority = in.nextInt();
        this.priority = priority;

        System.out.println("请输入修改之后的状态");
        int state = in.nextInt();
        this.state = state;
    }

    @Override
    public String toString()
    {
        String s = this.date + " " + this.task + " " + "id："+ this.id +" " + "优先级：" + this.priority + " ";
        if(this.state == 0) {
            s += "x";
        }
        else{
            s += "√";
        }
        return s;
    }

    @Override
    public int compareTo(TodoItem that){
        //如果date不同，按照date升序排序，如果相同，按priority降序排序
        int datecmp = this.date.compareTo(that.date);
        if (datecmp != 0) {
            return datecmp;
        } else {
            return Integer.compare(that.priority,this.priority);
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
