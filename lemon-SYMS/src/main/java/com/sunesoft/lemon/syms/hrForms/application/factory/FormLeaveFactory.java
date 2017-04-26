package com.sunesoft.lemon.syms.hrForms.application.factory;

import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DownloadFormLeaveDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormLeaveDto;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeave;
import com.sunesoft.lemon.syms.hrForms.domain.enums.LeaveType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiazl on 2017/4/18.
 */
public class FormLeaveFactory extends DtoFactory {

    /**
     * 查询数据转化为Dto
     *
     * @param leave
     * @return
     */
    public static FormLeaveDto convertToDto(FormLeave leave) {
        FormLeaveDto dto = new FormLeaveDto();
        dto = convert(leave, dto);
        dto.setLeaveTypeName(getLeaveTypeName(leave.getLeaveType()));
        dto.setScreateDateTime(DateHelper.formatDate(leave.getCreateDateTime()));
        return dto;
    }

    /**
     * 将list->List<Dto>
     *
     * @param leaves
     * @return
     */
    public static List<FormLeaveDto> convertToListDto(List<FormLeave> leaves) {
        List<FormLeaveDto> dtos = new ArrayList<>();
        if (leaves != null && leaves.size() > 0) {
            for (FormLeave leave : leaves) {
                dtos.add(convertToDto(leave));
            }
        }
        return dtos;
    }

    /**
     * 转化成下载的数据
     * @param leaves
     * @return
     */
    public static List<DownloadFormLeaveDto> convertToDownloadDto(List<FormLeave> leaves) {
        List<DownloadFormLeaveDto> dtos = new ArrayList<>();
        if (leaves != null && leaves.size() > 0) {
            for (FormLeave leave : leaves) {
                DownloadFormLeaveDto dto=new DownloadFormLeaveDto();
                dto=convert(leave,dto);
                dto.setLeaveTypeName(getLeaveTypeName(leave.getLeaveType()));
                dto.setDays(leave.getCountTime()==null?null:leave.getCountTime().intValue());
                dto.setScreateDateTime(DateHelper.formatDate(leave.getCreateDateTime()));
                String sd=DateHelper.formatDate(leave.getFromTime(),"yyyy-MM-dd")+"---"+DateHelper.formatDate(leave.getToTime(),"yyyy-MM-dd");
                dto.setDateSlot(sd);
                dtos.add(dto);
            }
        }
        return dtos;
    }
    private static String getLeaveTypeName(LeaveType type){
        String s="";
        switch (type.toString()){
            case "visitFamily":
                s="探亲假";
                break;
            case "paidAnnual":
                s="带薪年休假";
                break;
            case "recuperation":
                s="疗养假";
                break;
            case "sick":
                s="病假";
                break;
            case "thing":
                s="事假";
                break;
            case "marry":
                s="婚假";
                break;
            case "funeral":
                s="丧假";
                break;
            case "birth":
                s="生育假";
                break;
            case "onlyChild":
                s="独生子女照护假";
                break;
            case "abortion":
                s="流产休假";
                break;
            case "escort":
                s="陪护假";
                break;
            case "injury":
                s="工伤假";
                break;
            default:
                s="原因不明";

        }
        return s;

    }

}
