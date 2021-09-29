package com.elco.system.platform.logs.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author kay
 * @date 2021/9/27
 */
@ApiModel(value = "日志查询-入参")
@Data
public class SearchLog implements Serializable {
    @ApiModelProperty(value = "页码")
    private int page;
    @ApiModelProperty(value = "服务名称")
    private String serviceName;
    @ApiModelProperty(value = "微服务所在节点名称")
    private String hostName;
    @ApiModelProperty(value = "日志类型")
    private String level;
    @ApiModelProperty(value = "开始时间,格式2021-01-01 01:01:01")
    private String startTime;
    @ApiModelProperty(value = "结束时间,格式2021-01-01 01:01:01")
    private String endTime;
}
