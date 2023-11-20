package manager;

import task.Epic;
import task.SubTask;
import task.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();
    void deleteAllTasks();
    List<Epic> getAllEpic();
    List<SubTask> getAllSubTaskForEpic(Long epicId);
    void deleteSubTaskForEpic(Long epicId);
    void deleteAllEpic();
    void deleteById(long id);
    Task getTaskById(long id);
    Epic getEpicById(long id);
    SubTask getSubTaskById(long id);
    Task createTask(Task task);
    Epic createEpic(Epic epic);
    SubTask createSubTask(SubTask subTask);
    Task updateTask(Task task);
    Epic updateEpic(Epic epic);
    SubTask updateSubTask(SubTask subTask);
    void updateStatusEpic(Epic epic);
}
