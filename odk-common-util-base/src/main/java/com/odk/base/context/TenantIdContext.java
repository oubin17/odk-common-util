package com.odk.base.context;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * 租户上下文
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
public class TenantIdContext {

    private TenantIdContext() {}

    /**
     * 场景上下文
     */
    private static final ThreadLocal<String> TENANT_ID_CONTEXT = new ThreadLocal<>();

    private static final Set<String> TENANT_IDS = Set.of("DEFAULT", "ODK-");

    /**
     * 默认租户
     */
    public static final String DEFAULT_TENANT_ID = "DEFAULT";

    /**
     * 设置租户ID
     *
     * @param tenantId
     */
    public static void setTenantId(String tenantId) {
        if (StringUtils.isNotBlank(tenantId) && TENANT_IDS.contains(tenantId)) {
            TENANT_ID_CONTEXT.set(tenantId);
        } else {
            TENANT_ID_CONTEXT.set(DEFAULT_TENANT_ID);
        }
    }

    /**
     * 获取租户ID
     *
     * @return
     */
    public static String getTenantId() {
        return TENANT_ID_CONTEXT.get() == null ? DEFAULT_TENANT_ID : TENANT_ID_CONTEXT.get();
    }

    /**
     * 清除上下文
     */
    public static void clear() {
        TENANT_ID_CONTEXT.remove();
    }
}
