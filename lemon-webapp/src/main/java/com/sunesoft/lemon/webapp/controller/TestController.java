package com.sunesoft.lemon.webapp.controller;

import com.sunesoft.lemon.syms.rtx.RTXservice;
import com.sunesoft.lemon.syms.rtx.RTXserviceService;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhouz on 2016/5/13.
 */
@Controller
public class TestController extends Layout {



    @RequestMapping(value = "userexist")
    public void userexist(HttpServletResponse response) {
        RTXservice service =  new RTXserviceService().getRTXservice();
        //service.userIsExist("ywh");
        //service.sendNotify("1","2","3","c");
        AjaxResponse.write(response, service.userIsExist("ywh"));

    }

    @RequestMapping(value = "msg")
    public void sendMsg(HttpServletRequest resquest, HttpServletResponse response) {
        RTXservice service =  new RTXserviceService().getRTXservice();
        String msg= resquest.getParameter("msg");
        String usernames= resquest.getParameter("names");

        AjaxResponse.write(response, service.sendNotifyNoSave(usernames,"测试消息标题",msg));

    }
}
