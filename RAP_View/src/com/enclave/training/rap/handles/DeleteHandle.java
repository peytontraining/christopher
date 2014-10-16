package com.enclave.training.rap.handles;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.christopher.jpa.entitis.Project;
import com.christopher.jpa.entitis.Version;
import com.christopher.jpa.util.DataUpdater;
import com.enclave.training.rap.views.TreeView;

public class DeleteHandle extends AbstractHandler {

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
                    project.getVersions().remove(version);
                    DataUpdater.deleteVersion(version);
                    treeView.treeViewer.refresh(project);
                }
            }
        }

        return null;
    }
}
