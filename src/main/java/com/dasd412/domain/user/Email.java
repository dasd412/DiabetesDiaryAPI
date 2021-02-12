package com.dasd412.domain.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    public Email(){}

    public Email(String address){
       this(null,address);
    }

    public Email(Long id, String address){
        checkArgument(isNotEmpty(address),"address must be provided");
        checkArgument(address.length()>=4&&address.length()<=50,"address length must be between 4 and 50 characters!");
        checkArgument(checkAddress(address),"invalid email address : "+address);
        this.id=id;
        this.address=address;
    }

    private static boolean checkAddress(String address) {
        //이메일 정규식 표현과 매칭되는지 판단한다.
        return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+",address);
    }

    public String getAddress() {
        return address;
    }

    public String getName(){
        String[]tokens=address.split("@");
        if(tokens.length==2){
            return tokens[0];
        }
        else{
            return null;
        }
    }

    public String getDomainName(){
        String[] tokens=address.split("@");
        if(tokens.length==2){
            return tokens[1];
        }
        else{
            return null;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        if(obj==null||getClass()!=obj.getClass())return false;
        Email email=(Email)obj;
        return Objects.equals(this.address,email.address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("address",address)
                .toString();
    }

}
