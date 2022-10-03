package com.spider_net.spider.BaiduSpider;

import com.spider_net.entity.SpiderProxy;
import com.spider_net.utils.ProxyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;

@Slf4j
@Component
public class BaiduDownloader extends HttpClientDownloader {

    private SpiderProxy getSpiderProxy(){
        try {
            SpiderProxy spiderProxy = new ProxyUtils().getProxyHost();
            log.info("获取代理IP::" + spiderProxy);
            return spiderProxy;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page download(Request request, Task task) {
        Page download = super.download(request, task);

        try {
            Html html = download.getHtml();
            String title = html.getDocument().title();
            if (title.equals("百度安全验证") || title.equals("") || download.getStatusCode() != 200){
                log.warn("百度安全验证::" + request.getUrl());
                //  设置请求代理
                SpiderProxy spiderProxy = getSpiderProxy();
                this.setProxyProvider(SimpleProxyProvider.from(new Proxy(spiderProxy.getIp(),spiderProxy.getPort())));
                //  将爬取失败网址重新加入爬取队列
                log.info("将爬取失败网址重新加入爬取队列::" + request.getUrl());
                download.addTargetRequest(request);
            }
        }catch (Exception e){
            log.warn("获取页面出错::" + request.getUrl());
            //  设置请求代理
            SpiderProxy spiderProxy = getSpiderProxy();
            this.setProxyProvider(SimpleProxyProvider.from(new Proxy(spiderProxy.getIp(),spiderProxy.getPort())));
            log.info("将爬取失败网址重新加入爬取队列::" + request.getUrl());
            download.addTargetRequest(request);
        }

        return download;
    }

}
