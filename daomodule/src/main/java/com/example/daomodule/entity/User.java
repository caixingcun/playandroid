package com.example.daomodule.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by caixingcun on 2018/3/12.
 * greenDao数据库对应数据表 实体类
 * @Entity 能够被greenDao识别的数据库类型实体类
 * @Id 必须是Long 表示主键
 */
@Entity
public class User {
    @Id
    private Long id;
    private String page;
    private String content;
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPage() {
        return this.page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 795783441)
    public User(Long id, String page, String content) {
        this.id = id;
        this.page = page;
        this.content = content;
    }
    @Generated(hash = 586692638)
    public User() {
    }

}
