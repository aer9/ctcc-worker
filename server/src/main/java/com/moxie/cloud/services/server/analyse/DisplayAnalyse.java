package com.moxie.cloud.services.server.analyse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/24
 */


@Service
public class DisplayAnalyse {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayAnalyse.class);


    public List<String> displayAllAnalyse(Html html) {
//        String html = FileUtil.getResourceFile("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/java/file/www.miinac.gov.cn/data.html");
        Document doc = Jsoup.parse(html.toString());

        Elements elements = doc.select("div.colRcontent");

        System.out.println(elements);

        Elements element = elements.select(" tbody > tr").select("td >a");

        List<String> url = element.eachAttr("href");


        url = url.stream()
                .distinct()
                .map(t -> {
                    t = t.replaceAll("\\.\\.", "http://www.miinac.gov.cn/components");
                    return t;
                }).collect(Collectors.toList());


        return url;
    }


}
