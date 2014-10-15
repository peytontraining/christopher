package com.enclave.training.rap.views;

import java.text.SimpleDateFormat;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import com.christopher.jpa.entitis.Version;
import com.christopher.jpa.util.DataUpdater;
import com.enclave.training.rap.utils.ImageUtil;

public class FormView extends ViewPart {
    public static String VIEW_ID = "com.demo.views.formView";

    private Text tVersion;
    private Text tProject;
    private Text tDeployTime;
    private Text tDeploySource;
    private Text tSaveTime;
    private Text tTargetVersion;

    private FormToolkit toolkit;
    private ScrolledForm form;
    
    Version version;

    Composite composite;
    ToolBar toolBar;
    ToolItem item;

    @Override
    public void createPartControl(Composite parent) {
        composite = parent;
        createSelected();
        createForm(parent);
        
        item = new ToolItem(toolBar, SWT.PUSH);
        item.setImage(ImageUtil.IMG_SAVE);
        item.setEnabled(false);
        item.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(version != null) {
                    version.setName(tVersion.getText());
                    DataUpdater.update(version);
                    item.setEnabled(false);
                }
            }
        });
        item.setToolTipText("Save(Ctrl+S)");
        
        //Listen modify action
        tVersion.addModifyListener(new ModifyListener() {
            
            @Override
            public void modifyText(ModifyEvent event) {
                item.setEnabled(true);
            }
        });
    }

    @Override
    public void setFocus() {

    }

    // Private method

    private void createSelected() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        final IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        ISelectionService selectionService = window.getSelectionService();
        selectionService.addSelectionListener(new ISelectionListener() {

            @Override
            public void selectionChanged(IWorkbenchPart part, ISelection selection) {
                IStructuredSelection structuredSelection = (IStructuredSelection) selection;
                Object element = structuredSelection.getFirstElement();
                if (element != null) {
                    if (element instanceof Version) {
                        version = (Version) element;
                        tVersion.setText("" + version.getName());
                        ;
                        tProject.setText("" + version.getProject().getName());
                        tDeploySource.setText("" + version.getDeploySource());
                        tTargetVersion.setText("" + version.getTargetVersion());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.S");
                        tSaveTime.setText(dateFormat.format(version.getDeployTime()));
                        tDeployTime.setText(dateFormat.format(version.getSaveTime()));
                        item.setEnabled(false);
                    }
                }
            }
        });
    }

    private void createForm(final Composite parent) {
        Composite composite = new Composite(parent, SWT.BORDER);
        composite.setLayout(new FillLayout());
        toolkit = new FormToolkit(composite.getDisplay());
        form = toolkit.createScrolledForm(composite);
        form.getBody().setLayout(new FillLayout());
//        Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR
//                | Section.DESCRIPTION);
        
        Section section = new Section(form.getBody(), Section.TITLE_BAR | Section.DESCRIPTION);
        section.setText("Version Properties");

        Composite content = new Composite(section, SWT.NONE);
        content.setLayout(new GridLayout(2, false));
        content.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        ;

        Label versionLabel = toolkit.createLabel(content, "Version: ");
        versionLabel.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
        tVersion = toolkit.createText(content, "");
        tVersion.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label projectlabel = toolkit.createLabel(content, "Project: ");
        projectlabel.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
        tProject = toolkit.createText(content, "");
        tProject.setEnabled(false);
        tProject.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label dTimeLabel = toolkit.createLabel(content, "Deploy Time: ");
        dTimeLabel.setData(RWT.MARKUP_ENABLED, true);
        tDeployTime = toolkit.createText(content, "");
        tDeployTime.setEnabled(false);
        tDeployTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label dSourchLable = toolkit.createLabel(content, "Deploy Source: ");
        dSourchLable.setData(RWT.MARKUP_ENABLED, true);
        tDeploySource = toolkit.createText(content, "");
        tDeploySource.setEnabled(false);
        tDeploySource.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label saveTimeLabel = toolkit.createLabel(content, "Save Time: ");
        saveTimeLabel.setData(RWT.MARKUP_ENABLED, true);
        tSaveTime = toolkit.createText(content, "");
        tSaveTime.setEnabled(false);
        tSaveTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label targetVersion = toolkit.createLabel(content, "Target Version: ");
        targetVersion.setData(RWT.MARKUP_ENABLED, true);
        tTargetVersion = toolkit.createText(content, "");
        tTargetVersion.setEnabled(false);
        tTargetVersion.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        toolBar = new ToolBar(section, SWT.NONE);
        section.setTextClient(toolBar);
        section.setClient(content);
        
    }

}
