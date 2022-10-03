package com.spider_net.entity;

import lombok.Data;

@Data
public class SpiderArticle {
    private int id;
    private String title;
    private String content;
    private String path;
    private int source;
    private String date;
    private Double score;
}
