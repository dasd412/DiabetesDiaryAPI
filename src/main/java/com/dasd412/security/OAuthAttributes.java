package com.dasd412.security;

import com.dasd412.domain.user.Role;
import com.dasd412.domain.user.Writer;

import java.util.Map;

public class OAuthAttributes {

    private Map<String,Object>attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registeredId, String userNameAttributeName, Map<String, Object> attributes) { return ofGoogle(userNameAttributeName,attributes); }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return new Builder().name((String) attributes.get("name")).email((String) attributes.get("email")).attributes(attributes).nameAttributeKey(userNameAttributeName).build();
    }

    public Map<String, Object> getAttributes() { return attributes; }

    public String getAttributeKey() { return nameAttributeKey; }

    public String getEmail() {return email; }

    public String getName() {return name; }

    public Writer toEntity() { return new Writer(name,email, Role.USER); }

    static public class Builder{

        private Map<String,Object>attributes;
        private String nameAttributeKey;
        private String name;
        private String email;

        public Builder(){}

        public Builder(OAuthAttributes o) {
            this.attributes = o.attributes;
            this.nameAttributeKey = o.nameAttributeKey;
            this.name = o.name;
            this.email = o.email;
        }

        public Builder attributes(Map<String,Object>attributes){
            this.attributes=attributes;
            return this;
        }

        public Builder nameAttributeKey(String nameAttributeKey){
            this.nameAttributeKey=nameAttributeKey;
            return this;
        }
        public Builder name(String name){
            this.name=name;
            return this;
        }

        public Builder email(String email){
            this.email=email;
            return this;
        }

        public OAuthAttributes build(){
            return new OAuthAttributes(attributes,nameAttributeKey,name,email);
        }
    }
}
