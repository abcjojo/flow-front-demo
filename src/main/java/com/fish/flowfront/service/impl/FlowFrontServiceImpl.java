package com.fish.flowfront.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fish.flowfront.common.R;
import com.fish.flowfront.domain.FastAppSource;
import com.fish.flowfront.domain.FlowBaseInfo;
import com.fish.flowfront.domain.StatZoneDayBaseInfo;
import com.fish.flowfront.domain.ZoneBaseInfo;
import com.fish.flowfront.dto.FlowLoginDto;
import com.fish.flowfront.dto.ZoneBaseDto;
import com.fish.flowfront.exception.BaseException;
import com.fish.flowfront.exception.CustomException;
import com.fish.flowfront.mapper.FlowFrontMapper;
import com.fish.flowfront.service.FlowFrontService;
import com.fish.flowfront.utils.*;
import com.fish.flowfront.utils.page.TableDataInfo;
import com.fish.flowfront.vo.FlowFrontLoginVo;
import com.fish.flowfront.vo.FlowQueryVo;
import com.fish.flowfront.vo.FlowZoneQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class FlowFrontServiceImpl implements FlowFrontService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private FlowFrontMapper flowFrontMapper;

    /**
     * 流量主前台登录接口
     */
    @Override
    public R login(FlowFrontLoginVo loginVo) {
        // 获取流量主基本信息
        String username = loginVo.getUserName();
        FlowBaseInfo flowBaseInfo = flowFrontMapper.selectFlowBaseInfoByName(username);
        // 密码加盐处理
        String firPwd = DigestUtils.md5DigestAsHex(loginVo.getPassword().getBytes());
        String password = DigestUtils.md5DigestAsHex((firPwd + flowBaseInfo.getSalt()).getBytes());

        if (ObjectUtils.isEmpty(flowBaseInfo)) {
            return execException("登录用户：" + username + " 不存在", flowBaseInfo.getFlowId());
        } else if (StringUtils.isNotBlank(password) && (!password.equals(flowBaseInfo.getPassword()))) {
            return execException("密码错误", flowBaseInfo.getFlowId());
        } else if (Objects.equals(flowBaseInfo.getShowFlg(), 1L)) {
            return execException("该账号已被禁用，请联系管理员！", flowBaseInfo.getFlowId());
        } else if (Objects.equals(flowBaseInfo.getShowFlg(), 2L)) {
            return execException("该账号未被认领，请联系管理员！", flowBaseInfo.getFlowId());
        } else if (Objects.equals(flowBaseInfo.getDelFlg(), 1L)) {
            return execException("该账号不存在，请联系管理员！", flowBaseInfo.getFlowId());
        }

        FlowLoginDto loginDto = FlowLoginDto
                .builder()
                .flowId(flowBaseInfo.getFlowId())
                .username(flowBaseInfo.getUsername())
                .email(flowBaseInfo.getEmail())
                .nickname(flowBaseInfo.getNickname())
                .build();

        //通过tokenKey查找用户token
        String tokenKey = "gongju:flow:tokenkey:" + username;
        log.info("--------------tokenKey:{}", tokenKey);
        String token = stringRedisTemplate.opsForValue().get(tokenKey);
        if (StringUtils.isEmpty(token)) {
            token = UUID.randomUUID().toString().replace("-", "").toLowerCase(Locale.ROOT);
            stringRedisTemplate.opsForValue().set(tokenKey, token);
        }
        loginDto.setToken(token);
        loginDto.setLoginTime(DateUtils.getNowDate());

        // 保存用户token
        stringRedisTemplate.opsForValue().set(getTokenKey(token), JSONObject.toJSONString(loginDto));
        log.info("--------------getTokenKey(token):{}", getTokenKey(token));
        // 记录用户登录
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        Long nowDate = System.currentTimeMillis();
        flowFrontMapper.succeeLogin(ip, null, token, flowBaseInfo.getFlowId());
        return R.success(loginDto);

    }

    private R execException(String message, Long flowId) {
        // 记录登录失败次数
        flowFrontMapper.failLoginRecord(flowId);
        throw new BaseException(message);
    }

    /**
     * 退出登录
     *
     * @param username
     * @return
     */
    @Override
    public R logout(String username) {
        String tokenKey = "gongju:flow:tokenkey:" + username;
        String token = stringRedisTemplate.opsForValue().get(tokenKey);
        stringRedisTemplate.delete(tokenKey);
        stringRedisTemplate.delete(getTokenKey(token));
        return R.success();
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public FlowLoginDto getLoginUser() {
        String token = ServletUtils.getRequest().getHeader("TOKEN");
        if (StringUtils.isBlank(token)) {
            throw new CustomException("未登录，token为空", HttpStatus.UNAUTHORIZED);
        }
        String value = stringRedisTemplate.opsForValue().get(getTokenKey(token));
        if (StringUtils.isBlank(value)) {
            throw new CustomException("未登录，无对象", HttpStatus.UNAUTHORIZED);
        }
        return JSONObject.parseObject(value, FlowLoginDto.class);
    }


    private String getTokenKey(String key) {
        return "gongju:flow:tokenkey:" + key;
    }


    /**
     * 广告位管理
     *
     * @param flowQueryVo
     * @return
     */
    @Override
    public TableDataInfo zoneBaseInfo(FlowZoneQueryVo flowQueryVo) {
        FlowLoginDto loginUser = getLoginUser();
        List<ZoneBaseInfo> zoneBaseInfoList = flowFrontMapper.selectZoneBaseInfo(loginUser.getFlowId(), flowQueryVo.getZoneName());
        List<ZoneBaseDto> zoneBaseDtoList = new ArrayList<>();
        zoneBaseInfoList.forEach(item -> {
            ZoneBaseDto zoneBaseDto = new ZoneBaseDto();
            BeanUtils.copyProperties(item, zoneBaseDto);
            String hxdomain = flowFrontMapper.getHxdomain(item.getHxDomainId());
            if (item.getIntype().equals(3L)) {
                FastAppSource fastAppSource = flowFrontMapper.getFastappSource(item.getBagid());
                String extendCode ="hap://app/" + fastAppSource.getSourceBagName() +
                        '/' + fastAppSource.getSourcePage()
                        + "?strength=" + item.getReferBi().replace("'","").replace("http://","")
                        + "&hxid=" + item.getZoneId()
                        + "&type=" + item.getBagid();
                zoneBaseDto.setExtendCode(extendCode);
            } else {
                String extendCode = "//" + hxdomain + "/" + item.getHxurl();
                zoneBaseDto.setExtendCode(extendCode);
            }
            zoneBaseDtoList.add(zoneBaseDto);
        });

        return getDataTableLimit(zoneBaseDtoList, zoneBaseDtoList.size(), flowQueryVo.getPageNum(), flowQueryVo.getPageSize());
    }

    /**
     * 数据报表
     *
     * @param flowQueryVo
     * @return
     */
    @Override
    public TableDataInfo statZoneDay(FlowQueryVo flowQueryVo) {
        FlowLoginDto loginUser = getLoginUser();
        if (flowQueryVo.getEndDate() != null)
            flowQueryVo.setEndDate(DateUtil.getDateAfter(flowQueryVo.getEndDate(), 1));
        BigDecimal sumExp = flowFrontMapper.sumExpCountsByFlowId(loginUser.getFlowId(), flowQueryVo.getStartDate(), flowQueryVo.getEndDate(), flowQueryVo.getZoneId());
        List<StatZoneDayBaseInfo> statZoneDayBaseInfoList = flowFrontMapper.selectStatZoneDay(loginUser.getFlowId(), flowQueryVo.getStartDate(), flowQueryVo.getEndDate(), flowQueryVo.getZoneId());
        TableDataInfo dataTableLimit = getDataTableLimit(statZoneDayBaseInfoList, statZoneDayBaseInfoList.size(), flowQueryVo.getPageNum(), flowQueryVo.getPageSize());
        Map<String, Object> res = new HashMap<>();
        res.put("sumExp", sumExp);
        res.put("list", dataTableLimit.getRows());
        dataTableLimit.setRows(res);
        return dataTableLimit;
    }

    /**
     * 手动分页
     */
    public static TableDataInfo getDataTableLimit(List list, long total, Integer pageNum, Integer pageSize) {

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setTotal(total);
        List rows;
        int start = (pageNum - 1) * pageSize;
        int end = start + pageSize;
        if (list.size() <= end) {
            end = list.size();
        }
        if (list.size() >= start) {
            rows = list.subList(start, end);
        } else {
            rows = null;
        }
        rspData.setRows(rows);
        return rspData;
    }

    /**
     * 修改密码
     *
     * @param pwd
     * @return
     */
    @Override
    public Integer changePwd(String pwd) {
        if (com.fish.flowfront.utils.StringUtils.isBlank(pwd))
            throw new BaseException("密码不能为空");
        if (pwd.length() < 6)
            throw new BaseException("密码最少6位");

        FlowLoginDto loginUser = getLoginUser();
        FlowBaseInfo flowBaseInfo = flowFrontMapper.selectFlowBaseInfoByFlowId(loginUser.getFlowId());
        // 密码加盐处理
        String firPwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        String newPwd = DigestUtils.md5DigestAsHex((firPwd + flowBaseInfo.getSalt()).getBytes());
        Integer row = flowFrontMapper.changePwdByFlowId(pwd, newPwd, loginUser.getFlowId());
        // 改完密码重新登录
        if (row > 0) {
            logout(flowBaseInfo.getUsername());
        }
        return row;
    }
}
