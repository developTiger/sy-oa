package com.sunesoft.lemon.deanery.car.application.static_common;

import java.util.*;

/**
 * Created by xubo on 2016/6/20 0020.
 */
public class static_common {

    public final static List<String> DRIVER_TYPE =
            Arrays.asList("A1","A2","A3","B1","B2","C1","C2","C3","C4","D","E","F","M","N","P");

    /**
     * 专业类别
     * 01油气勘探
     * 02油气开发
     * 03炼油化工
     * 04油气集输
     * 05计 算 机
     * 06软 科 学
     * 07安全环保
     */
    public final static Map<String,String> SPECIALTYTYPE = new HashMap<String,String>();


    /**
     * 项目类型
     * 1：项目，2：课题，3：专题
     */
    public final static Map<String,String> PROJECTTYPE = new HashMap<String,String>();

    /**
     * 获奖成果分类
     */
    public final static Map<String,List<String>> WIN_RESULT_CLASSIFY =
            new HashMap<String,List<String>>();

    /**
     * 获奖级别
     */
    public final static List<String> WIN_LEVEL = new
            ArrayList<String>();

    public final static List<String> PATENT_TYPE = new
            ArrayList<String>();
    /**
     * 初始化参数
     */
    static{
        SPECIALTYTYPE.put("01","油气勘探");
        SPECIALTYTYPE.put("02","油气开发");
        SPECIALTYTYPE.put("03","炼油化工");
        SPECIALTYTYPE.put("04","油气集输");
        SPECIALTYTYPE.put("05","计 算 机");
        SPECIALTYTYPE.put("06","软 科 学");
        SPECIALTYTYPE.put("07","安全环保");

        PROJECTTYPE.put("1","项目");
        PROJECTTYPE.put("2","课题");
        PROJECTTYPE.put("3","专题");

        /**
         * 初始化内容
         */
        List<String> small_class_1 = new ArrayList<String>();
        List<String> small_class_2 = new ArrayList<String>();
        List<String> small_class_3 = new ArrayList<String>();

        small_class_1.add("国家最高科学技术奖");
        small_class_1.add("自然科学奖");
        small_class_1.add("技术发明奖");
        small_class_1.add("科技进步奖");
        small_class_1.add("股份公司及油田公司科技进步奖");
        small_class_1.add("股份公司及油田公司科技术创新奖");
        WIN_RESULT_CLASSIFY.put("Ⅰ",small_class_1);
        small_class_1 = null;

        small_class_2.add("规划奖");
        small_class_2.add("设计奖");
        small_class_2.add("成果奖");
        small_class_2.add("储量奖");
        small_class_2.add("技术革新奖");
        small_class_2.add("技术改进奖");
        WIN_RESULT_CLASSIFY.put("Ⅱ",small_class_2);
        small_class_2 = null;

        small_class_3.clear();
        small_class_3.add("现代化管理成果奖");
        small_class_3.add("QC成果奖");
        small_class_3.add("五小成果奖");
        small_class_3.add("青年创新创效奖");
        WIN_RESULT_CLASSIFY.put("Ⅲ",small_class_3);
        small_class_3 = null;

        WIN_LEVEL.add("国家级");
        WIN_LEVEL.add("省部级");
        WIN_LEVEL.add("油田公司级");
        WIN_LEVEL.add("厂处级");

        PATENT_TYPE.add("发明");
        PATENT_TYPE.add("实用新型");
        PATENT_TYPE.add("外观");

    }


}
