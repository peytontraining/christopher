package com.enclave.training.rap.handles;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.christopher.jpa.entitis.Device;
import com.christopher.jpa.entitis.Project;
import com.christopher.jpa.entitis.Version;
import com.christopher.jpa.util.DataUpdater;
import com.enclave.training.rap.views.TreeView;

public class SaveAsVersionHandle extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        TreeView treeView = (TreeView) window.getActivePage().findView(TreeView.TREE_VIEW);

        ISelectionService selection = window.getSelectionService();
        if (!selection.getSelection().isEmpty()) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection.getSelection();
            Object element = structuredSelection.getFirstElement();
            if (element != null) {
                if (element instanceof Version) {
                    Version version = (Version) element;
                    Project project = version.getProject();
                    InputDialog dialog = new InputDialog(window.getShell(),
                            "Save As", "Enter name to clone new version",
                            version.getName(),
                            new IInputValidator() {

                                @Override
                                public String isValid(String newText) {
                                    return null;
                                }
                            });
                    if (dialog.OK == dialog.open()) {
                        Version newVersion = new Version();
                        newVersion.setName(dialog.getValue());
                        newVersion.setProject(project);
                        List<Device> devices = new ArrayList<Device>();
                        for (Device device : version.getDevices()) {
                            device.setId(0);
                            device.setVersion(newVersion);
                            devices.add(device);
                        }
                        newVersion.setDevices(devices);
                        newVersion.setTargetVersion(version.getTargetVersion());
                        newVersion.setDeploySource(version.getDeploySource());

                        project.getVersions().add(newVersion);

                        // Insert new version to DB
                        DataUpdater.insert(newVersion);

                        treeView.treeViewer.refresh(project);
                    }
                }
            }
        }
        return null;
    }
}
