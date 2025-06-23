package models;
public class TaskForTeleSales {
    private int id;
    private int accountId;
    private String taskTitle;
    private String dateAssigned;
    private int isCompleted;

    public TaskForTeleSales(int id, int accountId, String taskTitle, String dateAssigned, int isCompleted) {
        this.id = id;
        this.accountId = accountId;
        this.taskTitle = taskTitle;
        this.dateAssigned = dateAssigned;
        this.isCompleted = isCompleted;
    }

    public int getId() { return id; }
    public int getAccountId() { return accountId; }
    public String getTaskTitle() { return taskTitle; }
    public String getDateAssigned() { return dateAssigned; }
    public int getIsCompleted() { return isCompleted; }
}
