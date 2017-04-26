package com.sunesoft.lemon.syms.lucene;

import com.sunesoft.lemon.fr.utils.word.WordPlaceHolder;
import com.sunesoft.lemon.fr.utils.word.wdWord;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.LuceneIndexManger;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.fileSys.domain.enums.DocType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
* Created by zhouz on 2016/7/7.
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class LucenceTest {

    @Autowired
    LuceneIndexManger luceneIndexManger;

    @Autowired
    FileService fileService;
    @Test
    public void createIndexTest(){
        File file=new File("D:\\luceneData\\新建 Microsoft Office Word 文档.docx");
        FileInfoDto dto = new FileInfoDto();
        dto.setFileName(file.getName());
        dto.setDeptId(1L);
        dto.setUserId(1L);
        dto.setFilePathId(1L);
        dto.setDocType(DocType.DeptFile);
        dto.setExtensions("docx");
        dto.setIsPublic(false);
        try {
            dto.setInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        luceneIndexManger.createIndex(dto);
    }

    @Test
    public void wordTest(){

        WordPlaceHolder hoder = new WordPlaceHolder("D:\\test.doc");
        hoder=    hoder.addTextHolder("name","zhouzh");
        hoder=    hoder.addTextHolder("type","中国");
        wdWord.exportWordByTemplate(hoder,"D:\\test11.doc");

    }

    @Test
    public void searchTest(){
        luceneIndexManger.searchIndex("deptId:1 AND userId:1");
    }



}
