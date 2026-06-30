package com.springtheory.ch05.ex_5_2.dao;

public enum Level {
    BASIC(1),
    SILVER(2),
    GOLD(3);

    private final int value;
    Level(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public static Level valueOf(int value) {
        return switch (value) {
            case 1 -> BASIC;
            case 2 -> SILVER;
            case 3 -> GOLD;
            default -> throw new IllegalArgumentException();
        };
    }

    public Level nextLevel() {
        return switch (this) {
            case BASIC -> SILVER;
            case SILVER -> GOLD;
            case GOLD -> GOLD;
        };
    }

    // 다음 단계 레벨을 돌려준다. 최고 등급(GOLD)은 다음이 없으므로 null.
    //  - '레벨 순서(BASIC->SILVER->GOLD)'라는 규칙을 Level 자신이 갖게 해서,
    //    업그레이드 로직이 곳곳에 흩어지지 않도록 한다(레벨 변경 규칙의 책임을 Level에 둔다).
}
