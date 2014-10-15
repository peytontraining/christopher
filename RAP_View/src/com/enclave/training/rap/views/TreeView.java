package com.enclave.training.rap.views;

import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.christopher.jpa.entitis.Customer;
import com.christopher.jpa.util.DataSlecter;
import com.enclave.training.rap.views.internal.TreeContentProvider;
import com.enclave.training.rap.views.internal.TreeLabelProvider;

public class TreeView extends ViewPart {
    public final static String TREE_VIEW = "com.demo.views.treeView";
    private TreeViewer treeViewer;
    private IPropertySheetPage propertyPage;
    private FilteredTree filter;
    private MenuManager menuManager;

    @Override
    public void createPartControl(final Composite parent) {
        GridLayout layout = new GridLayout();
        parent.setLayout(layout);
        filter = new FilteredTree(parent, SWT.MULTI | SWT.V_SCROLL, new PatternFilter(), true);
        treeViewer = filter.getViewer();
        treeViewer.setContentProvider(new TreeContentProvider());
        treeViewer.setLabelProvider(new TreeLabelProvider());
        
        List<Customer> customers = DataSlecter.getList("customer.getAll", Customer.class);
        
        treeViewer.setInput(customers);
        getSite().setSelectionProvider(treeViewer);
        
        menuManager = new MenuManager();
        treeViewer.getControl().setMenu(menuManager.createContextMenu(treeViewer.getControl()));
        getSite().registerContextMenu(menuManager, treeViewer);
    }

    @Override
    public void setFocus() {
    }

    @Override
    public Object getAdapter(Class adapter) {
        Object result = super.getAdapter(adapter);
        if (adapter == IPropertySheetPage.class) {
            if (propertyPage == null) {
                propertyPage = new PropertySheetPage();
            }
            result = propertyPage;
        }
        return result;
    }
    
    //########################################################################
    //##           Private Method                                          ###
    //########################################################################
}
