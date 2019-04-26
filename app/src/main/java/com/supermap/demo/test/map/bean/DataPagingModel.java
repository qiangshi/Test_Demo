package com.supermap.demo.test.map.bean;

import java.util.List;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 数据分页对象
 * @Date: 2019/4/14
 */
public class DataPagingModel<T> {

    //每页显示的条数
    private int pageNum;

    //当前页数
    private int page;

    //是否末页
    private boolean isPageEnd;

    //数据列表
    private List<T> dataList;

    /**
     * 构造函数
     * @param pageNum 每页显示的条数
     * @param page 当前页数
     */
    public DataPagingModel(int pageNum, int page) {
        this.pageNum = pageNum;
        this.page = page;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isPageEnd() {
        return isPageEnd;
    }

    public void setPageEnd(boolean pageEnd) {
        isPageEnd = pageEnd;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
