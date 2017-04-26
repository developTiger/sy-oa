package com.sunesoft.lemon.webapp.controller.resultCertificate;

import com.sunesoft.lemon.deanery.resultCertificate.application.ResultCertificateService;
import com.sunesoft.lemon.deanery.resultCertificate.application.criteria.ResultCertificateCriteria;
import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificateDto;
import com.sunesoft.lemon.deanery.treatiseCG.application.criteria.TreatiseCriteria;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;
import com.sunesoft.lemon.deanery.typeOfTreatise.dtos.TypeOfTreatiseDto;
import com.sunesoft.lemon.deanery.typeOfTreatise.dtos.TypeOfTreatiseService;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by pxj on 2016/9/28.
 */
@Controller
public class ResultCertificateController extends Layout {
    @Autowired
    ResultCertificateService resultCertificateService;
    @Autowired
    TypeOfTreatiseService typeOfTreatiseService;

    @RequestMapping(value = "sra_u_certificate")
    public ModelAndView index(Model model, HttpServletRequest request, HttpServletResponse response){
        List<TypeOfTreatiseDto> typeOfTreatiseDtos=typeOfTreatiseService.findAll();
        model.addAttribute("typeOfTreatise",typeOfTreatiseDtos);
        return view(layout,"resultCertificate/resultCertificate",model);
    }
    @RequestMapping(value = "ajax_certificate_query_list")
    public void ajax_certificate_query_list(ResultCertificateCriteria resultCertificateCriteria, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
/*        TreatiseCriteria criteria = new TreatiseCriteria();
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
        criteria.setIs_cooperate(isCooperate);*/
        PagedResult pagedResult = resultCertificateService.getResultCertificateList(resultCertificateCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }

    //详细信息
    @RequestMapping(value = "detailResultCertificate")
    public ModelAndView detailResultCertificate(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            ResultCertificateDto dto=resultCertificateService.getResultCertificateById(Long.parseLong(id));
            model.addAttribute("beans",dto);
        }
        return view("resultCertificate/detialResultCertificate",model);
    }

/*    //获取论著类型
    @RequestMapping(value = "findAllType")
    public void  findAllType(HttpServletResponse response){
        List<TypeOfTreatiseDto> typeOfTreatiseDtos=typeOfTreatiseService.findAll();
        String json = JsonHelper.toJson(typeOfTreatiseDtos);
        AjaxResponse.write(response,json);
    }*/

}
