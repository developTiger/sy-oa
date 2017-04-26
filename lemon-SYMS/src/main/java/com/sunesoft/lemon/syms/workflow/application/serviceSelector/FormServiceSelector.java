package com.sunesoft.lemon.syms.workflow.application.serviceSelector;

/**
 * Created by zhouz on 2016/6/29.
 */
public interface FormServiceSelector {

    public FormService getService(String formkind);


    public Boolean checkFormKind(String formKind);

}
