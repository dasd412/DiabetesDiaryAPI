package com.dasd412.security;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@Component
public class LoginUserArgsResolver implements HandlerMethodArgumentResolver {

  private final HttpSession httpSession;

  public LoginUserArgsResolver(HttpSession httpSession) {
    this.httpSession = httpSession;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {//컨트롤러 메서드의 특정 파라미터를 지원하는가 따짐.
    boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
    boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
    return isLoginUserAnnotation && isUserClass;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    //@LoginUser 어노테이션이 붙여진 곳에 세션에서 user키에 해당하는 밸류 값을 넣어준다.
    return httpSession.getAttribute("user");
  }
}
