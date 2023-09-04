package manager;

import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {
    public HashMap<Long, Task> tasks;
    static long id = 1;

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
        Epic epic = (Epic) tasks.get(epicId);
        return epic.getSubTaskList();
    }

    public void deleteSubTaskForEpic(Long epicId) {
        Epic epic = (Epic) tasks.get(epicId);
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

    public void deleteById(long id) {
        tasks.remove(id);
    }

    public Task getTaskById(long id) {
        return tasks.get(id);
    }

    public Epic getEpicById(long id) {
        return (Epic) tasks.get(id);
    }

    public SubTask getSubTaskById(long id) {
        return (SubTask) tasks.get(id);
    }

    public Task createTask(Task task) {
        if (task != null && task.getClass() == Task.class) {
            task.setId(id++);
            tasks.put(task.getId(), task);
        }
        return task;
    }

    public Epic createEpic(Epic epic) {
        if (epic != null && epic.getClass() == Epic.class) {
            epic.setId(id++);
            tasks.put(epic.getId(), epic);
        }
        return epic;
    }

    public SubTask createSubTask(SubTask subTask) {
        if (subTask != null && subTask.getClass() == SubTask.class) {
            subTask.setId(id++);
            tasks.put(subTask.getId(), subTask);
        }
        return subTask;
    }

    public Task updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return null;
        } else {
            Task oldTask = tasks.get(task.getId());
            if (!(oldTask.getDescription().equals(task.getDescription()))) {
                oldTask.setDescription(task.getDescription());
            }
            if (!(oldTask.getName().equals(task.getName()))) {
                oldTask.setName(task.getName());
            }
            if (!oldTask.getStatus().equals(task.getStatus())) {
                oldTask.setStatus(task.getStatus());
            }
            return oldTask;
        }
    }

    public Epic updateEpic(Epic epic) {
        if (!tasks.containsKey(epic.getId())) {
            return null;
        } else {
            Epic oldEpic = (Epic) tasks.get(epic.getId());
            if (!(oldEpic.getDescription().equals(epic.getDescription()))) {
                oldEpic.setDescription(epic.getDescription());
            }
            if (!(oldEpic.getName().equals(epic.getName()))) {
                oldEpic.setDescription((epic.getName()));
            }
            if (!oldEpic.getStatus().equals(epic.getStatus())) {
                oldEpic.setStatus(epic.getStatus());
            }
            return oldEpic;
        }
    }

    public SubTask updateSubTask(SubTask subTask) {
        if (!tasks.containsKey(subTask.getId())) {
            return null;
        } else {
            SubTask oldSubTask = (SubTask) tasks.get(subTask.getId());
            if (!(oldSubTask.getName().equals(subTask.getName()))) {
                oldSubTask.setName(subTask.getName());
            }
            if (!(oldSubTask.getDescription().equals(subTask.getDescription()))) {
                oldSubTask.setDescription(subTask.getDescription());
            }
            if (!oldSubTask.getStatus().equals(subTask.getStatus())) {
                oldSubTask.setStatus(subTask.getStatus());
            }
            return oldSubTask;
        }
    }
}
