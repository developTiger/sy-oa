package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.events.PublishEvent;
import com.sunesoft.lemon.fr.msg.ChannelType;
import com.sunesoft.lemon.fr.msg.MessageService;
import com.sunesoft.lemon.fr.msg.email.EmailMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EmployeeSerciceTest {


    @Autowired
    PublishEvent publishEvent;
    @Autowired
    MessageService messageService;

    @Test
    public void test1(){
       // sercice.addUser();
        Eventtset e = new Eventtset(UUID.randomUUID());
        publishEvent.Publish(e);

    }

    @Test
    public void msgTest(){
        EmailMessage message = new EmailMessage();
        message.setMessage("给习大大发邮件！！！");
        messageService.sendMessage(ChannelType.Email,message);

/*
        InnerNotice notice = new InnerNotice();
        notice.setMessage("给总经理发消息000000");
        messageService.sendMessage(ChannelType.InnerNotice,notice);*/
    }

}