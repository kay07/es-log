package com.elco.system.platform.logs.service;

import com.elco.platform.util.PageResult;
import com.elco.system.platform.logs.entity.dto.SearchLog;
import com.elco.system.platform.logs.entity.vi.MsgLog;

import java.util.List;

/**
 * @author kay
 * @date 2021/9/26
 */
public interface LogsService {
    PageResult<List<MsgLog>> msg(SearchLog searchLog);
}
