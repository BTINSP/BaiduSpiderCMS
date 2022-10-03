package com.spider_net.service;

import com.spider_net.entity.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ManageControllerService {

    void getCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException;

    String login(String username, String password, String code, HttpServletRequest request, HttpServletResponse response, Model model);

    String index(Model model);

    void startSpiderBySpiderName(String spiderName,HttpServletResponse response);

    void stopSpiderBySpiderName(String spiderName,HttpServletResponse response);

    void restartSpiderBySpiderName(String spiderName,HttpServletResponse response);
}
