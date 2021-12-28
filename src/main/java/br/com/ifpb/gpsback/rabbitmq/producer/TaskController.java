package br.com.ifpb.gpsback.rabbitmq.producer;

import br.com.ifpb.gpsback.model.Task;
import br.com.ifpb.gpsback.model.TaskAuxiliar;
import br.com.ifpb.gpsback.model.User;
import br.com.ifpb.gpsback.rabbitmq.ConfigureRabbitMq;
import br.com.ifpb.gpsback.services.TaskService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class TaskController {

    private final RabbitTemplate rabbitTemplate;

    public TaskController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    private TaskService taskService;

    public String sendMessage(TaskAuxiliar taskAux) {

        rabbitTemplate.convertAndSend(
                ConfigureRabbitMq.EXCHANGE_NAME,
                "pdist.taskmessage",
                taskAux);

        return "We have sent a message! :" + "";
    }

    @PostMapping("user/{iduser}/create/task/")
    public void create(@PathVariable long iduser, @RequestBody Task task) {
        User user = findUserById(iduser);
        TaskAuxiliar taskCr = new TaskAuxiliar("create",user,task);
        sendMessage(taskCr);
    }

    @DeleteMapping(path = {"user/{iduser}/delete/task/{idtask}"})
    public void delete(@PathVariable long iduser, @PathVariable long idtask) {
        User user = findUserById(iduser);
        TaskAuxiliar taskUp = new TaskAuxiliar("delete",user, idtask);
        sendMessage(taskUp);
    }

    @GetMapping(path = {"user/{iduser}/tasks"})
    public List<Task> findAll(@PathVariable long iduser) {
        return taskService.findAll(iduser);
    }

    @GetMapping(path= {"user/{iduser}/task/{idtask}"})
    public Task findById(@PathVariable long iduser, @PathVariable long idtask) {
        return taskService.findById(iduser, idtask);
    }

    @PutMapping(value = "user/{iduser}/task/update/{idtask}")
    public void update(@PathVariable long iduser, @PathVariable long idtask, @RequestBody Task task) throws CloneNotSupportedException {
        User user = findUserById(iduser);
        TaskAuxiliar taskUp = new TaskAuxiliar("update",user, task, idtask);
        sendMessage(taskUp);
    }

    public User findUserById(long iduser) {
        return taskService.findUserById(iduser);
    }

}
