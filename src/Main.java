import task.Task;
import task.Epic;
import task.Subtask;
import manager.TaskManager;
import task.Status;

public class Main {

public static void main (String[] args) {

        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача_1", "Описание задачи_1", Status.NEW);
        Task task2 = new Task("Задача_2", "Описание задачи_2", Status.IN_PROGRESS);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Эпик_1", "Описание эпика_1");
        Epic epic2 = new Epic("Эпик_2", "Описание эпика_2");

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        Subtask subtask1 = new Subtask("Подзадача_1", "Описание подзадачи_1", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача_2", "Описание подзадачи_2", Status.NEW, epic1.getId());
        Subtask subtask3 = new Subtask("Подзадача_3", "Описание подзадачи_3", Status.DONE, epic2.getId());

        taskManager.addSubTask(subtask1);
        taskManager.addSubTask(subtask2);
        taskManager.addSubTask(subtask3);
    System.out.println(taskManager.getAllTasks());
    System.out.println(taskManager.getAllEpics());
    System.out.println(taskManager.getAllSubTasks());
    taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getEpic(epic1.getId());
        taskManager.getEpic(epic2.getId());
        taskManager.getSubTask(subtask1.getId());
        taskManager.getSubTask(subtask2.getId());
        taskManager.getSubTask(subtask3.getId());

        taskManager.deleteTask(task1.getId());
        taskManager.deleteEpic(epic1.getId());
    System.out.println(taskManager.getAllTasks());
    System.out.println(taskManager.getAllEpics());
    System.out.println(taskManager.getAllSubTasks());
        taskManager.getTask(task2.getId());
        taskManager.getEpic(epic2.getId());


    }

}

