package com.sunesoft.lemon.deanery.prizewinner.application;

import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.prizewinner.application.dtos.PrizewinnerDto;
import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;
import com.sunesoft.lemon.deanery.prizewinner.domain.PrizewinnerRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
@Service(value = "prizewinnerService")
public class PrizewinnerServiceImpl extends GenericHibernateRepository<Prizewinner,Long> implements PrizewinnerService {
    @Autowired
    private PrizewinnerRepository prizewinnerRepository;

    @Override
    public List<PrizewinnerDto> getPrizeWinner(Long cgId,String cgName) {

        List<Prizewinner> prizewinners =   prizewinnerRepository.getPrizewinnerByCGName(cgId,cgName);

        List<PrizewinnerDto> listPd = new ArrayList<>();

        for (Prizewinner p : prizewinners){

           listPd.add(DeaneryUtil.convertFormListPrizeWinnerDto(p));

        }

        return listPd;
    }


}