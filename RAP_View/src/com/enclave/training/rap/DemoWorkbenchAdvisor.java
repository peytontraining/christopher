package com.enclave.training.rap;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class DemoWorkbenchAdvisor extends WorkbenchAdvisor {

    @Override
    public String getInitialWindowPerspectiveId() {
        return "com.demo.perspective.perspective";
    }

    @Override
    public void initialize(IWorkbenchConfigurer configurer) {
        super.initialize(configurer);
    }

    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
            final IWorkbenchWindowConfigurer windowConfigurer) {
        return new DemoWorkbenchWindowAdvisor(windowConfigurer);
    }

}
