package andy.testing.repository;

import andy.testing.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest
@Transactional
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @DirtiesContext
    @Test
    public void WhenGetAnExistUserByID_findById_ReturnAnUser(){
        var user = userRepository.findById(1L);
        Assertions.assertThat(user.isEmpty()).isFalse();
        Assertions.assertThat(user.get().getId()).isEqualTo(1L);

    }

    @Test
    public void WhenGetANonExistUserByID_findById_ReturnAnUser(){
        var user = userRepository.findById(10000L);
        Assertions.assertThat(user.isPresent()).isFalse();
    }
}
