package com.moxie.cloud.services.server.config;

import com.baidu.aip.ocr.AipOcr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/25
 */


@Configuration
public class ClientConfig {


    @Value("${baidu.api.appId}")
    private String baiduAppId  ;
    @Value("${baidu.api.apiKey}")
    private String baiduApiKey;
    @Value("${baidu.api.secretKey}")
    private String secretKey;


    @Value("${xunfei.api.appId}")
    private String xunfeiAppId;
    @Value("${xunfei.api.apiKey}")
    private String xunfeiApiKey;

    @Value("${huawei.api.username}")
    private String huaweiUsername;
    @Value("${huawei.api.pwd}")
    private String huaweiPwd;


    @Bean
    public  AipOcr getAipOcr(){
        AipOcr aipOcr = new AipOcr(baiduAppId, baiduApiKey, secretKey);
        // 可选：设置网络连接参数
        aipOcr.setConnectionTimeoutInMillis(5000);
        aipOcr.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
        return aipOcr;
    }


//    @Bean
//    public XunfeiOCR getXfOcr(){
//        XunfeiOCR webOCR = new XunfeiOCR(xunfeiAppId, xunfeiApiKey);
//        return webOCR;
//    }

//    @Bean
//    public HuaweiOCR getHwOcr(){
//        return new HuaweiOCR(huaweiUsername,huaweiPwd);
//    }


}
