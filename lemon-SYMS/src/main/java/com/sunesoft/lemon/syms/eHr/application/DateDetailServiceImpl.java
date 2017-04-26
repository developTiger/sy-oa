package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.domain.attendance.DateDetail;
import com.sunesoft.lemon.syms.eHr.domain.attendance.DateDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/11/7.
 */
@Service("dateDetailService")
public class DateDetailServiceImpl extends GenericHibernateFinder implements DateDetailService {

    @Autowired
    DateDetailRepository dateDetailRepository;

    @Override
    public CommonResult addDateDetail(DateDetail dateDetail) {
        dateDetailRepository.save(dateDetail);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult updateDateDetail(DateDetail dateDetail) {
        if (dateDetail.getId()!=null){
            DateDetail detail=this.getByid(dateDetail.getId());
            detail.setDescription(dateDetail.getDescription());
            detail.setHolidayType(dateDetail.getHolidayType());
            detail.setrDate(dateDetail.getrDate());
            dateDetailRepository.save(detail);
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public DateDetail getByid(Long id) {
        DateDetail dateDetail=dateDetailRepository.get(id);
        return dateDetail;
    }

    @Override
    public CommonResult deleteByIds(Long id) {
        if (id != null)
            dateDetailRepository.delete(id);
        return ResultFactory.commonSuccess();
    }

    @Override
    public List<DateDetail> getAll() {
        List<DateDetail> list = dateDetailRepository.getAll();
        return list;
    }
}
