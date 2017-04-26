package com.sunesoft.lemon.fr.msg.innerNotice;

import com.sunesoft.lemon.fr.msg.Msg;
import com.sunesoft.lemon.fr.msg.email.EmailMessage;
import com.sunesoft.lemon.fr.msg.email.SmtpChennel;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/5/18.
 */
@Service("innerNoticeChennel")
public class InnerNoticeChannelImpl implements InnerNoticeChennel {
    @Override
    public void process(Msg message) {
        System.out.println(((InnerNotice)message).getMessage());
    }
}
