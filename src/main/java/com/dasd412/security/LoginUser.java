package com.dasd412.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)//메소드의 파라미터로 선언된 객체에만 사용가능함.
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
    /*
    메소드 파라미터에 사용하면 세션 정보를 가져올 수 있다.
     */
}
