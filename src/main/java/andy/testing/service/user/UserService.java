package andy.testing.service.user;

import andy.testing.entity.UserEntity;
import andy.testing.exception.UserNotFoundException;
import andy.testing.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity add(UserEntity user){
        return userRepository.save(user);
    }

    public UserEntity update(UserEntity user){
        if(userRepository.existsById(user.getId())){
            throw  new RuntimeException("Not found the user");
        }
        return userRepository.save(user);
    }

    public UserEntity get(Long id){
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isEmpty()){
            throw new UserNotFoundException("Not found the user");
        }
        return result.get();
    }

    public List<UserEntity> list(){
        return (List<UserEntity>) userRepository.findAll();
    }

    public void delete(Long id){
        if(!userRepository.existsById(id)){
           throw new UserNotFoundException("Not found the user");
        }
        userRepository.deleteById(id);
    }


}
