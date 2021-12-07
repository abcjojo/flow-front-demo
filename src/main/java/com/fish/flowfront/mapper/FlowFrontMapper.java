package com.fish.flowfront.mapper;

import com.fish.flowfront.domain.FlowBaseInfo;
import com.fish.flowfront.domain.StatZoneDayBaseInfo;
import com.fish.flowfront.domain.ZoneBaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface FlowFrontMapper {

    /**
     * 获取流量主基本信息
     * @return
     */
    FlowBaseInfo selectFlowBaseInfoByName(@Param("username") String username);

    /**
     *  获取广告位基本信息
     * @param flowId
     * @param zoneName
     * @return
     */
    List<ZoneBaseInfo> selectZoneBaseInfo(@Param("flowId") Long flowId, @Param("zoneName") String zoneName);

    /**
     *  数据报表
     * @param
     * @return
     */
    List<StatZoneDayBaseInfo> selectStatZoneDay(@Param("flowId") Long flowId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("zoneId") String zoneId);

    /**
     *  根据流量主id查询流量主基本信息
     * @param flowId
     * @return
     */
    FlowBaseInfo selectFlowBaseInfoByFlowId(@Param("flowId") Long flowId);

    /**
     *  修改密码
     * @param pwd   明文密码
     * @param newPwd  加盐密码
     * @param flowId
     * @return
     */
    Integer changePwdByFlowId(@Param("pwd") String pwd, @Param("newPwd") String newPwd, @Param("flowId") Long flowId);

    /**
     *
     * @param flowId
     * @param startDate  检索开始时间
     * @param endDate  检索结束时间
     * @param zoneId  广告位id
     * @return
     */
    Integer countStatZoneDay(@Param("flowId") Long flowId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("zoneId") String zoneId);

    /**
     *  记录登录失败次数
     * @return
     */
    int failLoginRecord(@Param("flowId") Long flowId);

    /**
     *
      * @param ip         登录ip
     * @param nowDate       登录时间
     * @param token         token
     * @param flowId
     * @return
     */
    Integer succeeLogin(@Param("ip") String ip, @Param("nowDate") Long nowDate, @Param("token") String token, @Param("flowId") Long flowId);
}
