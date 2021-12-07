package com.fish.flowfront.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 广告主前台修改密码Vo
 */

@ApiModel("广告主前台修改密码Vo")
@Data
public class FlowFrontLoginVo {

    @ApiModelProperty(value = "userName")
    @NotNull
    private String userName;

    @ApiModelProperty(value = "password")
    @NotNull
    private String password;
}
