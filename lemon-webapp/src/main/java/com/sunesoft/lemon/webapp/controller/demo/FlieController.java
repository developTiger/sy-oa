package com.sunesoft.lemon.webapp.controller.demo;
import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.webapp.controller.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FlieController extends Layout {

    /**
     * 上传
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_p_loading")
    public ModelAndView carInfo(Model model,CarReportCriteria carReportCriteria){
        return view(layout,"demon/flieuploading",model);
    }





}
