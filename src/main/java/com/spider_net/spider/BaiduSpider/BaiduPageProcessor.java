package com.spider_net.spider.BaiduSpider;

import com.spider_net.entity.SpiderArticle;
import com.spider_net.utils.SpiderUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

@Component
public class BaiduPageProcessor implements PageProcessor {

    private final Site site = Site.me()
            .setSleepTime(1000)
            .setTimeOut(5000)
            .setRetryTimes(2)
            .setCharset("UTF-8")
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36 Edg/105.0.1343.33")
            .addHeader("Upgrade-Insecure-Requests","1")
            .addHeader("Sec-Fetch-Site","same-origin")
            .addHeader("Sec-Fetch-Mode","navigate")
            .addHeader("Sec-Fetch-Dest","document")
            .addHeader("sec-ch-ua-platform","\"Windows\"")
            .addHeader("sec-ch-ua-mobile","?0")
            .addHeader("sec-ch-ua","\"Microsoft Edge\";v=\"105\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"105\"")
            .addHeader("Referer","https://www.baidu.com/")
            .addHeader("Host","www.baidu.com")
            ;


    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        List<Selectable> nodes = html.css("div.result.c-container.xpath-log.new-pmd").nodes();

        List<SpiderArticle> spiderArticles = new ArrayList<>();
        List<String> keywords = new ArrayList<>();

        //  文章列表
        for (Selectable node : nodes) {
            Selectable title = node.regex("\"title\":\"(.*?)\"");
            Selectable url = node.regex("\"titleUrl\":\"(.*?)\"");
            Selectable content = node.regex("\"contentText\":\"(.*?)\"");

            SpiderArticle spiderArticle = new SpiderArticle();
            spiderArticle.setTitle(SpiderUtils.getCanonicalizeContent(title.get()));
            spiderArticle.setContent(SpiderUtils.getCanonicalizeContent(content.get()));
            spiderArticle.setPath(SpiderUtils.getBaiduCanonicalizeUrl(SpiderUtils.getCanonicalizeContent(url.get())));
            spiderArticle.setSource(1);

            spiderArticles.add(spiderArticle);
        }

        List<Selectable> words = html.regex("\"word\":\"(.*?)\"").nodes();

        //  相关搜索
        for (Selectable word : words) {
            keywords.add(word.get());
            //  根据相关搜索关键词向队列添加链接
            page.addTargetRequest(SpiderUtils.getSpliceUrlByKeyword(word.get()));
        }

        //  传入 pipeline 做持久化
        page.putField("spiderArticles",spiderArticles);
        page.putField("keywords",keywords);
    }

    @Override
    public Site getSite() {
        return site;
    }


}
