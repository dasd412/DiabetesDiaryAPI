package com.dasd412.configure;

import com.dasd412.security.LoginUserArgsResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    private final LoginUserArgsResolver loginUserArgsResolver;

    public WebMvcConfigure(LoginUserArgsResolver loginUserArgsResolver) {
        this.loginUserArgsResolver = loginUserArgsResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgsResolver);//어노테이션 리졸버 추가
    }
}
