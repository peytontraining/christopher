package com.enclave.training.rap.handles;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.christopher.jpa.entitis.Customer;
import com.christopher.jpa.entitis.Project;
import com.christopher.jpa.entitis.Version;
import com.enclave.training.rap.views.TreeView;

public class ProjectHandle extends AbstractHandler {

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
                    Customer customer = project.getCustomer();
                    Project newProject = new Project();
                    newProject.setName("UNKNOW");
                    newProject.setCustomer(customer);
                    Version newVersion = new Version();
                    newVersion.setName("1.0.0");
                    newProject.getVersions().add(newVersion);
                    customer.getProjects().add(newProject);
                    treeView.treeViewer.refresh(customer);
                }

                if (element instanceof Project) {
                    Project project = (Project) element;
                    Customer customer = project.getCustomer();
                    Project newProject = new Project();
                    newProject.setName("UNKNOW");
                    newProject.setCustomer(customer);
                    Version newVersion = new Version();
                    newVersion.setName("1.0.0");
                    newProject.getVersions().add(newVersion);
                    customer.getProjects().add(newProject);
                    treeView.treeViewer.refresh(customer);
                }

                if (element instanceof Customer) {
                    Customer customer = (Customer) element;
                    Project newProject = new Project();
                    newProject.setName("UNKNOW");
                    newProject.setCustomer(customer);
                    Version newVersion = new Version();
                    newVersion.setName("1.0.0");
                    newProject.getVersions().add(newVersion);
                    customer.getProjects().add(newProject);
                    treeView.treeViewer.refresh(customer);
                }
            }
        }

        return null;
    }
}
