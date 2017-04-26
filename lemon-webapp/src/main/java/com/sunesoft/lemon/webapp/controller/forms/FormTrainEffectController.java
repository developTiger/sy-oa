package com.sunesoft.lemon.webapp.controller.forms;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.NoticeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.NoticeDto;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainEffectDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.TrainFileDto;
import com.sunesoft.lemon.syms.hrForms.application.formTrain.FormTrainService;
import com.sunesoft.lemon.syms.hrForms.application.formTrainEffect.FormTrainEffectService;
import com.sunesoft.lemon.syms.hrForms.domain.TrainFile;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.Helper;
import com.sunesoft.lemon.webapp.utils.URI;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 培训效果评估单
 * Created by admin on 2016/7/27.
 */
@Controller
public class FormTrainEffectController extends Layout {

    @Autowired
    FormTrainEffectService formTrainEffectService;

    @Autowired
    FormTrainService formTrainService;

    @Autowired
    FileService fileService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    UserSession us;
    @Autowired
    FormListService formListService;
    /**
     * 培训效果评估单 新增首页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_rs_lc02_a")
    public ModelAndView index(Model model,HttpServletRequest request){
        FormListDto dto =formListService.getFormListInfo("SYY_RS_LC02");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date",DateHelper.formatDate(new Date(),"yyyy-MM-dd"));

        EmpSessionDto empSessionDto = us.getCurrentUser(request);//获取当前用户
        List<FormTrainDto> list=formTrainService.getFormTrainByEmpID(empSessionDto.getId());
        model.addAttribute("beans",list);
        String formNo = request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            FormTrainEffectDto formTrainEffectDto = formTrainEffectService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("fdto", formTrainEffectDto);
        }

        return view(applyLayout,"forms/syy_rs_lc02_a",model);
    }

    /**
     * 页面数据展示
     * @param request
     * @return
     */
    @RequestMapping("ajax_syy_rs_lc02_data")
    @ResponseBody
    public FormTrainEffectDto applyView(HttpServletRequest request) {
        String formKind = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormTrainEffectDto formTrainEffectDto = formTrainEffectService.getFormByFormNo(Long.parseLong(formKind));
        formTrainEffectDto.setIsViewOnly(Boolean.valueOf(viewOnly));
        return formTrainEffectDto;
    }


    /**
     * 表单提交 页面所有数据 包括文件
     * @param request
     * @param response
     */
    @RequestMapping(value="ajax_add_update_files")
    public void ajax_add_update_news(FormTrainEffectDto formTrainEffectDto,HttpServletRequest request,HttpServletResponse response)  {
//        FormTrainEffectDto formTrainEffectDto = new FormTrainEffectDto();
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("fileName");
        String beginTime = request.getParameter("strBeginDate");
        String endTime = request.getParameter("strEndDate");
        String trainName = request.getParameter("tName");
        List<TrainFileDto> list = new ArrayList<>();
        CommonResult result=null;
        for (MultipartFile myfile : myfiles) {
            if (myfile.isEmpty()) {
                result = new CommonResult(false,"请选择要上传的文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return;
            } else {
                try {
                    String fileName = myfile.getOriginalFilename();
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    FileInfoDto fileInfoDto=new FileInfoDto();

                    fileInfoDto.setFileName(fileName);

                    fileInfoDto.setExtensions(extension);
                    fileInfoDto.setInputStream(myfile.getInputStream());

                    String id=fileService.upload(fileInfoDto);

                    if(!StringUtils.isNullOrWhiteSpace(id)) {
                        TrainFileDto trainFileDto = new TrainFileDto();
                        trainFileDto.setFileId(id);
                        trainFileDto.setFileName(fileName);

                        list.add(trainFileDto);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        formTrainEffectDto.setApplyer(empSessionDto.getId());
        formTrainEffectDto.setApplyerName(empSessionDto.getName());
        formTrainEffectDto.setDeptId(empSessionDto.getDeptId());
        formTrainEffectDto.setDeptName(empSessionDto.getDeptName());

        formTrainEffectDto.setTrainBeginTime(DateHelper.parse(beginTime,"yyyy-MM-dd"));
        formTrainEffectDto.setTrainEndTime(DateHelper.parse(endTime,"yyyy-MM-dd"));
        formTrainEffectDto.setTrainName(trainName);
        formTrainEffectDto.setFilesDto(list);
        result= formTrainEffectService.addByDto(formTrainEffectDto);
        formTrainEffectService.submitForm(result.getId(),formTrainEffectDto.getFormKind());
        result= ResultFactory.commonSuccess();
        AjaxResponse.write(response, JsonHelper.toJson(result));
    }

    /**
     *通过formNo获取对应的四个字段
     * @param request
     * @param response
     */
    @RequestMapping("get_form_by_formKindName")
    public void getFormByFormKindName(HttpServletRequest request,HttpServletResponse response){
        String formNo = request.getParameter("formNo");
        if(!StringUtils.isNullOrWhiteSpace(formNo)){
            FormTrainDto formTrainDto = formTrainService.getFormByFormNo(Long.parseLong(formNo));
            String json = JsonHelper.toJson(formTrainDto);
            AjaxResponse.write(response,json);
        }

    }




}
