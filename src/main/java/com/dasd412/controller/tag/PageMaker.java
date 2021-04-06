package com.dasd412.controller.tag;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class PageMaker<T> {

  private Page<T> result;

  private Pageable previous;
  private Pageable next;

  private int currentPageNumber;
  private int totalPageNumber;

  private Pageable currentPage;

  private List<Pageable> pageList;

  public PageMaker(Page<T> result) {
    this.result = result;
    this.currentPage = result.getPageable();
    this.currentPageNumber = currentPage.getPageNumber() + 1;
    this.totalPageNumber = result.getTotalPages();
    this.pageList = new ArrayList<>();
    calculatePages();
  }

  private void calculatePages() {
    int tempEndNumber = (int) (Math.ceil(this.currentPageNumber / 10.0) * 10);

    int startNumber = tempEndNumber - 9;

    Pageable startPage = this.currentPage;

    for (int i = startNumber; i < this.currentPageNumber; i++) {
      startPage = startPage.previousOrFirst();
    }
    this.previous = startPage.getPageNumber() <= 0 ? null : startPage.previousOrFirst();

    if (this.totalPageNumber < tempEndNumber) {
      tempEndNumber = this.totalPageNumber;
      this.next = null;
    }
    for (int i = startNumber; i <= tempEndNumber; i++) {
      pageList.add(startPage);
      startPage = startPage.next();
    }

    this.next = startPage.getPageNumber() + 1 < totalPageNumber ? startPage : null;

  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("previous page : ",previous)
        .append("next page : ",next)
        .append("current page number : ",currentPageNumber)
        .append("total page number : "+totalPageNumber)
        .append("page list : "+pageList)
        .toString();
  }
}
