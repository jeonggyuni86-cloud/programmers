package com.springtheory.ch05.ex_5_1.domain;


import com.springtheory.ch05.ex_5_1.dao.Level;

//domain -> 사용자 정보를 저장함
public class User {

    private String id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommend;

    // * 레벨 업그레이드 동작을 User 자신이 갖도록 한다
    //  - "다음 레벨로 올린다"는 것은 사용자 데이터에 대한 처리이므로, 그 책임을 User에 둔다.
    //  - 이렇게 해두면 UserService는 '언제 올릴지'만 판단하고, '어떻게 올릴지'는 User에 맡길 수 있다.

    public void upgradeLevel() {
        this.level = this.level.nextLevel();
    }

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

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
