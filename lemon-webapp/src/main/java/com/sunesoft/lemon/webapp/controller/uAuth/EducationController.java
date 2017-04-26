package com.sunesoft.lemon.webapp.controller.uAuth;

import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.uAuth.application.criteria.PermissionGroupCriteria;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by swb on 2016/6/13.
 */
@Controller
public class EducationController extends Layout{


    @RequestMapping("sra_education")
    public ModelAndView educationInfos(Model model){
        return view(layout,"uAuth/queryEducation",model);
    }
    @RequestMapping("ajax_educationQuery_list")
    public void getEducationInfo(PermissionGroupCriteria param,HttpServletRequest request, HttpServletResponse response,Model model){
        List<Education> listeducation=new ArrayList<Education>();
        Education e1=new Education();
        e1.setId(10001L);
        e1.setDegree("学士");
        e1.setGettime(new Date());
        e1.setGraduate(new Date());
        e1.setLevel(1);
        e1.setSchool("南京大学");
        e1.setStudy("教学");
        e1.setSubject("行政管理");
        listeducation.add(e1);
        Education e2=new Education();
        e2.setId(10002L);
        e2.setDegree("学士");
        e2.setGettime(new Date());
        e2.setGraduate(new Date());
        e2.setLevel(1);
        e2.setSchool("东南大学");
        e2.setStudy("教学");
        e2.setSubject("工商管理");
        listeducation.add(e2);
        model.addAttribute("e",e1);
        PagedResult pagedResult=new PagedResult(listeducation,1,10,1);
        String json = JsonHelper.toJson(pagedResult);
        System.out.println(json);
        AjaxResponse.write(response, json);

    }
    @RequestMapping("_addEducation")
    public ModelAndView addEducation(PermissionGroupCriteria param,HttpServletRequest request, HttpServletResponse response,Model model){
        //return view("uAuth/_addEducation",model);
        String id = request.getParameter("id");

        if (!StringUtils.isNullOrWhiteSpace(id)) {
            List<Education> listeducation=new ArrayList<Education>();
            Education e1=new Education();
            e1.setDegree("学士");
            e1.setGettime(new Date());
            e1.setGraduate(new Date());
            e1.setLevel(1);
            e1.setSchool("南京大学");
            e1.setStudy("教学");
            e1.setSubject("行政管理");
            listeducation.add(e1);
            model.addAttribute("e",e1);
        }
        return view("uAuth/_addEducation",model);
    }
    /*@RequestMapping(value = "ajax_add_update_permission", method = RequestMethod.POST)
    public void addOrUpdatePermission(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("permissionName");
        String sort = request.getParameter("permissionSort");
        String menuIds = request.getParameter("permissionMenuIds");
        System.out.print("menuIds:"+menuIds);
        String id = request.getParameter("id");

        PermissionGroupDto permission = new PermissionGroupDto();
        permission.setName(name);
        permission.setSort(Integer.parseInt(sort));

        List list = new ArrayList<>();
        list.add(menuIds);
        permission.setMenuIds(list);
        System.out.print(permission.getMenuIds());
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            permission.setId(Long.parseLong(id));
        }

        String result = sysPermissionGroupService.addOrUpdate(permission) > 0 ? "success" : "error";
        System.out.println(result);
        AjaxResponse.write(response, "text", result);


    }*/
    @RequestMapping(value = "ajax_add_update_education",method = RequestMethod.POST)
    public void addOrUpdateEducation(HttpServletRequest request, HttpServletResponse response,Model model){
        String school=request.getParameter("school");
        String study=request.getParameter("study");
        String subject=request.getParameter("subject");
        String level=request.getParameter("level");
        String degree=request.getParameter("degree");
        Education education=new Education();
        education.setSchool(school);
        education.setStudy(study);
        education.setGraduate(new Date());
        education.setSubject(subject);
        education.setLevel(0);
        education.setDegree(degree);
        education.setGettime(new Date());

    }
    /*@RequestMapping(value = "ajax_deletePermission")
    public void deletePermission(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));m
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        } else {
            Boolean result = sysPermissionGroupService.delete(listid.toArray(new Long[listid.size()]));//后面的是进行格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }*/
    @RequestMapping("ajax_deleteEducation")
    public void deleteEducation(HttpServletRequest request, HttpServletResponse response){
        String ids=request.getParameter("ids");
        List<Long> listid=new ArrayList<Long>();
        String eids[]=ids.split(",");
        for(String id:eids){
            listid.add(Long.parseLong(id));
        }
        if(listid.size()==0){
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        }else{
            System.out.print("删除选择数据");
            AjaxResponse.write(response, "text", true ? "success" : "error");
        }
    }
    @RequestMapping("ajax_deleteById")
    public void deleteById(HttpServletRequest request,HttpServletResponse response,Long id){
        Education e=new Education();
        System.out.println("根据id删除");
    }
}