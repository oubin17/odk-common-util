package com.odk.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseDomain
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
public class BaseDomain implements Serializable {
    private static final long serialVersionUID = 6250836304635381314L;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
