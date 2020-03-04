package edu.online.Entity.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Data
@ToString
@Entity
@Table(name="role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Role {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name="role_name")
    private String roleName;
    @Column(name="role_code")
    private String roleCode;
    private String description;
    private String status;
    @Column(name="createTime")
    private Date create_time;
    @Column(name="update_time")
    private Date updateTime;


}
