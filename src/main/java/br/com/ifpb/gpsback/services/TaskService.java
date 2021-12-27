package br.com.ifpb.gpsback.services;

import br.com.ifpb.gpsback.model.Task;
import br.com.ifpb.gpsback.model.User;
import br.com.ifpb.gpsback.repository.TaskRepository;
import br.com.ifpb.gpsback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public void create(long iduser, Task task) {
        try{
            User user = userRepository.findById(iduser).get();
            user.addTask(task);
            taskRepository.save(task);
            userRepository.save(user);
        }
        catch (Exception e){
            System.out.println("Something went wrong on create task.");
        };
    }

    public void delete(long iduser, long idtask) {
        try{
            User user = findUserById(iduser);
            Task taskExcluir = user.getTasks().stream().filter(task -> task.getId() == idtask).collect(Collectors.toList()).get(0);
            user.remTask(taskExcluir);
            taskRepository.delete(taskExcluir);
            userRepository.save(user);
        }
        catch (Exception e){
            System.out.println("Something went wrong on delete task.");
        };
    }

    public List<Task> findAll(long iduser) {
        try{
            User user = userRepository.findById(iduser).get();
            return user.getTasks();
        }
        catch (Exception e){
            System.out.println("Something went wrong on list all task.");
        };
        return  null;
    }

    public Task findById(long iduser, long idtask) {
        try{
            User user = userRepository.findById(iduser).get();
            return user.getTasks().stream().filter(task -> task.getId() == idtask).collect(Collectors.toList()).get(0);

        }
        catch (Exception e){
            System.out.println("Something went wrong  on fin task by id.");
        };
        return  null;
    }

    public void update(long idtask, Task task, User user) {
        try{
            Task taskEscolhida = user.getTasks().stream().filter(task1 -> task1.getId() == idtask).collect(Collectors.toList()).get(0);
            taskEscolhida.setDate(task.getDate());
            taskEscolhida.setDescription(task.getDescription());
            taskEscolhida.setStatus(task.getStatus());
            taskEscolhida.setTitle(task.getTitle());
            taskRepository.save(taskEscolhida);
            userRepository.save(user);
        }
        catch (Exception e){
            System.out.println("Something went wrong on update task.");
        };
    }

    public void register(User user) {
        try{
            userRepository.save(user);
        }
        catch (Exception e){
            System.out.println("Something went wrong on create user.");
        };
    }

    public void deleteUser(long iduser) {
        try{
            userRepository.deleteById(iduser);
        }
        catch (Exception e){
            System.out.println("Something went wrong on delete user.");
        };
    }

    public void updateUser(long iduser, User user) {
        try{
            User userAux = userRepository.findById(iduser).get();
            userAux.setName(user.getName());
            userAux.setEmail(user.getEmail());
            userAux.setPassword(user.getPassword());
            userRepository.save(userAux);
        }
        catch (Exception e){
            System.out.println("Something went wrong on update user.");
        };
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(long iduser) {
        return userRepository.findById(iduser).get();
    }

    public User login(User user) {
        User userAux = userRepository.findByEmail(user.getEmail());
        if (userAux.getPassword().equals(user.getPassword())) {
            return  userAux;
        }
        return null;
    }
}
