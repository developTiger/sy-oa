package com.sunesoft.lemon.fr.msg.innerNotice;

import com.sunesoft.lemon.fr.msg.ChannelType;
import com.sunesoft.lemon.fr.msg.MessageChannel;
import com.sunesoft.lemon.fr.msg.MessageChannelSelector;
import com.sunesoft.lemon.fr.msg.email.SmtpChennel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/5/18.
 */
@Component
public class InnerNoticeChannelSelector implements MessageChannelSelector {
    private final ChannelType channelType = ChannelType.InnerNotice;

    @Autowired
    private InnerNoticeChennel innerNoticeChennel;

    @Override
    public MessageChannel getChannel(ChannelType selectType) {
        if (channelType == selectType) {
            return innerNoticeChennel;
        }

        return null;
    }
}
