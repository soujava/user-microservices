package sh.platform.example.user;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class Member extends PanacheEntity {

    @NotBlank(message = "Name is mandatory")
    public String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    public String email;

    @NotBlank(message = "JUG is mandatory")
    public String jug;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }

    public void update(Member member) {
        this.name = member.name;
        this.email = member.email;
        this.jug = member.jug;
    }
}
