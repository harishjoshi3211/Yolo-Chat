package com.example.yolochat;

public class requested_person {

    private String name;
    private String date;
    private String uid;

    public requested_person(String name,String date,String uid){

        this.name=name;
        this.date=date;
        this.uid=uid;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
