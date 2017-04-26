package com.sunesoft.lemon.deanery.car.application.factory;

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
import com.sunesoft.lemon.deanery.productionData.ProductionDate;
import com.sunesoft.lemon.deanery.productionData.ProductionDateDto1;
import com.sunesoft.lemon.deanery.project.application.dtos.ProjectMainApplyDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto1;
import com.sunesoft.lemon.deanery.project.domain.ProjectMainApply;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectAchievement.application.dtos.ProjectAchievementDto2;
import com.sunesoft.lemon.deanery.projectAchievement.domain.ProjectAchievement;
import com.sunesoft.lemon.deanery.projectCG.application.dtos.ProjectResultDto;
import com.sunesoft.lemon.deanery.projectCG.domain.ProjectResult;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto1;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseFileDto;
import com.sunesoft.lemon.deanery.treatiseCG.domain.Treatise;
import com.sunesoft.lemon.deanery.treatiseCG.domain.TreatiseFile;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.descriptor.java.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by xubo on 2016/6/17 0017.
 */
public class DeaneryUtil {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    public static ScientificResearchProjectDto convertFromListScientificResearchProjectDto(ScientificResearchProject scientificResearchProject){
        ScientificResearchProjectDto scientificResearchProjectDto = new ScientificResearchProjectDto();

        BeanUtils.copyProperties(scientificResearchProject,scientificResearchProjectDto);
        scientificResearchProjectDto.setYear(scientificResearchProject.getNiandu());
        scientificResearchProjectDto.setBeginTime1(DateHelper.formatDate(scientificResearchProject.getBeginTime()));
        scientificResearchProjectDto.setEndTime1(DateHelper.formatDate(scientificResearchProject.getEndTime()));
        scientificResearchProjectDto.setLeaderName(scientificResearchProject.getLeaderName());
          List ss=  scientificResearchProject.getScientficApproveFileList();
        if (ss.size()>0) {
            scientificResearchProjectDto.setFileApproveList(ss);
        }
        List tt=  scientificResearchProject.getScientficProjectFileList();
        if (tt.size()>0) {
            scientificResearchProjectDto.setFileList(tt);
        }
            if( !StringUtils.isNullOrWhiteSpace(new String(scientificResearchProject.getProjectPlanInfoTxt()) ) ){
                try {
                    scientificResearchProjectDto.setProjectPlanInfo(new String(scientificResearchProject.getProjectPlanInfoTxt(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return scientificResearchProjectDto;
    }
    //项目信息库点头转换
    public static ScientificRPKUDto convertFromListScientificResearchProjectDto(ScientificRPKU scientificRPKU){
        ScientificRPKUDto scientificRPKUDto = new ScientificRPKUDto();

        BeanUtils.copyProperties(scientificRPKU,scientificRPKUDto);
        scientificRPKUDto.setYear(scientificRPKU.getNiandu());
        scientificRPKUDto.setYear_Str(DateHelper.formatDate(scientificRPKU.getNiandu(),"yyyy"));
        scientificRPKUDto.setBeginTime1(DateHelper.formatDate(scientificRPKU.getBeginTime(),"yyyy-MM-dd"));
        scientificRPKUDto.setEndTime1(DateHelper.formatDate(scientificRPKU.getEndTime(),"yyyy-MM-dd"));
        scientificRPKUDto.setLeaderName(scientificRPKU.getLeaderName());

        if( !StringUtils.isNullOrWhiteSpace(new String(scientificRPKU.getProjectPlanInfoTxt()) ) ){
            try {
                scientificRPKUDto.setProjectPlanInfo(new String(scientificRPKU.getProjectPlanInfoTxt(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return scientificRPKUDto;
    }

    //转换
    public static ScientificRPKUDto1 convertFromListScientificResearchProjectDto1(ScientificRPKU scientificRPKU){
        ScientificRPKUDto1 scientificRPKUDto1 = new ScientificRPKUDto1();
        scientificRPKUDto1.setProjectName(scientificRPKU.getProjectName());
        scientificRPKUDto1.setSpecialtyType(scientificRPKU.getSpecialtyType());
        scientificRPKUDto1.setProjectType(scientificRPKU.getProjectType());
        scientificRPKUDto1.setAssumeCompany(scientificRPKU.getAssumeCompany());
        scientificRPKUDto1.setJoinComopany(scientificRPKU.getJoinComopany());
        scientificRPKUDto1.setBeginTime(DateHelper.formatDate(scientificRPKU.getBeginTime(), "yyyy-MM-dd"));
        scientificRPKUDto1.setEndTime(DateHelper.formatDate(scientificRPKU.getEndTime(), "yyyy-MM-dd"));
        scientificRPKUDto1.setLeaderName(scientificRPKU.getLeaderName());
        scientificRPKUDto1.setRemark(scientificRPKU.getRemark());
        if( !StringUtils.isNullOrWhiteSpace(new String(scientificRPKU.getProjectPlanInfoTxt()) ) ){
            try {
                scientificRPKUDto1.setProjectPlanInfo(new String(scientificRPKU.getProjectPlanInfoTxt(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return scientificRPKUDto1;
    }

    public static <S, T> T convert(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static ScientificResearchProjectDto1 convertFromListScientificResearchProjectDto1(ScientificResearchProject scientificResearchProject){
        ScientificResearchProjectDto1 scientificResearchProjectDto1 = new ScientificResearchProjectDto1();
        scientificResearchProjectDto1.setProjectNo(scientificResearchProject.getProjectNo());
        scientificResearchProjectDto1.setProjectName(scientificResearchProject.getProjectName());
       /* scientificResearchProjectDto1.setSpecialtyType(scientificResearchProject.getSpecialtyType());*/
        try {
            if(scientificResearchProject.getProjectPlanInfoTxt()!=null && !scientificResearchProject.getProjectPlanInfoTxt().equals("")){
                scientificResearchProjectDto1.setProjectPlanInfo(new String(scientificResearchProject.getProjectPlanInfoTxt(),"UTF-8"));
            }
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        scientificResearchProjectDto1.setAssumeCompany(scientificResearchProject.getAssumeCompany());
        scientificResearchProjectDto1.setJoinComopany(scientificResearchProject.getJoinComopany());
        scientificResearchProjectDto1.setBeginTime(DateHelper.formatDate(scientificResearchProject.getBeginTime(),"yyyy-MM-dd"));
        scientificResearchProjectDto1.setEndTime(DateHelper.formatDate(scientificResearchProject.getEndTime(),"yyyy-MM-dd"));
        scientificResearchProjectDto1.setRemark(scientificResearchProject.getRemark());
     /*   scientificResearchProjectDto1.setProjectType(scientificResearchProject.getProjectType().toString());*/
        scientificResearchProjectDto1.setLeader(scientificResearchProject.getLeader().toString());
        scientificResearchProjectDto1.setDeputy(scientificResearchProject.getDeputy().toString());
       /* scientificResearchProjectDto1.setGroupMembersName(scientificResearchProject.getGroupMembers());
        scientificResearchProjectDto1.setProjectStatus(scientificResearchProject.getProjectStatus());*/
       /* scientificResearchProjectDto1.setDeptId(String.valueOf(scientificResearchProject.getDeptId()));*/
      /*  scientificResearchProjectDto1.setId(scientificResearchProject.getId().toString());*/
        /* scientificResearchProjectDto1.setFormNo(scientificResearchProject.getFormNo().toString());*/
      /*  scientificResearchProjectDto1.setApplyer(scientificResearchProject.getApplyer().toString());*/
   /*     scientificResearchProjectDto1.setApplyDate(DateHelper.formatDate(scientificResearchProject.getApplyDate(),"yyyy-MM-dd"));*/
     /*   scientificResearchProjectDto1.setFormStatus(scientificResearchProject.getFormStatus().name());*/
        return scientificResearchProjectDto1;
    }

    public static ProductionDateDto1 convertFromListProductionDateDto1(ProductionDate productionDate){
        ProductionDateDto1 productionDateDto1 = new ProductionDateDto1();

        BeanUtils.copyProperties(productionDate,productionDateDto1);
        Date BGFSSJ=productionDate.getBGFSSJ1() ;
        Date JSRQ=productionDate.getJSRQ1();
        Date SYRQ=productionDate.getSYRQ1();
        Date BCRQ=productionDate.getBCRQ1();
        productionDateDto1.setBGFSSJ( DateHelper.formatDate(BGFSSJ,"yyyy-MM-dd"));
        productionDateDto1.setJSRQ(DateHelper.formatDate(JSRQ, "yyyy-MM-dd"));
        productionDateDto1.setSYRQ(DateHelper.formatDate(SYRQ, "yyyy-MM-dd"));
        productionDateDto1.setBCRQ(DateHelper.formatDate(BCRQ, "yyyy-MM-dd"));
        return productionDateDto1;
    }
    //设置FormDeliver
    public static FormDeliverApplyDto convertFromFormDeliverApplyDto(FormDeliverApply formDeliverApply){
        FormDeliverApplyDto formDeliverApplyDto1=new FormDeliverApplyDto();
        formDeliverApplyDto1.setFormNo(formDeliverApply.getFormNo());
        formDeliverApplyDto1.setFormKind(formDeliverApply.getFormKind());
        formDeliverApplyDto1.setFormKindName(formDeliverApply.getFormKindName());

//        formDeliverApplyDto1.setCategory(formDeliverApply.getCategory());
//        formDeliverApplyDto1.setAchievements(formDeliverApply.getAchievements());
//        formDeliverApplyDto1.setCategoryPro(formDeliverApply.getCategoryPro());
//        formDeliverApplyDto1.setCompany(formDeliverApply.getCompany());
//        formDeliverApplyDto1.setCooperate(formDeliverApply.getCooperate());
//        formDeliverApplyDto1.setOpenEndTime(formDeliverApply.getOpenEndTime());
//        formDeliverApplyDto1.setSummery(formDeliverApply.getSummery());
//        formDeliverApplyDto1.setSubWords(formDeliverApply.getSubWords());
//        formDeliverApplyDto1.setBriefly(formDeliverApply.getBriefly());
//        formDeliverApplyDto1.setOpinion1(formDeliverApply.getOpinion1());
//        formDeliverApplyDto1.setOpinion2(formDeliverApply.getOpinion2());
        return formDeliverApplyDto1;
    }

    public static ProjectAchievementDto2 convertFromProjectAchievementDto2(ProjectAchievement projectAchievement){
        ProjectAchievementDto2 projectAchievementDto2=new ProjectAchievementDto2();
        BeanUtils.copyProperties(projectAchievement,projectAchievementDto2);
        Date niandu =  projectAchievement.getNiandu();
        SimpleDateFormat s= new SimpleDateFormat("yyyy");
        SimpleDateFormat s1= new SimpleDateFormat("yyyy-MM-dd");
        String nianduSting= s.format(niandu);
        projectAchievementDto2.setYear(nianduSting);
        projectAchievementDto2.setAwardTime(s1.format(projectAchievement.getAwardDate()));
        projectAchievementDto2.setDeliverBeginTime(s1.format(projectAchievement.getBeginDate()));
        projectAchievementDto2.setDeliverEndTime(s1.format(projectAchievement.getEndDate()));
        return projectAchievementDto2;
    }



    public static ScientificResearchProject convertFromListScientificResearchProjectDto(ScientificResearchProjectDto scientificResearchProjectDto){

        ScientificResearchProject scientificResearchProject = new ScientificResearchProject();
        scientificResearchProject.setLeaderName(scientificResearchProjectDto.getLeaderName());
        scientificResearchProject.setNiandu(scientificResearchProjectDto.getYear());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        String bD=scientificResearchProjectDto.getBeginTime1();
        String eD=scientificResearchProjectDto.getEndTime1();
        try{
            Date date=sdf.parse(bD);
            Date date2=sdf.parse(eD);
            scientificResearchProject.setBeginTime(date);
            scientificResearchProject.setEndTime(date2);
        }catch (Exception e){
            e.printStackTrace();
        }
        BeanUtils.copyProperties(scientificResearchProjectDto,scientificResearchProject);
       if( !StringUtils.isNullOrWhiteSpace(scientificResearchProjectDto.getProjectPlanInfo())) {
           scientificResearchProject.setProjectPlanInfoTxt(scientificResearchProjectDto.getProjectPlanInfo().getBytes(Charset.forName("UTF-8")));
       }

        return scientificResearchProject;
    }

    public static ProjectMainApply convertFromListProjectMainApplyDto(ProjectMainApplyDto projectMainApplyDto){

        ProjectMainApply projectMainApply = new ProjectMainApply();
           projectMainApply.setMainContent(projectMainApplyDto.getMainContent());
           projectMainApply.setMainBeginDate(projectMainApplyDto.getMainBeginDate());
           projectMainApply.setTitle(projectMainApplyDto.getTitle());
          //SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
           BeanUtils.copyProperties(projectMainApplyDto,projectMainApply);
           return projectMainApply;
    }


    public static TreatiseDto converFromListTreatiseDto(Treatise treatise){
        TreatiseDto treatiseDto = new TreatiseDto();
        BeanUtils.copyProperties(treatise,treatiseDto);
        treatiseDto.setPublish_Time_(DateHelper.formatDate(treatise.getPublish_Time(),"yyyy-MM-dd"));
        return treatiseDto;
    }
    public static Treatise converFromListTreatiseDto(TreatiseDto treatiseDto){
        Treatise treatise = new Treatise();
        BeanUtils.copyProperties(treatiseDto,treatise);
        treatise.setPublish_Time(DateHelper.parse(treatiseDto.getPublish_Time_(),"yyyy-MM-dd"));
        return treatise;
    }
    public static Treatise treatiseDtoToTreatise(TreatiseDto treatiseDto){
        Treatise treatise = new Treatise();
        BeanUtils.copyProperties(treatiseDto,treatise);
        return treatise;
    }

    public static TreatiseFile converFromListTreatiseFileDto(TreatiseFileDto treatiseFileDto){
        TreatiseFile treatiseFile = new TreatiseFile();
        BeanUtils.copyProperties(treatiseFileDto,treatiseFile);
        //treatise.setPublish_Time(DateHelper.parse(treatiseDto.getPublish_Time_(),"yyyy-MM-dd"));
        return treatiseFile;
    }
    public static TreatiseFile converFromListToTreatiseFileDto(TreatiseFile treatiseFile){
        TreatiseFileDto treatiseFileDto = new TreatiseFileDto();
        BeanUtils.copyProperties(treatiseFile,treatiseFileDto);
        //treatise.setPublish_Time(DateHelper.parse(treatiseDto.getPublish_Time_(),"yyyy-MM-dd"));
        return treatiseFile;
    }

    public static PatentDto converFromListPatentDto(Patent patent){
        PatentDto patentDto = new PatentDto();
        BeanUtils.copyProperties(patent,patentDto);
        return patentDto;
    }

    public static Patent converFromListPatentDto(PatentDto patentDto) {
        Patent patent = new Patent();
        BeanUtils.copyProperties(patentDto, patent);
        return patent;
    }

    public static ProjectResultDto converFromListProjectResultDto(ProjectResult project){
        ProjectResultDto projectResultDto = new ProjectResultDto();
        BeanUtils.copyProperties(project,projectResultDto);
//        Boolean f = project.getIs_Cooperate_Result();
      /*  if ( null == f  ){
            projectResultDto.setIs_Cooperate_Result_Str(false);
        }else{
            projectResultDto.setIs_Cooperate_Result_Str(f);
        }*/
        return projectResultDto;
    }

    public static ProjectResult converFromListProjectResultDto(ProjectResultDto projectResultDto){
        ProjectResult projectResult = new ProjectResult();
        BeanUtils.copyProperties(projectResultDto,projectResult);
        return projectResult;
    }

    public static PrizewinnerDto convertFormListPrizeWinnerDto(Prizewinner p){
        PrizewinnerDto dto = new PrizewinnerDto();
        BeanUtils.copyProperties(p,dto);
        return dto;
    }
    //把司机DTO类型转换成司机数据类型
    public static  Prizewinner convertFormListPrizeWinnerDto(PrizewinnerDto dto){
        Prizewinner prizewinner = new Prizewinner();
        BeanUtils.copyProperties(dto, prizewinner);
        return prizewinner;
    }

    //把司机类型转换成司机DTO数据类型
    public static DriverDto convertFormListDriverDto(Driver driver){
        DriverDto dto = new DriverDto();
        BeanUtils.copyProperties(driver,dto);
        return dto;
    }
    //把司机DTO类型转换成司机数据类型
    public  Driver convertFormListDriverDto(DriverDto dto){
        Driver driver = new Driver();
        BeanUtils.copyProperties(dto, driver);
        return driver;
    }

    public static CarDto converFormListCatDto(Car car){
        CarDto dto = new CarDto();
        /**
         * 车辆状态 1：待命，2：出车，3：维修，4：停运
         */
        switch (car.getStatus()){
            case 1:dto.setStatusName("待命");
                break;
            case 2:dto.setStatusName("出车");
                break;
            case 3:dto.setStatusName("维护保养");
                break;
            case 4:dto.setStatusName("停运");
                break;
        }
        BeanUtils.copyProperties(car,dto);
        try {
            dto.setRepairLogOld(new String(car.getRepairLogTxt(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            return dto;
        }
    }

    public static Car convertFormListCarrDto(CarDto dto){
        Car car = new Car();
        BeanUtils.copyProperties(dto, car);

        car.setRepairLogTxt(dto.getRepairLog().getBytes(Charset.forName("UTF-8")));
        return car;
    }

    public static CompanyDto convertFormListCompanyDto(Company company){
        CompanyDto companyDto = new CompanyDto();
        BeanUtils.copyProperties(company,companyDto );
        return companyDto;
    }

    public static Company convertFormListCompanyDto(CompanyDto companyDto){
        Company company = new Company();
        BeanUtils.copyProperties(companyDto,company);
        return company;
    }

    public static Driver converDtoDriver(DriverDto dto){
        Driver driver = new Driver();
        BeanUtils.copyProperties(dto,driver);
        return driver;
    }

    /**
     * 返回流程DTO数据
     * @param carApply
     * @return
     */
    public static CarApplyDto converCarApplyDto(CarApply carApply){
        CarApplyDto carApplyDto = new CarApplyDto();
        BeanUtils.copyProperties(carApply,carApplyDto);
        return carApplyDto;
    }

    /**
     *
     * @param carApplyDto
     * @return
     */
    public static CarApply converDtoCarApply(CarApplyDto carApplyDto){
        CarApply carApply = new CarApply();
        if ( null != carApplyDto.getGoStartDate()){
            carApply.setGoStartTime(DateHelper.parse(carApplyDto.getGoStartDate(),"yyyy-MM-dd HH:mm:ss"));
        }
        if ( null != carApplyDto.getGoArriveDate()){
            carApply.setGoArriveTime(DateHelper.parse(carApplyDto.getGoArriveDate(),"yyyy-MM-dd HH:mm:ss"));
        }
        if ( null != carApplyDto.getPredictGoArriveDate()){
            carApply.setPredictGoArriveTime(DateHelper.parse(carApplyDto.getPredictGoArriveDate(),"yyyy-MM-dd HH:mm:ss"));
        }
        if ( null != carApplyDto.getPredictGoStartDate()){
            carApply.setPredictGoStartTime(DateHelper.parse(carApplyDto.getPredictGoStartDate(),"yyyy-MM-dd HH:mm:ss"));
        }
        BeanUtils.copyProperties(carApplyDto,carApply);
        return carApply;
    }

    public static ReceptionDto converReceptionNB(ReceptionNB receptionNB){
        ReceptionDto receptionDto = new ReceptionDto();
        BeanUtils.copyProperties(receptionNB,receptionDto);
        return receptionDto;
    }

    public static ReceptionNB converDtoReception(ReceptionDto receptionDto){
        ReceptionNB receptionNB = new ReceptionNB();
        BeanUtils.copyProperties(receptionDto,receptionNB);
        return receptionNB;
    }

    /**
     * request参数封装
     */
    public static Map<String,Object> converToMap(HttpServletRequest request){
        Map<String,Object> params = new HashMap<String,Object>();
        Enumeration<String> paraNames = request.getParameterNames();
        while (paraNames.hasMoreElements()) {
            String element = paraNames.nextElement();
            int index = element.indexOf("_");
            String paraValue = request.getParameter(element);
            if(index == -1) {
                params.put(element, paraValue);
            } else {
                char type = element.charAt(0);
                String name = element.substring(index + 1);
                Object value = null;
                switch(type) {
                    case 'S':
                        value = paraValue;
                        break;
                    case 'I':
                        value = Integer.valueOf(paraValue);
                        break;
                    case 'L':
                        value = Long.valueOf(paraValue);
                        break;
                    case 'B':
                        value = Boolean.valueOf(paraValue);
                        break;
                    case 'D':
                        value = DateHelper.parse(paraValue,"yyyy-MM-dd HH:mm:ss");
                        break;
                    case 'N':
                        value = Double.valueOf(paraValue);
                        break;
                    default:
                        value = paraValue;
                        break;
                }
                params.put(name, value);
            }
        }
        return params;
    }
}
