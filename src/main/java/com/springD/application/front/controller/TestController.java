package com.springD.application.front.controller;

import com.springD.framework.web.BaseController;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tan on 2016/1/3.
 */

@Controller
public class TestController extends BaseController {

    //只是模拟 测试注解可用. 此方法放这里不合适 因为/front已经是anon了,这里再限制权限.shiro无法判断
    @RequiresPermissions("/system")
    @RequestMapping(value = "/front/test",method = RequestMethod.GET)
    public String test(@RequestParam(value = "zhangsan",defaultValue = "wangwu")String username){

        return "/front/index";
    }

    @RequestMapping(value="/users/{userId}/topics/{topicId}")
    public String testPathVariable(
            @PathVariable(value="userId") int userId,
            @PathVariable(value="topicId") int topicId){

        return "";

    }
    @RequestMapping(value="/user/testCookieValue")
    public String testCookieValue(@CookieValue(value="JSESSIONID", defaultValue="") String sessionId) {

        return "";
    }

    @RequestMapping(value="/header")
    public String test(
            @RequestHeader("User-Agent") String userAgent,
            @RequestHeader(value="Accept") String[] accepts){

        return "";
    }

    //@RequestMapping(value="/header")
    //public String test(@ModelAttribute("user") UserModel user){
    //
    //    return "";
    //}




}
