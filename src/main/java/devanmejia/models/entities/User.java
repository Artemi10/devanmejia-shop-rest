package devanmejia.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import devanmejia.models.enums.UserRole;
import devanmejia.models.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name="email")
    @JsonIgnore
    private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> orders;
    @Enumerated(value = EnumType.STRING)
    private UserState state;
    @JsonIgnore
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;
    private String refreshToken;
    private String logInCode;
    private String resetCode;
}

