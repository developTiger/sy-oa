package com.sunesoft.lemon.webapp.controller;

import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.syms.eHr.application.NoticeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.NoticeCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.NoticeDto;
import com.sunesoft.lemon.syms.eHr.domain.notice.NoticeType;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormHeaderCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/5/26.
 */
@Controller
public class HomeController extends Layout {

    @Autowired
    FormHeaderService formHeaderService;

    @Autowired
    UserSession userSession;

    @Autowired
    NoticeService noticeService;

    @RequestMapping(value = "sra_index")
    public ModelAndView index(Model model) {
        return view(layout, "index", model);
    }

    @RequestMapping(value = "ajax_query_myApply")
    public void queryMyApply(FormHeaderCriteria formHeaderCriteria,HttpServletResponse response,HttpServletRequest request){


        formHeaderCriteria.setPageSize(5);
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
        formStatuses.add(FormStatus.RC);
        formStatuses.add(FormStatus.RJ);
        formHeaderCriteria.setArrFormStatus(formStatuses);
        formHeaderCriteria.setApplyer(us.getCurrentUser(request).getId());
        PagedResult<FormHeaderDto> pagedResult =  formHeaderService.findFormPaged(formHeaderCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }

    @RequestMapping(value = "ajax_query_applyWorkDone")
    public void queryApplyWorkDone(HttpServletResponse response,HttpServletRequest request,FormHeaderCriteria formHeaderCriteria){

        List<FormStatus> list =  new ArrayList<>();
        list.add(FormStatus.AP);
        formHeaderCriteria.setArrFormStatus(list);
        formHeaderCriteria.setPageSize(5);
        formHeaderCriteria.setApplyer(us.getCurrentUser(request).getId());
        PagedResult<FormHeaderDto> pagedResult =  formHeaderService.findFormPaged(formHeaderCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }

    @RequestMapping(value = "ajax_query_myWorkToDo")
    public void queryMyWorkToDo(HttpServletResponse response,FormHeaderCriteria formHeaderCriteria,HttpServletRequest request){
        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);

        List<FormStatus> list = new ArrayList<>();
        list.add(FormStatus.UA);
        formHeaderCriteria.setArrFormStatus(list);
        formHeaderCriteria.setApproveStatus(ApproveStatus.U);
        formHeaderCriteria.setApproverId(empSessionDto.getId());
        formHeaderCriteria.setPageSize(5);
        PagedResult<FormHeaderDto> pagedResult = formHeaderService.findFormPaged(formHeaderCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }

    @RequestMapping(value = "ajax_query_myWorkDone")
    public void queryMyWorkDone(HttpServletResponse response,FormHeaderCriteria formHeaderCriteria,HttpServletRequest request){
        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);

//        List<FormStatus> list = new ArrayList<>();
//        list.add(FormStatus.AP);
//        formHeaderCriteria.setArrFormStatus(list);
        formHeaderCriteria.setApproveStatus(ApproveStatus.A);
        formHeaderCriteria.setApproverId(empSessionDto.getId());
        formHeaderCriteria.setPageSize(5);
        PagedResult<FormHeaderDto> pagedResult = formHeaderService.findFormPaged(formHeaderCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }

    @RequestMapping(value = "ajax_query_notice_index")
    public void queryNotice(HttpServletResponse response,NoticeCriteria noticeCriteria){

        PagedResult PagedResult = noticeService.GetNoticePaged(noticeCriteria);
        List<NoticeDto> list = PagedResult.getItems();
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getNoticeType().equals(NoticeType.News)){
//                list.remove(i);
//                i--;
//            }
//        }
        String json = JsonHelper.toJson(new ListResult<>(list));
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_query_news_notice")
    public void queryNews(HttpServletResponse response,NoticeCriteria noticeCriteria){
        PagedResult PagedResult = noticeService.GetNoticePaged(noticeCriteria);
        List<NoticeDto> list = PagedResult.getItems();
//        if(null == list) return;
//        for (int i = 0; i < list.size(); i++) {
//            if (!list.get(i).getNoticeType().equals(NoticeType.News)){
//                list.remove(i);
//                i--;
//            }
//        }
        String json = JsonHelper.toJson(new ListResult<>(list));
        AjaxResponse.write(response, json);
    }

}
