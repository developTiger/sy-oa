//package com.sunesoft.lemon.deanery.ReceptionFlow.application;
//
//import com.sunesoft.lemon.deanery.FormFlow.domain.CountersignRepository;
//import com.sunesoft.lemon.deanery.ReceptionFlow.application.dtos.ReceptionDto;
//import com.sunesoft.lemon.deanery.ReceptionFlow.domain.*;
//import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
//import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
//import com.sunesoft.lemon.fr.utils.BeanUtils;
//import org.hibernate.Criteria;
//import org.hibernate.criterion.Restrictions;
//import org.snaker.engine.SnakerEngine;
//import org.snaker.engine.access.QueryFilter;
//import org.snaker.engine.entity.Order;
//import org.snaker.engine.entity.Process;
//import org.snaker.engine.entity.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
///**
// * Created by wangwj on 2016/8/2 0002.
// */
//@Service(value = "receptionService")
//public class ReceptionServiceImpl  extends GenericHibernateFinder implements ReceptionService {
//    @Autowired
//    private ReceptionRepository receptionRepository;
//    @Autowired
//    private AccommodationReposiroty accommodationReposiroty;
//    @Autowired
//    private WorkingLunchRepository workingLunchRepository;
//    @Autowired
//    private BanquetRepository banquetRepository;
//    @Autowired
//    private CountersignRepository countersignRepository;
//    @Autowired
//    private SnakerEngine snakerEngine;
//
//    @Override
//    public Long save(ReceptionDto dto) {
//        Order order = snakerEngine.startInstanceById(dto.getProcessId());
////        Order order = snakerEngine.startInstanceById("52949cfb67704959b4513e1037036ec2");
//        List<Task> tasks = snakerEngine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
//        List<Task> newTasks = new ArrayList<Task>();
//        String orderid = null;
//        if(tasks != null && tasks.size() > 0) {
//            Task task = tasks.get(0);
//            newTasks.addAll(snakerEngine.executeTask(task.getId()));
//            orderid = task.getOrderId();
//        }
//        ReceptionNB receptionNB=new ReceptionNB();
//        BeanUtils.copyProperties(dto,receptionNB);
//        receptionNB.setOrderId(order.getId());
//        receptionNB.setFlow_status(0);
//        Long l= receptionRepository.save(receptionNB);
//        return l;
//    }
//
//
//    @Override
//    public Long apply(ReceptionDto dto) {
//
//
////        List<Task> tasks = snakerEngine.task().createNewTask(taskId, TaskModel.TaskType.Major.ordinal(), actors);
////        snakerEngine.task().complete(taskId, operator);
//        return 0L;
//    }
//
//
//    @Override
//    public String  getApplyUrl(String Id){
//        Process process = snakerEngine.process().getProcessById(Id);
//        return process.getInstanceUrl();
//    }
//
//    @Override
//    public  Long dean(ReceptionDto dto,int choice){
//        Long l=UpdateByDto(dto);
//        Map<String,Object> args = new HashMap<String, Object>();
//        if(choice==0){
//            args.put("choose","mainLD");
//        }else if(choice==1){
//            args.put("choose","fgleader");
//        }
//        args.put("processId",dto.getProcessId());
//        args.put("taskId",dto.getTaskId());
//        args.put("orderId",dto.getOrderId());
//        snakerEngine.executeTask(dto.getTaskId(),null,args);
//        return l;
//    }
//
//    @Override
//    public Long secondary(ReceptionDto dto,int choice){
//        Long l=UpdateByDto(dto);
//        if(choice==0){
//            snakerEngine.executeTask(dto.getTaskId());
//        }
//        return l;
//    }
//
//    @Override
//    public Long mainApply(ReceptionDto dto,int choice){
//        Long l=UpdateByDto(dto);
//        Map<String,Object> args = new HashMap<String, Object>();
//        if(choice==0){
//            args.put("result","agree");
//        }else if(choice==1){
//            args.put("result","unagree");
//        }
//        snakerEngine.executeTask(dto.getTaskId(),null,args);
//        return l;
//    }
//    @Override
//    public Long dean2(ReceptionDto dto){
//        Long l=UpdateByDto(dto);
//        snakerEngine.executeTask(dto.getTaskId());
//        return l;
//    }
//    @Override
//    public Long confirm(ReceptionDto dto){
//        ReceptionNB receptionNB=receptionRepository.get(dto.getId());
//        BeanUtils.copyProperties(dto,receptionNB);
//        receptionNB.setFlow_status(3);
//        Long l=receptionRepository.save(receptionNB);
//        snakerEngine.executeTask(dto.getTaskId());
//        return l;
//    }
//
//    @Override
//    public ReceptionDto getById(String orderId){
//        Criteria criteria = getSession().createCriteria(ReceptionNB.class);
//        criteria.add(Restrictions.eq("orderId",orderId));
//        List<ReceptionNB> list=criteria.list();
//        if(list!=null && list.size()>0){
//            ReceptionDto dto=new ReceptionDto();
//            BeanUtils.copyProperties(list.get(0),dto);
//            return dto;
//        }else {
//            return null;
//        }
//    }
//
//
//    public Long UpdateByDto(ReceptionDto dto){
//        ReceptionNB receptionNB=receptionRepository.get(dto.getId());
//        BeanUtils.copyProperties(dto,receptionNB);
//        Long l=receptionRepository.save(receptionNB);
//        return l;
//    }
//}
