package com.adobe.aem.practice.site.core.models;

import java.util.List;

public class NavItems {
    private String title;
    private String path;
    private List<NavItems> children;
    private boolean active;

    public NavItems(String title, String path, List<NavItems> children, boolean active) {
        this.title = title;
        this.path = path;
        this.children = children;
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public List<NavItems> getChildren() {
        return children;
    }

    public boolean isActive() {
        return active;
    }

    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }
}
