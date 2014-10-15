package com.enclave.training.rap;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;

public class DemoApplication implements IApplication {

    @Override
    public Object start(IApplicationContext context) throws Exception {
        Display display = PlatformUI.createDisplay();
        WorkbenchAdvisor advisor = new DemoWorkbenchAdvisor();
        int result = PlatformUI.createAndRunWorkbench(display, advisor);
        display.close();

        return new Integer(result);
    }

    @Override
    public void stop() {

    }

}
