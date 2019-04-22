package com.moxie.cloud.services.server.service.impl;

import com.baidu.aip.ocr.AipOcr;
import com.moxie.cloud.services.server.service.ImageOcrService;
import netscape.javascript.JSException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/25
 */

@Service
public class BaiduImageOcrServiceImpl implements ImageOcrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaiduImageOcrServiceImpl.class);

    @Autowired
    private AipOcr aipOcr;

//    private static AipOcr aipOcr=new AipOcr("10528248","PPIIoUBkCTjsjLgU2ZzFAnGF","ZDeEfdhdYVP3l8S9oT7yryqnoviDrERG");

    // 传入可选参数调用接口
    private HashMap<String, String> options = new HashMap<>();

    @PostConstruct
    public void init() {
        options.put("result_type", "json");
    }


    @Override
    public String getOcrResult(String filePath) {
        LOGGER.info("use baidu ai analyse picture start");
        HashMap<String, String> options = new HashMap<String, String>();

        JSONObject res = aipOcr.tableRecognitionAsync(filePath, options);

        String requestId = (String) getRequest(res, "request_id");
//        getTableDataRecognition(requestId, "ret-code");

        JSONObject dataResult = getDataResult(requestId, "ret-code");
        LOGGER.info("use baidu ai analyse picture end");


        return dataResult.toString();
    }

    private Object getRequest(JSONObject res, String key) {
        JSONArray ja = res.getJSONArray("result");

        JSONObject jsonObject = ja.getJSONObject(0);

        Object value = jsonObject.get(key);

        return value;
    }

    private Object getRequestGet(JSONObject res, String key) {
        JSONObject ja = res.getJSONObject("result");

//        JSONObject jsonObject = ja.getJSONObject(0);

//        Object value = jsonObject.get(key);

        return ja;
    }


    private JSONObject getDataResult(String requestId) {


        // 表格识别结果
        JSONObject res = aipOcr.tableResultGet(requestId, options);
        return res;
    }


    private JSONObject getDataResult(String requestId, String key) {
        // 表格识别结果
        JSONObject jsonObject = aipOcr.tableResultGet("10528248_886714", options);

        Integer value = (Integer) getRequestGet(jsonObject, "result");
        if (value != 3) {
            getDataResult(requestId, "ret-code");
        }
        return jsonObject;
    }


    public String tableRecognizeToJson(String file) {
        String value = null;

        try {
            JSONObject jsonres = aipOcr.tableRecognizeToJson(file, 30000);

            if (jsonres.has("result")) {
                JSONObject ja = jsonres.getJSONObject("result");
                if (ja.has("result_data")) {
                    //                value = ja.getJSONObject("result_data").toString();
                    value = ja.getString("result_data");
                }
            }
        } catch (Exception e) {
            LOGGER.error("tableRecognizeToJson error:", e);
        }

        return value;
    }


}
