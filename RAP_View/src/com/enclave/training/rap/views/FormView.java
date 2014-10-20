package com.enclave.training.rap.views;

import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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

import com.christopher.jpa.entitis.Project;
import com.christopher.jpa.entitis.Version;
import com.christopher.jpa.util.DataUpdater;
import com.enclave.training.rap.utils.ImageUtil;

public class FormView extends ViewPart {
    public static String VIEW_ID = "com.demo.views.formView";

    // Version properties
    private Text tVersion;
    private Text tProject;
    private Text tDeployTime;
    private Text tDeploySource;
    private Text tSaveTime;
    private Text tTargetVersion;

    // Project properties
    private Text tProjectName;
    private Button bGUses;
    private Text tGHost;
    private Combo bGPort;
    private Text tGUuid;
    private Combo cLicense;
    private Button bIsBlockDeploy;
    private Text tNotes;

    private FormToolkit toolkit;
    private ScrolledForm form;

    private Version version;
    private Project project;

    private Composite versionProperties;
    private Composite projectProperties;
    private ToolBar toolBar;
    private ToolItem saveTool;

    private TreeView treeView;

    @Override
    public void createPartControl(final Composite parent) {
        treeView = (TreeView) PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow()
                .getActivePage().findView(TreeView.TREE_VIEW);

        parent.setLayout(new StackLayout());
        createSelected();
        createVersionProperties(parent);
        createProjectProperties(parent);

        saveTool = new ToolItem(toolBar, SWT.PUSH);
        saveTool.setImage(ImageUtil.IMG_SAVE);
        saveTool.setEnabled(false);
        saveTool.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (version != null) {
                    version.setName(tVersion.getText());
                    version = DataUpdater.update(version);
                    treeView.treeViewer.refresh(version.getProject());
                }

                if (project != null) {
                    project.setName(tProjectName.getText());
                    project = DataUpdater.update(project);

                    treeView.treeViewer.refresh(project);
                }
                saveTool.setEnabled(false);
            }
        });
        saveTool.setToolTipText("Save(Ctrl+S)");

        // Listen modify action
        tVersion.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent event) {
                saveTool.setEnabled(true);
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
            public void selectionChanged(IWorkbenchPart part,
                    ISelection selection) {
                versionProperties.setVisible(false);
                projectProperties.setVisible(false);
                IStructuredSelection structuredSelection = (IStructuredSelection) selection;
                Object element = structuredSelection.getFirstElement();
                if (element != null) {
                    if (element instanceof Version) {
                        project = null;
                        version = (Version) element;
                        tVersion.setText("" + version.getName());
                        tProject.setText("" + version.getProject().getName());
                        tDeploySource.setText("" + version.getDeploySource());
                        tTargetVersion.setText("" + version.getTargetVersion());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.S");
                        if (version.getDeployTime() != null)
                            tSaveTime.setText(dateFormat.format(version.getDeployTime()));
                        if (version.getSaveTime() != null)
                            tDeployTime.setText(dateFormat.format(version.getSaveTime()));
                        saveTool.setEnabled(false);
                        versionProperties.setVisible(true);
                    } else if (element instanceof Project) {
                        version = null;
                        projectProperties.setVisible(true);
                        project = (Project) element;
                        tProjectName.setText(project.getName());
                    }
                }
            }
        });
    }

    /**
     * createVersionProperties
     * <p>
     * Create section for display version properties. Default this section
     * visible is false.
     * </p>
     * 
     * @param parent
     */
    private void createVersionProperties(final Composite parent) {
        versionProperties = new Composite(parent, SWT.BORDER);
        versionProperties.setLayout(new FillLayout());
        toolkit = new FormToolkit(versionProperties.getDisplay());
        form = toolkit.createScrolledForm(versionProperties);
        form.getBody().setLayout(new FillLayout());

        Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.COMPACT);
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
        versionProperties.setVisible(false);
    }

    /**
     * createProjectProperties
     * <p>
     * Create section for display project properties. Default this section
     * visible is false.
     * </p>
     * 
     * @param parent
     */
    private void createProjectProperties(final Composite parent) {
        projectProperties = new Composite(parent, SWT.BORDER);
        projectProperties.setLayout(new FillLayout());
        toolkit = new FormToolkit(projectProperties.getDisplay());
        form = toolkit.createScrolledForm(projectProperties);
        form.getBody().setLayout(new FillLayout());

        Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.COMPACT);
        section.setText("Project Properties");

        Composite content = new Composite(section, SWT.NONE);
        content.setLayout(new GridLayout(2, false));
        content.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        toolkit.createLabel(content, "Name: ");
        tProjectName = toolkit.createText(content, "");
        tProjectName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        toolkit.createLabel(content, "Gateway uses: ");
        Composite groupRadio = new Composite(content, SWT.NONE);
        groupRadio.setLayout(new FillLayout());
        bGUses = toolkit.createButton(groupRadio, "UUID", SWT.RADIO);
        bGUses = toolkit.createButton(groupRadio, "Host/Port", SWT.RADIO);

        toolkit.createLabel(content, "Gateway Host: ");
        tGHost = toolkit.createText(content, "");
        tGHost.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        toolkit.createLabel(content, "Gateway Port: ");
        bGPort = new Combo(content, SWT.READ_ONLY);
        bGPort.add("8080");
        bGPort.select(0);
        bGPort.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        toolkit.createLabel(content, "Gateway UUID: ");
        tGUuid = toolkit.createText(content, "");
        tGUuid.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        toolkit.createLabel(content, "License: ");
        cLicense = new Combo(content, SWT.READ_ONLY);
        cLicense.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        bIsBlockDeploy = toolkit.createButton(content, "Deployment locked", SWT.CHECK);
        toolkit.createLabel(content, "");

        tNotes = toolkit.createText(content, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
        tNotes.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
        tNotes.setEditable(false);

        toolBar = new ToolBar(section, SWT.NONE);
        section.setTextClient(toolBar);
        section.setClient(content);

        projectHandleModifyEvent();
    }

    /**
     * projectHandleModifyEvent Add modify event to text box, combo box and
     * button.
     */
    private void projectHandleModifyEvent() {
        tProjectName.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent event) {
                if (!saveTool.isEnabled())
                    saveTool.setEnabled(true);
            }
        });

        bGUses.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!saveTool.isEnabled())
                    saveTool.setEnabled(true);
            }
        });

        tGHost.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent event) {
                if (!saveTool.isEnabled())
                    saveTool.setEnabled(true);
            }
        });

        tGUuid.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent event) {
                if (!saveTool.isEnabled())
                    saveTool.setEnabled(true);
            }
        });
    }
}
