package com.moxie.cloud.services.server.law;

import com.moxie.cloud.services.server.service.DataCrawlerService;
import com.moxie.cloud.services.server.service.impl.TaskServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/4/17
 */

@Component
public class LawerServiceImpl  {
    private static final Logger LOGGER = LoggerFactory.getLogger(LawerServiceImpl.class);

    @Autowired
    LawerProcessor lawerProcessor;

    public void dataCrawler(String url, Integer i) {
        Long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
        startTime = System.currentTimeMillis();

        Spider.create(lawerProcessor)
                .thread(5)
                .addUrl(url)
                .run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】共抓取,耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");

    }
}
