package com.sunesoft.lemon.deanery.project.application.factory;

import com.sunesoft.lemon.deanery.project.application.ProjectMainApplyService;
import com.sunesoft.lemon.deanery.project.application.ScientificResearchProjectService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangwj on 2016/8/4 0004.
 */
@Component
public class ProjectMainApplySelector implements FormServiceSelector {
    private final String formKind = "SYY_MAIN_LC01";
    @Autowired
    private ProjectMainApplyService projectMainApplyService;
    @Override
    public FormService getService(String formkind) {
        if(formKind.equals(formkind)){
            return projectMainApplyService;
        }
        return null;
    }

    @Override
    public Boolean checkFormKind(String formKind) {
        return this.formKind.equals(formKind);
    }
}
