package com.sunesoft.lemon.syms.eHr.application.factory;

import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.dtos.AttendDayDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.AttendDowloadsDto;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by zhouz on 2016/8/4.
 */
public class AttendDownloadFactory extends ExpotExcel<AttendDowloadsDto> {


    @Override
    protected HSSFWorkbook exportExcel(HSSFWorkbook workbook, HSSFCellStyle style, String title, String sheetName, String[] headers, Collection<AttendDowloadsDto> collection, String datePattern) {

        HSSFFont font=workbook.createFont();
        font.setFontName("黑体");
        style=workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        Field[] fields = AttendDowloadsDto.class.getDeclaredFields();
        Iterator it = collection.iterator();
        int daySize=0;
        AttendDowloadsDto dowloadsDto0=new AttendDowloadsDto();
        while(it.hasNext()) {
            dowloadsDto0= (AttendDowloadsDto)it.next();
            daySize=dowloadsDto0.getDays().size();
            break;
        }

        int length=daySize+fields.length-2;
        HSSFSheet sheet=workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(2);
        sheet.setDefaultRowHeightInPoints(20);
        sheet.setColumnWidth(0, 8 * 256);
        sheet.setColumnWidth(1, 20 * 256);

        HSSFRow row=sheet.createRow(0);
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("新疆油田公司员工考勤表");
        row.setHeight((short)500);
        HSSFCellStyle hssfCellStyle=workbook.createCellStyle();
        hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        hssfCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont titleFont=workbook.createFont();
        titleFont.setFontHeightInPoints((short)20);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        hssfCellStyle.setFont(titleFont);
        cell.setCellStyle(hssfCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,length-2));
        row=sheet.createRow(1);
        row.setHeight((short)500);
        for(int i=0;i<length;i++){
            cell=row.createCell(i);
        }
        row=sheet.createRow(2);
        row.setHeight((short)500);
        int temp=(length-6)/5-3;

        HSSFCellStyle cellStyleT=workbook.createCellStyle();
        cellStyleT.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleT.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        cell=row.createCell(0);
        cell.setCellStyle(cellStyleT);
        cell.setCellValue("单位名称：" + dowloadsDto0.getDeptName());

        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, temp));
        cell=row.createCell(temp+2);
        cell.setCellStyle(cellStyleT);
        cell.setCellValue("作业区（车间、队）：" + dowloadsDto0.getDeptName());
        sheet.addMergedRegion(new CellRangeAddress(2, 2, temp + 2, temp * 3 + 2));
        cell=row.createCell(temp * 3 + 4);
        cell.setCellStyle(cellStyleT);
        cell.setCellValue("班组：" + dowloadsDto0.getDeptName());
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 3 * temp + 4, 4 * temp + 8));
        cell=row.createCell(4 * temp + 11);
        cell.setCellStyle(cellStyleT);
        cell.setCellValue("用工形式：");
        sheet.addMergedRegion(new CellRangeAddress(2,2, 4 * temp + 11, 9 * temp / 2 + 11));
        cell= row.createCell(9*temp/2+14);
        cell.setCellStyle(cellStyleT);
        cell.setCellValue("考勤日期：" + dowloadsDto0.getBeginTime() + "~" + dowloadsDto0.getEndTime());
        sheet.addMergedRegion(new CellRangeAddress(2,2,9*temp/2+14,length-2));

        row=sheet.createRow(3);
        row.setHeight((short)600);
        cell=row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("序号");
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));
        cell= row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("姓名");
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1));
        cell= row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("出   勤    情   况");
        for(int i=3;i<=daySize+1;i++){
            cell=row.createCell(i);
            cell.setCellStyle(style);
        }
        sheet.addMergedRegion(new CellRangeAddress(3,3,2,daySize+1));
        cell= row.createCell(daySize+2);
        cell.setCellStyle(style);
        cell.setCellValue("制度工作日");
        sheet.addMergedRegion(new CellRangeAddress(3, 4, daySize + 2, daySize + 2));
        cell= row.createCell(daySize+3);
        cell.setCellStyle(style);
        cell.setCellValue("出勤天数I");
        sheet.addMergedRegion(new CellRangeAddress(3, 4, daySize + 3, daySize + 3));
        cell= row.createCell(daySize+4);
        cell.setCellStyle(style);
        cell.setCellValue("驾驶天数");
        sheet.addMergedRegion(new CellRangeAddress(3, 4, daySize + 4, daySize + 4));
        cell= row.createCell(daySize+5);
        cell.setCellStyle(style);
        cell.setCellValue("误餐天数");
        sheet.addMergedRegion(new CellRangeAddress(3, 4, daySize + 5, daySize + 5));
        cell= row.createCell(daySize+6);
        cell.setCellStyle(style);
        cell.setCellValue("疾病救济天数");
        sheet.addMergedRegion(new CellRangeAddress(3, 4, daySize + 6, daySize + 6));
        cell= row.createCell(daySize+7);
        cell.setCellStyle(style);
        cell.setCellValue("加班");
        row.createCell(daySize+8).setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(3,3,daySize+7,daySize+8));
        cell= row.createCell(daySize+9);
        cell.setCellStyle(style);
        cell.setCellValue("夜班");
        row.createCell(daySize+10).setCellStyle(style);
        row.createCell(daySize+11).setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(3,3,daySize+9,daySize+11));
        cell= row.createCell(daySize+12);
        cell.setCellStyle(style);
        cell.setCellValue("野外工作天数");
        row.createCell(daySize+13).setCellStyle(style);
        row.createCell(daySize+14).setCellStyle(style);
        row.createCell(daySize+15).setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(3,3,daySize+12,daySize+14));
        cell= row.createCell(daySize+15);
        cell.setCellStyle(style);
        cell.setCellValue("不在岗天数");
        for(int i=daySize+16;i<daySize+34;i++){
            row.createCell(i).setCellStyle(style);
        }
        sheet.addMergedRegion(new CellRangeAddress(3,3,daySize+15,daySize+33));
        cell= row.createCell(daySize+34);
        cell.setCellStyle(style);
        cell.setCellValue("保健天数");
        sheet.addMergedRegion(new CellRangeAddress(3,4,daySize+34,daySize+34));
        cell= row.createCell(daySize+35);
        cell.setCellStyle(style);
        cell.setCellValue("备注");
        sheet.addMergedRegion(new CellRangeAddress(3,4,daySize+35,daySize+35));


        row=sheet.createRow(4);
        row.createCell(0).setCellStyle(style);
        row.createCell(1).setCellStyle(style);
        row.setHeight((short)1600);
        List<AttendDayDto> list=dowloadsDto0.getDays();
        for(int i=0;i<list.size();i++){
            cell= row.createCell(i+2);
            cell.setCellStyle(style);
            cell.setCellValue(list.get(i).getDay());
        }
        row.createCell(daySize+2).setCellStyle(style);
        row.createCell(daySize+3).setCellStyle(style);
        row.createCell(daySize+4).setCellStyle(style);
        row.createCell(daySize+5).setCellStyle(style);
        row.createCell(daySize+6).setCellStyle(style);
        cell= row.createCell(daySize+7);
        cell.setCellStyle(style);
        cell.setCellValue("公休J1");
        cell= row.createCell(daySize+8);
        cell.setCellStyle(style);
        cell.setCellValue("节日J2");
        cell= row.createCell(daySize+9);
        cell.setCellStyle(style);
        cell.setCellValue("其他夜班YB1");
        cell= row.createCell(daySize+10);
        cell.setCellStyle(style);
        cell.setCellValue("小夜班YB3");
        cell= row.createCell(daySize+11);
        cell.setCellStyle(style);
        cell.setCellValue("大夜班YB2");
        cell= row.createCell(daySize+12);
        cell.setCellStyle(style);
        cell.setCellValue("野外Y1");
        cell= row.createCell(daySize+13);
        cell.setCellStyle(style);
        cell.setCellValue("野外Y2");
        cell= row.createCell(daySize+14);
        cell.setCellStyle(style);
        cell.setCellValue("野外Y3");
        cell= row.createCell(daySize+15);
        cell.setCellStyle(style);
        cell.setCellValue("出差Z");
        cell= row.createCell(daySize+16);
        cell.setCellStyle(style);
        cell.setCellValue("学历学习X1");
        cell= row.createCell(daySize+17);
        cell.setCellStyle(style);
        cell.setCellValue("脱产学习X2");
        cell= row.createCell(daySize+18);
        cell.setCellStyle(style);
        cell.setCellValue("带薪休假S1");
        cell= row.createCell(daySize+19);
        cell.setCellStyle(style);
        cell.setCellValue("疗养L");
        cell= row.createCell(daySize+20);
        cell.setCellStyle(style);
        cell.setCellValue("探亲假T");
        cell= row.createCell(daySize+21);
        cell.setCellStyle(style);
        cell.setCellValue("婚丧假H");
        cell= row.createCell(daySize+22);
        cell.setCellStyle(style);
        cell.setCellValue("工伤假G");
        cell= row.createCell(daySize+23);
        cell.setCellStyle(style);
        cell.setCellValue("产假C");
        cell= row.createCell(daySize+24);
        cell.setCellStyle(style);
        cell.setCellValue("产后休长假C1");
        cell= row.createCell(daySize+25);
        cell.setCellStyle(style);
        cell.setCellValue("护理假S2");
        cell= row.createCell(daySize+26);
        cell.setCellStyle(style);
        cell.setCellValue("陪护假S2");
        cell= row.createCell(daySize+27);
        cell.setCellStyle(style);
        cell.setCellValue("计划生育假S2");
        cell= row.createCell(daySize+28);
        cell.setCellStyle(style);
        cell.setCellValue("病假B");
        cell= row.createCell(daySize+29);
        cell.setCellStyle(style);
        cell.setCellValue("事假S3");
        cell= row.createCell(daySize+30);
        cell.setCellStyle(style);
        cell.setCellValue("旷工K1");
        cell= row.createCell(daySize+31);
        cell.setCellStyle(style);
        cell.setCellValue("拘留K2");
        cell= row.createCell(daySize+32);
        cell.setCellStyle(style);
        cell.setCellValue("戒毒K3");
        cell= row.createCell(daySize+33);
        cell.setCellStyle(style);
        cell.setCellValue("劳保W");
        row.createCell(daySize+34).setCellStyle(style);
        row.createCell(daySize+35).setCellStyle(style);

        int indexR=5;
        int serialNumber=1;
        for(AttendDowloadsDto dowloadsDto:collection){
            int indexC=0;
            row=sheet.createRow(indexR);
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(serialNumber);

            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getEmpName());
            for(AttendDayDto dayDto:dowloadsDto.getDays()){
                cell= row.createCell(indexC++);
                cell.setCellStyle(style);
                cell.setCellValue(dayDto.getCord());
            }
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getSystemWorking());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getAttendD());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getDriverD());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getWuCaiD());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getDiseaseD());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getGwork());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getJwork());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getOtherNightWork());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getSmallNightWork());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getBigNightWork());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getWildO());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getWildW());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getWildT());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getBusinessTravel());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getStudyExperience());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getTstudy());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getVacation());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getSpa());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getVisitFamily());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getWeddingFuneral());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getWorkHurt());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getBirthChild());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getLbirth());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getNurseH());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getNurseP());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getNurseJ());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getSick());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getAleave());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getAbsenteeism());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getCustody());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getDrug());
            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getLabor());

            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getHealthCare());

            cell= row.createCell(indexC++);
            cell.setCellStyle(style);
            cell.setCellValue(dowloadsDto.getRemark());
            indexR++;
            serialNumber++;
        }
        row=sheet.createRow(indexR);
        row.createCell(1).setCellValue("审核人:");
        sheet.addMergedRegion(new CellRangeAddress(indexR,indexR,1,1+temp));
        sheet.addMergedRegion(new CellRangeAddress(indexR,indexR,2+temp,3+temp));
        row.createCell(4+temp).setCellValue("年");
        row.createCell(5 + temp);
        row.createCell(6+temp).setCellValue("月");
        row.createCell(7+temp);
        row.createCell(8+temp).setCellValue("日");
        int couT=0;
        for(int i=1;i<temp;i++) {
            row.createCell(i + temp + 8);
            couT=i+temp+8;
        }
        row.createCell(couT+1).setCellValue("领导审核：");
        sheet.addMergedRegion(new CellRangeAddress(indexR,indexR,couT+1,couT+temp));
        row.createCell(couT+temp + 2);
        sheet.addMergedRegion(new CellRangeAddress(indexR,indexR,couT+temp +3,couT+temp +4));
        row.createCell(couT+temp +5).setCellValue("年");
        row.createCell(couT+temp  + 6);
        row.createCell(couT+temp +7).setCellValue("月");
        row.createCell(couT+temp  + 8);
        row.createCell(couT+temp +9).setCellValue("日");
        row.createCell(couT+temp +12).setCellValue("考勤员：");
        sheet.addMergedRegion(new CellRangeAddress(indexR,indexR,couT+12,couT+12+2*temp));
        row.createCell(couT+14+2*temp).setCellValue(DateHelper.formatDate(new Date(),"yyyy-MM-dd"));

        return workbook;
    }



}
