package com.odk.base.vo.response;

import com.odk.base.vo.VO;

import java.util.ArrayList;
import java.util.List;

/**
 * PageResponse
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
public class PageResponse<T> extends VO {
    private static final long serialVersionUID = -4986832240116546246L;

    /**
     * 分页列表
     */
    private List<T> pageList;

    /**
     * 总数
     */
    private int count;

    /**
     * 返回对象
     *
     * @param pageList
     * @param count
     * @return
     * @param <T>
     */
    public static <T> PageResponse<T> of(List<T> pageList, int count) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.pageList = pageList;
        pageResponse.count = count;
        return pageResponse;
    }

    /**
     * 无数据
     *
     * @return
     * @param <T>
     */
    public static <T> PageResponse<T> ofEmpty() {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.pageList = new ArrayList<>();
        pageResponse.count = 0;
        return pageResponse;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
