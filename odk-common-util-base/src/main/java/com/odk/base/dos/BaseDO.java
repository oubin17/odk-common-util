package com.odk.base.dos;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * BaseDO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
public class BaseDO extends DO {
    private static final long serialVersionUID = -279848917774829594L;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 租户ID
     */
    private String tenantId;

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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
