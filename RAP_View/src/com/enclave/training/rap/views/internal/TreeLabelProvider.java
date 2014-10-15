package com.enclave.training.rap.views.internal;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.christopher.jpa.entitis.Customer;
import com.christopher.jpa.entitis.Device;
import com.christopher.jpa.entitis.Project;
import com.christopher.jpa.entitis.Version;
import com.enclave.training.rap.utils.ImageUtil;

public class TreeLabelProvider extends LabelProvider {
    public Image getImage(Object element) {
        if (element instanceof Customer) {
            return ImageUtil.IMG_CUSTOMER;
        } else if (element instanceof Project) {
            return ImageUtil.IMG_PROJECT;
        }

        if (element instanceof Version && ((Version) element).isDeploy()) {
            return ImageUtil.IMG_VERSION_DEPLOY;
        }

        return ImageUtil.IMG_VESION;
    }

    @Override
    public String getText(Object element) {
        if (element instanceof Customer) {
            return ((Customer) element).getName();
        } else if (element instanceof Project) {
            return ((Project) element).getName();
        } else if (element instanceof Version) {
            return ((Version) element).getName();
        }
        return ((Device) element).getName();

    }
}