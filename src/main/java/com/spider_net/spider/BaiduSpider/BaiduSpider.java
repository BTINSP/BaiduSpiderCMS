package com.spider_net.spider.BaiduSpider;

import com.spider_net.spider.WebSpiderDuplicateRemover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

@Component
public class BaiduSpider {
    private final BaiduPipeline baiduPipeline;

    public BaiduSpider(BaiduPipeline baiduPipeline) {
        this.baiduPipeline = baiduPipeline;
    }

    public Spider create(){
        Spider baiduSpider = Spider.create(new BaiduPageProcessor())
                .addUrl("https://www.baidu.com/s?ie=UTF-8&wd=html%20form")
                //  取消去重
                .setScheduler(new QueueScheduler().setDuplicateRemover(new WebSpiderDuplicateRemover()))
                //  下载器
                .setDownloader(new BaiduDownloader())
                //  保存
                .addPipeline(baiduPipeline);

        //  设置爬虫名称
        baiduSpider.setUUID("BaiduSpider");

        return baiduSpider;

    }

}
