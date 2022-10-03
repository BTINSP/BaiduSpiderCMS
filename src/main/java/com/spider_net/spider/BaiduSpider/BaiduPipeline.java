package com.spider_net.spider.BaiduSpider;


import com.spider_net.entity.SpiderArticle;
import com.spider_net.mapper.ManageMapper;
import com.spider_net.mapper.SpiderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class BaiduPipeline implements Pipeline {

    private final SpiderMapper spiderMapper;

    public BaiduPipeline(SpiderMapper spiderMapper) {
        this.spiderMapper = spiderMapper;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<SpiderArticle> spiderArticles = resultItems.get("spiderArticles");
        List<String> keywords = resultItems.get("keywords");

        log.info("内容:: {}, 关键词:: {}",spiderArticles.size(),keywords.size());

        //  文章做持久化
        for (SpiderArticle spiderArticle : spiderArticles) {
            if (!Objects.isNull(spiderArticle)){
                boolean result = spiderMapper.addSpiderArticle(
                        spiderArticle.getTitle(),
                        spiderArticle.getContent(),
                        spiderArticle.getPath(),
                        spiderArticle.getSource());

                if (!result){
                    log.error("文章插入失败,title:{}",spiderArticle.getTitle());

                }
            }
        }

        //  关键词做持久化
        for (String keyword : keywords) {
            if (!Objects.isNull(keyword)){
                //  默认传入为爬取状态
                if (!spiderMapper.addSpiderKeyword(keyword, 0)){
                    log.error("关键词插入失败：{}",keyword);
                }
            }
        }

    }
}
