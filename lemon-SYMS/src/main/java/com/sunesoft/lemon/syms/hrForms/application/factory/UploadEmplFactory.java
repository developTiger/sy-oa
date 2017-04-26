package com.sunesoft.lemon.syms.hrForms.application.factory;

import com.sunesoft.lemon.fr.results.UniqueResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ReadExcel;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpAppraiseDetailDto;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Created by liulin on 2016/8/15.
 */
public class UploadEmplFactory extends ReadExcel<EmpDto> {
    @Override
    protected UniqueResult<EmpDto> convertRow(HSSFRow row, int rowNum, int colNum) {
        String value;
        try {
            EmpDto dto = new EmpDto();
            //loginName	depatName	userName	position	pDescription	phone	mobile	email
            dto.setLoginName(getCellFormatValue(row.getCell(0)));
            dto.setDeptName(getCellFormatValue(row.getCell(1)));
            dto.setName(getCellFormatValue(row.getCell(2)));
            dto.setPosition(getCellFormatValue(row.getCell(3)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(4))))
                dto.setpDescription(getCellFormatValue(row.getCell(4)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(5))))
                dto.setPhone(getCellFormatValue(row.getCell(5)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(6))))
                dto.setMobile(getCellFormatValue(row.getCell(6)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(7))))
                dto.setEmail(getCellFormatValue(row.getCell(7)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(8))))
                dto.setUserNo(getCellFormatValue(row.getCell(8)));
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(9)))){
              String sex =   getCellFormatValue(row.getCell(9));
                if(sex.equals("男"))
                    dto.setSex(1);
                else
                    dto.setSex(0);

            }

            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(10))))
                dto.setNation(getCellFormatValue(row.getCell(10)));

            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(11))))
                dto.setStrBirthday(getCellFormatValue(row.getCell(11)));
           if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(12))))
                 dto.setJoinTime(getCellFormatValue(row.getCell(12)));

//            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(0))))
//                dto.setName(getCellFormatValue(row.getCell(0)));
//            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(2))))
//                dto.setUserNo(getCellFormatValue(row.getCell(2)));
//            dto.setPassword(getCellFormatValue(row.getCell(3)));
//            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(4))))
//                dto.setDeptName(getCellFormatValue(row.getCell(4)));
//            dto.setRoleName(getCellFormatValue(row.getCell(5)));
//            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(6))))
//                dto.setGroupName(getCellFormatValue(row.getCell(6)));
//            dto.setPosition(getCellFormatValue(row.getCell(7)));


            return new UniqueResult<EmpDto>(dto);
        }catch (Exception ex){
            return new UniqueResult<EmpDto>("行"+rowNum+",数据解析错误，请检查！");
        }
    }

    /**
     * 判断表格是否正确
     * @param row 表头信息(列名)
     * @return Boolean
     */
    protected Boolean checkExcelCol(HSSFRow row) {

        //可判断指定列名是否一致等，如返回false 则会报格式不正确的错误
        if(row.getLastCellNum()!=13)
            return false;
        return true;
    }
}
