package com.sunesoft.lemon.syms.hrForms.application.factory;

import com.sunesoft.lemon.fr.results.UniqueResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ReadExcel;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpAppraiseDetailDto;
import org.apache.poi.hssf.usermodel.HSSFRow;

import javax.annotation.processing.RoundEnvironment;

/**
 * Created by liulin on 2016/8/15.
 */
public class UploadEmpAppraiseDetailFactory extends ReadExcel<EmpAppraiseDetailDto> {
    @Override
    protected UniqueResult<EmpAppraiseDetailDto> convertRow(HSSFRow row, int rowNum, int colNum) {
        String value;
        try {
            EmpAppraiseDetailDto dto = new EmpAppraiseDetailDto();
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(0))))
                dto.setLoginName(getCellFormatValue(row.getCell(0)));
            dto.setEmpSelfGrade(getCellFormatValue(row.getCell(1)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(2))))
                dto.setDeptScores(Float.parseFloat(getCellFormatValue(row.getCell(2))));
            dto.setDeptGrade(getCellFormatValue(row.getCell(3)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(4))))
                dto.setGroupScores(Float.parseFloat(getCellFormatValue(row.getCell(4))));
            dto.setGroupGrade(getCellFormatValue(row.getCell(5)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(6))))
                dto.setChargeLeaderScores(getCellFormatValue(row.getCell(6)));
            dto.setChargeLeaderLevel(getCellFormatValue(row.getCell(7)));


            return new UniqueResult<EmpAppraiseDetailDto>(dto);
        }catch (Exception ex){
            return new UniqueResult<EmpAppraiseDetailDto>("行"+rowNum+",数据解析错误，请检查！");
        }
    }

    /**
     * 判断表格是否正确
     * @param row 表头信息(列名)
     * @return Boolean
     */
    protected Boolean checkExcelCol(HSSFRow row) {

        //可判断指定列名是否一致等，如返回false 则会报格式不正确的错误
        if(row.getLastCellNum()!=8)
            return false;
        return true;
    }
}
