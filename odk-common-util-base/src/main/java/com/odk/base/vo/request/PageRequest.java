package com.odk.base.vo.request;

/**
 * PageRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
public class PageRequest extends BaseRequest {
    private static final long serialVersionUID = -2560169579099818943L;

    private int pageNo = 0;

    private int pageSize = 10;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
