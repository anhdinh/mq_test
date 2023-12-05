package andy.testing.controller.user;

import andy.testing.controller.BaseApiController;
import andy.testing.dto.user.UserDto;
import andy.testing.entity.UserEntity;
import andy.testing.exception.UserNotFoundException;
import andy.testing.service.mq.artemis.receive.UserMessagingService;
import andy.testing.service.mq.rabit.RabbitUserMessagingService;
import andy.testing.service.user.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserApiController extends BaseApiController {

    private final UserService userService;


    private  final RabbitUserMessagingService rabbitUserMessagingService;


    public UserApiController(UserService userService, ModelMapper modelMapper, RabbitUserMessagingService rabbitUserMessagingService) {
        super(modelMapper);
        this.userService = userService;
        this.rabbitUserMessagingService = rabbitUserMessagingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            UserEntity foundUser = userService.get(id);
            UserDto userDto = entity2Dto(foundUser, UserDto.class);
            return ResponseEntity.ok(userDto);
        }catch (UserNotFoundException exception){
           return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        List<UserEntity> list =  userService.list();
        if(list.isEmpty()){
            return  ResponseEntity.noContent().build();
        }
       return ResponseEntity.ok().body(listToDto(list,UserDto.class));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid UserEntity user) {
        UserEntity createdUser = userService.add(user);
        URI uri = URI.create("/api/users/" + createdUser.getId());
        UserDto userDto = entity2Dto(createdUser, UserDto.class);
        rabbitUserMessagingService.sendUserToQueue(createdUser);
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserEntity user) {
      try {
          var userEntity =  userService.get(id);
          userEntity.setEmail(user.getEmail());
          userEntity.setLastName(user.getLastName());
          userEntity.setFirstName(user.getFirstName());
          userService.update(userEntity);
          return ResponseEntity.ok().body(entity2Dto(userEntity,UserDto.class));
      }catch (UserNotFoundException ex){
          return ResponseEntity.notFound().build();
      }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException userNotFoundException){
            return ResponseEntity.notFound().build();
        }
    }


}
