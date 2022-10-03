package com.spider_net.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class SpiderProxy {
    @JsonProperty("IP")
    private String ip;
    private int port;
    private String host;
    private String deadline;
}
