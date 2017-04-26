package com.sunesoft.lemon.syms.workflow.application.serviceSelector;


import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhouz on 2016/6/29.
 */
@Service("formKindManagerService")
public class FormKindManagerServiceImpl implements FormKindManagerService {
    @Resource
    List<FormServiceSelector> formServiceSelectorList;

    @Override
    public FormService getFormService(String formKind) {
        for (FormServiceSelector selector : formServiceSelectorList) {
            if (selector.checkFormKind(formKind)) {
                FormService service = selector.getService(formKind);
                if (service != null)
                    // System.out.println(selector.getService(formKind));
                    return service;
            }
        }
        return null;
    }

}
