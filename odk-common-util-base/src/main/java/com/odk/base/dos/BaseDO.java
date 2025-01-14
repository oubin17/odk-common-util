package com.odk.base.dos;

import com.odk.base.listener.TenantEntityListener;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * BaseDO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
@MappedSuperclass
@EntityListeners(TenantEntityListener.class)
public abstract class BaseDO extends DO {

    private static final long serialVersionUID = -5695130590258342820L;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreatedDate
    private LocalDateTime createTime;

    @Column(name = "create_by")
    @CreatedBy
    private String createBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @LastModifiedDate
    private LocalDateTime updateTime;

    @Column(name = "update_by")
    @LastModifiedBy
    private String updateBy;

    /**
     * 租户ID
     */
    @Column(name = "tenant_id")
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
