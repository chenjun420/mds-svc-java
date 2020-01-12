package com.pts.mds.model;

import lombok.Data;

@Data
public class InfoWithPage<T> {

    private int pagesTotal;
    private int pageNum;
    private int pageSize;

    private T data;
}
