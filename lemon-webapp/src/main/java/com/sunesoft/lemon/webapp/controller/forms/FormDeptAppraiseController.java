package com.sunesoft.lemon.webapp.controller.forms;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptSubAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEmpAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.application.factory.UploadDeptAppraiseDetailFactory;
import com.sunesoft.lemon.syms.hrForms.application.formDeptAppraise.FormDeptAppraiseService;
import com.sunesoft.lemon.syms.hrForms.application.formDeptSubAppraise.FormDeptSubAppraiseService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/7/29.
 */
@Controller
public class FormDeptAppraiseController extends Layout {

    @Autowired
    UserSession us;

    @Autowired
    FormDeptAppraiseService formDeptAppraiseService;

    @Autowired
    FormDeptSubAppraiseService formDeptSubAppraiseService;

    @Autowired
    FormListService formListService;
    @Autowired
    DeptmentService deptmentService;

    @Autowired
    UserSession userSession;


    @RequestMapping(value = "syy_rs_lc04_a")
    public ModelAndView apply(Model model, HttpServletRequest request) {


        String formNo = request.getParameter("formNo");
        EmpSessionDto empDto = us.getCurrentUser(request);
        DeptAppraiseDto deptAppraiseDto = null;
        if (!"".equals(formNo) && formNo != null) {
            deptAppraiseDto = formDeptAppraiseService.getFormByFormNo(Long.parseLong(formNo));
        }
        model.addAttribute("appraise", deptAppraiseDto);
        FormListDto dto = formListService.getFormListInfo("SYY_RS_LC04");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("formType", dto.getFormType());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        model.addAttribute("userInfo", empDto);
        return view(applyLayout, "forms/syy_rs_lc04_a", model);
    }

    @RequestMapping(value = "ajax_add_update_dept_appraise")
    @ResponseBody
    public CommonResult ajax_add_update_appraise(DeptAppraiseDto deptAppraiseDto, HttpServletRequest request) {
        String startDate = request.getParameter("strStartDate");
        String endDate = request.getParameter("strEndDate");
        CommonResult commonResult = new CommonResult(true);
        if (!StringUtils.isNullOrWhiteSpace(startDate) && !StringUtils.isNullOrWhiteSpace(endDate)) {
            try {
                deptAppraiseDto.setStartDate(DateHelper.parse(startDate));
                deptAppraiseDto.setEndDate(DateHelper.parse(endDate));
            } catch (Exception ex) {
                return new CommonResult(false, "请选择日期！");
            }
        }
//        //增加一个主流程
//        CommonResult result = formDeptAppraiseService.addByDto(deptAppraiseDto);
//        if (result.getIsSuccess()) {
//            commonResult = formDeptAppraiseService.submitForm(result.getId(), deptAppraiseDto.getFormKind());
//
//            //增加子表单
//            List<DeptmentDto> deptmentDtos = deptmentService.getAllDept();
//            for (DeptmentDto dept : deptmentDtos) {
//                //院领导不参加自评
//                if (dept.getDeptName() != null && dept.getDeptName().equals("院领导")) {
//                    continue;
//                } else {
//                    DeptSubAppraiseDto subAppraiseDto = new DeptSubAppraiseDto();
//                    BeanUtils.copyProperties(deptAppraiseDto, subAppraiseDto);
//                    subAppraiseDto.setParentFormNo(result.getId());
//                    subAppraiseDto.setBlongDeptId(dept.getId());
//                    subAppraiseDto.setBlongDeptName(dept.getDeptName());
//                    subAppraiseDto.setFormKind("SYY_RS_LC04_01");
//                    subAppraiseDto.setFormKindName("部门考核自评");
//                    subAppraiseDto.setHasApplyView(false);
//                    CommonResult res = formDeptSubAppraiseService.addByDto(subAppraiseDto);
//                    CommonResult subResult = formDeptSubAppraiseService.submitForm(res.getId(), subAppraiseDto.getFormKind());
//                    if (!subResult.getIsSuccess())
//                        commonResult = subResult;
//                }
//            }
//        }
        CommonResult result = formDeptAppraiseService.addByDto(deptAppraiseDto);
        commonResult = formDeptAppraiseService.submitForm(result.getId(), deptAppraiseDto.getFormKind());
        return commonResult;
    }


    @RequestMapping("ajax_syy_rs_lc04_data")
    @ResponseBody
    public DeptAppraiseDto applyView_04(Model model, HttpServletRequest request) {
        String formKind = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        DeptAppraiseDto deptAppraiseDto = formDeptAppraiseService.getFormByFormNo(Long.parseLong(formKind));
        deptAppraiseDto.setIsViewOnly(Boolean.valueOf(viewOnly));

        //第二步的时候需要将当前用户的deptId和deptName放到页面上去,而且查看只能看自己的
        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);

        if (deptAppraiseDto.getClStep()==2 && !deptAppraiseDto.getIsViewOnly()) {
            DeptAppraiseDetailDto detailDto = new DeptAppraiseDetailDto();
            detailDto.setDeptId(empSessionDto.getDeptId());
            detailDto.setDeptName(empSessionDto.getDeptName());
            List<DeptAppraiseDetailDto> list = new ArrayList<>();
            list.add(detailDto);
            deptAppraiseDto.setDetailDtos(list);
        }
        //判断第2步的查看
        if (deptAppraiseDto.getClStep()==2 && deptAppraiseDto.getIsViewOnly()) {

            //查询出来的数据 申请人能看到所有，其他的人只能看本部门的信息
            List<DeptAppraiseDetailDto> list1 = deptAppraiseDto.getDetailDtos();
            if (!deptAppraiseDto.getApplyerName().equals(empSessionDto.getLoginName())) {
                if (list1 != null && list1.size() > 0) {
                    for (int i = 0; i < list1.size(); i++) {
                        if (empSessionDto.getDeptId() != list1.get(i).getDeptId()) {
                            list1.remove(i);
                            i--;
                        }
                    }
                }
                deptAppraiseDto.setDetailDtos(list1);
            }
        }

        return deptAppraiseDto;
    }


    @RequestMapping("ajax_syy_rs_lc04_01_data")
    @ResponseBody
    public DeptSubAppraiseDto applyView_0401(Model model, HttpServletRequest request) {
        String formKind = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        DeptSubAppraiseDto deptSubAppraiseDto = formDeptSubAppraiseService.getFormByFormNo(Long.parseLong(formKind));
        deptSubAppraiseDto.setIsViewOnly(Boolean.valueOf(viewOnly));
        return deptSubAppraiseDto;
    }

    @RequestMapping("ajax_syy_rs_lc04_down")
    public void download(Model model, HttpServletRequest request, HttpServletResponse response) {

        String formKind = request.getParameter("formNo");
        DeptAppraiseDto deptAppraiseDto = formDeptAppraiseService.getFormByFormNo(Long.parseLong(formKind));
//        doExportExcel(String title, String[] headers,
//                Collection<T> collection, String datePattern,HttpServletResponse response) {

        ExpotExcel<DeptAppraiseDetailDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"编号", "部门信息", "部门id", "部门档次", "部门分数", "部门等级", "审核意见", "专家组档次", "专家组分数", "专家组等级"};
        expotExcel.doExportExcel("部门考核表", header, deptAppraiseDto.getDetailDtos(), "yyyy-MM-dd", response);

    }

    //上传文件(季度考核主流程)
    @RequestMapping("ajax_syy_rs_lc04_upload")
    public void upload(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String formNo = request.getParameter("formNo");
        CommonResult result = null;
        if (StringUtils.isNullOrWhiteSpace(formNo)) {
            result = new CommonResult(false, "sorry！！");
            AjaxResponse.write(response, JsonHelper.toJson(result));
            return;
        }
        EmpSessionDto emp = us.getCurrentUser(request);
        //上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("myfiles");
        for (MultipartFile myfile : myfiles) {
            if (myfile.isEmpty()) {
                result = new CommonResult(false, "请选择要上传的文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return;
            } else {
//                originalFilename = myfile.getOriginalFilename();
                try {
                    UploadDeptAppraiseDetailFactory factory = new UploadDeptAppraiseDetailFactory();
                    ListResult<DeptAppraiseDetailDto> dtos = factory.readExcel(myfile.getInputStream());
                    if (dtos.getIsSuccess()) {
                        result = formDeptAppraiseService.updateByList(Long.parseLong(formNo), dtos.getItems());
                        request.setAttribute("isUpload", true);
                    } else
                        result = new CommonResult(false, dtos.getMsg());

                } catch (IOException e) {
                    System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                    e.printStackTrace();
                    result = new CommonResult(false, "文件上传失败，请重试！！");
                    AjaxResponse.write(response, JsonHelper.toJson(result));
                    return;
                }
            }
        }
        AjaxResponse.write(response, JsonHelper.toJson(result));
        return;
    }


}
