package com.moxie.cloud.services.server.law;

import com.moxie.cloud.services.server.util.FileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/4/17
 */


public class LawerAnalyse {



    public static void lawerAnalyse(){


        String data = FileUtil.getResourceFile("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/java/file/lawerdata.html").toString();

        Document doc  = Jsoup.parse(data);



    }

    public static void main(String[] args) {
        lawerAnalyse();
    }

}
