package com.dasd412.controller.tag;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageVO {

  private static final int DEFAULT_SIZE = 10;
  private static final int MAX_SIZE = 20;

  private int page;
  private int size;

  private String keyword;
  private String type;

  public PageVO() {
    this.page = 1;
    this.size = DEFAULT_SIZE;
  }

  public int getPage() {
    return page;
  }

  public int getSize() {
    return size;
  }

  public String getKeyword() {
    return keyword;
  }

  public String getType() {
    return type;
  }

  public void setPage(int page) {
    this.page = page < 0 ? 1 : page;
  }

  public void setSize(int size) {
    this.size = size < DEFAULT_SIZE || size > MAX_SIZE ? DEFAULT_SIZE : size;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Pageable makePageable(SortDir direction, String... props) {
    Sort.Direction dir = direction == SortDir.DESC ? Direction.DESC : Direction.ASC;
    return PageRequest.of(this.page - 1, this.size, dir, props);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("page", page)
        .append("size", size)
        .append("type", type)
        .append("keyword", keyword)
        .toString();
  }

}
