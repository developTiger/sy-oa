package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.CompanyCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverReportCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.CompanyDto;
import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.car.domain.CompanyRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangkefan on 2016/6/17 0017.
 */
@Service(value = "companyService")
public class CompanyServiceImpl extends GenericHibernateFinder implements CompanyService{


    @Autowired
    CompanyRepository companyRepository;

    /**
     * 检查公司名称是否有重复
     * @param companyName
     * @return
     */
    @Override
    public boolean checkCompanyName(String companyName) {

        List<Company> list = companyRepository.getAllcoms();

                for(int i = 0 ; i < list.size() ; i++) {
                    /**
                     * 允许公司名为空的情况
                     */
                    if(StringUtils.isNullOrWhiteSpace(companyName)){

                        break;
                    }

                    if (list.get(i).getName() != null &&!list.get(i).getName().equals(companyName)) {
                        continue;
                    }
                    return false;
                }

        return true;
    }

    /**
     * 展示公司信息
     * @param companyCriteria
     * @return
     */
    @Override
    public PagedResult<CompanyDto> getCompany(CompanyCriteria companyCriteria) {
        Criteria criterion = getSession().createCriteria(Company.class);
        criterion.add(Restrictions.eq("isActive", true));
        if(companyCriteria.getName() != null){
            criterion.add(Restrictions.like("name",companyCriteria.getName(), MatchMode.ANYWHERE));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((companyCriteria.getPageNumber() - 1) * companyCriteria.getPageSize()).setMaxResults(companyCriteria.getPageSize());
        List<Company> beans = criterion.list();
        List<CompanyDto> list = new ArrayList<CompanyDto>();
        for (Company company : beans) {
            list.add(DeaneryUtil.convertFormListCompanyDto(company));
        }
        return new PagedResult<CompanyDto>(list, companyCriteria.getPageNumber(), companyCriteria.getPageSize(), totalCount);
    }

    /**
     * 根据ID获取公司信息
     * @param id
     * @return
     */
    @Override
    public CompanyDto getCompany(Long id){
        Company company = companyRepository.get(id);
        return DeaneryUtil.convertFormListCompanyDto(company);
    }

    @Override
    public PagedResult<CompanyDto> getCompany(CompanyDto dto) {
        return null;
    }

    @Override
    public boolean addComanyName(CompanyDto dto) {
        return false;
    }

    @Override
    public boolean delComanyName(Long id) {
        return false;
    }

    @Override
    public boolean delComanyName(CompanyDto dto) {
        return false;
    }
    /**
     * 删除公司信息
     * @param ids
     * @return
     */
    @Override
    public boolean delComanyName(Long[] ids) {

        for(int i = 0 ; i < ids.length ; i++)
        {
             if(ids[i] > 0){

                 Criteria criterion = getSession().createCriteria(Company.class);
                 if (ids!=null&&ids.length>0) {
                     criterion.add(Restrictions.in("id", ids));
                 }
                 List<Company> beans = criterion.list();
                 for (Company bean : beans) {
                     bean.setIsActive(false);
                     companyRepository.save(bean);
                 }

             }
        }

        return true;

    }
    /**
     * 新增或更新公司信息
     * @param companyDto
     * @return
     */
    @Override
    public Long addOrUpdate(CompanyDto companyDto) {
        Long l = 1l;

        if(companyDto!=null){
             l =  companyRepository.save(DeaneryUtil.convertFormListCompanyDto(companyDto));
        }

        return l;
    }

}
