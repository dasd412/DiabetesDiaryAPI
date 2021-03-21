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

    protected Writer(){}

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
        /*
        명령문과 조회문이 같이 존재한다는 문제점이 존재한다.
        하지만 CustomUserService의 람다식에서 반환값이 필요하기 때문에 void 대신 Writer를 반환값으로 하였다.
         */
        this.name=name;//       명령문
        return this;//      조회문
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
