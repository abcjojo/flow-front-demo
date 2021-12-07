package com.fish.flowfront.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "流量主信息")
public class FlowLoginDto {

    @ApiModelProperty(value = "流量主id")
    private Long flowId;

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最近登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "email")
    private String email;
}
