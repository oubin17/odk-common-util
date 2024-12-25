package com.odk.base.vo.request;

import java.util.Objects;

/**
 * PageRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
public class PageRequest extends BaseRequest {
    private static final long serialVersionUID = -2560169579099818943L;

    // 页码（从 1 开始）
    private int pageNo = 1;

    // 每页条目数
    private int pageSize = 10;

    // 排序字段
    private String sortField;

    // 排序方向（ASC 或 DESC）
    private String sortDirection = "ASC";

    public PageRequest() {
    }

    public PageRequest(int pageNo, int pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    public PageRequest(int pageNo, int pageSize, String sortField, String sortDirection) {
        setPageNo(pageNo);
        setPageSize(pageSize);
        setSortField(sortField);
        setSortDirection(sortDirection);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = Math.max(pageNo, 1);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
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
        return (pageNo - 1) * pageSize;
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
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", sortField='" + sortField + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageRequest that = (PageRequest) o;
        return pageNo == that.pageNo &&
                pageSize == that.pageSize &&
                Objects.equals(sortField, that.sortField) &&
                Objects.equals(sortDirection, that.sortDirection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNo, pageSize, sortField, sortDirection);
    }

}
