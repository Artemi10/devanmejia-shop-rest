package devanmejia.models.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import devanmejia.models.enums.UserRole;
import devanmejia.models.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Table(name = "users")

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
    @Column(name = "age")
    @JsonIgnore
    private int age;
    @JsonIgnore
    @Column(name = "city")
    private String cityName;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> orders;
    @Enumerated(value = EnumType.STRING)
    private UserState state;
    @JsonIgnore
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

}

