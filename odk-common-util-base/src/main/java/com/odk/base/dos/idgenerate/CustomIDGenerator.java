package com.odk.base.dos.idgenerate;

import com.odk.base.dos.BaseDO;
import com.odk.base.idgenerator.SnowflakeIdUtil;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * CustomIDGenerator
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/27
 */
public class CustomIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // 1. 增加类型安全校验
        if (!(object instanceof BaseDO)) {
            throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
        }
        BaseDO baseDO = (BaseDO) object;
        // 2. 优化空值判断逻辑
        if (baseDO.getId() == null) {
            // 3. 明确异常处理逻辑
            try {
                return String.valueOf(SnowflakeIdUtil.nextId());
            } catch (Exception e) {
                throw new MappingException("Snowflake ID generation failed", e);
            }
        }
        return baseDO.getId();
    }
}

