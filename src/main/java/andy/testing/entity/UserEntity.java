package andy.testing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.io.Serializable;

@Entity
@Table(name = "user_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false,unique = true)
    @NotBlank(message = "E-mail must not empty")
    @Email(message = "Email mush have valid email address")
    private String email;

    @Column(name = "first_name",length = 20,nullable = false)
    private String firstName;

    @Column(name = "last_name",length = 20,nullable = false)
    private String lastName;

    @Column(length = 20,nullable = false)
    private String password;


}
