package com.sunesoft.lemon.deanery.resultCertificate.application.factory;

import com.sunesoft.lemon.deanery.ReceptionFlow.application.dtos.ReceptionDto;
import com.sunesoft.lemon.deanery.ReceptionFlow.domain.ReceptionNB;
import com.sunesoft.lemon.deanery.car.application.CompanyService;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.CompanyDto;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.car.domain.CompanyRepository;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.deanery.carWorkFlow.application.dtos.CarApplyDto;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverApplyDto;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApply;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.deanery.patentCG.domain.Patent;
import com.sunesoft.lemon.deanery.prizewinner.application.dtos.PrizewinnerDto;
import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;
import com.sunesoft.lemon.deanery.project.application.dtos.ProjectMainApplyDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto1;
import com.sunesoft.lemon.deanery.project.domain.ProjectMainApply;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectCG.application.dtos.ProjectResultDto;
import com.sunesoft.lemon.deanery.projectCG.domain.ProjectResult;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseFileDto;
import com.sunesoft.lemon.deanery.treatiseCG.domain.Treatise;
import com.sunesoft.lemon.deanery.treatiseCG.domain.TreatiseFile;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by pxj on 2016/9/28
 */
public class DeaneryUtil {

    public static <S, T> T convert(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
