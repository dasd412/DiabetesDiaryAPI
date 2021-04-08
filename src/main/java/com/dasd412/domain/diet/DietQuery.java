package com.dasd412.domain.diet;


public class DietQuery {

  private final QueryType type;
  private String keyword;

  public DietQuery(String type, String keyword) {
    if (type == null) {
      this.type = QueryType.DEFAULT;
    } else {
      this.type = QueryType.FoodName;
    }

    this.keyword = keyword;
  }

  public String getKeyword() {
    return keyword;
  }

  public QueryType getType() {
    return type;
  }
}
