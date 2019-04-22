package com.moxie.cloud.services.server.service.impl;

import com.moxie.cloud.services.server.process.ImgProcess;
import com.moxie.cloud.services.server.service.DataCrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/24
 */


@Component
public class ImgServiceImpl implements DataCrawlerService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ImgServiceImpl.class);

    @PostConstruct
    public void init() {
        LOGGER.info("ImgServiceImpl init");
    }

    @Autowired
    ImgProcess imgProcess;

    @Override
    public void dataCrawler(String url,Integer i) {

        Long startTime, endTime;
        LOGGER.info(" ImgServiceImpl i:" + i);
        LOGGER.info("【爬虫开始】请耐心等待一大波数据到你碗里来...");
        startTime = System.currentTimeMillis();

        Spider.create(imgProcess)
                .thread(5)
                .addUrl(url)
                .run();
        endTime = System.currentTimeMillis();
        LOGGER.info("【爬虫结束】共抓取,耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");



    }


}
