package com.moxie.cloud.services.server.process;

import com.google.common.collect.Lists;
import com.moxie.cloud.services.server.analyse.NoticeAnalyse;
import com.moxie.cloud.services.server.constants.CommonConstants;
import com.moxie.cloud.services.server.service.WorkerService;
import com.moxie.cloud.services.server.util.DownloadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/24
 */

@Component
public class ImgProcess implements PageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImgProcess.class);

    private static Site site = Site.me().setSleepTime(15000).setCycleRetryTimes(4);


    @Autowired
    NoticeAnalyse noticeAnalyse;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    WorkerService workerService;

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {

        List<String> urls = Lists.newArrayList();

        urls = noticeAnalyse.noticeAnalyse(page.getHtml());

        if (urls!=null && urls.size()>0){
            imgProcess(urls);
        }else {
            workerService.analyseHtml(page.getHtml().toString());
        }
    }


    private void imgProcess(List<String> urls){
        LOGGER.info("-------img process start-----");



        List<String> imgFileNames = Lists.newArrayList();

        urls.stream().forEach(t -> {

            String fileName = workerService.downloadImg(t);
            imgFileNames.add(fileName);
        });

        List<String> fileNames = (List<String>) redisTemplate.opsForList().rightPop("imgFileName");
        if (fileNames != null && fileNames.size() > 0) {
            fileNames.addAll(imgFileNames);
            redisTemplate.opsForList().rightPush(CommonConstants.REDIS_FILE_LIST_NAME, fileNames);
        } else {
            redisTemplate.opsForList().rightPush(CommonConstants.REDIS_FILE_LIST_NAME, imgFileNames);
        }

        LOGGER.info("-------img process end-----");
    }

}
