package pfe.spring.controller;

import pfe.spring.entity.Task;
import pfe.spring.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/task/")
@RequiredArgsConstructor
@Transactional
@CrossOrigin("*")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;



    @PutMapping("enCours/{idTask}")
    @ResponseBody
    public ResponseEntity<?> setEnCours(@PathVariable Long idTask) {
        try {
            taskService.setEnCours(idTask);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @PutMapping("update/{idTask}")
    @ResponseBody
    public ResponseEntity<?> updateTask(@PathVariable Long idTask,@RequestBody Task updatedTask) {
        try {
            taskService.updateTask(idTask,updatedTask);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task");
        }
    }


    @PutMapping("submit/{idTask}")
    @ResponseBody
    public ResponseEntity<?> submitTask(@PathVariable Long idTask,@RequestBody Task updatedTask) {
        try {
            taskService.submitTask(idTask,updatedTask);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting task");
        }
    }


    @GetMapping("/get/{idTask}")
    public Task getTask(@PathVariable Long idTask){
        return taskService.getTask(idTask);
    }
}
