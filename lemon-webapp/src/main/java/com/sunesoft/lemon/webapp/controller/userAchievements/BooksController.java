package com.sunesoft.lemon.webapp.controller.userAchievements;

import com.sunesoft.lemon.deanery.StringCommHelper;
import com.sunesoft.lemon.deanery.treatiseCG.application.TreatiseService;
import com.sunesoft.lemon.deanery.treatiseCG.application.criteria.TreatiseCriteria;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;
import com.sunesoft.lemon.deanery.typeOfTreatise.dtos.TypeOfTreatiseDto;
import com.sunesoft.lemon.deanery.typeOfTreatise.dtos.TypeOfTreatiseService;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 2016/7/5.
 */
@Controller
public class BooksController extends Layout {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TreatiseService treatiseService;
    @Autowired
    FormListService formListService;
    @Autowired
    UserSession us;
    @Autowired
    FormHeaderService headerService;
    @Autowired
    TypeOfTreatiseService typeOfTreatiseService;

    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
    @RequestMapping(value = "sra_u_books")
    public ModelAndView index(Model model,HttpServletRequest request,HttpServletResponse response){
        List<TypeOfTreatiseDto> typeOfTreatiseDtos=typeOfTreatiseService.findAll();
        model.addAttribute("typeOfTreatise",typeOfTreatiseDtos);
        return view(layout,"userAchievements/books",model);
    }

    @RequestMapping(value = "ajax_book_query_list")
    public void queryBooks(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {

        TreatiseCriteria criteria = new TreatiseCriteria();


        String beginTime = request.getParameter("begin_Date");
        String end_Date = request.getParameter("end_Date");
        criteria.setBegin_Date(DateHelper.parse(beginTime,"yyyy-MM-dd"));
        criteria.setEnd_Date(DateHelper.parse(end_Date,"yyyy-MM-dd"));

        String treatiseName = URLDecoder.decode(request.getParameter("treatise_Name"), "UTF-8");
        String treatise_level = request.getParameter("treatise_Level");
        String treatise_Press = URLDecoder.decode(request.getParameter("treatise_Press"), "UTF-8");

        String is_Core = request.getParameter("is_Core");
        Boolean isCore = (is_Core != null && !"".equals(is_Core)) ? Boolean.valueOf(is_Core) : null;

        String make_No = request.getParameter("make_No");

        String is_cooperate = request.getParameter("is_cooperate");
        Boolean isCooperate = (is_cooperate != null && !"".equals(is_cooperate)) ? Boolean.valueOf(is_cooperate) : null;


        String unit = URLDecoder.decode(request.getParameter("unit"),"UTF-8");

        String pageNum = request.getParameter("pageNumber");
        if(pageNum != null && !"".equals(pageNum.trim())){
            criteria.setPageNumber(Integer.valueOf(pageNum));
        }
        String pageSize = request.getParameter("pageSize");
        if(pageSize != null && !"".equals(pageSize.trim())){
            criteria.setPageSize(Integer.valueOf(pageSize));
        }


        criteria.setTreatise_Name(treatiseName);
        criteria.setTreatise_Level(treatise_level);
        criteria.setTreatise_Press(treatise_Press);
        criteria.setMake_No(make_No);
        criteria.setUnit(unit);
        criteria.setIs_Core(isCore);
        criteria.setIs_cooperate(isCooperate);
        PagedResult pagedResult = treatiseService.getTreatise(criteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }

    //获取所有的员工信息,ajax获取信息
    @RequestMapping(value = "ajax_get_all_emp")
    public void getAllEmp(HttpServletRequest request,HttpServletResponse response){

        List<EmpDto> list=employeeService.getAllEmps();

        String json = JsonHelper.toJson(list);//转换成json格式的数据,js可以获取到数据
        AjaxResponse.write(response,json);

    }
    //新增
    @RequestMapping(value = "_addbooksForm")
    public ModelAndView addBooksInfo(Model model,HttpServletRequest request,HttpServletResponse response){

        List<EmpDto> list1=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list1){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        List<EmpDto> list=employeeService.getAllEmps();
        model.addAttribute("people",list);


        return view(layout,"userAchievements/_addbooksForm",model);
    }

    @RequestMapping(value = "ajax_add_update_book",method = RequestMethod.POST)
    public void addOrUpdateBooks(HttpServletRequest request,HttpServletResponse response){
        TreatiseDto dto = new TreatiseDto();
        String treatise_Name = request.getParameter("treatise_Name");
        String publish_Time = request.getParameter("publish_Time_");
        String treatise_Level = request.getParameter("treatise_Level");
        String treatise_Press = request.getParameter("treatise_Press");

        String is_Core = request.getParameter("is_Core");
        Boolean isCore = (is_Core != null && !"".equals(is_Core)) ? Boolean.valueOf(is_Core) : false;


        String unit = request.getParameter("unit");
        String make_No = request.getParameter("make_No");

        String is_cooperate = request.getParameter("is_cooperate");
        Boolean isCooperate = (is_cooperate != null && !"".equals(is_cooperate)) ? Boolean.valueOf(is_cooperate) : false;
        String winner_info = request.getParameter("winner_info");


        dto.setTreatise_Name(treatise_Name);
        dto.setPublish_Time_(publish_Time);
        dto.setTreatise_Level(treatise_Level);
        dto.setTreatise_Press(treatise_Press);
        dto.setUnit(unit);
        dto.setMake_No(make_No);
        dto.setWinner_info(winner_info);
        dto.setIs_Core(isCore);
        dto.setIs_cooperate(isCooperate);



        String result=treatiseService.addOrUpdate(dto)>0?"success":"error";
        AjaxResponse.write(response,"text",result);

    }

    //修改
    @RequestMapping(value = "modifyBooks")
    public ModelAndView modifyBooks(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            TreatiseDto dto=treatiseService.getByIdTreatise(Long.parseLong(id));
            model.addAttribute("beans",dto);
        }

        List<EmpDto> list1=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list1){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        List<EmpDto> list=employeeService.getAllEmps();
        model.addAttribute("people",list);

        return view(layout,"userAchievements/modifyBooks",model);
    }

    @RequestMapping(value = "ajax_modify_book",method = RequestMethod.POST)
    public void modifyBooks(HttpServletRequest request,HttpServletResponse response){
        TreatiseDto dto = new TreatiseDto();

        String treatise_Name = request.getParameter("treatise_Name");
        String publish_Time = request.getParameter("publish_Time_");
        String treatise_Level = request.getParameter("treatise_Level");
        String treatise_Press = request.getParameter("treatise_Press");
        String id = request.getParameter("id");

        String is_Core = request.getParameter("is_Core");
        Boolean isCore = (is_Core != null && !"".equals(is_Core)) ? Boolean.valueOf(is_Core) : false;

        String unit = request.getParameter("unit");
        String make_No = request.getParameter("make_No");

        String is_cooperate = request.getParameter("is_cooperate");
        Boolean isCooperate = (is_cooperate != null && !"".equals(is_cooperate)) ? Boolean.valueOf(is_cooperate) : false;

        String winnerInfos = request.getParameter("info");
        String info = request.getParameter("info");


        dto.setTreatise_Name(treatise_Name);
        dto.setPublish_Time_(publish_Time);
        dto.setTreatise_Level(treatise_Level);
        dto.setTreatise_Press(treatise_Press);
        dto.setUnit(unit);
        dto.setMake_No(make_No);
        dto.setId(Long.parseLong(id));
        dto.setIs_Core(isCore);
        dto.setIs_cooperate(isCooperate);

        dto.setWinner_info(winnerInfos);
        dto.setInfo(info);



        String result=treatiseService.addOrUpdate(dto)>0?"success":"error";
        AjaxResponse.write(response,"text",result);
    }

    //详细信息
    @RequestMapping(value = "detailBooks")
    public ModelAndView detailBooks(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            TreatiseDto dto=treatiseService.getByIdTreatise(Long.parseLong(id));
  /*          String []peoples=dto.getInfo().split(",");
            String peoples2=new String();
            for(int i=0;i<peoples.length;i++){
                String [] ren=peoples[i].split("@");
                peoples2+=ren[1]+",";
            }
            peoples2=peoples2.substring(0,peoples2.length()-1);
            dto.setWinner_info(peoples2);*/
            model.addAttribute("beans",dto);
        }
        return view("userAchievements/detailBooks",model);
    }

    //删除
    @RequestMapping(value = "ajax_deleteBooks")
    public void deleteProjectInfo(HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        } else {
            Boolean result = treatiseService.delete((listid.toArray(new Long[listid.size()])));//格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }
    /*
    * 申请页面*/
    @RequestMapping(value = "syy_gy_lc03_a")
    public ModelAndView addProjectResult(Model model,HttpServletRequest request){
        FormListDto dto =formListService.getFormListInfo("SYY_GY_LC03");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));


        List<EmpDto> list1=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list1){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        List<EmpDto> list=employeeService.getAllEmps();
        model.addAttribute("people",list);


        return view(applyLayout,"userAchievements/syy_gy_lc03_a",model);
    }

    /*
    * 通过流程新增数据
    * */
    @RequestMapping(value = "add_update_book", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addupdatebook(HttpServletRequest request, HttpServletResponse response) {
        TreatiseDto dto = new TreatiseDto();
        String treatise_Name = request.getParameter("treatise_Name");
        String publish_Time = request.getParameter("publish_Time_");
        String treatise_Level = request.getParameter("treatise_Level");
        String treatise_Press = request.getParameter("treatise_Press");

        String is_Core = request.getParameter("is_Core");
        Boolean isCore = (is_Core != null && !"".equals(is_Core)) ? Boolean.valueOf(is_Core) : false;


        String unit = request.getParameter("unit");
        String make_No = request.getParameter("make_No");

        String is_cooperate = request.getParameter("is_cooperate");
        Boolean isCooperate = (is_cooperate != null && !"".equals(is_cooperate)) ? Boolean.valueOf(is_cooperate) : false;
        String winner_info = request.getParameter("winner_info");

        String formKind = request.getParameter("formKind");
        String formKindName = URI.deURI(request.getParameter("formKindName"));
        String applyer=request.getParameter("applyer");
        String applyerName=request.getParameter("applyerName");
        String deptId=request.getParameter("deptId");
        String deptName=request.getParameter("deptName");


        dto.setTreatise_Name(treatise_Name);
        dto.setPublish_Time_(publish_Time);
        dto.setTreatise_Level(treatise_Level);
        dto.setTreatise_Press(treatise_Press);
        dto.setUnit(unit);
        dto.setMake_No(make_No);
        dto.setWinner_info(winner_info);
        dto.setIs_Core(isCore);
        dto.setIs_cooperate(isCooperate);

        dto.setFormKind(formKind);
        dto.setFormKindName(formKindName);
        dto.setApplyer(Long.parseLong(applyer));
        dto.setApplyerName(applyerName);
        dto.setDeptId(Long.parseLong(deptId));
        dto.setDeptName(deptName);

//        String result=treatiseService.addOrUpdate(dto)>0?"success":"error";
//        AjaxResponse.write(response,"text",result);
        CommonResult commonResult=treatiseService.addOrUpdate2(dto);
        CommonResult result =treatiseService.submitForm(commonResult.getId(),dto.getFormKind());
        return result;
    }
/*
* 第一步审核页面
* */
    @RequestMapping(value = "syy_gy_lc03_view1")
    public ModelAndView formProjectApplyView(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("viewOnly",viewOnly);
        FormHeaderDto dto2 = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto2);
        String id = request.getParameter("id");
        if (formNo!=null && formNo!=0L) {

            TreatiseDto dto=treatiseService.getByFormNoTreatise(formNo);
            model.addAttribute("beans",dto);
        }

        List<EmpDto> list1=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list1){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        List<EmpDto> list=employeeService.getAllEmps();
        model.addAttribute("people",list);


        return view(viewOnly.equals("false")?formLayout:formViewLayout,  "userAchievements/syy_gy_lc03_view1", model);
    }

    /*
    * 第一步审核
    * */
    @RequestMapping(value ="gy_lc03_approve1")
    @ResponseBody
    public CommonResult approve1(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
        TreatiseDto dto2 = new TreatiseDto();

        String treatise_Name = request.getParameter("treatise_Name");
        String publish_Time = request.getParameter("publish_Time_");
        String treatise_Level = request.getParameter("treatise_Level");
        String treatise_Press = request.getParameter("treatise_Press");
        String id = request.getParameter("id");

        String is_Core = request.getParameter("is_Core");
        Boolean isCore = (is_Core != null && !"".equals(is_Core)) ? Boolean.valueOf(is_Core) : false;

        String unit = request.getParameter("unit");
        String make_No = request.getParameter("make_No");

        String is_cooperate = request.getParameter("is_cooperate");
        Boolean isCooperate = (is_cooperate != null && !"".equals(is_cooperate)) ? Boolean.valueOf(is_cooperate) : false;

        String winnerInfos = request.getParameter("info");
        String info = request.getParameter("info");
        String leaderOpinion = request.getParameter("leaderOpinion");
        dto2.setFormNo(StringCommHelper.nullToLong(request.getParameter("formNo")));
        dto2.setFormKind(StringCommHelper.nullToString(request.getParameter("formKind")));
        dto2.setTreatise_Name(treatise_Name);
        dto2.setPublish_Time_(publish_Time);
        dto2.setTreatise_Level(treatise_Level);
        dto2.setTreatise_Press(treatise_Press);
        dto2.setUnit(unit);
        dto2.setMake_No(make_No);
        dto2.setId(Long.parseLong(id));
        dto2.setIs_Core(isCore);
        dto2.setIs_cooperate(isCooperate);

        dto2.setWinner_info(winnerInfos);
        dto2.setInfo(info);
        dto2.setLeaderOpinion(leaderOpinion);
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        CommonResult result=treatiseService.addOrUpdate3(dto2,dto);
        return result;
    }

    /*
    * 第二步审核页面
    * */
    @RequestMapping(value = "syy_gy_lc03_view2")
    public ModelAndView formProjectApplyView2(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("viewOnly",viewOnly);
        FormHeaderDto dto2 = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto2);
        String id = request.getParameter("id");
        if (formNo!=null && formNo!=0L) {

            TreatiseDto dto=treatiseService.getByFormNoTreatise(formNo);
            model.addAttribute("beans",dto);
        }

        List<EmpDto> list1=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list1){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        List<EmpDto> list=employeeService.getAllEmps();
        model.addAttribute("people",list);


        return view(viewOnly.equals("false")?formLayout:formViewLayout,  "userAchievements/syy_gy_lc03_view2", model);
    }


    /*
    * 第二步审核
    * */
    @RequestMapping(value ="gy_lc03_approve2")
    @ResponseBody
    public CommonResult approve2(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
        TreatiseDto dto2 = new TreatiseDto();

        String treatise_Name = request.getParameter("treatise_Name");
        String publish_Time = request.getParameter("publish_Time_");
        String treatise_Level = request.getParameter("treatise_Level");
        String treatise_Press = request.getParameter("treatise_Press");
        String id = request.getParameter("id");

        String is_Core = request.getParameter("is_Core");
        Boolean isCore = (is_Core != null && !"".equals(is_Core)) ? Boolean.valueOf(is_Core) : false;

        String unit = request.getParameter("unit");
        String make_No = request.getParameter("make_No");

        String is_cooperate = request.getParameter("is_cooperate");
        Boolean isCooperate = (is_cooperate != null && !"".equals(is_cooperate)) ? Boolean.valueOf(is_cooperate) : false;

        String winnerInfos = request.getParameter("info");
        String info = request.getParameter("info");
        String leaderOpinion = request.getParameter("leaderOpinion");
        String deptOpinion = request.getParameter("deptOpinion");
        dto2.setFormNo(StringCommHelper.nullToLong(request.getParameter("formNo")));
        dto2.setFormKind(StringCommHelper.nullToString(request.getParameter("formKind")));
        dto2.setTreatise_Name(treatise_Name);
        dto2.setPublish_Time_(publish_Time);
        dto2.setTreatise_Level(treatise_Level);
        dto2.setTreatise_Press(treatise_Press);
        dto2.setUnit(unit);
        dto2.setMake_No(make_No);
        dto2.setId(Long.parseLong(id));
        dto2.setIs_Core(isCore);
        dto2.setIs_cooperate(isCooperate);

        dto2.setWinner_info(winnerInfos);
        dto2.setInfo(info);
        dto2.setLeaderOpinion(leaderOpinion);
        dto2.setDeptOpinion(deptOpinion);
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        CommonResult result=treatiseService.addOrUpdate3(dto2,dto);
        return result;
    }
}
