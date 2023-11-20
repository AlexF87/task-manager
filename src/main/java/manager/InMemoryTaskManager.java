package manager;

import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    public HashMap<Long, Task> tasks;
    static long id = 1;

    @Override
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getClass() == Task.class) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    @Override
    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            if (task.getClass() == Task.class) {
                tasks.remove(task.getId());
            }
        }
    }

    @Override
    public List<Epic> getAllEpic() {
        List<Epic> epicList = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getClass() == Epic.class) {
                epicList.add((Epic) task);
            }
        }
        return epicList;
    }

    @Override
    public List<SubTask> getAllSubTaskForEpic(Long epicId) {
        Epic epic = (Epic) tasks.get(epicId);
        return epic.getSubTaskList();
    }

    @Override
    public void deleteSubTaskForEpic(Long epicId) {
        Epic epic = (Epic) tasks.get(epicId);
        List<SubTask> subTaskList = epic.getSubTaskList();
        for (SubTask subTask : subTaskList) {
            tasks.remove(subTask.getId());
        }
        updateStatusEpic(epic);
    }

    @Override
    public void deleteAllEpic() {
        List<Epic> epicList = getAllEpic();
        for (Epic epic : epicList) {

            //В начале удаляем все SubTask данного эпика
            deleteSubTaskForEpic(epic.getId());

            //Потом удаляем сам Epic
            tasks.remove(epic.getId());
        }
    }

    @Override
    public void deleteById(long id) {
        tasks.remove(id);
    }

    @Override
    public Task getTaskById(long id) {
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(long id) {
        return (Epic) tasks.get(id);
    }

    @Override
    public SubTask getSubTaskById(long id) {
        return (SubTask) tasks.get(id);
    }

    @Override
    public Task createTask(Task task) {
        if (task != null && task.getClass() == Task.class) {
            task.setId(id++);
            tasks.put(task.getId(), task);
        }
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        if (epic != null && epic.getClass() == Epic.class) {
            epic.setId(id++);
            tasks.put(epic.getId(), epic);
        }
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        if (subTask != null && subTask.getClass() == SubTask.class) {
            subTask.setId(id++);
            tasks.put(subTask.getId(), subTask);
        }
        return subTask;
    }

    @Override
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

    @Override
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

    @Override
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
                updateStatusEpic((Epic)tasks.get(subTask.getIdEpic()));
            }
            return oldSubTask;
        }
    }

    @Override
    public void updateStatusEpic(Epic epic) {
        List<SubTask> subTaskList = getAllSubTaskForEpic(epic.getId());
        if (subTaskList.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        } else {
            for (int i = 0; i < subTaskList.size(); i++) {
                SubTask subTask = subTaskList.get(i);
                if (!subTask.getStatus().equals(Status.DONE)) {
                    break;
                }
                if (i == subTaskList.size() - 1) {
                    epic.setStatus(Status.DONE);
                    return;
                }
            }
            for (int i = 0; i < subTaskList.size(); i++) {
                SubTask subTask = subTaskList.get(i);
                if (!subTask.getStatus().equals(Status.NEW)) {
                    break;
                }
                if (i == subTaskList.size()-1) {
                    epic.setStatus(Status.NEW);
                    return;
                }
            }
            epic.setStatus(Status.IN_PROGRESS);
        }

    }
}
