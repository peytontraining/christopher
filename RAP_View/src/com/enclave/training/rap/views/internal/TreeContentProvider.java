package com.enclave.training.rap.views.internal;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.christopher.jpa.entitis.Customer;
import com.christopher.jpa.entitis.Project;

public class TreeContentProvider implements ITreeContentProvider {
    private final Object[] EMPTY_ARRAY = new Object[0];

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    }

    @Override
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof List) {
            return ((List) inputElement).toArray();
        }
        return EMPTY_ARRAY;
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof Customer && hasChildren(parentElement)) {
            return ((Customer) parentElement).getProjects().toArray();
        }

        if (parentElement instanceof Project && hasChildren(parentElement)) {
            return ((Project) parentElement).getVersions().toArray();
        }

        return EMPTY_ARRAY;
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof Customer && ((Customer) element).getProjects().size() > 0) {
            return true;
        }

        if (element instanceof Project && ((Project) element).getVersions().size() > 0) {
            return true;
        }
        return false;
    }
}
