package com.dasd412.security;

import com.dasd412.domain.user.Writer;
import com.dasd412.domain.user.WriterRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
public class CustomUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final WriterRepository writerRepository;
    private final HttpSession httpSession;

    public CustomUserService(WriterRepository writerRepository, HttpSession httpSession){
        this.writerRepository=writerRepository;
        this.httpSession=httpSession;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest,OAuth2User>loader=new DefaultOAuth2UserService();
        OAuth2User oAuth2User=loader.loadUser(oAuth2UserRequest);

        String registeredId=oAuth2UserRequest.getClientRegistration().getRegistrationId();//현재 로그인 중인 서비스를 구분하기 위함. 네이버인지 구글인지 구분!

        String userNameAttributeName=oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();//로그인하는 id의 PK를 가져온다.

        OAuthAttributes attributes=OAuthAttributes.of(registeredId,userNameAttributeName,oAuth2User.getAttributes());

        Writer writer= saveOrUpdate(attributes);

        httpSession.setAttribute("user",new SessionUser(writer));//세션에 사용자 정보 저장!

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(writer.getRoleKey())),attributes.getAttributes(),attributes.getAttributeKey());
    }

    private Writer saveOrUpdate(OAuthAttributes attributes) {
        Writer writer=writerRepository.findByEmail(attributes.getEmail())
                .map(entity->entity.updateName(attributes.getName()))
                .orElse(attributes.toEntity());

        return writerRepository.save(writer);
    }
}
