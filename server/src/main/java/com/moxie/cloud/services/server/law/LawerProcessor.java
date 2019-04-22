package com.moxie.cloud.services.server.law;

import com.moxie.cloud.services.server.process.WorkerProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/4/17
 */


@Component
public class LawerProcessor implements PageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LawerProcessor.class);



    private static Site site = Site.me().setSleepTime(3000)
            .setCycleRetryTimes(3);


    @Override
    public void process(Page page) {

        LOGGER.debug("-------lawer process start-----");



        LOGGER.debug("-------lawer process end-----");

    }

    @Override
    public Site getSite() {
        return site;
    }
}
