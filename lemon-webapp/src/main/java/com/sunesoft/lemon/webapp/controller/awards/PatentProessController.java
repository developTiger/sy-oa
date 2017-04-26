package com.sunesoft.lemon.webapp.controller.awards;

import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.patentCG.application.PatentFlowService;
import com.sunesoft.lemon.deanery.patentCG.application.PatentService;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDeaneryUtil;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentFlowDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
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
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pxj on 2016/8/29.
 */
@Controller
public class PatentProessController extends Layout {

    @Autowired
    FormListService formListService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PatentFlowService patentFlowService;
    @Autowired
    PatentService patentService;
    @Autowired
    FormHeaderService headerService;
    @Autowired
    UserSession us;
    @Autowired
    DeptmentService deptmentService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 专利流程新增页面
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("syy_gy_lc02_a")
    public ModelAndView modelAndView(HttpServletRequest request, Model model) {
        FormListDto dt = formListService.getFormListInfo("SYY_GY_LC02");
        List<EmpDto> list = employeeService.getAllEmps();
        Map<String, String> map = new HashMap<String, String>();
        for (EmpDto ed : list) {
            map.put(String.valueOf(ed.getId()), ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb", json);
        model.addAttribute("emp", list);
        model.addAttribute("winresultclassify", static_common.WIN_RESULT_CLASSIFY);
        model.addAttribute("winlevel", static_common.WIN_LEVEL);
        model.addAttribute("patenttype", static_common.PATENT_TYPE);

        List<DeptmentDto> deptmentDtos= deptmentService.getByDeptsIds();
        model.addAttribute("deptment", deptmentDtos);
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 = Long.parseLong(id);
            PatentDto patentDto = patentService.getByIdPatent(id1);
            Date ad = patentDto.getApply_Date();
            patentDto.setApply_Date_Str(sdf.format(ad));
            Date vd = patentDto.getValid_Date();
            patentDto.setValid_Date_Str(sdf.format(vd));
            model.addAttribute("beans", patentDto);
        }
        model.addAttribute("formKind", dt.getFormKind());
        model.addAttribute("formKindName", dt.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
        return view(applyLayout, "awards/syy_gy_lc02_a", model);
    }

    /***
     * ajax_gy_lc02_data 保存专利流程新增数据
     *
     * @param patentFlowDto 专利流程对应字段
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_gy_lc02_data")
    @ResponseBody
    public CommonResult ajax_gy_lc02_data(PatentFlowDto patentFlowDto, HttpServletRequest request) {
        String in = request.getParameter("winner_Info");
        PatentDto patentDto = PatentDeaneryUtil.convertFromListPatentFlowDto(patentFlowDto);
        CommonResult commonResult = patentFlowService.addByDto(patentDto);
        Long id = patentFlowService.findPatentId(commonResult.getId());
        patentFlowService.savePrizeWinner(id, in);
        CommonResult result = patentFlowService.submitForm(commonResult.getId(), patentFlowDto.getFormKind());
        return result;
    }

    //第一步审核页面
    @RequestMapping(value = "syy_gy_lc02_view")
    public ModelAndView syy_gy_lc02_view(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<PatentFlowDto> queryProject= patentFlowService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        PatentDto formFatentDto = patentFlowService.getFormByFormNo(formNo);
        Date ad = formFatentDto.getApply_Date();
        formFatentDto.setApply_Date_Str(sdf.format(ad));
        Date vd = formFatentDto.getValid_Date();
        formFatentDto.setValid_Date_Str(sdf.format(vd));
        model.addAttribute("beans",formFatentDto);
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
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "awards/syy_gy_lc02_view", model);
        }
        return view(formLayout,  "awards/syy_gy_lc02_view", model);//审核 或 补充数据

    }
    //第一步审核保存
    @RequestMapping(value = "syy_gy_lc02_approve")
    @ResponseBody
    public CommonResult syy_gy_lc02_approve(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =patentFlowService.nextProject(dto);
        return result;
    }
    //第二步审核页面
    @RequestMapping(value = "syy_gy_lc02_view1")
    public ModelAndView syy_gy_lc02_view1(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<PatentFlowDto> queryProject= patentFlowService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        PatentDto formFatentDto = patentFlowService.getFormByFormNo(formNo);
        Date ad = formFatentDto.getApply_Date();
        formFatentDto.setApply_Date_Str(sdf.format(ad));
        Date vd = formFatentDto.getValid_Date();
        formFatentDto.setValid_Date_Str(sdf.format(vd));
        model.addAttribute("beans",formFatentDto);
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
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "awards/syy_gy_lc02_view", model);
        }
        return view(formLayout,  "awards/syy_gy_lc02_view", model);//审核 或 补充数据

    }
    //第二步审核保存
    @RequestMapping(value = "syy_gy_lc02_approve1")
    @ResponseBody
    public CommonResult syy_gy_lc02_approve1(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =patentFlowService.nextProject(dto);
        return result;
    }


//获取获奖人list数据
    @RequestMapping(value = "ajax_query_addEmps1")
    public void queryAddEmps(HttpServletResponse response,HttpServletRequest request,EmpCriteria empCriteria){
        String name = URI.deURI(request.getParameter("empName"));
        String dept = URI.deURI(request.getParameter("deptName"));
        if (!StringUtils.isNullOrWhiteSpace(name) && !("undefined").equals(name)) {
            empCriteria.setName(name);
        }
        if (!StringUtils.isNullOrWhiteSpace(dept) && !("undefined").equals(dept)) {
            empCriteria.setDeptId(Long.parseLong(dept));
        }
        //通过部门id获取部门员工
        String deptId = request.getParameter("deptId");
        Long emp_deptId=null;
        if (!StringUtils.isNullOrWhiteSpace(deptId)) {
            emp_deptId = Long.parseLong(deptId);
        }

        empCriteria.setPageSize(10);
        PagedResult<EmpDto> pagedResult=employeeService.findEmpsPaged(empCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

//获取人名
  /*  @RequestMapping(value = "ajax_add_update_emps1", method = RequestMethod.POST)
    @ResponseBody//此注解：返回ajax
    public CommonResult addOrUpdateEmps(HttpServletRequest request, HttpServletResponse response) {
        String formKind = request.getParameter("formKind");

         return null;
        }

*/

    }
