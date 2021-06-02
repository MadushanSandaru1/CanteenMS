package uor.fot.canteenMS.entities;

import javax.persistence.*;

@Entity
//@NamedStoredProcedureQueries(
//        {@NamedStoredProcedureQuery(name = "user_creation_procedure",procedureName = "user_account_create",
//                parameters = {@StoredProcedureParameter(mode = ParameterMode.IN,name = "user_id",type = Integer.class),
//                              @StoredProcedureParameter(mode = ParameterMode.IN,name = "user_registered_no",type = String.class),
//                              @StoredProcedureParameter(mode = ParameterMode.IN,name = "user_name",type = String.class),
//                              @StoredProcedureParameter(mode = ParameterMode.IN,name = "user_role_id",type = Integer.class)
//                })}
//        )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String registered_no;
    private String name;
    private String mobile;
    private String email;
    private Integer role_id;
    private String room_no;
    private Integer is_deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistered_no() {
        return registered_no;
    }

    public void setRegistered_no(String registered_no) {
        this.registered_no = registered_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }
}
