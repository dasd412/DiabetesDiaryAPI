package com.dasd412.configure;

import com.dasd412.domain.user.Role;
import com.dasd412.service.security.CustomUserService;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                .authorizeRequests().antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**","/vendor/**","/sidebar-07/**","/api/login","/login**","/callback/", "/webjars/**", "/error**", "/oauth2/authorization/**").permitAll()
                .antMatchers("/api/diabetes/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/api/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)//로그아웃시 세션 무효화
                .deleteCookies("JSESSIONID")//세션 id 쿠키 제거
                .clearAuthentication(true)
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(userService);
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher>httpSessionPublisher(){
        //로그아웃 시 세션 삭제 보장을 하기 위한 빈 주입
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }


}
