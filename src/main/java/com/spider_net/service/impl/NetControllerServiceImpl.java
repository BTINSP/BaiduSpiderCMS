package com.spider_net.service.impl;

import com.spider_net.entity.SpiderArticle;
import com.spider_net.entity.SpiderKeyword;
import com.spider_net.mapper.NetMapper;
import com.spider_net.service.NetControllerService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class NetControllerServiceImpl implements NetControllerService {
    private final NetMapper netMapper;

    private static final String title = "百里香百科";

    public NetControllerServiceImpl(NetMapper netMapper){
        this.netMapper = netMapper;
    }

    @Override
    public String Index(Model model) {
        List<SpiderArticle> randomArticles = netMapper.getRandomArticle();
        model.addAttribute("title",title);
        model.addAttribute("randomArticles",randomArticles);
        return "index";
    }

    @Override
    public String Article(String keyword, Model model) {
        List<SpiderArticle> articleByKeywords = netMapper.getArticleByKeyword(keyword);
        List<SpiderKeyword> randomKeywords = netMapper.getRandomKeyword();

        model.addAttribute("title",keyword + "-" + title);
        model.addAttribute("articles",articleByKeywords);
        model.addAttribute("randomKeywords",randomKeywords);
        return "article";
    }

    @Override
    public String Comment(String name, String email, String comment, Model model, HttpServletRequest request) {
        System.out.println(request.getMethod());
        if (request.getMethod().equals("POST")){
            boolean result = netMapper.addComment(name, email, comment);
            if (result){
                model.addAttribute("msg","success");
                return "result";
            }
            model.addAttribute("msg","fail");
            return "result";
        }
        return "comment";
    }
}
