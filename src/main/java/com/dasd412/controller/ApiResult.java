package com.dasd412.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

public class ApiResult<T> {//컨트롤러 리턴 값을 공통으로 전달해주는 용도의 클래스. 통일성 제공.

  private final boolean success;

  private final T response;

  private final ApiError error;

  private ApiResult(boolean success, T response, ApiError error) {
    this.success = success;
    this.response = response;
    this.error = error;
  }

  public static <T> ApiResult<T> OK(T response) {
    return new ApiResult<>(true, response, null);
  }

  public static ApiResult<?> ERROR(Throwable throwable, HttpStatus status) {
    return new ApiResult<>(false, null, new ApiError(throwable, status));
  }

  public static ApiResult<?> ERROR(String errorMessage, HttpStatus status) {
    return new ApiResult<>(false, null, new ApiError(errorMessage, status));
  }

  public boolean isSuccess() {
    return success;
  }

  public T getResponse() {
    return response;
  }

  public ApiError getError() {
    return error;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("success", success)
        .append("response", response)
        .append("error", error)
        .toString();
  }

}

