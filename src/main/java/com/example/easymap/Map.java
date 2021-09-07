package com.example.easymap;


public class Map {
    private String name;
    private Double floor;
    private String description;
    private String creator_id;
    private String _id;

    public Map(){}
    public Map(String name, Double floor, String description, String creator_id, String _id){
        this.name = name;
        this.floor = floor;
        this.description = description;
        this.creator_id = creator_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFloor() {
        return floor;
    }

    public void setFloor(Double floor) {
        this.floor = floor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) { this.creator_id = creator_id; }

    public String get_id() { return _id; }

    public void set_id(String _id) {
        this._id = _id;
    }
}
