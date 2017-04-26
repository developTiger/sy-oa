package com.sunesoft.lemon.syms.eHr.application;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.sunesoft.lemon.fr.annotations.EventListener;

/**
 * Created by zhouz on 2016/5/17.
 */
@EventListener
public class TestListener {




    @Subscribe
    //@AllowConcurrentEvents
    public void PrintEvent(Eventtset tstt){
        System.out.println("test  ca ca ca  成功了！！！！");
        System.out.println(tstt.getEventId());
    }
}
