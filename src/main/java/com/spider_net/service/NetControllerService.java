package com.spider_net.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface NetControllerService {

    String Index(Model model);

    String Article(String keyword,Model model);

    String Comment(String name, String email, String comment, Model model, HttpServletRequest request);

}
