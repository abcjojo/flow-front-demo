package com.fish.flowfront.controller;

import com.fish.flowfront.common.R;
import com.fish.flowfront.domain.StatZoneDayBaseInfo;
import com.fish.flowfront.domain.ZoneBaseInfo;
import com.fish.flowfront.service.FlowFrontService;
import com.fish.flowfront.utils.page.TableDataInfo;
import com.fish.flowfront.vo.FlowFrontLoginVo;
import com.fish.flowfront.vo.FlowQueryVo;
import com.fish.flowfront.vo.FlowZoneQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Api(value = "流量主前台")
@RestController
@RequestMapping("/flowfront")
@CrossOrigin
public class FlowFrontController {

    @Autowired
    private FlowFrontService flowFrontService;

    /**
     *  流量主前台登录接口
     */
    @PostMapping("/login")
    @ApiOperation(value = "广告主前台登录", notes = "广告主前台登录")
    public R login(@RequestBody @Validated FlowFrontLoginVo loginVo) {
        return flowFrontService.login(loginVo);
    }

    /**
     *  登出
     * @param loginVo
     * @return
     */
    @ApiOperation(value = "退出登录", notes = "退出登录")
    @PostMapping("/loginOut")
    public R loginOut(@RequestBody FlowFrontLoginVo loginVo) {
        return flowFrontService.logout(loginVo.getUserName());
    }

    /**
     * 修改密码
     * @param pwd
     * @return
     */
    @PostMapping("/changePwd")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public R changePwd(@RequestBody String pwd) {
        return flowFrontService.changePwd(pwd) > 0 ? R.success() : R.error();
    }

    /**
     *  广告位管理
     * @param flowQueryVo
     * @return
     */
    @ApiOperation(value = "广告位管理", notes = "广告位管理")
    @PostMapping("/zoneBaseInfo")
    public TableDataInfo zoneBaseInfo(@RequestBody FlowZoneQueryVo flowQueryVo) {
        return flowFrontService.zoneBaseInfo(flowQueryVo);
    }

    /**
     *  数据报表
     * @param flowQueryVo
     * @return
     */
    @ApiOperation(value = "数据报表", notes = "数据报表")
    @PostMapping("/statZoneDay")
    public TableDataInfo statZoneDay(@RequestBody FlowQueryVo flowQueryVo) {
        return flowFrontService.statZoneDay(flowQueryVo);
    }

}