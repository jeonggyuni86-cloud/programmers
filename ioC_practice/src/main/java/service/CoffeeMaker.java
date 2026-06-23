package service;

import bean.Bean;

public class CoffeeMaker {
    private final Bean bean;

    public CoffeeMaker(Bean bean) {
        this.bean = bean;
    }

    public void brew() {
        System.out.println(bean.getOrigin() + "로 커피를 내립니다.");
    }
}
