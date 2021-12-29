package br.com.ifpb.gpsback.tests;

import br.com.ifpb.gpsback.model.User;
import br.com.ifpb.gpsback.repository.UserRepository;
import br.com.ifpb.gpsback.services.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private TaskService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void registrarUserTeste(){
        User user = new User("teste", "teste@gmail.com", "1234");
        userService.register(user);
        User userT = userRepository.findByEmail(user.getEmail());
        Assertions.assertNotNull(userT);
    }

    @Test
    public void updateUserNameTeste(){
        User user = new User(1L, "teste", "teste", "teste");
        userService.register(user);

        User userT = userRepository.findByEmail(user.getEmail());
        userT.setName("Baaa");
        userService.updateUser(user.getId(), userT);

        User userRepo = userRepository.findByEmail(userT.getEmail());

        Assertions.assertEquals(user.getEmail(), userRepo.getEmail());
        Assertions.assertEquals(user.getEmail(), userT.getEmail());
    }

    @Test
    public void deleteUserTeste(){
        User user = new User("teste", "teste4@gmail.com", "1234");
        userService.register(user);
        User userT = userRepository.findByEmail(user.getEmail());
        userService.deleteUser(userT.getId());
        User userDeletado = userRepository.findByEmail(userT.getEmail());
        Assertions.assertNull(userDeletado);
    }
}