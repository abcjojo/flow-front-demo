package com.fish.flowfront.service;

import com.fish.flowfront.common.R;
import com.fish.flowfront.domain.StatZoneDayBaseInfo;
import com.fish.flowfront.domain.ZoneBaseInfo;
import com.fish.flowfront.utils.page.TableDataInfo;
import com.fish.flowfront.vo.FlowFrontLoginVo;
import com.fish.flowfront.vo.FlowQueryVo;
import com.fish.flowfront.vo.FlowZoneQueryVo;

import java.util.List;

public interface FlowFrontService {

    /**
     *  流量主前台登录接口
     */
    R login(FlowFrontLoginVo loginVo);

    /**
     * 退出登录
     * @param userName
     * @return
     */
    R logout(String userName);

    /**
     *  广告位管理
     * @param flowQueryVo
     * @return
     */
    TableDataInfo zoneBaseInfo(FlowZoneQueryVo flowQueryVo) ;

    /**
     *  数据报表
     * @param flowQueryVo
     * @return
     */
    TableDataInfo statZoneDay(FlowQueryVo flowQueryVo);

    /**
     * 修改密码
     * @param pwd
     * @return
     */
    Integer changePwd(String pwd);
}
