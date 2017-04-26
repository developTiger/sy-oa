package com.sunesoft.lemon.deanery.projectCG.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.projectCG.domain.ProjectResult;
import com.sunesoft.lemon.deanery.projectCG.domain.ProjectResultRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 * 实现项目成果数据仓库类
 */
@Service("projectResultRepository")
public class ProjectResultRepositoryImpl
        extends GenericHibernateRepository<ProjectResult,Long>
        implements ProjectResultRepository{
    /**
     * 项目成果统计查询
     * 目前根据获奖级别、获奖等级、是否合作成果统计
     * win_level 获奖级别
     * win_grade 获奖等级
     * is_cooperate_result 是否合作成果
     * project_result_num 项目成果数量
     * @return
     */
    @Override
    public List<Map<String, Object>> projectCGReport() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT t.win_level,t.win_grade,t.is_cooperate_result,count(1) project_result_num FROM syy_oa_project_result t where t.IS_ACTIVE = 1 GROUP BY t.win_level,t.win_grade,t.is_cooperate_result");
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
}
