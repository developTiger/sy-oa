package com.sunesoft.lemon.webapp.controller.publishnews;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.AdviceTypeService;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.NoticeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.NoticeCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.NoticeDto;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.hrForms.application.formevection.FormEvectionService;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.Helper;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhouz on 2016/7/18.
 */
@Controller
public class publishNewsController extends Layout {

    @Autowired
    AdviceTypeService adviceTypeService;
    @Autowired
    FormEvectionService formEvectionService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    FileService fileService;

    @Autowired
    UserSession us;

    @RequestMapping(value = "sra_p_showNewsList")
    public ModelAndView sra_p_showNewsList(Model model, HttpServletRequest request, HttpServletResponse response) {
        return view(layout, "publishnews/showNewsList", model);
    }

    @RequestMapping(value = "ajax_news_query_list")
    public void roleQuery_pagelist(NoticeCriteria criteria, HttpServletResponse response) {
        criteria.setNoticeName(URI.deURI(criteria.getNoticeName()));
        PagedResult PagedResult = noticeService.GetNoticePaged(criteria);
         String json = JsonHelper.toJson(PagedResult);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "sra_p_publish")
    public ModelAndView sra_p_publish(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            NoticeDto noticeDto = noticeService.getById(Long.parseLong(id));
            model.addAttribute("noticeEdit", noticeDto);
            if(noticeDto!=null&&noticeDto.getToDept()!=null)
                model.addAttribute("selectDept",noticeDto.getToDept().split(","));
        }
        List<DeptmentDto> deptmentDto = deptmentService.getByDeptsIds();
        model.addAttribute("deptAll", deptmentDto);
        //新闻通知类型
        model.addAttribute("adviceTypes", adviceTypeService.getCanUse());
        return view(layout, "publishnews/publish", model);
    }

    @RequestMapping(value = "sra_p_showNews")
    public ModelAndView sra_p_showNews(Model model, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            NoticeDto noticeDto = noticeService.getById(Long.parseLong(id));
            model.addAttribute("noticeShow", noticeDto);
        }
        return view(layout, "publishnews/showNews", model);
    }

    @RequestMapping(value = "_deleteNotice", method = RequestMethod.POST)
    public ModelAndView _deleteNotice(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        return view("publishnews/_deleteNotice", model);
    }

    @RequestMapping(value = "_ajax_delete_notice", method = RequestMethod.POST)
    public void _ajax_delete_notice(Model model, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        CommonResult commonResult = noticeService.deleteNotice(Long.parseLong(id));

        AjaxResponse.write(response, JsonHelper.toJson(commonResult));
    }


    @RequestMapping(value = "ajax_add_update_news")
    public void ajax_add_update_news(NoticeDto noticeDto, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String publishId = request.getParameter("id");
        String tempKindEditors = request.getParameter("tempKindEditors");
        String sDate = request.getParameter("strSDate");
        String eDate = request.getParameter("strEDate");
        if (!StringUtils.isNullOrWhiteSpace(sDate))
            noticeDto.setsDate(Helper.formateStringToDate(sDate));
        if (!StringUtils.isNullOrWhiteSpace(eDate))
            noticeDto.seteDate(Helper.formateStringToDate(eDate));
        noticeDto.setContent(tempKindEditors);
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("filename1");
        CommonResult result;
        for (MultipartFile myfile : myfiles) {
            if (!myfile.isEmpty()) {
                try {
                    String fileName = myfile.getOriginalFilename();
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    FileInfoDto fileInfoDto = new FileInfoDto();
                    fileInfoDto.setFileName(fileName);
                    fileInfoDto.setExtensions(extension);
                    fileInfoDto.setUserName(us.getCurrentUser(request).getName());
                    fileInfoDto.setInputStream(myfile.getInputStream());
                    String id = fileService.upload(fileInfoDto);
                    if (!StringUtils.isNullOrWhiteSpace(id)) {
                        noticeDto.setFileId(id);
                        noticeDto.setFilename(fileName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!StringUtils.isNullOrWhiteSpace(publishId)) {
            noticeDto.setId(Long.parseLong(publishId));
        }
        result = noticeService.AddOrUpdateNotice(noticeDto, us.getCurrentUser(request));
//        try {
//            response.sendRedirect("sra_p_showNewsList");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        response.setContentType("text/plain;charset=utf8");
        response.getWriter().write(JsonHelper.toJson(result));


    }
}

