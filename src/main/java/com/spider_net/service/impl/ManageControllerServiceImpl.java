package com.spider_net.service.impl;

import com.spider_net.entity.User;
import com.spider_net.mapper.ManageMapper;
import com.spider_net.mapper.SpiderMapper;
import com.spider_net.service.ManageControllerService;
import com.spider_net.spider.BaiduSpider.BaiduSpider;
import com.spider_net.spider.WebSpiderMonitor;
import com.spider_net.utils.SpiderUtils;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderStatusMXBean;

import javax.management.JMException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ManageControllerServiceImpl implements ManageControllerService {

    private final ManageMapper manageMapper;
    private final WebSpiderMonitor webSpiderMonitor;
    private final SpiderMapper spiderMapper;


    public ManageControllerServiceImpl(
            ManageMapper manageMapper,
            BaiduSpider baiduSpider,
            WebSpiderMonitor webSpiderMonitor,
            SpiderMapper spiderMapper
    ) throws JMException {
        this.manageMapper = manageMapper;
        this.webSpiderMonitor = webSpiderMonitor;
        this.spiderMapper =  spiderMapper;


        //  初始化爬虫并添加300随机关键词
        List<String> randomKeyword = spiderMapper.getRandomKeyword(300);
        List<String> spiderKeywordUrlsByStrings = SpiderUtils.getSpiderKeywordUrlsByString(randomKeyword);

        Spider baidu = baiduSpider.create();
        baidu.addUrl(spiderKeywordUrlsByStrings.toArray(new String[0]));

        baidu.thread(10);
        this.webSpiderMonitor.register(baidu);
    }

    @Override
    public void getCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        // 验证码存入session
        request.getSession().setAttribute("Login::Captcha", specCaptcha.text().toLowerCase());

        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

    @Override
    public String login(String username, String password, String code, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (request.getMethod().equals("POST")){
            if(!Objects.isNull(code)){
                String CaptchaCode = (String) request.getSession().getAttribute("Login::Captcha");
                if (code.equals(CaptchaCode)){
                    try {
                        User userByUsername = manageMapper.getUserByUsername(username);
                        if (userByUsername.getPassword().equals(password)){
                            request.getSession().setAttribute("Login::User", userByUsername);
                            response.sendRedirect("/manage/");
                            return index(model);
                        }
                    }catch (Exception e){
                        model.addAttribute("msg","账号或密码不正确");
                    }
                }
                model.addAttribute("msg","验证码不正确");
            }
        }
        return "manage/login";
    }

    @Override
    public String index(Model model) {
        List<SpiderStatusMXBean> spiderMonitorStatus = webSpiderMonitor.getSpiderMonitorStatus();
        model.addAttribute("statusList",spiderMonitorStatus);
        return "manage/index";
    }

    @Override
    public void startSpiderBySpiderName(String spiderName, HttpServletResponse response) {
        try {
            SpiderStatusMXBean spiderStatusMXBeanByUUID = SpiderUtils.getSpiderStatusMXBeanByUUID(
                    webSpiderMonitor.getSpiderMonitorStatus(),
                    spiderName);

            spiderStatusMXBeanByUUID.start();
            response.sendRedirect("/manage/");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stopSpiderBySpiderName(String spiderName, HttpServletResponse response) {
        try {
            SpiderStatusMXBean spiderStatusMXBeanByUUID = SpiderUtils.getSpiderStatusMXBeanByUUID(
                    webSpiderMonitor.getSpiderMonitorStatus(),
                    spiderName);
            spiderStatusMXBeanByUUID.stop();
            response.sendRedirect("/manage/");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void restartSpiderBySpiderName(String spiderName, HttpServletResponse response) {
        try {
            SpiderStatusMXBean spiderStatusMXBeanByUUID = SpiderUtils.getSpiderStatusMXBeanByUUID(
                    webSpiderMonitor.getSpiderMonitorStatus(),
                    spiderName);
            spiderStatusMXBeanByUUID.stop();
            spiderStatusMXBeanByUUID.start();
            response.sendRedirect("/manage/");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



}
