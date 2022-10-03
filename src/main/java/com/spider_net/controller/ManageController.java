package com.spider_net.controller;

import com.spider_net.entity.User;
import com.spider_net.mapper.ManageMapper;
import com.spider_net.service.ManageControllerService;
import com.spider_net.spider.BaiduSpider.BaiduSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/manage")
public class ManageController {

    private final ManageControllerService manageControllerService;

    public ManageController(ManageControllerService manageControllerService) {
        this.manageControllerService = manageControllerService;
    }


    @RequestMapping(value = "login",method = {RequestMethod.GET,RequestMethod.POST})
    public String login( @RequestParam(value = "username",required = false)String username,
                         @RequestParam(value = "password",required = false)String password,
                         @RequestParam(value = "authCode",required = false)String code,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         Model model
                         )
    {
        return manageControllerService.login(username, password, code, request, response, model);
    }


    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        manageControllerService.getCaptchaImage(request,response);
    }

    @GetMapping({"/","index.html"})
    public String index(Model model){
        return manageControllerService.index(model);
    }

    @GetMapping("startSpider/{spiderName}")
    public void startSpiderBySpiderName(@PathVariable("spiderName")String spiderName,HttpServletResponse response){
        manageControllerService.startSpiderBySpiderName(spiderName,response);
    }

    @GetMapping("stopSpider/{spiderName}")
    public void stopSpiderBySpiderName(@PathVariable("spiderName")String spiderName,HttpServletResponse response){
        manageControllerService.stopSpiderBySpiderName(spiderName,response);
    }

    @GetMapping("restartSpider/{spiderName}")
    public void restartSpiderBySpiderName(@PathVariable("spiderName")String spiderName,HttpServletResponse response) {
        manageControllerService.restartSpiderBySpiderName(spiderName, response);
    }

}
