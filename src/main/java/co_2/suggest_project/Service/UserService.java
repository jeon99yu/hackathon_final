package co_2.suggest_project.Service;

import co_2.suggest_project.Model.UserDTO;
import co_2.suggest_project.Repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Transactional
    public void insertUser(UserDTO userDTO) {
        userRepository.save(userDTO);
    }

    @Transactional
    public UserDTO selectUser(int userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public List<UserDTO> selectAllUser() {
        return userRepository.findAll();
    }


}
