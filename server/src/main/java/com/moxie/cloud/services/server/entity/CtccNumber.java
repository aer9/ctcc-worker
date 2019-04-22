package com.moxie.cloud.services.server.entity;

import com.moxie.cloud.services.server.analyse.DataAnalyse;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/25
 */


@Data
public class CtccNumber {

    private Long id;
    private String number;
    private String company;
    private String purpose;
    private Date releaseTime;
    private Date createTime = new Date();
    private Date lastModifyTime;

    public CtccNumber() {
    }

    public CtccNumber(String number, String company, String purpose) {
        this.number = number;
        this.company = company;
        this.purpose = purpose;
    }
}
