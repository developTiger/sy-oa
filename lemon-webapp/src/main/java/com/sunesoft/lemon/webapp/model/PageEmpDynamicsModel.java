package com.sunesoft.lemon.webapp.model;

/**
 * Created by xiazl on 2017/4/19.
 */
public class PageEmpDynamicsModel extends EmpDynamicsModel {
    private  int pageSize;
    private  int pageNumber;
    private  int pagesCount;
    private  int totalItemsCount;

    public PageEmpDynamicsModel(int pageNumber, int pageSize,int totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pagesCount = countPages(pageSize, totalItemsCount);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    private int countPages(int size, int itemsCount) {
        return (int) Math.ceil((double) itemsCount / size);
    }
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(int totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }
}
