package andy.testing.controller;

import andy.testing.controller.user.UserApiController;
import andy.testing.dto.user.UserDto;
import andy.testing.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestMapping {

    @Autowired
    UserApiController userController;
    @DisplayName("test object mapping")
    @Test
    public void WhenPutAnEntity_ReturnADto(){
        UserEntity user = new UserEntity();
        user.setEmail("dta89uct@gmai.com");
        user.setId(1L);
        user.setFirstName("andinh");

        UserDto userDto = userController.entity2Dto(user, UserDto.class);

        Assertions.assertEquals(user.getFirstName(),userDto.getFirstName());
    }

    @Test
    public void WhenSetListEntity_ReturnListDto(){
        UserEntity user = new UserEntity();
        user.setEmail("dta89uct@gmai.com");
        user.setId(1L);
        user.setFirstName("andinh");

        UserEntity user2 = new UserEntity();
        user2.setEmail("dta89uct@gmai2.com");
        user2.setId(2L);
        user2.setFirstName("andinh2");

        List<UserEntity> list =  new ArrayList<>();
        list.add(user);
        list.add(user2);

       List<UserDto> listDto = userController.listToDto(list, UserDto.class);
       org.assertj.core.api.Assertions.assertThat(listDto.size()).isEqualTo(2);
    }
}
