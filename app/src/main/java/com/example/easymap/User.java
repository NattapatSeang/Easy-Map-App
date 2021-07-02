package com.example.easymap;

import org.bson.types.ObjectId;

public class User {
    private String _id;
    private String email;
    private Boolean isCreator;
    private String name;
    private String password;
    private String phoneNo;
    private String bookmark[];

    public User(){}
    public User(String _id, String email, Boolean isCreator, String name, String password
            , String phoneNo, String[] bookmark){
        this._id = _id;
        this.email = email;
        this.isCreator = isCreator;
        this.name = name;
        this.password = password;
        this.phoneNo = phoneNo;
        for(int i = 0; i < bookmark.length; i++)
        {
            this.bookmark[i] = bookmark[i];
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getCreator() {
        return isCreator;
    }

    public void setCreator(Boolean creator) {
        isCreator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String[] getBookmark() {
        return bookmark;
    }

    public void setBookmark(String[] bookmark) {
        this.bookmark = bookmark;
    }
}
