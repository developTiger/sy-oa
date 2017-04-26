package com.sunesoft.lemon.syms.eHr.application.FormTrainEffectTest;

import com.sunesoft.lemon.syms.eHr.application.NoticeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.NoticeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestNotice {

    @Autowired
    public NoticeService noticeService;

    @Test
    public void test(){
        String name = "fffffffffs";
        List<NoticeDto> lists  = noticeService.getByName(name);
        for(NoticeDto noticeDto : lists){
             System.out.print(noticeDto.getId());
        }
    }
}
