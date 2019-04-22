package com.moxie.cloud.services.server.analyse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moxie.cloud.services.server.constants.CommonConstants;
import com.moxie.cloud.services.server.entity.CtccNumber;
import com.moxie.cloud.services.server.mapper.CtccNumberMapper;
import com.moxie.cloud.services.server.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/25
 */

@Component
public class DataAnalyse {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataAnalyse.class);

    @Autowired
    private CtccNumberMapper ctccNumberMapper;


    public Boolean getResultDataAnalyse(String data) throws IOException {
        LOGGER.info("getResultDataAnalyse start");

//        JSONObject json = new JSONObject(data);
//        Map<String, Object> map = new HashMap<>();
//        Iterator it = json.keys();
//        while (it.hasNext()) {
//            String key = (String) it.next();
//            Object value = json.get(key);
//            map.put(key, value);
//        }

//        data = FileUtil.getResourceFile("/Users/yangjiawei/IdeaProjects/ctccnet-worker/server/src/main/java/file/img/data.txt");

        try {
            ObjectMapper mapper = new ObjectMapper();

//            Map<String, Object> resultDataMap = mapper.readValue(data, HashMap.class);
//
//
//            Map<String, Object> resultMap = (Map<String, Object>) resultDataMap.get("result");
//
//            String resultData = (String) resultMap.get("result_data");
//
//            if (StringUtils.isBlank(resultData))
//                return;

            Map<String, Object> resultDataFormMap = mapper.readValue(data, HashMap.class);

            List<HashMap> resultDatas = (List<HashMap>) resultDataFormMap.get("forms");


            Map<String, String> dataMap = new HashMap<>();


            HashMap<String, Object> r = resultDatas.get(0);
            List<HashMap<String, Object>> bodies = (List<HashMap<String, Object>>) r.get("body");


            if (bodies.size() % 3 != 0) {
                LOGGER.error("图片识别异常:body.size:" + bodies.size());
                return false;
            }
            List<Integer> size = new ArrayList<>();

            for (HashMap<String, Object> kv : bodies) {
                List<Integer> columns = (List<Integer>) kv.get("column");
                List<Integer> rows = (List<Integer>) kv.get("row");
                String word = (String) kv.get("word");
                dataMap.put(rows.get(0) + "-" + columns.get(0), word);

                if (!size.containsAll(rows)) {
                    size.addAll(rows);
                }

            }

            LOGGER.debug("data insert into database start");
            for (int k = 0; k < size.size(); k++) {
                if (!CommonConstants.WORD_0_0.equalsIgnoreCase(dataMap.get(k + "-0")) &&
                        !CommonConstants.WORD_1_0.equalsIgnoreCase(dataMap.get(k + "-1")) &&
                        !CommonConstants.WORD_2_0.equalsIgnoreCase(dataMap.get(k + "-2"))) {
                    CtccNumber ctccNumber = new CtccNumber(dataMap.get(k + "-0"), dataMap.get(k + "-1"), dataMap.get(k + "-2"));

                    ctccNumber.setLastModifyTime(new Date());
                    LOGGER.debug("ctccNumber:" + ctccNumber);
//                    CtccNumber c = ctccNumberMapper.getCtccNumberByNumber(ctccNumber.getNumber());
//                    if (null == c) {
//                        ctccNumberMapper.insertCtccNumber(ctccNumber);
//                    } else {
//                        ctccNumber.setLastModifyTime(new Date());
//                        ctccNumberMapper.updateCtccnumberById(ctccNumber);
//                    }
                    dataInsert(ctccNumber);
                }

            }

            LOGGER.info("getResultDataAnalyse end");
            return true;
        } catch (Exception e) {
            LOGGER.error("getResultDataAnalyse error:", e);
            return false;
        }


    }


    public void dataInsert(CtccNumber ctccNumber) {
        try {
            CtccNumber c = ctccNumberMapper.getCtccNumberByNumber(ctccNumber.getNumber());
            if (null == c) {
                ctccNumberMapper.insertCtccNumber(ctccNumber);
            } else {
                ctccNumber.setLastModifyTime(new Date());
                ctccNumberMapper.updateCtccnumberById(ctccNumber);
            }

            LOGGER.debug("dataInsert success!");
        } catch (Exception e) {
            LOGGER.error("dataInsert error:" + e);
        }

    }


}
