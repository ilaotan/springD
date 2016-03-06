package com.springD.application.front.controller;

import com.springD.framework.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tan on 2016/1/3.
 */

@Controller
public class IndexController extends BaseController {


    @RequestMapping(value = "/front/index",method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response,Model model){
        String x = "test111233311";
        model.addAttribute("name",x);
        return "front/index";
    }
}
