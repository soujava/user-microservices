package sh.platform.example.user;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class User extends PanacheEntity {

    @NotBlank(message = "Name is mandatory")
    public String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    public String email;


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }

    public void update(User user) {
        this.name = user.name;
        this.email = user.email;
    }
}
