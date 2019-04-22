package com.moxie.cloud.services.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/28
 */


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDataBody {


    @JsonProperty("column")
    private List<Integer> column;

//    private Double probability;

    @JsonProperty("row")
    private List<Integer> row;

    private String word;

}
