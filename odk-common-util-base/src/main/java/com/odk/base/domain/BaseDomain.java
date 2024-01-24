package com.odk.base.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
