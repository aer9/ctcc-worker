package com.moxie.cloud.services.server.controller;

import com.moxie.cloud.services.server.analyse.DataAnalyse;
import com.moxie.cloud.services.server.constants.CommonConstants;
import com.moxie.cloud.services.server.service.WorkerService;
import com.moxie.cloud.services.server.service.impl.BaiduImageOcrServiceImpl;
import com.moxie.cloud.services.server.service.impl.TaskServiceImpl;
import com.moxie.cloud.services.server.thread.TaskThread;
import com.moxie.cloud.services.server.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/23
 */


@RestController
@RequestMapping("/")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);


    private String url = "http://www.miinac.gov.cn/components/newhome/displayAll.jsp?dataType=12&d-16544-p=";

    @Autowired
    BaiduImageOcrServiceImpl baiduImageOcrService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    DataAnalyse dataAnalyse;
    @Autowired
    TaskServiceImpl taskServiceImpl;

    @Autowired
    WorkerService workerService;

    @GetMapping("task")
    public String taskRun(@RequestParam("p") int p) {


        for (int i = p; i <= 250; i++) {
            try {

                taskServiceImpl.dataCrawler(url + i, i);
            } catch (Exception e) {
                LOGGER.error("i:{},error:{}", i, e);
            }
        }
//        List<String> fileNames = (List<String>) redisTemplate.opsForList().rightPop("imgFileName");
//        List<Object> files =  redisTemplate.opsForList().range("imgFileName", 0, -1);
        try {
//            for (String filename : fileNames) {
//
//                LOGGER.info("filename:" + filename);
//                String result = baiduImageOcrService.tableRecognizeToJson("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/resources/static/img/" + filename + ".jpg");
//            String result = baiduImageOcrService.getOcrResult("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/resources/static/img/0b1860da-1263-4ec6-a0b4-96783777b50d.jpg");
//            String result = baiduImageOcrService.tableRecognizeToJson("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/resources/static/img/c1afd4b4-c267-425e-abce-ea9ac6449a4f.jpg");

//                if (StringUtils.isNotBlank(result))
//                    dataAnalyse.getResultDataAnalyse(result);
//            }
        } catch (Exception e) {
            LOGGER.error("getResultDataAnalyse:", e);
        }
        LOGGER.info("TaskController.taskRun end");

        return "task success!";

    }

    @GetMapping("analyse")
    public String analyse() {

//        List<String> fs = Arrays.asList("f89f5742-8475-4324-92fb-a9ef768a7b2d","ee72fbfa-9d00-4b36-b5c3-ca92f2c253ae","ead6a2a9-ceba-43fb-8d9e-0d8494514f48","f4ea1bf9-cc00-4e9c-8509-3aa7d076541e","d39410cb-fc69-45eb-9620-a0219e8df3f3","5ff13f10-8927-40c0-8bf4-3aa6b5589dea","c30c2902-01e6-453d-b07e-1994f676db1f","3287061c-3953-4d41-b9bb-59e13a223441","1d5db4e6-5b45-4b7e-981a-3b45ace40045","77353dd4-1c97-43dc-b0e0-0c5c3e036b56","3859222a-8396-441c-b804-52cb2a6e0088","1dc97d02-d806-478e-9780-69bdb179a599","77e87019-35ea-4981-9d5e-8bdb9b9593ea");
//
//        redisTemplate.opsForList().rightPush(CommonConstants.REDIS_FILE_LIST_NAME, fs);
//        workerService.analyseRecycle();
//        Runnable runnable = new TaskThread();
//        runnable.run();

        for (int i = 0; i < 10; i++) {
            new Thread("" + i) {
                @Override
                public void run() {
                    super.run();
                }
            }.start();
        }

        return "task success!";

    }

    @PostMapping("redis")
    public String redis(@RequestParam(value = "redisVal") List<String> reds,
                        @RequestParam(value = "key") String key) {

//        List<String> fs = Arrays.asList();

        LOGGER.info("redis list value:", redisTemplate.opsForList().rightPop(key));

        redisTemplate.opsForList().rightPush(key, reds);
        return "redis success!";

    }


    @GetMapping("file")
    public String fileRun(@RequestParam("file") String file) {


        try {

            String result = baiduImageOcrService.tableRecognizeToJson("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/resources/static/img/" + file + ".jpg");

            if (StringUtils.isNotBlank(result)) {
                if (dataAnalyse.getResultDataAnalyse(result)) {
                    redisTemplate.opsForList().rightPop("imgFiles");
//                    File file1 = new File("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/resources/static/img/" + file + ".jpg");
//                    file1.delete();

                }
            }
        } catch (Exception e) {

            LOGGER.error("getResultDataAnalyse:", e);

        }
        LOGGER.info("TaskController.fileRun end");

        return "task success!";

    }


    @GetMapping("downimg")
    public String downImgRun(@RequestParam("url") String url) {

        String fileName = workerService.downloadImg("http://www.miinac.gov.cn/mydownFile?id=" + url + "&flash=yes");

        String result = baiduImageOcrService.tableRecognizeToJson("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/resources/static/img/" + fileName + ".jpg");

        if (StringUtils.isNotBlank(result)) {
            try {
                if (!dataAnalyse.getResultDataAnalyse(result)) {
                    redisTemplate.opsForList().rightPop("imgFiles");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("fileName:" + fileName);

//        List<String> imgs = (List<String>) redisTemplate.opsForList().rightPop("imgFiles");

        redisTemplate.opsForList().rightPush("imgFiles", fileName);


        return "down success!";

    }

}
