package com.xedu.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/10 16:15.
 * @Description: TeachplanMediaPub数据库实体类
 */
@Data
@ToString
@Entity
@Table(name="teachplan_media_pub")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class TeachplanMediaPub {
    private static final long serialVersionUID = -916357110051689485L;
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(name="teachplan_id")
    private String teachplanId;

    @Column(name="media_id")
    private String mediaId;

    @Column(name="media_fileoriginalname")
    private String mediaFileOriginalName;

    @Column(name="media_url")
    private String mediaUrl;
    @Column(name = "courseid")
    private String courseId;
    @Column(name = "timestamp")
    private Date timestamp;
}
