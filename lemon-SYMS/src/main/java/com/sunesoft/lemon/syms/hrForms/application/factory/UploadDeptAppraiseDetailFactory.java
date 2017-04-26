package com.sunesoft.lemon.syms.hrForms.application.factory;

import com.sunesoft.lemon.fr.results.UniqueResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.fr.utils.excel.ReadExcel;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEmpAppraiseDto;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Created by zhouz on 2016/8/15.
 */
public class UploadDeptAppraiseDetailFactory extends ReadExcel<DeptAppraiseDetailDto> {
    @Override
    protected UniqueResult<DeptAppraiseDetailDto> convertRow(HSSFRow row, int rowNum, int colNum) {
        String value;
        try {
            DeptAppraiseDetailDto dto = new DeptAppraiseDetailDto();
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(0))))
                dto.setId(Long.parseLong(getCellFormatValue(row.getCell(0))));
            dto.setDeptName(getCellFormatValue(row.getCell(1)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(2))))
                dto.setDeptId(Long.parseLong(getCellFormatValue(row.getCell(2))));
            dto.setDeptLevel(getCellFormatValue(row.getCell(3)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(4))))
                dto.setDeptScores(Float.parseFloat(getCellFormatValue(row.getCell(4))));
            dto.setDeptGrade(getCellFormatValue(row.getCell(5)));
//            dto.setDeptRemark(getCellFormatValue(row.getCell(6)));
            dto.setGroupLevel(getCellFormatValue(row.getCell(7)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(8))))
                dto.setGroupScores(Float.parseFloat(getCellFormatValue(row.getCell(8))));
            dto.setGroupGrade(getCellFormatValue(row.getCell(9)));
            dto.setGroupRemark(getCellFormatValue(row.getCell(10)));

            return new UniqueResult<DeptAppraiseDetailDto>(dto);
        }catch (Exception ex){
            return new UniqueResult<DeptAppraiseDetailDto>("行"+rowNum+",数据解析错误，请检查！");
        }
    }

    /**
     * 判断表格是否正确
     * @param row 表头信息(列名)
     * @return Boolean
     */
    protected Boolean checkExcelCol(HSSFRow row) {

        //可判断指定列名是否一致等，如返回false 则会报格式不正确的错误
        if(row.getLastCellNum()!=11)
            return false;
        return true;
    }
}
