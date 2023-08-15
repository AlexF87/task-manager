package manager;

import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {
    public HashMap<Long, Task> tasks;

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getClass() == Task.class) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            if (task.getClass() == Task.class) {
                tasks.remove(task.getId());
            }
        }
    }

    public List<Epic> getAllEpic() {
        List<Epic> epicList = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getClass() == Epic.class) {
                epicList.add((Epic) task);
            }
        }
        return epicList;
    }

    public List<SubTask> getAllSubTaskForEpic(Long epicId) {
        Epic epic = (Epic)tasks.get(epicId);
        return epic.getSubTaskList();
    }

    public void deleteSubTaskForEpic(Long epicId) {
        Epic epic = (Epic)tasks.get(epicId);
        List<SubTask> subTaskList = epic.getSubTaskList();
        for (SubTask subTask : subTaskList) {
            tasks.remove(subTask.getId());
        }
    }
    public void deleteAllEpic() {
        List<Epic> epicList = getAllEpic();
        for (Epic epic : epicList) {

            //В начале удаляем все SubTask данного эпика
            deleteSubTaskForEpic(epic.getId());

            //Потом удаляем сам Epic
            tasks.remove(epic.getId());
        }
    }


}
