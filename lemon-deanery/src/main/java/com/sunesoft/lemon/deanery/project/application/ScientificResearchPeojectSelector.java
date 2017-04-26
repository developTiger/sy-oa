package com.sunesoft.lemon.deanery.project.application;

import com.sunesoft.lemon.deanery.project.application.ScientificResearchProjectService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangwj on 2016/8/4 0004.
 */
@Component
public class ScientificResearchPeojectSelector implements FormServiceSelector {
    private final String formKind = "SYY_KG_LC01";
    @Autowired
    private ScientificResearchProjectService scientificResearchProjectService;
    @Override
    public FormService getService(String formkind) {
        if(formKind.equals(formkind)){
            return scientificResearchProjectService;
        }
        return null;
    }

    @Override
    public Boolean checkFormKind(String formKind) {
        return this.formKind.equals(formKind);
    }
}
