package com.odk.base.vo.request;

import java.io.Serial;
import java.util.Objects;

/**
 * PageRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
public class PageParamRequest extends BaseRequest {

    @Serial
    private static final long serialVersionUID = -2560169579099818943L;

    // 页码（从 1 开始）
    private int page = 1;

    // 每页条目数
    private int size = 10;

    // 排序字段
    private String sortField;

    // 排序方向（ASC 或 DESC）
    private String sortDirection = "ASC";

    public PageParamRequest() {
    }

    public PageParamRequest(int page, int size) {
        setPage(page);
        setSize(size);
    }

    public PageParamRequest(int page, int size, String sortField, String sortDirection) {
        setPage(page);
        setSize(size);
        setSortField(sortField);
        setSortDirection(sortDirection);
    }

    public int getPage() {
        return page;
    }

    /**
     * 首页码为 0
     *
     * @param page
     */
    public void setPage(int page) {
        this.page = Math.max(page, 0);
    }

    public int getSize() {
        return size;
    }

    /**
     * 每页条目数，最小值为 1
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = Math.max(size, 1);
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        if (!"ASC".equalsIgnoreCase(sortDirection) && !"DESC".equalsIgnoreCase(sortDirection)) {
            throw new IllegalArgumentException("Sort direction must be 'ASC' or 'DESC'");
        }
        this.sortDirection = sortDirection.toUpperCase();
    }

    /**
     * 计算偏移量
     *
     * @return 偏移量
     */
    public int getOffset() {
        return (page - 1) * size;
    }

    /**
     * 判断是否有排序
     *
     * @return true 如果设置了排序字段，否则 false
     */
    public boolean isSorted() {
        return sortField != null && !sortField.isEmpty();
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "pageNo=" + page +
                ", pageSize=" + size +
                ", sortField='" + sortField + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageParamRequest that = (PageParamRequest) o;
        return page == that.page &&
                size == that.size &&
                Objects.equals(sortField, that.sortField) &&
                Objects.equals(sortDirection, that.sortDirection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, size, sortField, sortDirection);
    }

}
