package com.elco.system.platform.logs.entity.vi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author kay
 * @date 2021/9/28
 */
@Data
@ApiModel(value = "日志详情-出参")
public class MsgLog implements Serializable {
    @ApiModelProperty(value = "日志内容")
    private String message;
    @ApiModelProperty(value = "所在节点")
    private String node;
    @ApiModelProperty(value = "服务名称")
    private String service;
    @ApiModelProperty(value = "入库时间")
    private String intoTime;
}
