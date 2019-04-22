package com.moxie.cloud.services.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/24
 */


public class DownloadUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadUtil.class);


    public static void download(String urlString, String filename, String savePath) throws Exception {

        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 设置请求头
        con.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        // 设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "/" + filename + ".jpg");
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();

    }

    public static void downImage(String urlString, String filename, String savePath) throws Exception {
        URL url = new URL(urlString);

        InputStream in = url.openStream();
        File sf = new File(savePath);
        String imgName = sf.getPath() + "/" + filename + ".jpg";

        FileOutputStream fileOutputStream = new FileOutputStream(new File(imgName));

        byte[] buf = new byte[1024];

        int length = 0;

        LOGGER.info("开始下载:" + url);
        while ((length = in.read(buf, 0, buf.length)) != -1) {
            fileOutputStream.write(buf, 0, length);
        }
        //关闭流
        in.close();
        fileOutputStream.close();
        LOGGER.info("下载完成");


    }


    /**
     * 获得随机UUID文件名
     *
     * @return
     */
    public static String generateRandonFileName() {
        // 获得扩展名
        return UUID.randomUUID().toString();
    }
}
