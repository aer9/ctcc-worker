package com.moxie.cloud.services.server.service;

import com.moxie.cloud.services.server.analyse.DataAnalyse;
import com.moxie.cloud.services.server.constants.CommonConstants;
import com.moxie.cloud.services.server.dto.CtccNumberDto;
import com.moxie.cloud.services.server.entity.CtccNumber;
import com.moxie.cloud.services.server.service.impl.BaiduImageOcrServiceImpl;
import com.moxie.cloud.services.server.util.DownloadUtil;
import com.moxie.cloud.services.server.util.FileUtil;
import com.moxie.cloud.services.server.util.PatternExtend;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/2/14
 */


@Service
@Slf4j
public class WorkerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerService.class);

    private static final String NUMBER_REGEX = "短消息(?<number>.*)由工业和信息化部核配给(?<company>.*?)。 ";
    private final static PatternExtend CTCC_NUMBER_REGEXPATTERN = PatternExtend.compile(NUMBER_REGEX);

    private static final String NUMBER1_REGEX = "码号(?<number>.*)由工业和信息化部核配给(?<company>.*?)。 ";
    private final static PatternExtend CTCC_NUMBER1_REGEXPATTERN = PatternExtend.compile(NUMBER1_REGEX);

    private static final String PURPOSE_REGEX = "批准用途:(.*)";
    private final static PatternExtend PURPOSE_REGEXPATTERN = PatternExtend.compile(PURPOSE_REGEX);

    //    码号.*在.*由工业和信息化部核配给.*。使用
    private static final String NUMBER2_REGEX = "码号(?<number>.*)在.*由工业和信息化部核配给(?<company>.*?)。使用";
    private final static PatternExtend CTCC_NUMBER2_REGEXPATTERN = PatternExtend.compile(NUMBER2_REGEX);


    @Autowired
    BaiduImageOcrServiceImpl baiduImageOcrService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    DataAnalyse dataAnalyse;


    public Boolean analyseRecycle() {
        List<String> fileNames = (List<String>) redisTemplate.opsForList().rightPop(CommonConstants.REDIS_FILE_LIST_NAME);

        try {
            if (fileNames != null && fileNames.size() > 0) {
                LOGGER.info("fileNames:" + fileNames);
                for (int i = 0; i < fileNames.size(); i++) {
                    String filename = fileNames.get(i);

                    LOGGER.info("filename:" + fileNames.get(i));
                    String result = baiduImageOcrService.tableRecognizeToJson("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/resources/static/img/" + filename + ".jpg");

                    if (StringUtils.isNotBlank(result)) {
                        if (dataAnalyse.getResultDataAnalyse(result)) {
                            LOGGER.info("remove filename:" + fileNames.get(i));
                            fileNames.remove(filename);
                            i--;
//                            File file = new File("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/resources/static/img/" + filename + ".jpg");
//                            file.delete();
                        }
                    }
                }
                redisTemplate.opsForList().rightPush(CommonConstants.REDIS_FILE_LIST_NAME, fileNames);

//                if (fileNames != null && fileNames.size() <= 0) {
//                    LOGGER.info("analyse over");
//                    return true;
//                }else {
//                    analyseRecycle();
//                }
            }

        } catch (Exception e) {
            redisTemplate.opsForList().rightPush(CommonConstants.REDIS_FILE_LIST_NAME, fileNames);
            LOGGER.error("getResultDataAnalyse:", e);
            return false;
        }
        LOGGER.info("TaskController.analyse end");
        return true;
    }


    public String downloadImg(String url) {

        try {
            String fileName = DownloadUtil.generateRandonFileName();
            DownloadUtil.downImage(url, fileName, "/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/resources/static/img");
            LOGGER.info("img download success");
            return fileName;
        } catch (Exception e) {
            LOGGER.error("img download fail");
            return null;
        }
    }


    public static void main(String[] args) {
//        analyseHtml(null);
        CtccNumberDto obj = new CtccNumberDto();
        WeakReference<CtccNumberDto> wf = new WeakReference<CtccNumberDto>(obj);

        SoftReference sf = new SoftReference(obj);

        PhantomReference<CtccNumberDto> pf = new PhantomReference<CtccNumberDto>(obj,null);
        System.out.println(wf);
        System.out.println(sf);
        wf.get().setNumber("1");
//        wf=null;
        System.out.println(pf.get());
//        System.gc();
        System.out.println(wf.isEnqueued());
        System.out.println(sf.isEnqueued());
        System.out.println(pf.isEnqueued());
//        wf.get().getNumber();
        System.out.println(2);


    }


    public void analyseHtml(String html) {

//        String html = FileUtil.getResourceFile("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/java/file/data.html");


        Document doc = Jsoup.parse(html);

        Elements elements = doc.select("div#content");

        Elements elements1 = elements.select("tbody>tr>td");

        if (elements1 != null && elements1.size() > 0) {


            String td0 = elements1.get(0).text();

            String td1 = elements1.get(1).text();

            CtccNumber ctccNumber = new CtccNumber();

            List<Map<String, String>> resultMaps = CTCC_NUMBER2_REGEXPATTERN.matcherMapList(td0);

            if (resultMaps == null) {
                resultMaps = CTCC_NUMBER_REGEXPATTERN.matcherMapList(td0);
                if (resultMaps == null)
                    resultMaps = CTCC_NUMBER1_REGEXPATTERN.matcherMapList(td0);
            }


            if (resultMaps != null && resultMaps.size() > 0) {
                String purpose = PURPOSE_REGEXPATTERN.matcherV(td1);

                Map<String, String> resultMap = resultMaps.get(0);
                ctccNumber.setNumber(resultMap.get("number"));
                ctccNumber.setCompany(resultMap.get("company"));
                ctccNumber.setLastModifyTime(new Date());
                ctccNumber.setPurpose(purpose);

                dataAnalyse.dataInsert(ctccNumber);

            }
            LOGGER.debug("ctccNumber:" + ctccNumber);
        }

    }


}
