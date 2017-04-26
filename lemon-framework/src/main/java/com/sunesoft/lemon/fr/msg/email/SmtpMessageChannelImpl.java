package com.sunesoft.lemon.fr.msg.email;

import com.sunesoft.lemon.fr.msg.Msg;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/5/18.
 */
@Service("smtpChennel")
public class SmtpMessageChannelImpl implements SmtpChennel {
    @Override
    public void process(Msg message) {
        System.out.println(((EmailMessage)message).getMessage());
    }
}
