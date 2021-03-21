package com.dasd412.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class EmailUtils {

  private EmailUtils() {
  }

  public static boolean checkAddress(String address) {
    checkArgument(isNotEmpty(address), "address must be provided");
    checkArgument(address.length() >= 4 && address.length() <= 50,
        "address length must be between 4 and 50 characters!");

    //이메일 정규식 표현과 매칭되는지 판단한다.
    return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", address);
  }

  public static String getName(String address) {
      if (!checkAddress(address)) {
          return null;
      }

    String[] tokens = address.split("@");
    if (tokens.length == 2) {
      return tokens[0];
    } else {
      return null;
    }
  }

  public static String getDomainName(String address) {
      if (!checkAddress(address)) {
          return null;
      }

    String[] tokens = address.split("@");
    if (tokens.length == 2) {
      return tokens[1];
    } else {
      return null;
    }
  }

}
