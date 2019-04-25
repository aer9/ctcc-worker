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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Lawyer lawyer = (Lawyer) o;

        if (id != null ? !id.equals(lawyer.id) : lawyer.id != null) return false;
        if (rank != null ? !rank.equals(lawyer.rank) : lawyer.rank != null) return false;
        if (name != null ? !name.equals(lawyer.name) : lawyer.name != null) return false;
        if (lawyerNo != null ? !lawyerNo.equals(lawyer.lawyerNo) : lawyer.lawyerNo != null) return false;
        if (company != null ? !company.equals(lawyer.company) : lawyer.company != null) return false;
        if (address != null ? !address.equals(lawyer.address) : lawyer.address != null) return false;
        if (mobile != null ? !mobile.equals(lawyer.mobile) : lawyer.mobile != null) return false;
        if (phone != null ? !phone.equals(lawyer.phone) : lawyer.phone != null) return false;
        if (email != null ? !email.equals(lawyer.email) : lawyer.email != null) return false;
        if (wechat != null ? !wechat.equals(lawyer.wechat) : lawyer.wechat != null) return false;
        if (qq != null ? !qq.equals(lawyer.qq) : lawyer.qq != null) return false;
        if (weibo != null ? !weibo.equals(lawyer.weibo) : lawyer.weibo != null) return false;
        if (goodBusiness != null ? !goodBusiness.equals(lawyer.goodBusiness) : lawyer.goodBusiness != null)
            return false;
        if (honor != null ? !honor.equals(lawyer.honor) : lawyer.honor != null) return false;
        return type != null ? type.equals(lawyer.type) : lawyer.type == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lawyerNo != null ? lawyerNo.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (wechat != null ? wechat.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        result = 31 * result + (weibo != null ? weibo.hashCode() : 0);
        result = 31 * result + (goodBusiness != null ? goodBusiness.hashCode() : 0);
        result = 31 * result + (honor != null ? honor.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
