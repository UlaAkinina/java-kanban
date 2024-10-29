package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;


import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {

    private int id = 1;
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();


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

        updateStatusEpic(epic);

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
        if (tasks.get(id) != null) {
            tasks.remove(id);
        }

    }


    public void deleteSubTask(int id) {

        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtaskIds().remove((Integer) subtask.getId());
            updateStatusEpic(epic);
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
            updateStatusEpic(epic);
        }
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void updateStatusEpic(Epic epic) {
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtasks.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        for (int subtaskId : subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);
            switch (subtask.getStatus()) {
                case NEW:
                    epic.setStatus(Status.NEW);
                    break;
                case DONE:
                    epic.setStatus(Status.DONE);
                    break;
                default:
                    epic.setStatus(Status.IN_PROGRESS);
                    break;


            }
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
            updateStatusEpic(epic);
        }
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateStatusEpic(epic);
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





