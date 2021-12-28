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
public class UserController {

    private final RabbitTemplate rabbitTemplate;

    public UserController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    private TaskService taskService;

    public String sendMessage(TaskAuxiliar taskAux) {

        rabbitTemplate.convertAndSend(
                ConfigureRabbitMq.EXCHANGE_NAME,
                "pdist.usermessage",
                taskAux);

        return "We have sent a message! :" + "";
    }

    // USER

    @PostMapping("register")
    public void register(@RequestBody User user) {
        TaskAuxiliar userCr = new TaskAuxiliar("register", user);
        sendMessage(userCr);
    }

    @DeleteMapping(path = {"delete/{iduser}"})
    public void deleteUser(@PathVariable long iduser) {
        TaskAuxiliar userUp = new TaskAuxiliar("deleteUser", iduser);
        sendMessage(userUp);
    }

    @GetMapping(path= {"{iduser}"})
    public User findUserById(@PathVariable long iduser) {
        return taskService.findUserById(iduser);
    }

    @GetMapping(path= {"findall"})
    public  List<User> findAllUsers() {
        return taskService.findAllUsers();
    }

    @PutMapping(value = "update/{iduser}")
    public void updateUser(@PathVariable long iduser, @RequestBody User user) {
        TaskAuxiliar userUp = new TaskAuxiliar("updateUser", user, iduser);
        sendMessage(userUp);
    }

    @PostMapping(value = "login")
    public User login( @RequestBody User user) {
        return taskService.login(user);
    }
}
