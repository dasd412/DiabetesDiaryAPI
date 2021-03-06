package com.dasd412.domain.user;

public enum Role {
    GUEST("ROLE_GUEST","손님"),
    USER("ROLE_USER","일반 사용자");

    private final String key;
    private final String title;

    Role(String key, String title){
        this.key=key;
        this.title=title;
    }


    public String getKey() {
        return this.key;
    }
    public String getTitle(){
        return this.title;
    }
}
