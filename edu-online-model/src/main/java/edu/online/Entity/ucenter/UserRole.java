package edu.online.Entity.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "user_role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class UserRole {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "role_id")
    private String roleId;
    private String creator;
    @Column(name = "create_time")
    private Date createTime;

}
