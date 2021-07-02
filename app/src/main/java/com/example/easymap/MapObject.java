package com.example.easymap;

public class MapObject {
    private String _id;
    private String map_id;
    private String name;
    private Double location_x;
    private Double location_y;
    private Double width_x;
    private Double width_y;
    private String detail;
    private Boolean highlight;

    public MapObject(){}

    public MapObject(String _id, String map_id, String name, Double location_x, Double location_y, Double width_x, Double width_y, String detail) {
        this._id = _id;
        this.map_id = map_id;
        this.name = name;
        this.location_x = location_x;
        this.location_y = location_y;
        this.width_x = width_x;
        this.width_y = width_y;
        this.detail = detail;
        this.highlight = false;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getHighligt() {
        return highlight;
    }

    public void setHighligt(Boolean highligt) {
        this.highlight = highligt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMap_id() {
        return map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLocation_x() {
        return location_x;
    }

    public void setLocation_x(Double location_x) {
        this.location_x = location_x;
    }

    public Double getLocation_y() {
        return location_y;
    }

    public void setLocation_y(Double location_y) {
        this.location_y = location_y;
    }

    public Double getWidth_x() {
        return width_x;
    }

    public void setWidth_x(Double width_x) {
        this.width_x = width_x;
    }

    public Double getWidth_y() {
        return width_y;
    }

    public void setWidth_y(Double width_y) {
        this.width_y = width_y;
    }
}
