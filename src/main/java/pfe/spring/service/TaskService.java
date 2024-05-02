package pfe.spring.service;

import pfe.spring.entity.Avancement;
import pfe.spring.entity.Task;

public interface TaskService {

    public int calculateProgress(Avancement avancement);
    public Task setEnCours(Long idTask);

    public Task getTask(Long idTask);

    public Task updateTask(Long idTask, Task updatedTask);

    public Task submitTask(Long idTask, Task updatedTask);
}
