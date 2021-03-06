package com.fish.flowfront.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("广告位数据")
public class ZoneBaseInfo {

    @ApiModelProperty("广告位id")
    private Long zoneId;

    @ApiModelProperty("")
    private Long intype;

    @ApiModelProperty("")
    private Long bagid;

    @ApiModelProperty("广告位名称")
    private String zoneName;

    @ApiModelProperty("投放类型")
    private String styleType;

    @ApiModelProperty("投放类型")
    private String styleTypeText;

    @ApiModelProperty("代码")
    private String extendCode;

    @ApiModelProperty("唤醒url")
    private String hxurl;

    @ApiModelProperty("唤醒域名id")
    private String hxDomainId;

    @ApiModelProperty("")
    private String referBi;
}
