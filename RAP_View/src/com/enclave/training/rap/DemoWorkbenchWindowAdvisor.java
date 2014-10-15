package com.enclave.training.rap;

import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class DemoWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public DemoWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    @Override
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setShellStyle(SWT.NO_TRIM);
        configurer.setShowCoolBar(true);
        configurer.setShowMenuBar(false);
        configurer.setShowStatusLine(true);
        configurer.setShowProgressIndicator(true);
        configurer.setShowPerspectiveBar(true);

        IPersistentPreferenceStore preferenceStore = (IPersistentPreferenceStore) PlatformUI
                .getPreferenceStore();
        preferenceStore.setValue(
                IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR,
                 false);

        preferenceStore.setValue(
                IWorkbenchPreferenceConstants.SHOW_OTHER_IN_PERSPECTIVE_MENU,
                 false);

        preferenceStore.setValue(
                IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR,
                 true);

        preferenceStore.setValue(
                        IWorkbenchPreferenceConstants.PERSPECTIVE_BAR_EXTRAS,
                        "com.demo.perspective.perspective, com.demo.perspective.perspective1, com.demo.perspective.perspective2, com.demo.perspective.perspective3, com.demo.perspective.perspective4");
    }

    @Override
    public void postWindowOpen() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        window.getShell().setFullScreen(true);
    }

}
