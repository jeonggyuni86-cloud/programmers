package com.springtheory.ch03.ex_3_4.domain;


//domain -> 사용자 정보를 저장함
public class User {

    private String id;
    private String name;
    private String password;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("id: %s, name: %s, password: %s", id, name, password);
    }
}
