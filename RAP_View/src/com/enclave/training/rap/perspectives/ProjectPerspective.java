package com.enclave.training.rap.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.enclave.training.rap.views.DeviceView;
import com.enclave.training.rap.views.FormView;
import com.enclave.training.rap.views.TreeView;

public class ProjectPerspective implements IPerspectiveFactory {

    @Override
    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);

        IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.35f, editorArea);

        layout.addStandaloneView(FormView.VIEW_ID, false, IPageLayout.BOTTOM, 0.7f, "topLeft");

        IFolderLayout topRight = layout
                .createFolder("topRight", IPageLayout.TOP, 0.65f, editorArea);

        IFolderLayout bottomRight = layout.createFolder("bottomRight", IPageLayout.BOTTOM, 0.3f,
                editorArea);

        // set view
        topLeft.addView(TreeView.TREE_VIEW);
        topLeft.addView("com.demo.views.formView4");
        topLeft.addView("com.demo.views.formView5");
        layout.getViewLayout(TreeView.TREE_VIEW).setCloseable(false);
        layout.getViewLayout("com.demo.views.formView4").setCloseable(false);
        layout.getViewLayout("com.demo.views.formView5").setCloseable(false);

        topRight.addView(DeviceView.VIEW_ID);
        topRight.addView("com.demo.views.formView1");
        topRight.addView("com.demo.views.formView2");
        topRight.addView("com.demo.views.formView3");

        layout.getViewLayout(DeviceView.VIEW_ID).setCloseable(false);
        layout.getViewLayout("com.demo.views.formView1").setCloseable(false);
        layout.getViewLayout("com.demo.views.formView2").setCloseable(false);
        layout.getViewLayout("com.demo.views.formView3").setCloseable(false);

//        layout.addPerspectiveShortcut("com.demo.perspective.perspective1");
//        layout.addPerspectiveShortcut("com.demo.perspective.perspective2");
//        layout.addPerspectiveShortcut("com.demo.perspective.perspective3");
//        layout.addPerspectiveShortcut("com.demo.perspective.perspective4");
    }

}
