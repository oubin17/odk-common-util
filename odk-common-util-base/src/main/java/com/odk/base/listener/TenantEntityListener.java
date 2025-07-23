package com.odk.base.listener;

import com.odk.base.context.TenantIdContext;
import com.odk.base.dos.BaseDO;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * TenantEntityListener
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/19
 */
@Component
public class TenantEntityListener {

    // 使用 @Value 获取配置文件中的租户 ID
//    @Transient // 非持久化字段，用于注入 Spring 的配置值
//    @Value("${tenant.id}")
//    private String tenantId;

    @PrePersist
    public void prePersist(BaseDO entity) {
        if (entity.getTenantId() == null) {
            entity.setTenantId(TenantIdContext.getTenantId()); // 自动填充租户 ID
        }
    }
}
