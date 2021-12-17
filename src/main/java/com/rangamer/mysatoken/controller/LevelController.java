package com.rangamer.mysatoken.controller;

import cn.hutool.core.exceptions.ValidateException;
import com.rangamer.mysatoken.base.SingleResult;
import com.rangamer.mysatoken.service.impl.UpLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jesse
 * @date 2021年12月09日 14:19
 */
@Api(value = "等级")
@RestController
@RequestMapping("/level")
public class LevelController {

    @Autowired
    UpLevelService upLevelService;

    @PostMapping("/up")
    @ApiOperation(value = "手动触发任务")
    public SingleResult<String> upLevel(@RequestBody String cookie) {
        SingleResult<String> result = new SingleResult<>();
        String msg = "";
        try {
            //cookie = "_uuid=2FE8109F5-2B7B-1101010-DC9B-24844243F51D66296infoc; buvid3=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; b_nut=1638722266; buvid_fp=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; CURRENT_FNVAL=2000; bsource=share_source_qqchat; blackside_state=1; sid=5lc2xq5u; rpdid=|(m)~||YkJk0J'uYJ)mJlk~k; innersign=0; b_lsid=9210221043_17D98A7C8C8; fingerprint=66d70d1a951c7dce4ab31177ca6e0a4d; buvid_fp_plain=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; DedeUserID=62004798; DedeUserID__ckMd5=eea1927a88df77ae; SESSDATA=520abe81%2C1654495694%2C89d55*c1; bili_jct=e700c685bae47bf68f11a801fd0324f5; i-wanna-go-back=1; b_ut=6";
            msg = upLevelService.getInfo(cookie,true);
        } catch (ValidateException e){
            msg = e.getMessage();
            result.setSuccess(false);
        }catch (Exception e) {
            result.setMessage("手动触发失败！");
            result.setSuccess(false);
            e.printStackTrace();
        }
        result.setMessage(msg);
        return result;
    }

    @PostMapping("/getInfo")
    @ApiOperation(value = "仅查看资料")
    public SingleResult<String> getInfo(@RequestBody String cookie) {
        SingleResult<String> result = new SingleResult<>();
        String msg = "";
        try {
            //cookie = "_uuid=2FE8109F5-2B7B-1101010-DC9B-24844243F51D66296infoc; buvid3=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; b_nut=1638722266; buvid_fp=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; CURRENT_FNVAL=2000; bsource=share_source_qqchat; blackside_state=1; sid=5lc2xq5u; rpdid=|(m)~||YkJk0J'uYJ)mJlk~k; innersign=0; b_lsid=9210221043_17D98A7C8C8; fingerprint=66d70d1a951c7dce4ab31177ca6e0a4d; buvid_fp_plain=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; DedeUserID=62004798; DedeUserID__ckMd5=eea1927a88df77ae; SESSDATA=520abe81%2C1654495694%2C89d55*c1; bili_jct=e700c685bae47bf68f11a801fd0324f5; i-wanna-go-back=1; b_ut=6";
            msg = upLevelService.getInfo(cookie,false);
            System.out.println("返回值>>>："+msg);
        } catch (ValidateException e){
            msg = e.getMessage();
            result.setSuccess(false);
        }catch (Exception e) {
            result.setMessage("手动触发失败！");
            result.setSuccess(false);
            e.printStackTrace();
        }
        result.setMessage(msg);
        return result;
    }

}
