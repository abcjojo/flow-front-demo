package com.fish.flowfront.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "数据统计实体")
public class StatZoneDayBaseInfo {

    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ApiModelProperty(value = "点击数")
    private Long expCounts;

    @ApiModelProperty(value = "真实收益")
    private Long realEar;
}
