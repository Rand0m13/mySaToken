package com.rangamer.mysatoken.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rangamer.mysatoken.util.BIJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author jesse
 * @date 2021年12月09日 14:07
 */
@Service
@Slf4j
public class UpLevelService {

    String cookies = "_uuid=2FE8109F5-2B7B-1101010-DC9B-24844243F51D66296infoc; buvid3=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; b_nut=1638722266; buvid_fp=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; CURRENT_FNVAL=2000; bsource=share_source_qqchat; blackside_state=1; sid=5lc2xq5u; rpdid=|(m)~||YkJk0J'uYJ)mJlk~k; innersign=0; b_lsid=9210221043_17D98A7C8C8; fingerprint=66d70d1a951c7dce4ab31177ca6e0a4d; buvid_fp_plain=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; DedeUserID=62004798; DedeUserID__ckMd5=eea1927a88df77ae; SESSDATA=520abe81%2C1654495694%2C89d55*c1; bili_jct=e700c685bae47bf68f11a801fd0324f5; i-wanna-go-back=1; b_ut=6";

    String cookiesJson = "{'_uuid': '2FE8109F5-2B7B-1101010-DC9B-24844243F51D66296infoc', 'buvid3': 'CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc', 'b_nut': '1638722266', 'buvid_fp': 'CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc', 'CURRENT_FNVAL': '2000', 'bsource': 'share_source_qqchat', 'blackside_state': '1', 'sid': '5lc2xq5u', 'rpdid': \"|(m)~||YkJk0J'uYJ)mJlk~k\", 'innersign': '0', 'b_lsid': '9210221043_17D98A7C8C8', 'fingerprint': '66d70d1a951c7dce4ab31177ca6e0a4d', 'buvid_fp_plain': 'CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc', 'DedeUserID': '62004798', 'DedeUserID__ckMd5': 'eea1927a88df77ae', 'SESSDATA': '520abe81%2C1654495694%2C89d55*c1', 'bili_jct': 'e700c685bae47bf68f11a801fd0324f5', 'i-wanna-go-back': '1', 'b_ut': '6'}";

    private String assemblyJsonData(String cookie) {
        Map<String, String> map = BIJsonUtil.getMap(cookie);
        return JSON.toJSONString(map);
    }

    private Map<String, String> assemblyJsonData2Map(String cookie) {
        return BIJsonUtil.getMap(cookie);
    }

    /**
     * 执行升级
     * @param cookie
     * @return
     */
    public String getInfo(String cookie,boolean up) {
        String msg = "啥都没干！";
        String resInfo = HttpRequest.get("http://api.bilibili.com/x/space/myinfo")
                .header(Header.COOKIE, cookie)//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();
        if (StringUtils.isNotBlank(resInfo)) {
            Map map = (Map) JSONObject.parse(resInfo);
            String code = String.valueOf(map.get("code"));
            if ("0".equals(code)) {
                log.info("获取个人信息成功！");
                String data = String.valueOf(map.get("data"));
                Map dataMap = (Map) JSONObject.parse(data);
                Integer uid = (Integer) dataMap.get("mid");
                String name = String.valueOf(dataMap.get("name"));
                Integer level = (Integer) dataMap.get("level");
                //获取经验数据
                String exp = String.valueOf(dataMap.get("level_exp"));
                Map expMap = (Map) JSONObject.parse(exp);
                Integer currentExp = (Integer) expMap.get("current_exp");
                Integer nextExp = (Integer) expMap.get("next_exp");
                Integer subExp = nextExp - currentExp;
                Integer days = subExp / 65;
                BigDecimal coin = this.getCoin(cookie);
                msg = name + "，你好！你当前的等级为" + level + "级，硬币数量为" + coin + "，当前经验值为" + currentExp + "点，还差" +
                        subExp + "点经验升至下一级，预计还需要" + days + "天。";
                log.info(msg);
                if (up){
                    this.getActiveInfo(cookie);
                }
            } else {
                throw new ValidateException("cookie错误！");
            }
        }
        return msg;
    }

    /**
     * 获取硬币数量
     *
     * @param cookie
     * @return
     */
    public BigDecimal getCoin(String cookie) {
        BigDecimal coin = BigDecimal.ZERO;
        String coinInfo = HttpRequest.get("http://account.bilibili.com/site/getCoin")
                .header(Header.COOKIE, cookie)//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();
        if (StringUtils.isNotBlank(coinInfo)) {
            Map map = (Map) JSONObject.parse(coinInfo);
            String code = String.valueOf(map.get("code"));
            if ("0".equals(code)) {
                String data = String.valueOf(map.get("data"));
                Map dataMap = (Map) JSONObject.parse(data);
                coin = new BigDecimal(String.valueOf(dataMap.get("money")));
            }
        }
        return coin;
    }

    /**
     * 获取推荐动态
     *
     * @param cookie
     */
    public void getActiveInfo(String cookie) {
        String activeInfo = HttpRequest.get("https://api.bilibili.com/x/web-interface/index/top/rcmd?fresh_type=3")
                .header(Header.COOKIE, cookie)//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();
        if (StringUtils.isNotBlank(activeInfo)) {
            Map map = (Map) JSONObject.parse(activeInfo);
            String code = String.valueOf(map.get("code"));
            if ("0".equals(code)) {
                String data = String.valueOf(map.get("data"));
                Map dataMap = (Map) JSONObject.parse(data);
                String item = String.valueOf(dataMap.get("item"));
                List<JSONObject> list = (List<JSONObject>) JSONArray.parse(item);
                List<String> aidList = CollUtil.newArrayList();
                list.forEach(x -> aidList.add(String.valueOf(x.get("id"))));
                List<String> randomSeries = aidList.subList(0, 5);//随便取五个视频
                if (CollUtil.isNotEmpty(randomSeries) && randomSeries.size() == 5) {
                    Map<String, String> jsonMap = this.assemblyJsonData2Map(cookie);
                    String csrf = jsonMap.get("bili_jct");
                    randomSeries.forEach(aid -> {
                        this.toCoin(aid, cookie, csrf);
                        ThreadUtil.sleep(5000);//睡5秒
                        this.toView(aid,cookie,csrf);
                        ThreadUtil.sleep(5000);//睡5秒
                        this.shareVideo(aid,cookie,csrf);
                    });
                }

            }
        }
    }

    /**
     * 投币
     * @param aid
     * @param cookie
     * @param csrf
     */
    private void toCoin(String aid, String cookie, String csrf) {
        BigDecimal coinNum = this.getCoin(cookie);
        if (coinNum.compareTo(BigDecimal.ZERO) < 1) {
            throw new ValidateException("硬币数量不够！");
        }
        //数量够就投币
        Map map = MapUtil.newHashMap();
        map.put("aid", aid);
        map.put("multiply", 1);
        map.put("select_like", 1);
        //map.put("cross_domain",true); 此字段不能加
        map.put("csrf", csrf);
        String info = HttpRequest.post("http://api.bilibili.com/x/web-interface/coin/add")
                .header(Header.COOKIE, cookie)//头信息，多个头信息多次调用此方法即可
                .form(map)
                .timeout(20000)//超时，毫秒
                .execute().body();
        Map result = (Map) JSONObject.parse(info);
        String code = String.valueOf(result.get("code"));
        if ("0".equals(code)) {
            log.info("为aid={}的视频投币成功！", aid);
        } else {
            log.info("为aid={}的视频投币失败！未知原因", aid);
        }
    }

    /**
     * 看视频
     * @param aid
     * @param cookie
     * @param csrf
     */
    private void toView(String aid, String cookie, String csrf) {
        int playedTime = RandomUtil.randomInt(10, 100);
        Map map = MapUtil.newHashMap();
        map.put("aid", aid);
        map.put("played_time", playedTime);
        map.put("csrf", csrf);
        String info = HttpRequest.post("https://api.bilibili.com/x/click-interface/web/heartbeat")
                .header(Header.COOKIE, cookie)//头信息，多个头信息多次调用此方法即可
                .form(map)
                .timeout(20000)//超时，毫秒
                .execute().body();

        Map result = (Map) JSONObject.parse(info);
        String code = String.valueOf(result.get("code"));
        if ("0".equals(code)) {
            log.info("观看aid={}的视频成功！", aid);
        } else {
            log.info("观看aid={}的视频失败！未知原因", aid);
        }
    }

    /**
     * 分享视频
     * @param aid
     * @param cookie
     * @param csrf
     */
    private void shareVideo(String aid, String cookie, String csrf) {
        Map map = MapUtil.newHashMap();
        map.put("aid", aid);
        map.put("csrf", csrf);
        String info = HttpRequest.post("https://api.bilibili.com/x/web-interface/share/add")
                .header(Header.COOKIE, cookie)//头信息，多个头信息多次调用此方法即可
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Connection","keep-alive")
                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36 Edg/93.0.961.38")
                .form(map)
                .timeout(20000)//超时，毫秒
                .execute().body();

        Map result = (Map) JSONObject.parse(info);
        String code = String.valueOf(result.get("code"));
        if ("0".equals(code)) {
            log.info("分享aid={}的视频成功！", aid);
        } else {
            log.info("分享aid={}的视频失败！未知原因", aid);
        }
    }


}
