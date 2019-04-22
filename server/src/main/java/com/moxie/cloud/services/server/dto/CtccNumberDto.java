package com.moxie.cloud.services.server.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/25
 */

@Data
public class CtccNumberDto {
    private String number;
    private String company;
    private String purpose;
    private Date releaseTime;


    public CtccNumberDto() {
    }

    public CtccNumberDto(String number, String company, String purpose) {
        this.number = number;
        this.company = company;
        this.purpose = purpose;
    }
}
