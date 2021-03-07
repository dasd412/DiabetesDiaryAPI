package com.dasd412.security;

import com.dasd412.domain.user.Writer;

import java.io.Serializable;

public class SessionUser  implements Serializable {

    private String name;
    private String email;

    public SessionUser(Writer writer) {
        this.name= String.valueOf(writer.getName());
        this.email=writer.getEmail();
    }

    public String getName() { return name; }

    public String getEmail() { return email; }
}
