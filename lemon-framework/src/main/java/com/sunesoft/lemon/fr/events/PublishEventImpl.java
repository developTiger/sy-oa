package com.sunesoft.lemon.fr.events;


import com.google.common.eventbus.EventBus;

import com.sunesoft.lemon.fr.annotations.EventListener;
import com.sunesoft.lemon.fr.events.BaseEvent;
import com.sunesoft.lemon.fr.events.PublishEvent;
import com.sunesoft.lemon.fr.matcher.ClassesFactory;
import com.sunesoft.lemon.fr.matcher.Matcher;
import com.sunesoft.lemon.fr.matcher.Matchers;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouz on 2016/5/17.
 */
@Service("publishEvent")
public class PublishEventImpl implements PublishEvent {
    private static EventBus eventBus = new EventBus("WorkBus");


    static {
      Matcher matchs = Matchers.annotatedWith(EventListener.class);
       ClassesFactory classes=ClassesFactory.getInstance();
        for (Class<?> aClass : classes.getAllClass()) {
            matchs.matches(aClass);
            try {
                eventBus.register(aClass.newInstance()) ;
            }catch (Exception ex){
            }

        }

    }

    @Override
    public <T extends BaseEvent> void Publish(T t) {try {
          eventBus.post(t);
        } catch (Exception ex) {
           // exceptionPolicy.HandleException(this,ex);
        }
    }

}
