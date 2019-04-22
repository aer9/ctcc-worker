package com.moxie.cloud.services.server.process;

import com.moxie.cloud.services.server.service.impl.ImgServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/23
 */

@Component
public class WorkerProcess implements PageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerProcess.class);


    @Autowired
    ImgServiceImpl imgServiceImpl;


    private static Site site = Site.me().setSleepTime(3000)
            .setCycleRetryTimes(3);

    @PostConstruct
    public void init() {
        LOGGER.info("TaskServiceImpl init");
    }


    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {


        LOGGER.debug("-------worker process start-----");

        List<String> urls = displayAllAnalyse(page.getHtml());

        urls.stream().forEach(t -> imgServiceImpl.dataCrawler(t,0));


        LOGGER.debug("-------worker process end-----");

    }

    public List<String> displayAllAnalyse(Html html) {
        Document doc = Jsoup.parse(html.toString());

        Elements elements = doc.select("div.colRcontent");


        Elements element = elements.select(" tbody > tr").select("td >a");

        List<String> url = element.eachAttr("href");


        url = url.stream()
                .distinct()
                .map(t -> {
                    t = t.replaceAll("\\.\\.", "http://www.miinac.gov.cn/components")
                            .replaceAll("&amp;", "&");
                    return t;
                }).collect(Collectors.toList());


        return url;
    }


}
