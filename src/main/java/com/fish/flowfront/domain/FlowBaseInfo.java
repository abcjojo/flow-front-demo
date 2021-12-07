package com.fish.flowfront.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowBaseInfo {
    private Long flowId;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String salt;
    private Long showFlg;
    private Long delFlg;
}
