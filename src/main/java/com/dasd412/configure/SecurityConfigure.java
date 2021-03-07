package com.dasd412.configure;

import com.dasd412.domain.user.Role;
import com.dasd412.security.CustomUserService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    private final CustomUserService userService;

    public SecurityConfigure(CustomUserService userService){
        this.userService=userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()//h2-console 화면 사용하기 위한 설정
        .and()
                .authorizeRequests().antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**","/vendor/**","/sidebar-07/**").permitAll()
                .antMatchers("/api/diabetes/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(userService);
    }


}
