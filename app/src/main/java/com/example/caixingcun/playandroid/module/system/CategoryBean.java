package com.example.caixingcun.playandroid.module.system;

import java.util.List;

/**
 * Created by caixingcun on 2018/3/19.
 */

public class CategoryBean<T> {

    /**
     * children : []
     * courseId : 13
     * id : 231
     * name : kotlin
     * order : 115
     * parentChapterId : 0
     * visible : 1
     */

    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;
    private List<T> children;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
