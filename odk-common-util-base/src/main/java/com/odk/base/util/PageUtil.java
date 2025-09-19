package com.odk.base.util;

import com.odk.base.vo.request.PageParamRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * PageUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/9/19
 */
public class PageUtil {

    private PageUtil() {
    }

    /**
     * 转成 Pageable 对象
     *
     * @param pageParamRequest
     * @return
     */
    public static Pageable convertToPageRequest(PageParamRequest pageParamRequest) {
        if (pageParamRequest == null) {
            return null;
        }
        if (pageParamRequest.isSorted()) {
            Sort sort = Sort.by(Sort.Direction.fromString(pageParamRequest.getSortDirection()), pageParamRequest.getSortField());
            return PageRequest.of(pageParamRequest.getPage(), pageParamRequest.getSize(), sort);
        } else {
            return PageRequest.of(pageParamRequest.getPage(), pageParamRequest.getSize());
        }

    }

}
