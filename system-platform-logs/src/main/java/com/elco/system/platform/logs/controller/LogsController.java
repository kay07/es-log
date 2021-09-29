package com.elco.system.platform.logs.controller;

import com.elco.platform.util.PageResult;
import com.elco.system.platform.logs.entity.dto.SearchLog;
import com.elco.system.platform.logs.entity.vi.MsgLog;
import com.elco.system.platform.logs.service.LogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kay
 * @date 2021/9/26
 */
@RestController
@RequestMapping(value = "/logs")
@Api(tags = "日志中心")
public class LogsController {
    @Resource
    private LogsService logsService;
    @ApiOperation(value = "查看")
    @RequestMapping(value = "/msg",method = RequestMethod.POST)
    public PageResult<List<MsgLog>> msg(@RequestBody SearchLog searchLog){
        return logsService.msg(searchLog);
    }
}
