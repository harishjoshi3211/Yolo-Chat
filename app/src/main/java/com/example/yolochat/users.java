package com.example.yolochat;

public class users {

    private String name;
    private String status;
    private String uid;

    public users(){

    }

    public users(String name,String status)
    {
        this.name=name;
        this.status=status;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
