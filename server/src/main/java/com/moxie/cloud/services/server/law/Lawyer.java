package com.moxie.cloud.services.server.law;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/4/17
 */


@Data
public class Lawyer {


    private Long id;
    private Integer rank;
    private String name;
    private String lawyerNo;
    private String company;
    private String address;
    private String mobile;
    private String phone;
    private String email;
    private String wechat;
    private String qq;
    private String weibo;
    private String goodBusiness;
    private String honor;

    /**标示专业方向 */
    private String type;



}
