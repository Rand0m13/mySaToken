package com.rangamer.mysatoken.task;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rangamer.mysatoken.mapper.BiCookieMapper;
import com.rangamer.mysatoken.model.BiCookie;
import com.rangamer.mysatoken.service.impl.UpLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jesse
 * @date 2021年12月09日 13:59
 */
@Component
@Slf4j
@Async
public class ScheduledTask {

    @Autowired
    UpLevelService upLevelService;

    @Autowired
    BiCookieMapper biCookieMapper;



    /**
     * 每天上午10:15触发
     */
    @Async("up_thread")
    @Scheduled(cron="0 15 10 * * ?")
    public void scheduledTask() {
        //查出所有的cookies
        QueryWrapper<BiCookie> qw = new QueryWrapper<>();
        qw.eq("needUpLevel",true);
        List<BiCookie> biCookieList = this.biCookieMapper.selectList(qw);
        List<String> cookieList = biCookieList.stream().map(BiCookie::getCookie).collect(Collectors.toList());
        cookieList.forEach(cookie -> ThreadUtil.execAsync(() -> {
            try {
                upLevelService.getInfo(cookie,true);
                upLevelService.getInfo(cookie,false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        log.info("任务执行时间->{}",LocalDateTime.now());
    }

    /**
     * 上次执行后200秒执行
     */
    @Async("up_thread")
    @Scheduled(fixedRate = 3600000)
    public void scheduledTask2() {
        QueryWrapper<BiCookie> qw = new QueryWrapper<>();
        qw.eq("needUpLevel",true);
        List<BiCookie> biCookieList = this.biCookieMapper.selectList(qw);
        List<String> cookieList = biCookieList.stream().map(BiCookie::getCookie).collect(Collectors.toList());
        //cookieList.add("_uuid=475D9BDC-14C0-4B20-DB7B-EB1D12C2F63A51679infoc; buvid3=BF1CAB61-8936-4AFF-B7E7-4D842AC8758B167629infoc; fingerprint=8e5aea2e4af33ade511382e170cbabda; buvid_fp=BF1CAB61-8936-4AFF-B7E7-4D842AC8758B167629infoc; buvid_fp_plain=B1DEE478-0611-AD85-BDD2-0347E6AB855683172infoc; SESSDATA=d7adddd3%2C1646705095%2C364ce%2A91; bili_jct=5aacd365050735205c46f1c694188d8e; DedeUserID=107588046; DedeUserID__ckMd5=deab16309e683fe4; sid=jkjqpopt; blackside_state=1; rpdid=|(m)~|lJYkY0J'uYJuulJu||; CURRENT_BLACKGAP=1; PVID=1; LIVE_BUVID=AUTO3216348160933524; CURRENT_QUALITY=0; video_page_version=v_old_home; CURRENT_FNVAL=2000; b_ut=5; i-wanna-go-back=2; b_lsid=217647310_17DC63BF75D; bsource=search_baidu; _dfcaptcha=becb72ec4d67a3494829f56fd7efdc81; innersign=0");
        //cookieList.add("_uuid=2FE8109F5-2B7B-1101010-DC9B-24844243F51D66296infoc; buvid3=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; b_nut=1638722266; buvid_fp=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; CURRENT_FNVAL=2000; bsource=share_source_qqchat; blackside_state=1; sid=5lc2xq5u; rpdid=|(m)~||YkJk0J'uYJ)mJlk~k; innersign=0; b_lsid=9210221043_17D98A7C8C8; fingerprint=66d70d1a951c7dce4ab31177ca6e0a4d; buvid_fp_plain=CD1767BA-4D10-44B9-AFE2-F786603662B1167613infoc; DedeUserID=62004798; DedeUserID__ckMd5=eea1927a88df77ae; SESSDATA=520abe81%2C1654495694%2C89d55*c1; bili_jct=e700c685bae47bf68f11a801fd0324f5; i-wanna-go-back=1; b_ut=6");
        //cookieList.add("_uuid=CC48F1C0-835F-5BE9-273C-C99D36EEAFE429028infoc; buvid3=8A498936-529A-4A6E-941F-14AE3A5B07D5148792infoc; fingerprint=0faad13d9d3e7e2d1ef5c6ba7cf69e58; buvid_fp=8A498936-529A-4A6E-941F-14AE3A5B07D5148792infoc; buvid_fp_plain=8A498936-529A-4A6E-941F-14AE3A5B07D5148792infoc; SESSDATA=ca760532%2C1641734515%2C88951%2A71; bili_jct=29ff28e9b59b0736406608de24176ad6; DedeUserID=39886774; DedeUserID__ckMd5=6dfe019cc6763b0c; sid=bcg3620p; rpdid=|(J~RY|llJR|0J'uYkYu)klm~; LIVE_BUVID=AUTO3816318611984820; CURRENT_QUALITY=80; video_page_version=v_old_home; i-wanna-go-back=1; b_ut=6; CURRENT_FNVAL=2000; bsource=search_bing; CURRENT_BLACKGAP=0; blackside_state=0; PVID=2; bp_video_offset_39886774=604682479900937908; bp_t_offset_39886774=604682479900937908; innersign=0; b_lsid=4844DE1010_17DC645B126");
        cookieList.forEach(cookie -> ThreadUtil.execAsync(() -> {
            try {
                upLevelService.getInfo(cookie,false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        log.info("2任务执行时间->{}",LocalDateTime.now());
    }

}
