package com.odk.base.context;

import com.odk.base.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static com.odk.base.exception.BizErrorCode.TENANT_ILLEGAL;
import static com.odk.base.exception.BizErrorCode.TENANT_NULL;

/**
 * 租户上下文
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
public class TenantIdContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantIdContext.class);


    private TenantIdContext() {}

    /**
     * 场景上下文
     */
    private static final ThreadLocal<String> TENANT_ID_CONTEXT = new ThreadLocal<>();

    private static final Set<String> TENANT_IDS = Set.of("DEFAULT", "ODK-LUSHI");

    /**
     * 默认租户
     */
    public static final String DEFAULT_TENANT_ID = "DEFAULT";

    /**
     * 设置租户ID, 如果为空则设置为默认租户ID
     *
     * @param tenantId
     */
    public static void setTenantIdOrDefault(String tenantId) {
        if (StringUtils.isNotBlank(tenantId) && TENANT_IDS.contains(tenantId)) {
            TENANT_ID_CONTEXT.set(tenantId);
        } else {
            TENANT_ID_CONTEXT.set(DEFAULT_TENANT_ID);
        }
    }

    /**
     * 设置租户ID, 如果为空则抛出异常
     *
     * @param tenantId
     */
    public static void setTenantId(String tenantId) {
        if (StringUtils.isNotBlank(tenantId) && TENANT_IDS.contains(tenantId)) {
            TENANT_ID_CONTEXT.set(tenantId);
        } else {
            LOGGER.error("租户ID非法: {}", tenantId);
            throw new BizException(TENANT_ILLEGAL, "租户非法:" + tenantId);
        }
    }

    /**
     * 获取租户ID, 如果为空则抛出异常
     *
     * @return
     */
    public static String getTenantId() {
        if (TENANT_ID_CONTEXT.get() == null) {
            LOGGER.error("租户ID为空");
            throw new BizException(TENANT_NULL, "租户非法");
        }
        return TENANT_ID_CONTEXT.get();
    }

    /**
     * 获取租户ID，如果为空则返回默认租户ID
     *
     * @return
     */
    public static String getTenantIdOrDefault() {
        return TENANT_ID_CONTEXT.get() == null ? DEFAULT_TENANT_ID : TENANT_ID_CONTEXT.get();
    }

    /**
     * 清除上下文
     */
    public static void clear() {
        TENANT_ID_CONTEXT.remove();
    }
}
