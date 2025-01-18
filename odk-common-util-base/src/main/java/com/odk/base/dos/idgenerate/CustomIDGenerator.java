package com.odk.base.dos.idgenerate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
        //这里如果Object中的id不为空，则直接使用object中的id，实现逻辑待优化
        BaseDO baseDO = (BaseDO)object;
        if (null == baseDO.getId()) {
            return String.valueOf(SnowflakeIdUtil.nextId());
        } else {
            return baseDO.getId();
        }
    }
}
