package com.example.easymap;

import java.util.ArrayList;

public class MapItem {
    private String _id;
    private String object_id;
    private String name;
    private String description;
    private ArrayList<String> category;

    public MapItem() {}

    public MapItem(String _id, String object_id, String name, String description, ArrayList<String> category) {
        this._id = _id;
        this.object_id = object_id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }
}
