package com.dasd412.controller.tag;

public enum SortDir {
  DESC(0), ASC(1);

  private int dir;

  SortDir(int dir) {
    this.dir = dir;
  }
}
