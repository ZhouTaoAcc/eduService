package edu.online.Entity.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Data
@ToString
@Entity
@Table(name="permission")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Permission {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name="role_id")
    private String roleId;
    @Column(name="menu_id")
    private String menuId;
    @Column(name="createTime")
    private Date create_time;


}
