package com.moxie.cloud.services.server.context;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zhangwt n 2018-12-10.
 */
@Slf4j
public class HuaweiOCR {
//    private String username;
//    private String password;
//    private String regionName;
//
//    public HuaweiOCR(String username, String password) {
//        this.username = username;
//        this.password = password;
//        this.regionName = "cn-south-1";
//    }
//
//    /**
//     * 构造使用Token方式访问服务的请求Token对象
//     * 可参考文档如下进行配置:
//     * http://support.huaweicloud.com/api-ocr/ocr_03_0005.html
//     *
//     * @param name 用户名
//     * @param pwd 密码
//     * @param domainName 域名
//     * @param projectName 项目名称
//     * @return 构造访问的JSON对象
//     */
//    private String requestBody(String name, String pwd, String domainName, String projectName) {
//        JSONObject auth = new JSONObject();
//
//        JSONObject identity = new JSONObject();
//
//        JSONArray methods = new JSONArray();
//        methods.add("password");
//        identity.put("methods", methods);
//
//        JSONObject password = new JSONObject();
//
//        JSONObject user = new JSONObject();
//        user.put("name", name);
//        user.put("password", pwd);
//
//        JSONObject domain = new JSONObject();
//        domain.put("name", name);
//        user.put("domain", domain);
//
//        password.put("user", user);
//
//        identity.put("password", password);
//
//        JSONObject scope = new JSONObject();
//
//        JSONObject scopeProject = new JSONObject();
//        scopeProject.put("name", projectName);
//
//        JSONObject projectDomain = new JSONObject();
//        projectDomain.put("name", domainName);
//        scopeProject.put("domain", projectDomain);
//
//        scope.put("project", scopeProject);
//
//        auth.put("identity", identity);
//        auth.put("scope", scope);
//
//        JSONObject params = new JSONObject();
//        params.put("auth", auth);
//        return params.toJSONString();
//    }
//
//    private String getToken()
//            throws UnsupportedOperationException, IOException {
//
//        CloseableHttpResponse response = null;
//        try {
//            // 1.构建获取Token所需要的参数
//            String requestBody = requestBody(this.username, this.password, this.username, this.regionName);
//            String url ="https://iam.cn-south-1.myhuaweicloud.com/v3/auth/tokens";
//            Map<String,String> headers = new MapBuilder.Builder<String,String>().put("Content-Type", ContentType.APPLICATION_JSON.toString()).build();
//            StringEntity stringEntity = new StringEntity(requestBody,
//                    "utf-8");
//
//            // 2.传入IAM服务对应的参数, 使用POST方法调用服务并解析出Token value
//            HttpsClientUtil client = new HttpsClientUtil();
//            response = client.post(url, null, stringEntity,headers);
//            Header[] xst = response.getHeaders("X-Subject-Token");
//            return xst[0].getValue();
//        } finally {
//            if (response != null){
//                response.close();
//            }
//        }
//    }
//
//
//
//    /**
//     * 通用文字识别，使用Base64编码后的文件方式，使用Token认证方式访问服务
//     * @param formFile 文件路径
//     * @throws IOException
//     */
//    public String requestOcrGeneralTextBase64(String formFile) throws Exception {
//        String token = getToken();
//        // 1.构建通用文字识别服务所需要的参数
//        String url = "https://ocr.cn-south-1.myhuaweicloud.com/v1.0/web-image";
//        CloseableHttpResponse response = null;
//        try {
//            Map<String,String> headers = new MapBuilder.Builder<String,String>().put("X-Auth-Token",token).put("Content-Type", ContentType.APPLICATION_JSON.toString()).build();
//            byte[] fileData = FileUtils.readFileToByteArray(new File(formFile));
//            String fileBase64Str = Base64.encodeBase64String(fileData);
//            JSONObject json = new JSONObject();
//            json.put("image", fileBase64Str);
//            //
//            // 1.a 此项参数可选，可以指定是否检测文字方向，不填则默认不检测文字方向，
//            // detect_direction可选:true, false
//            //
//            json.put("detect_direction", false);
//
//            StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");
//            HttpsClientUtil client = new HttpsClientUtil();
//            // 2.传入通用文字识别服务对应的参数, 使用POST方法调用服务并解析输出识别结果
//            response = client.post(url, null, stringEntity,headers);
//            return EntityUtils.toString(response.getEntity(), "UTF-8");
//        } catch (Exception e) {
//            log.error("华为文字识别异常",e);
//        }finally {
//            if (response != null){
//                response.close();
//            }
//        }
//        return null;
//    }
}
