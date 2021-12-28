package br.com.ifpb.gpsback.rabbitmq.consumer;

import br.com.ifpb.gpsback.model.Task;
import br.com.ifpb.gpsback.model.TaskAuxiliar;
import br.com.ifpb.gpsback.model.User;
import br.com.ifpb.gpsback.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;


import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TaskHandler {

    @Autowired
    private TaskService taskService;

    public void handleMessage(TaskAuxiliar taskaux ){

        if (Objects.equals(taskaux.getMethod(), "create")){
            this.create(taskaux.getUser(), taskaux.getTask());
        }
        else if (Objects.equals(taskaux.getMethod(), "update")){
            this.update(taskaux.getId(), taskaux.getTask(), taskaux.getUser());
        }
        else if (Objects.equals(taskaux.getMethod(), "delete")){
            this.delete(taskaux.getUser(), taskaux.getId());
        }
        else if (Objects.equals(taskaux.getMethod(), "register")){
            this.register(taskaux.getUser());
        }
        else if (Objects.equals(taskaux.getMethod(), "deleteUser")){
            this.deleteUser(taskaux.getId());
        }
        else if (Objects.equals(taskaux.getMethod(), "updateUser")){
            this.updateUser(taskaux.getId(), taskaux.getUser());
        }


    }

    public void create(User user, Task task) {
         taskService.create(user, task);
    }

    public void delete(User user, long idtask) {
         taskService.delete(user, idtask);
    }

    public void  update(long idtask, Task task, User user) {
         taskService.update(idtask, task, user);
    }

    public void register(User user) {
        taskService.register(user);
    }

    public void deleteUser(long iduser) {
        taskService.deleteUser(iduser);
    }

    public void  updateUser(long iduser, User user) {
        taskService.updateUser(iduser, user);
    }

}


