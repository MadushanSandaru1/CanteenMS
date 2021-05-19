package uor.fot.canteenMS.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Login {

    @Id
    private Integer id;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
