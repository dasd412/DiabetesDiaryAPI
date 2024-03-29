package com.dasd412.controller.error;

import com.dasd412.utils.MessageUtil;
import org.apache.commons.lang3.StringUtils;

public class NotFoundException extends ServiceRuntimeException {


  static final String MESSAGE_KEY = "error.notfound";

  static final String MESSAGE_DETAILS = "error.notfound.details";

  public NotFoundException(Class<?> cls, Object... values) {
    this(cls.getSimpleName(), values);
  }

  public NotFoundException(String targetName, Object... values) {
    super(MESSAGE_KEY, MESSAGE_DETAILS, new String[]{targetName,
        (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
  }

  @Override
  public String getMessage() {
    return MessageUtil.getMessage(getDetailKey(), getParams());
  }

  @Override
  public String toString() {
    return MessageUtil.getMessage(getMessageKey());
  }

}
