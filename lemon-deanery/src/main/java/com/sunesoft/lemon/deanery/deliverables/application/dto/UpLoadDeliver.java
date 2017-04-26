package com.sunesoft.lemon.deanery.deliverables.application.dto;

import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.fr.results.UniqueResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ReadExcel;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Created by MJ003 on 2016/10/14.
 */
public class UpLoadDeliver   extends ReadExcel<FormDeliverAplyExportDto> {
    @Override
    protected UniqueResult<FormDeliverAplyExportDto> convertRow(HSSFRow row, int rowNum, int colNum) {
        try {
            FormDeliverAplyExportDto dto = new FormDeliverAplyExportDto();
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(0)))){
                dto.setProjectNo(getCellFormatValue(row.getCell(0)));
            }
/*            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(1)))) {
               // System.out.print(getCellFormatValue(row.getCell(1)));
                String no=getCellFormatValue(row.getCell(1)).replace(".0","");
                dto.setProjectPlan_No(Long.parseLong(no));
            }*/
            if(!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(1)))){
                dto.setProjectName(getCellFormatValue(row.getCell(1)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(2)))) {
                dto.setAssumeCompany(getCellFormatValue(row.getCell(2)));
            }
/*            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(3)))) {
                dto.setProjectPlan_Type(getCellFormatValue(row.getCell(3)));
            }*/
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(3)))) {
                dto.setLeader(Long.parseLong(getCellFormatValue(row.getCell(3))));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(4)))) {
                dto.setGroupMembers(getCellFormatValue(row.getCell(4)));
            }

            return new UniqueResult<FormDeliverAplyExportDto>(dto);
        }catch (Exception ex){
            return new UniqueResult<FormDeliverAplyExportDto>("行"+rowNum+",数据解析错误，请检查！");
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
        if(row.getLastCellNum()!=5)
            return false;
        return true;
    }

}
