package com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.factory;

import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.fr.results.UniqueResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ReadExcel;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.util.Date;

/**
 * Created by pxj on 2016/9/9.
 */
public class UploadProjectPlan extends ReadExcel<ProjectPlanInput> {
    @Override
    protected UniqueResult<ProjectPlanInput> convertRow(HSSFRow row, int rowNum, int colNum) {
        try {
            ProjectPlanInput dto = new ProjectPlanInput();
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(0)))){
                dto.setProjectPlan_InputYear_Str(getCellFormatValue(row.getCell(0)));
            }
/*            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(1)))) {
               // System.out.print(getCellFormatValue(row.getCell(1)));
                String no=getCellFormatValue(row.getCell(1)).replace(".0","");
                dto.setProjectPlan_No(Long.parseLong(no));
            }*/
            if(!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(1)))){
                dto.setProjectPlan_Number(getCellFormatValue(row.getCell(1)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(2)))) {
                dto.setProjectPlan_Name(getCellFormatValue(row.getCell(2)));
            }
/*            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(3)))) {
                dto.setProjectPlan_Type(getCellFormatValue(row.getCell(3)));
            }*/
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(3)))) {
                dto.setProjectPlan_Content(getCellFormatValue(row.getCell(3)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(4)))) {
                dto.setProjectPlan_BearUnit(getCellFormatValue(row.getCell(4)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(5)))) {
                dto.setProjectPlan_ParticipatingUnit(getCellFormatValue(row.getCell(5)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(6)))) {
                dto.setStartTime(DateHelper.parse(getCellFormatValue(row.getCell(6)),"yyyy-MM-dd"));
                // dto.setProjectPlan_StartEndTime(getCellFormatValue(row.getCell(7)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(7)))) {
                dto.setEndTime(DateHelper.parse(getCellFormatValue(row.getCell(7)),"yyyy-MM-dd"));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(8)))) {
                dto.setProjectPlan_Manager(getCellFormatValue(row.getCell(8)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(9)))){
                dto.setProjectPlan_Email(getCellFormatValue(row.getCell(9)));
            }
/*            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(12)))) {
                dto.setProjectPlan_Remark(getCellFormatValue(row.getCell(12)));
            }*/
            return new UniqueResult<ProjectPlanInput>(dto);
        }catch (Exception ex){
            return new UniqueResult<ProjectPlanInput>("行"+rowNum+",数据解析错误，请检查！");
        }
    }

    /**
     * 判断表格是否正确
     * @param row 表头信息(列名)
     * @return Boolean
     */
    protected Boolean checkExcelCol(HSSFRow row) {
        System.out.print(row.getLastCellNum());
        //可判断指定列名是否一致等，如返回false 则会报格式不正确的错误
        if(row.getLastCellNum()!=10)
            return false;
        return true;
    }

}
