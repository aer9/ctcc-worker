package com.moxie.cloud.services.server.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/23
 */


public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    private static String getFileContent(String path) {
        String content = getFileContent(path, "utf-8");
        if (content != null && content.contains("�")) {
            content = getFileContent(path, "gbk");
        }
        return content;
    }

    public static String getFileContent(String path, String encoding) {
        FileInputStream stream = null;
        String content = null;
        try {
            stream = new FileInputStream(new File(path));
            content = IOUtils.toString(stream, encoding);

        } catch (Exception e) {
            LOGGER.error("getResourceFile error", e);
        } finally {
            IOUtils.closeQuietly(stream);
        }
        return content;
    }


    public static String getResourceFile(String filePath) {

        String content = null;
        boolean bFindBill = false;

        if (StringUtils.isNotBlank(filePath)) {
            content = getFileContent(filePath);
            bFindBill = true;
        }

        if (!bFindBill) {
            LOGGER.error("not bill content: " + filePath);
        }
        return content;
    }

}
