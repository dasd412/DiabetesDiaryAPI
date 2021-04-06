package com.dasd412.domain.diet;

import static com.google.common.base.Preconditions.checkArgument;

public class DietQuery {

  private final QueryType type;
  private String keyword;

  public DietQuery(QueryType type) {
    checkArgument(type==QueryType.DEFAULT,"Diet Query constructor with one parameter must have DEFAULT type only.");
    this.type = type;
  }

  public DietQuery(QueryType type, String keyword) {
    checkArgument(type!=QueryType.DEFAULT,"Diet Query constructor with two parameter must not have DEFAULT type.");
    this.type = type;
    this.keyword = keyword;
  }

  public String getKeyword() {
    return keyword;
  }

  public QueryType getType() {
    return type;
  }
}
