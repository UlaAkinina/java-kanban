package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;


import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {

    private int id = 1;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();


    public void addTask(Task task) {
        task.setId(id);
        tasks.put(task.getId(), task);
        id++;
    }


    public void addSubTask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);

        if (epic == null) {
            return;
        }
        subtask.setId(id);
        subtasks.put(subtask.getId(), subtask);
        epic.setSubtaskIds(subtask.getId());

        epicCheckStatus(epic);

        id++;
    }

    public void addEpic(Epic epic) {
        epic.setId(id);
        epics.put(epic.getId(), epic);
        id++;
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }


    public ArrayList<Subtask> getAllSubTasks() {
        return new ArrayList<>(subtasks.values());
    }


    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void getTask(int id) {
        tasks.get(id);
    }


    public void getSubTask(int id) {
        subtasks.get(id);
    }


    public void getEpic(int id) {
        epics.get(id);
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }


    public void deleteSubTask(int id) {

        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtaskIds().remove((Integer) subtask.getId());
            epicCheckStatus(epic);
            subtasks.remove(id);

        }
    }


    public void deleteEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }


    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubTasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            epicCheckStatus(epic);
        }
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void epicCheckStatus(Epic epic) {
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtasks.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        int newStat = 0;
        for (int subtaskId : subtaskIds) {

            Subtask subtask = subtasks.get(subtaskId);
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                    epic.setStatus(Status.IN_PROGRESS);
                    return;
                }
                if (subtask.getStatus() == Status.NEW) {
                    newStat++;
                }
            }
            if (newStat == epic.getSubtaskIds().size()) {
                epic.setStatus(Status.NEW);
            } else {
                epic.setStatus(Status.DONE);
            }
           }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }

    }


    public void updateSubTask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            epicCheckStatus(epic);
        }
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            epicCheckStatus(epic);
        }
    }

    public ArrayList<Subtask> SubtasksByEpicId(int id) {
        ArrayList<Subtask> subtasksNew = new ArrayList<>();
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
                subtasksNew.add(subtasks.get(epic.getSubtaskIds().get(i)));
            }
        }
            return subtasksNew;

    }
}





