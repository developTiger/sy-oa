package com.sunesoft.lemon.ay.equimentForms.application.equipmentFactory;

import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.fr.results.UniqueResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ReadExcel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Created by xiazl on 2016/9/23.
 */
public class UploadEquipment extends ReadExcel<EquipmentDto> {

    @Override
    public UniqueResult<EquipmentDto> convertRow(HSSFRow row, int rowNum, int colNum) {
        try {
            EquipmentDto dto = new EquipmentDto();
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(0)))){
                dto.setMeasuringName(getCellFormatValue(row.getCell(0)).trim());
            }
            if(!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(1)))){
//                if ((getCellFormatValue(row.getCell(1)).trim()).equals("符合"))
//                dto.setTestResult(true);
//                else
//                    dto.setTestResult(false);

              /*  // 定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);*/

                row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);//设置单元格格式为 文本格式


                dto.setPropertyNum(getCellFormatValue(row.getCell(1)));
            }
//            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(2)))) {
////                dto.setTestValue(Double.parseDouble(getCellFormatValue(row.getCell(2))));
//                dto.setPropertyType(getCellFormatValue(row.getCell(2)).trim());
//            }

            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(2)))) {
//                dto.setTestTime(DateHelper.parse(getCellFormatValue(row.getCell(3)),"yyyy-MM-dd"));
                dto.setMeasurement(getCellFormatValue(row.getCell(2)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(3)))) {
//                dto.setNextTestTime(DateHelper.parse(getCellFormatValue(row.getCell(4)), "yyyy-MM-dd"));
                dto.setStandard(getCellFormatValue(row.getCell(3)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(4)))) {
                dto.setFactoryName(getCellFormatValue(row.getCell(4)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(5)))) {
                dto.setOutFactoryNum(getCellFormatValue(row.getCell(5)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(6)))) {
                dto.setNetValue(getCellFormatValue(row.getCell(6)).trim());
            }
//            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(8)))) {
//                dto.setDeposit(getCellFormatValue(row.getCell(8)).trim());
//            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(7)))) {
                dto.setPost(getCellFormatValue(row.getCell(7)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(8)))) {
                dto.setOriginal(getCellFormatValue(row.getCell(8)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(9)))) {
                dto.setNetValue(getCellFormatValue(row.getCell(9)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(10)))) {
                dto.setStandardValue(Double.parseDouble(getCellFormatValue(row.getCell(10))));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(11)))) {
                dto.setMinValue(Double.parseDouble(getCellFormatValue(row.getCell(11))));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(12)))) {
                dto.setMaxValue(Double.parseDouble(getCellFormatValue(row.getCell(12))));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(13)))) {
                dto.setTestValue(Double.parseDouble(getCellFormatValue(row.getCell(13))));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(14)))) {
                dto.setUseStation(getCellFormatValue(row.getCell(14)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(15)))) {
                dto.setUnitName(getCellFormatValue(row.getCell(15)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(16)))) {
                dto.setRemark(getCellFormatValue(row.getCell(16)).trim());
            }

            return new UniqueResult<EquipmentDto>(dto);
        }catch (Exception ex){
            return new UniqueResult<EquipmentDto>("行"+rowNum+",数据解析错误，请检查！");
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
        if(row.getLastCellNum()!=17)
            return false;
        return true;
    }
}
