package com.sunesoft.lemon.webapp.controller.equipment;

import com.sunesoft.lemon.ay.dateCheck.application.Criteria.DateCheckCriteria;
import com.sunesoft.lemon.ay.dateCheck.application.DateCheckService;
import com.sunesoft.lemon.ay.dateCheck.application.dtos.DateCheckDto;
import com.sunesoft.lemon.ay.dateCheck.domain.DateCheck;
import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentHealthRecordCriteria;
import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentWorkingRecordCriteria;
import com.sunesoft.lemon.ay.equipment.application.EquipmentHealthRecordService;
import com.sunesoft.lemon.ay.equipment.application.EquipmentWorkingRecordService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentHealthRecordDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentWorkingRecordDto;
import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentHealthRecord;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentWorkingRecord;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.CommandAPDU;
import java.util.Date;

/**
 * Created by admin on 2016/10/21.
 */
@Controller
public class EquipmentOperationController extends Layout {

    @Autowired
    EquipmentWorkingRecordService equipmentWorkingRecordService;

    @Autowired
    UserSession userSession;

    @Autowired
    EquipmentHealthRecordService equipmentHealthRecordService;

    @Autowired
    DateCheckService checkService;

    @RequestMapping(value = "ajax_query_equipmentWorking")
    public void queryWorking(HttpServletRequest request, EquipmentWorkingRecordCriteria criteria, HttpServletResponse response) {
        String beginTime = request.getParameter("begTime");
        String enTime = request.getParameter("enTime");

        String id = request.getParameter("id");

        if (!StringUtils.isNullOrWhiteSpace(beginTime))
            criteria.setBeginTime(DateHelper.parse(beginTime, "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(enTime))
            criteria.setEndTime(DateHelper.parse(enTime, "yyyy-MM-dd"));


        PagedResult<EquipmentWorkingRecordDto> pagedResult = equipmentWorkingRecordService.getPagesByequipWorkRecord(criteria, id);
        String save = request.getParameter("save");
        if(!StringUtils.isNullOrWhiteSpace(save)){
            pagedResult.setCanOpera(Integer.parseInt(save));
        }
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_addOrUpdate_working", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdateWorking(HttpServletRequest request, EquipmentWorkingRecordDto dto) {
        String id = request.getParameter("hiddenWorkingId");

        if (!StringUtils.isNullOrWhiteSpace(id))
            dto.setId(Long.parseLong(id));

        EmpSessionDto empSessionDto = userSession.getCurrentUser(request);

        String count = request.getParameter("count");
        String workTime = request.getParameter("workTime");

        dto.setPersonId(empSessionDto.getId());
        dto.setPersonName(empSessionDto.getName());


        dto.setSampleCount(Integer.parseInt(count));
        dto.setTime(new Date());

        CommonResult commonResult = equipmentWorkingRecordService.addOrUpdateEquipWorkRecord(dto);
        return commonResult;
    }

    @RequestMapping(value = "ajax_update_working_record")
    @ResponseBody
    public EquipmentWorkingRecordDto queryUpdateData(HttpServletRequest request) {
        String id = request.getParameter("id");
        EquipmentWorkingRecord equipmentWorkingRecord = equipmentWorkingRecordService.getById(Long.parseLong(id));
        EquipmentWorkingRecordDto dto = DtoFactory.convert(equipmentWorkingRecord, new EquipmentWorkingRecordDto());
        return dto;
    }

    @RequestMapping(value = "ajax_delete_working_record")
    @ResponseBody
    public CommonResult deleteWorking(HttpServletRequest request) {
        String id = request.getParameter("id");
        CommonResult commonResult = equipmentWorkingRecordService.delete(Long.parseLong(id));
        return commonResult;
    }

    @RequestMapping(value = "ajax_query_equipmentHealth")
    public void queryHealthRecord(HttpServletRequest request, EquipmentHealthRecordCriteria criteria, HttpServletResponse response) {
        String beginTime = request.getParameter("begTime");
        String enTime = request.getParameter("enTime");
        String id = request.getParameter("id");

        if (!StringUtils.isNullOrWhiteSpace(beginTime))
            criteria.setBeginTime(DateHelper.parse(beginTime, "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(enTime))
            criteria.setEndTime(DateHelper.parse(enTime, "yyyy-MM-dd"));


        PagedResult<EquipmentHealthRecordDto> pagedResult = equipmentHealthRecordService.getPagesByequipWorkHealth(criteria, id);
        String save = request.getParameter("save");
        if(!StringUtils.isNullOrWhiteSpace(save)){
            pagedResult.setCanOpera(Integer.parseInt(save));
        }
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_addOrUpdate_equipmentHealth", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdateHealth(HttpServletRequest request, EquipmentHealthRecordDto dto) {
        String id = request.getParameter("hiddenWorkingId");

        if (!StringUtils.isNullOrWhiteSpace(id))
            dto.setId(Long.parseLong(id));

        EmpSessionDto empSessionDto = userSession.getCurrentUser(request);


        dto.setPersonId(empSessionDto.getId());
        dto.setPersonName(empSessionDto.getName());


        dto.setTime(new Date());

        CommonResult commonResult = equipmentHealthRecordService.addOrUpdateEquipWorkHealth(dto);
        return commonResult;
    }

    @RequestMapping(value = "ajax_update_working_health")
    @ResponseBody
    public EquipmentHealthRecordDto queryUpdateData1(HttpServletRequest request) {
        String id = request.getParameter("id");
        EquipmentHealthRecord equipmentWorkingRecord = equipmentHealthRecordService.getById(Long.parseLong(id));
        EquipmentHealthRecordDto dto = DtoFactory.convert(equipmentWorkingRecord, new EquipmentHealthRecordDto());
        return dto;
    }

    @RequestMapping(value = "ajax_delete_health_record")
    @ResponseBody
    public CommonResult deleteWorking1(HttpServletRequest request) {
        String id = request.getParameter("id");
        CommonResult commonResult = equipmentHealthRecordService.delete(Long.parseLong(id));
        return commonResult;
    }

    @RequestMapping(value = "ajax_query_equipmentDateCheck")
    public void queryDateCheck(HttpServletRequest request, DateCheckCriteria checkCriteria, HttpServletResponse response) {
        checkCriteria.setOrderByProperty("checkTime");
        checkCriteria.setAscOrDesc(false);
        String begin = request.getParameter("begTime");
        String end = request.getParameter("enTime");
        checkCriteria.setBeginTime(begin);
        checkCriteria.setEndTime(end);
        PagedResult<DateCheckDto> pagedResult = checkService.paged(checkCriteria);
        String save = request.getParameter("save");
        if(!StringUtils.isNullOrWhiteSpace(save)){
            pagedResult.setCanOpera(Integer.parseInt(save));
        }
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    //表的填写
    @RequestMapping(value = "ajax_addOrUpdate_dateCheck")
    @ResponseBody
    public CommonResult addOrUpdate(DateCheckDto dto, HttpServletRequest request, HttpServletResponse response) {
//        EmpSessionDto user = userSession.getCurrentUser(request);
//        dto.setCheckPerson(user.getName());
        String id = request.getParameter("hiddenDateCheckId");
        String euipId = request.getParameter("equipmentId");
        dto.setEquipId(Long.parseLong(euipId));
        CommonResult c = null;
        if (StringUtils.isNullOrWhiteSpace(id)) {
            c = checkService.add(dto);//增加
        } else {
            dto.setId(Long.parseLong(id));
            c = checkService.update(dto);//修改
        }
        return c;
    }

    @RequestMapping(value = "ajax_update_date_check")
    @ResponseBody
    public DateCheckDto queryUpdateDateCheck(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isNullOrWhiteSpace(id)) return null;
        DateCheckDto dto = checkService.getById(Long.parseLong(id));
        return dto;
    }
}
