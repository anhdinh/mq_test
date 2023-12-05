package andy.testing.controller;

import andy.testing.controller.user.UserApiController;
import andy.testing.entity.UserEntity;
import andy.testing.exception.UserNotFoundException;
import andy.testing.service.mq.artemis.receive.UserMessagingService;
import andy.testing.service.mq.rabit.RabbitUserMessagingService;
import andy.testing.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserApiController.class)
public class UserApiControllerTests {
    public static final String API_END_POINT_PATH = "/api/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMessagingService userMessagingService;

    @MockBean
    private RabbitUserMessagingService rabbitUserMessagingService;



    @Test
    public void add_shouldAddReturnFor400BadRequest() throws Exception {

        UserEntity userEntity = UserEntity.builder().email("").lastName("").firstName("").password("").build();
        String requestBody = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(
                post(API_END_POINT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void add_shouldAddAndReturn201CreatedForRequest() throws Exception {

        UserEntity createdUser = UserEntity.builder().email("dta89@gmai.com")
                .lastName("andy").firstName("Love").password("andy@123").build();
        String requestBody = objectMapper.writeValueAsString(createdUser);
        createdUser.setId(1L);
        Mockito.doNothing().when(userMessagingService).convertAndSend(any());
        Mockito.when(userService.add(any(UserEntity.class))).thenReturn(createdUser);
        Mockito.doNothing().when(rabbitUserMessagingService).sendUserToQueue(any());
        mockMvc.perform(
                        post(API_END_POINT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)).andExpect(status().isCreated())
                .andExpect(header().string("Location", is(API_END_POINT_PATH + "/1")))
                .andExpect(jsonPath("$.email", is(createdUser.getEmail())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andDo(print());
    }


    @Test
    public void get_should404NotFoundWhenNotGivenId() throws Exception {

        long userId = 123L;
        String requestUrl = API_END_POINT_PATH + "/" + userId;

        Mockito.when(userService.get(userId)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get(requestUrl)).andExpect(status().isNotFound());
    }



    @Test
    public void get_shouldReturnUserDetail() throws Exception {

        long userId = 1;
        UserEntity userEntity = UserEntity.builder().id(1L)
                .email("dta89uct@gmail.com").firstName("anhdinh").lastName("anhdin").password("xxxx").build();
        String requestUrl = API_END_POINT_PATH + "/" + userId;

        Mockito.when(userService.get(userId)).thenReturn(userEntity);
        mockMvc.perform(
                        get(requestUrl)).andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(userEntity.getEmail())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andDo(print());
    }


    @Test
    public void whenGetList_GetList_ReturnNoContent() throws Exception {

        List<UserEntity> userEntityList = List.of();

        Mockito.when(userService.list()).thenReturn(userEntityList);
        mockMvc.perform(get(API_END_POINT_PATH)).andExpect(status().isNoContent());
        Mockito.verify(userService,Mockito.times(1)).list();

    }


    @Test
    public void whenGetList_GetList_ReturnAList() throws Exception {

        UserEntity entity1 = UserEntity.builder().id(1L)
                .email("dta89uct@gmail.com").firstName("xxx1").lastName("xx").password("").build();
        UserEntity entity2 = UserEntity.builder().id(2L)
                .email("dta89uct2@gmail.com").firstName("xxx2").lastName("xx").password("").build();
        List<UserEntity> userEntityList = Arrays.asList(entity1,entity2);

        Mockito.when(userService.list()).thenReturn(userEntityList);
        mockMvc.perform(get(API_END_POINT_PATH)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("xxx1"))
                .andExpect(jsonPath("$[1].firstName").value("xxx2"));

        Mockito.verify(userService,Mockito.times(1)).list();

    }

    @Test void update_whenUpdateNonExistUser_Return404NotFound() throws Exception {

        UserEntity updateUserData = UserEntity.builder().email("dta89uct@gmail.com")
                .firstName("xxx1").lastName("xx").password("").build();
        String requestBody = objectMapper.writeValueAsString(updateUserData);
        long id =  12;
        String requestUrl = API_END_POINT_PATH + "/" + id;

        Mockito.when(userService.get(id)).thenThrow(new UserNotFoundException(""));
        mockMvc.perform(put(requestUrl).contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andExpect(status().isNotFound());

    }

    @Test void update_WhenUpdateExistUser_ReturnNewDataUser() throws Exception {

        UserEntity updateUserData = UserEntity.builder().email("dta89uct@gmail1.com")
                .firstName("xxx1").lastName("xx").password("").build();
        String requestBody = objectMapper.writeValueAsString(updateUserData);
        long id =  1;
        UserEntity mockUpdatedUser = UserEntity.builder().email("dta89uct@gmail1.com")
                .firstName("xxx1").lastName("xx").password("").build();
        String requestUrl = API_END_POINT_PATH + "/" + id;

        Mockito.when(userService.get(id)).thenReturn(updateUserData);
        Mockito.when(userService.update(any(UserEntity.class))).thenReturn(mockUpdatedUser);


        mockMvc.perform(put(requestUrl).contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("dta89uct@gmail1.com"));

    }

    @Test void whenDeleteNonExistUser_Return404NotFound() throws Exception{

        long id =  12;
        String requestUrl = API_END_POINT_PATH + "/" + id;

        Mockito.doThrow(UserNotFoundException.class).when(userService).delete(id);

        Mockito.when(userService.get(id)).thenThrow(new UserNotFoundException(""));
        mockMvc.perform(delete(requestUrl)).andExpect(status().isNotFound());

    }

    @Test void whenDeleteExistUser_ReturnNoContent() throws Exception{

        long id =  12;
        String requestUrl = API_END_POINT_PATH + "/" + id;

        Mockito.doNothing().when(userService).delete(id);

        mockMvc.perform(delete(requestUrl)).andExpect(status().isNoContent());

    }



}
