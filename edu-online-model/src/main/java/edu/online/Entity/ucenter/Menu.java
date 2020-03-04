package edu.online.Entity.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Data
@ToString
@Entity
@Table(name="menu")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Menu {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String code;
    @Column(name="p_code")
    private String pCode;
    @Column(name="p_id")
    private String pId;
    @Column(name="menu_name")
    private String menuName;
    private String url;
    @Column(name="is_menu")
    private String isMenu;
    private Integer level;
    private Integer sort;
    private String status;
    private String icon;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="update_time")
    private Date updateTime;


}
