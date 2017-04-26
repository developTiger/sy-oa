package com.sunesoft.lemon.webapp.controller.awards;

import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.patentCG.application.PatentService;
import com.sunesoft.lemon.deanery.patentCG.application.criteria.PatentCriteria;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.deanery.prizewinner.application.PrizewinnerService;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
@Controller
public class PatentInfoController extends Layout {
   @Autowired
    PatentService patentService;
   @Autowired
    PrizewinnerService prizewinnerService;
    @Autowired
    EmployeeService employeeService;

    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );

    @RequestMapping(value = "sra_u_patent")
    public ModelAndView carInfo(Model model) {
        return view(layout, "awards/patentInfo", model);
    }

    @RequestMapping("ajax_patentQuery_list")
    public void getEducationInfo(PatentCriteria PatentCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        PatentCriteria.setPatent_Name(URLDecoder.decode(PatentCriteria.getPatent_Name(),"UTF-8"));
        String beginTime = request.getParameter("begindate1");
        String endTime = request.getParameter("enddate1");
        try {
            if(!StringUtils.isNullOrWhiteSpace(beginTime)) {
                Date beginTime1 = sdf.parse(beginTime);
                PatentCriteria.setBeginDate(beginTime1);
            }
            Date endTime1=sdf.parse(endTime);
            PatentCriteria.setEndDate(endTime1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PagedResult pagedResult=patentService.getPatent(PatentCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);

    }

    @RequestMapping(value = "addpatentInfoForm")
    public ModelAndView addProjectInfo(Model model,HttpServletRequest request){
        List<EmpDto> list=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        model.addAttribute("emp",list);
        model.addAttribute("winresultclassify",static_common.WIN_RESULT_CLASSIFY);
        model.addAttribute("winlevel",static_common.WIN_LEVEL);
        model.addAttribute("patenttype",static_common.PATENT_TYPE);

        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 =Long.parseLong(id);
            PatentDto patentDto=patentService.getByIdPatent(id1);

             Date ad = patentDto.getApply_Date();
             patentDto.setApply_Date_Str(sdf.format(ad));
             Date vd = patentDto.getValid_Date();
             patentDto.setValid_Date_Str(sdf.format(vd));

            model.addAttribute("beans", patentDto);


        }
        return view(layout,"awards/_addpatentInfoForm",model);
    }
    //详细
    @RequestMapping(value = "detailpatentInfoForm")
    public ModelAndView detailProjectInfo(Model model,HttpServletRequest request){
        String json = JsonHelper.toJson(static_common.SPECIALTYTYPE);
        model.addAttribute("specialType_fb",json);
        model.addAttribute("specialType",static_common.SPECIALTYTYPE);
        model.addAttribute("winresultclassify",static_common.WIN_RESULT_CLASSIFY);
        model.addAttribute("winlevel",static_common.WIN_LEVEL);
        model.addAttribute("patenttype",static_common.PATENT_TYPE);

        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 =Long.parseLong(id);
            PatentDto patentDto=patentService.getByIdPatent(id1);
            Date ad = patentDto.getApply_Date();
            patentDto.setApply_Date_Str(sdf.format(ad));
            Date vd = patentDto.getValid_Date();
            patentDto.setValid_Date_Str(sdf.format(vd));
            model.addAttribute("beans", patentDto);

//            List<PrizewinnerDto> list= prizewinnerService.getPrizeWinner(id1);
//            model.addAttribute("lists", list);
        }
        return view("awards/_detailpatentInfoForm",model);
    }
    @RequestMapping(value = "ajax_deletePatent")
    public void deleteProjectResult(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        } else {
            Boolean result = patentService.delPatent(listid.toArray(new Long[listid.size()]));//后面的是进行格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }

    //添加信息
    @RequestMapping(value = "ajax_add_update_patent", method = RequestMethod.POST)
    public void addOrUpdateProjectResult( HttpServletRequest request, HttpServletResponse response) {
          String id = request.getParameter("id");
          String patent_Name=request.getParameter("patent_Name");
          String app_No=request.getParameter("app_No");
          String patent_Type=request.getParameter("patent_Type");
          String apply_Date=request.getParameter("apply_Date");
          String valid_Date=request.getParameter("valid_Date");
          String info=request.getParameter("winner_Info1");
           PatentDto patentDto=new PatentDto();
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 = Long.parseLong(id);
            patentDto.setId(id1);
        }
           patentDto.setPatent_Name(patent_Name);
           patentDto.setApp_No(app_No);
           patentDto.setPatent_Type(patent_Type);
        try {
            Date apply_Date1= sdf.parse(apply_Date);
            patentDto.setApply_Date(apply_Date1);
            Date valid_Date1= sdf.parse(valid_Date);
            patentDto.setValid_Date(valid_Date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        patentDto.setWinner_info(info);
        String result = patentService.addOrUpdatePatent(patentDto) > 0 ? "success" : "error";
        System.out.println(result);
        AjaxResponse.write(response, "text", result);
    }


}




