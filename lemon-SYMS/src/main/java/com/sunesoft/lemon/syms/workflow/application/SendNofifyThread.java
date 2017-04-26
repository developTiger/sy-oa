package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.loggers.Logger;
import com.sunesoft.lemon.syms.rtx.RTXservice;
import com.sunesoft.lemon.syms.rtx.RTXserviceService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaowy on 2017/3/23.
 */
public class SendNofifyThread {

    @Autowired
    Logger logger;

    private String userName;
    private String title;
    private String content;

    public SendNofifyThread(String userName, String title, String content) {
        this.userName = userName;
        this.title = title;
        this.content = content;
    }

    public void start() {
        final Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    RTXservice rtXservice = new RTXserviceService().getRTXservice();
                    rtXservice.sendNotifyNoSave(userName, title, content);
                } catch (Exception e) {
                    System.out.println("erro msg:" + e.getMessage()+"stacktrace:"+e.getStackTrace());
                    //logger.error("erro msg:" + e.getMessage()+"stacktrace:"+e.getStackTrace());
                    throw e;
                }
            }
        });
        t.start();
    }
}