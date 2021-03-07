package com.dasd412.domain.user;

import com.dasd412.utils.EmailUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Writer(){}

    public Writer(String name, String email,Role role) {
        checkArgument(name.length()>0,"name length must be longer than zero");
        checkNotNull(email,"Email must be provided!");
        checkArgument(EmailUtils.checkAddress(email),"email format must be satisfied");
        checkNotNull(role,"Role must be provided!");
        this.name = name;
        this.email = email;
        this.role=role;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public String getEmail() {
        return email;
    }

    public Writer updateName(String name){
        this.name=name;
        return this;
    }

    public String getRoleKey(){ return this.role.getKey(); }

    @Override
    public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
              .append("name",name)
              .append("email",email)
              .append("role",role)
              .toString();
    }
}
