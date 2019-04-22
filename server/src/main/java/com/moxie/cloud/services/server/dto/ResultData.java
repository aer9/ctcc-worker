package com.moxie.cloud.services.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/28
 */


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultData {

    @JsonProperty("form_num")
    private Integer formNum;

    @JsonProperty("forms")
    private ResultdataForms resultdataForms;


}
