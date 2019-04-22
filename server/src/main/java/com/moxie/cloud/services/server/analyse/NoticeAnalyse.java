package com.moxie.cloud.services.server.analyse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/24
 */


@Component
public class NoticeAnalyse {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeAnalyse.class);


    public List<String> noticeAnalyse(Html html) {
//        String html = FileUtil.getResourceFile();

        Document doc = Jsoup.parse(html.toString());

        Elements elements = doc.select("div#content");

        Elements elements1 = elements.select("span >img");

        List<String> imgUrl = elements1.eachAttr("src");

        imgUrl = imgUrl.stream().map(t -> {
            t = "http://www.miinac.gov.cn" + t;
            return t;
        }).collect(Collectors.toList());

        System.out.println(imgUrl);
        return imgUrl;

    }

}
