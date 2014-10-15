package com.enclave.training.rap.utils;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ImageUtil {

    // Images
    public static final Image IMG_CUSTOMER = createImage("customer.png");
    public static final Image IMG_PROJECT = createImage("project.png");
    public static final Image IMG_VESION = createImage("version.png");
    public static final Image IMG_VERSION_DEPLOY = createImage("last_deployed_version.png");
    public static final Image IMG_ORROW_UP = createImage("arrow_up.png");
    public static final Image IMG_ORROW_DOWN = createImage("arrow_down.png");
    public static final Image IMG_CAMERA = createImage("camera.png");
    public static final Image IMG_CAMERA_IP = createImage("cctv.png");
    public static final Image IMG_F_CONTROLLER = createImage("fireplace-controller.png");

    // Icons
    public final static Image IMG_SAVE = createIcon("save.png");
    
    // private parameter
    private static final String BUNDLE_NAME = "RAP_View";
    private static final String IMG_FOLDER = "/icons/images/";
    private static final String ICON_FOLDER = "/icons/";


    private static Image createImage(String name) {
        ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(BUNDLE_NAME, IMG_FOLDER + name);
        return descriptor.createImage();
    }

    private static Image createIcon(String name) {
        ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(BUNDLE_NAME, ICON_FOLDER + name);
        return descriptor.createImage();
    }
    
    public static ImageDescriptor createDesIcon(String imageName) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(BUNDLE_NAME, ICON_FOLDER + imageName);
    }
}
