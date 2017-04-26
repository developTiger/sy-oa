package com.sunesoft.lemon.deanery.project.application.factory;

import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto1;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.UniqueResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ReadExcel;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by zy on 2016/8/15.
 */
public class UploadProjectApplyDetailFactory extends ReadExcel<ScientificResearchProjectDto1> {
    @Override
    protected UniqueResult<ScientificResearchProjectDto1> convertRow(HSSFRow row, int rowNum, int colNum) {
        String value;
        try {
            ScientificResearchProjectDto1 dto = new ScientificResearchProjectDto1();
        /*  if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(0)))) {
                dto.setId(getCellFormatValue(row.getCell(0)));
            }*/
           /* if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(1)))) {
                dto.setFormNo(getCellFormatValue(row.getCell(1)));
            }*/
          /*  if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(1)))) {
                dto.setApplyer(getCellFormatValue(row.getCell(1)));
            }*/
           /* if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(1)))) {
                dto.setProjectStatus(getCellFormatValue(row.getCell(1)));
            }*/
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(0)))) {
                dto.setProjectNo(getCellFormatValue(row.getCell(0)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(1)))) {
                dto.setProjectName(getCellFormatValue(row.getCell(1)));
            }
           /* if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(6)))) {
                dto.setSpecialtyType(getCellFormatValue(row.getCell(6)));
            }*/
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(2)))) {
                dto.setProjectPlanInfo(getCellFormatValue(row.getCell(2)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(3)))) {
                dto.setAssumeCompany(getCellFormatValue(row.getCell(3)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(4)))) {
                dto.setJoinComopany(getCellFormatValue(row.getCell(4)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(5)))) {
                dto.setBeginTime(getCellFormatValue(row.getCell(5)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(6)))) {
                dto.setEndTime(getCellFormatValue(row.getCell(6)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(7)))) {
                dto.setRemark(getCellFormatValue(row.getCell(7)));
            }
           /* if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(13)))) {
                dto.setProjectType(getCellFormatValue(row.getCell(13)));
            }*/
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(8)))) {
                dto.setLeader(getCellFormatValue(row.getCell(8)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(9)))) {
                dto.setLeaderAdress(getCellFormatValue(row.getCell(9)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(10)))) {
                dto.setDeputy(getCellFormatValue(row.getCell(10)));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(11)))) {
                dto.setDeputyAdress(getCellFormatValue(row.getCell(11)));
            }


          /*  if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(17)))) {
                dto.setDeptId(getCellFormatValue(row.getCell(17)));
            }*/
//            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(19)))) {
//                String deptId=getCellFormatValue(row.getCell(18));
//                Criteria criteria = getSession().createCriteria(Deptment.class);
//                criteria.add(Restrictions.eq("id",deptId));
//                List<Deptment> list=criteria.list();
//                if(list !=null && list.size()>0){
//                    dto.setDeptName(list.get(0).getDeptName());
//                }
//            }
           /* if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(18)))) {
                dto.setFormStatus(getCellFormatValue(row.getCell(18)));
            }*/
//            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(14)))) {
//                dto.setProjectStatus(getCellFormatValue(row.getCell(14)));
//            }
            return new UniqueResult<ScientificResearchProjectDto1>(dto);
        }catch (Exception ex){
            return new UniqueResult<ScientificResearchProjectDto1>("行"+rowNum+",数据解析错误，请检查！");
        }
    }

    /**
     * 判断表格是否正确
     * @param row 表头信息(列名)
     * @return Boolean
     */
    protected Boolean checkExcelCol(HSSFRow row) {

        //可判断指定列名是否一致等，如返回false 则会报格式不正确的错误
        if(row.getLastCellNum()!=12)
            return false;
        return true;
    }
}
