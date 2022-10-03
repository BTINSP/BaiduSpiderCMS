package com.spider_net.controller;

import com.spider_net.service.NetControllerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class NetController {

    private final NetControllerService netControllerService;

    public NetController(NetControllerService netControllerService){
        this.netControllerService = netControllerService;
    }

    @GetMapping({"/","/index.html"})
    public String Index(Model model){
        return netControllerService.Index(model);
    }

    @GetMapping("/article/{keyword}.html")
    public String Article(@PathVariable("keyword")String keyword, Model model){
        return netControllerService.Article(keyword,model);
    }

    @GetMapping("/comment")
    @RequestMapping(value = "/comment",method = {RequestMethod.GET,RequestMethod.POST})
    public String Connect(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "email",required = false)String email,
            @RequestParam(value = "comment",required = false)String comment,
            Model model,
            HttpServletRequest request
    ){
        return netControllerService.Comment(name,email,comment,model,request);
    }

    @GetMapping("/to/{source}/{url}")
    @ResponseBody
    public void ToSite(@PathVariable("source")String source, @PathVariable("url")String url, HttpServletResponse response) throws IOException {
        switch (source){
            case "1": {
                response.sendRedirect("http://www.baidu.com/link?url=" + url);
            }
            default:{
                response.setStatus(400);
            }
        }
    }

}
