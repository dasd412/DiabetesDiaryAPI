package com.dasd412.domain.diary;

import com.dasd412.domain.user.Email;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade=CascadeType.ALL)//1대1 관계
    @JoinColumn(name="email_id")//주 객체는 Writer, 대상 객체는 Email. Writer -> Email 단방향 관계
    private Email email;

    @OneToMany(mappedBy = "writer")
    private Set<DiabetesDiary> diaries;

    public Writer(){}

    public Writer(Email email){
        this(null,null,email);
    }

    public Writer(String name, Email email){
        this(null,name,email);
    }

    public Writer(Long id,String name, Email email) {
        checkNotNull(email,"Email must be provided!");
        this.id=id;
        this.name = name;
        this.email = email;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Email getEmail() {
        return email;
    }


    @Override
    public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
              .append("name",name)
              .append("email",email)
              .toString();
    }
}
