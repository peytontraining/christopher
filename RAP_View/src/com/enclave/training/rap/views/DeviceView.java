package com.enclave.training.rap.views;

import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.christopher.jpa.entitis.Device;
import com.christopher.jpa.entitis.Version;
import com.enclave.training.rap.utils.ImageUtil;

public class DeviceView extends ViewPart {
    public static final String VIEW_ID = "com.demo.views.deviceView";

    private TableViewer viewer;
    private TableFilter filter;
    private TableFinder tableFinder;

    private Button bFilter;

    @Override
    public void createPartControl(Composite parent) {

        GridLayout layout = new GridLayout(2, false);
        parent.setLayout(layout);
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout searchLayout = new GridLayout(8, false);
        composite.setLayout(searchLayout);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        Label filterLabel = new Label(composite, SWT.NONE);
        filterLabel.setText("Filter: ");

        final Text filterText = new Text(composite, SWT.BORDER | SWT.SEARCH);
        filterText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        bFilter = new Button(composite, SWT.NONE);
        bFilter.setImage(PlatformUI.getWorkbench().getSharedImages()
                .getImage(ISharedImages.IMG_ETOOL_CLEAR));
        bFilter.setEnabled(false);

        Label blank = new Label(composite, SWT.NONE);
        blank.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label searchLabel = new Label(composite, SWT.NONE);
        searchLabel.setText("Find: ");

        final Text findText = new Text(composite, SWT.BORDER | SWT.SEARCH);
        findText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Button bFindDown = new Button(composite, SWT.NONE);
        bFindDown.setImage(ImageUtil.IMG_ORROW_DOWN);
        Button bFindUp = new Button(composite, SWT.NONE);
        bFindUp.setImage(ImageUtil.IMG_ORROW_UP);

        viewer = new TableViewer(parent, SWT.BORDER);
        viewer.setContentProvider(new ViewContentProvider());
        viewer.setLabelProvider(new ViewLabelProvider());
        final Table table = viewer.getTable();
        viewer.setColumnProperties(initColumn(table));
        viewer.getTable().setHeaderVisible(true);
        viewer.getTable().setLinesVisible(true);

        GridData gridData = new GridData();
        gridData.verticalAlignment = GridData.FILL;
        gridData.horizontalSpan = 2;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;

        viewer.getControl().setLayoutData(gridData);

        createSelected();

        filterText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent event) {
                String searchTxt = filterText.getText();
                filter.setSearchText(searchTxt);
                viewer.refresh();

                if ("".equals(searchTxt)) {
                    bFilter.setEnabled(false);
                } else {
                    bFilter.setEnabled(true);
                }
            }
        });

        bFilter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                filterText.setText("");
                filter.setSearchText(filterText.getText());
                viewer.refresh();
                bFilter.setEnabled(false);
            }
        });

        // Add modify listener to find text box.
        findText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent event) {
                tableFinder.setFindText(findText.getText());
                tableFinder.find();
            }
        });

        bFindDown.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                tableFinder.findNext();
            }
            
        });

        bFindUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                tableFinder.findBack();
            }
        });

        tableFinder = new TableFinder(viewer.getTable());
        filter = new TableFilter();
        viewer.addFilter(filter);
        
    }

    @Override
    public void setFocus() {
        viewer.getTable().setFocus();
    }

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
                        Version version = (Version) element;
                        viewer.setInput(version);
                        viewer.refresh();
                    }
                }
            }
        });

    }

    // Private method
    private String[] initColumn(Table table) {
        String[] result = new String[5];

        TableColumn name = new TableColumn(table, SWT.BORDER);
        name.setText("Name");
        name.setWidth(250);
        result[0] = "Name";

        TableColumn appModule = new TableColumn(table, SWT.NONE);
        appModule.setText("App Module");
        appModule.setWidth(150);
        result[1] = "App Module";

        TableColumn deviceType = new TableColumn(table, SWT.NONE);
        deviceType.setText("Device Type");
        deviceType.setWidth(150);
        result[2] = "Device Type";

        TableColumn pLocation = new TableColumn(table, SWT.NONE);
        pLocation.setText("Physical Location");
        pLocation.setWidth(200);
        result[3] = "Physical Location";

        TableColumn manufacturer = new TableColumn(table, SWT.NONE);
        manufacturer.setText("Manufacturer");
        manufacturer.setWidth(150);
        result[4] = "Manufacturer";

        return result;
    }

    // Private class

    private class ViewContentProvider implements IStructuredContentProvider {

        @Override
        public void dispose() {

        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

        }

        @Override
        public Object[] getElements(Object inputElement) {
            List<Device> list = ((Version) inputElement).getDevices();
            return list.toArray();
        }

    }

    private class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            Device device = (Device) element;
            if (columnIndex == 0) {
                if ("DVD/NVR".equals(device.getDeviceType())) {
                    return ImageUtil.IMG_CAMERA;
                }

                if ("IP Cameras".equals(device.getDeviceType())) {
                    return ImageUtil.IMG_CAMERA_IP;
                }

                if ("Fireplance Controller".equals(device.getDeviceType())) {
                    return ImageUtil.IMG_F_CONTROLLER;
                }
            }

            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {
            if (columnIndex == 0) {
                return ((Device) element).getName();
            } else if (columnIndex == 1) {
                return ((Device) element).getAppModule();
            } else if (columnIndex == 2) {
                return ((Device) element).getDeviceType();
            } else if (columnIndex == 3) {
                return ((Device) element).getPhysicalLocation();
            } else {
                return ((Device) element).getManutacturer();
            }
        }

    }

    private class TableFilter extends ViewerFilter {
        private String searchText;

        public void setSearchText(String searchText) {
            StringBuilder builder = new StringBuilder();
            this.searchText = builder.append(".*").append(searchText).append(".*").toString();

        }

        @Override
        public boolean select(Viewer viewer, Object parentElement, Object element) {
            if (searchText == null || searchText.length() == 0) {
                return true;
            } else {
                Device device = (Device) element;
                if (device.getName() != null && device.getName().matches(searchText)
                        || device.getAppModule() != null
                        && device.getAppModule().matches(searchText)
                        || device.getDeviceType() != null
                        && device.getDeviceType().matches(searchText)
                        || device.getPhysicalLocation() != null
                        && device.getPhysicalLocation().matches(searchText)
                        || device.getManutacturer() != null
                        && device.getManutacturer().matches(searchText)) {

                    return true;
                }

                return false;
            }
        }

    }

    private class TableFinder {
        private String findText;
        private int selected;
        private Table table;

        public TableFinder(Table table) {
            selected = -1;
            this.table = table;
        }

        public void setFindText(String findText) {
            StringBuilder builder = new StringBuilder();
            this.findText = builder.append(".*").append(findText).append(".*").toString();
        }

        public void find() {
            if (table.getItemCount() <= 0 || ".*.*".equals(findText)) {
                table.deselectAll();
                return;
            }

            Device device;
            int temp = -1;
            do {
                temp++;
                if(temp >= table.getItemCount()) break;
                device = (Device) table.getItem(temp).getData();
            } while (!isMatch(device));

            if (temp < table.getItemCount()) {
                selected = temp;
                table.setSelection(selected);
            } else {
                table.deselect(selected);
            }
        }

        public void findNext() {
            if (table.getItemCount() <= 0 || ".*.*".equals(findText)) {
                table.deselectAll();
                return;
            }

            Device device;
            int temp = selected;
            do {
                temp++;
                if (temp >= table.getItemCount())
                    temp = 0;
                device = (Device) table.getItem(temp).getData();
            } while (!isMatch(device));

            if (selected != temp && temp < table.getItemCount()) {
                selected = temp;
                table.setSelection(selected);
            }
        }

        public void findBack() {
            if (table.getItemCount() <= 0 || ".*.*".equals(findText)) {
                table.deselectAll();
                return;
            }
            
            Device device;
            int temp;

            temp = selected;
            do {
                temp--;
                if (temp < 0)
                    temp = table.getItemCount() - 1;
                device = (Device) table.getItem(temp).getData();
            } while (!isMatch(device));

            if (selected != temp && temp < table.getItemCount()) {
                selected = temp;
                table.setSelection(selected);
            }
        }

        private boolean isMatch(Device device) {
            return device.getName() != null && device.getName().matches(findText)
                    || device.getAppModule() != null && device.getAppModule().matches(findText)
                    || device.getDeviceType() != null && device.getDeviceType().matches(findText)
                    || device.getPhysicalLocation() != null
                    && device.getPhysicalLocation().matches(findText)
                    || device.getManutacturer() != null
                    && device.getManutacturer().matches(findText);
        }
    }
}
