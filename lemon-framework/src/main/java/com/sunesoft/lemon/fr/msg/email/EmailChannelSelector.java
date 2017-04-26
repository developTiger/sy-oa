package com.sunesoft.lemon.fr.msg.email;

import com.sunesoft.lemon.fr.msg.ChannelType;
import com.sunesoft.lemon.fr.msg.MessageChannel;
import com.sunesoft.lemon.fr.msg.MessageChannelSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhouz on 2016/5/18.
 */
@Component
public class EmailChannelSelector implements MessageChannelSelector {
    private final ChannelType channelType = ChannelType.Email;

    @Autowired
    private SmtpChennel smtpChennel;

    @Override
    public MessageChannel getChannel(ChannelType selectType) {
        if (channelType == selectType) {
            return smtpChennel;
        }
        return null;
    }
}
