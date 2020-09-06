package com.example.yolochat;

public class friends_class {


    private String name;
    private String status;
    private String uid;

    public friends_class(){

    }

    public friends_class(String name,String status,String uid)
    {
        this.name=name;
        this.status=status;
        this.uid=uid;
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
