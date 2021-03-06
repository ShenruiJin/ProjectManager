package com.elle.ProjectManager.presentation;//GEN-LINE:variables

import com.elle.ProjectManager.database.DBConnection;
import com.elle.ProjectManager.database.ModifiedTableData;
import com.elle.ProjectManager.logic.ColumnPopupMenu;
import static com.elle.ProjectManager.logic.ITableConstants.TASKFILES_TABLE_NAME;
//import static com.elle.ProjectManager.logic.ITableConstants.TASKNOTES_TABLE_NAME;
import static com.elle.ProjectManager.logic.ITableConstants.TASKS_TABLE_NAME;
import com.elle.ProjectManager.logic.Tab;
import com.elle.ProjectManager.logic.TableFilter;
import com.elle.ProjectManager.logic.Validator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * AddIssueFileWindow
 *
 * @author Louis W.
 * @author Carlos Igreja
 * @since June 10, 2015
 * @version 0.6.3
 */
public class AddIssueFileWindow extends JFrame {

    // attributes
    private String[] columnNames;
    private int numRowsAdded;           // number of rows added counter
    private Map<String, Tab> tabs;       // used to update the records label
    private Statement statement;

    // components
    private ProjectManagerWindow projectManager;
    private LogWindow logWindow;
    private DefaultTableModel model;

    private Color defaultSelectedBG;

    private ArrayList<Integer> rowsNotEmpty; // only includes rows that have data

//    private PopupWindowInTableCell tableCellPopupWindow;

    private JTable table;

    // used to notify if the tableSelected is editing
    // the tableSelected.isEditing method has issues from the tableModelListener
    private boolean isEditing;

    int lastSelectedRow = -1, lastSelectedColumn = -1;

    /**
     * Creates new form AddRecordsWindow
     */
    public AddIssueFileWindow() {

        rowsNotEmpty = new ArrayList<>();
        isEditing = false;

        projectManager = ProjectManagerWindow.getInstance();
        logWindow = projectManager.getLogWindow();
        tabs = projectManager.getTabs();
        statement = projectManager.getStatement();

        table = new JTable();

//        columnNames = new String[projectManager.getSelectedTable().getColumnCount()];
        // set the selected tableSelected name
        table.setName(projectManager.getSelectedTabName());

        // get default selected bg color
        defaultSelectedBG = table.getSelectionBackground();

        // create a new empty tableSelected
        createEmptyTable();
        // initialize components
        initComponents();

        scrollpane.setPreferredSize(table.getPreferredSize());
        scrollpane.setViewportView(table);

        // sets the keyboard focus manager
        setKeyboardFocusManager(this);

        // add listeners
        addTableListeners(this);

        // submit button does not start enabled because the tableSelected is empty
        btnSubmit.setEnabled(false);

        // set the label header
        this.setTitle("Add Records to " + table.getName());

        Dimension scrollPanelDimension = scrollpane.getPreferredSize();

        // set the size for AddRecord window
        this.setPreferredSize(new Dimension((int) scrollPanelDimension.getWidth(),
                (int) (scrollPanelDimension.getHeight() + 80)));
        this.setMinimumSize(new Dimension((int) scrollPanelDimension.getWidth(), 120));

//        if (!tableCellPopupWindow.isPopupWindowShow(isEditing)) {
//            tableCellPopupWindow.setTableListener(table, this);
//        }
        // set this window to appear in the middle of Project Manager
        this.setLocationRelativeTo(projectManager);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                projectManager.setAddRecordsWindowShow(false);

                projectManager.setDisableProjecetManagerFunction(true);

            }
        });
//        this.pack();
        System.out.println("add record window create!");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        scrollpane = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new java.awt.Dimension(894, 560));

        scrollpane.setBorder(null);
        scrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSubmit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit)
                    .addComponent(btnCancel))
                .addGap(0, 31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 212, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    /**
     * jSubmitActionPerformed This is performed when the submit button is
     * executed. Refactored by Carlos Igreja 7-28-2015
     *
     * @param evt
     */
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {                                          

        projectManager.setDisableProjecetManagerFunction(true);
        submit();
    }                                         

    /**
     * submit This is used when the submit button is pressed or if the enter key
     * is pressed when the tableSelected is finished editing to submit the data
     * to the database.
     */
    private void submit() {

        Object cellValue = null;                 // store cell value
        int col = 0;                             // column index
        int row = 0;                             // row index

        // check if data is valid
        if (validateData()) {

            // once data checked, execute sql statement
            // first get the insert statement for the tableSelected
            String insertInto = "INSERT INTO " + table.getName() + " (";

            // this tableSelected should already not include the primary key
            for (col = 0; col < table.getColumnCount(); col++) {
                if (col != table.getColumnCount() - 1) {
                    insertInto += table.getColumnName(col) + ", ";
                } else {
                    insertInto += table.getColumnName(col) + ") ";
                }
            }

            numRowsAdded = 0; // reset numRowsAdded counter

            // Now get the values to add to the database
            String values = "";
            for (row = 0; row < table.getRowCount(); row++) {
                values = "VALUES (";  // start the values statement
                System.out.println(table.getColumnCount() + "table column number");
                for (col = 0; col < table.getColumnCount(); col++) {

                    // get cell value
                    cellValue = table.getValueAt(row, col);

                    // format the cell value for sql
                    if (cellValue != null) {

                        // if cell is empty it must be null
                        if (cellValue.toString().equals("")) {
                            cellValue = null;
                        } // if the cell is not empty it must have single quotes
                        else {
                            cellValue = "'" + cellValue + "'";
                        }
                    }
                    System.out.println("add record submit" + cellValue + "at " + row + " " + col);

                    // skip empty rows
                    // this must be after the format cell value so the "" => null
                    if (col == 0 && cellValue == null) {
                        break;
                    }

                    // add each value for each column to the values statement
                    if (col != table.getColumnCount() - 1) {
                        values += cellValue + ", ";
                    } else {
                        values += cellValue + ");";
                    }
                }
                System.out.println(values);

                try {
                    // execute the sql statement
                    if (!values.equals("VALUES (")) {      //skip if nothing was added
                        // open connection because might time out
                        DBConnection.close();
                        DBConnection.open();
                        statement = DBConnection.getStatement();
                        statement.executeUpdate(insertInto + values);
                        numRowsAdded++;   // increment the number of rows added
                    }
                } catch (SQLException sqlException) {
                    try {
                        JOptionPane.showMessageDialog(null, "Upload failed!");

                        if (statement.getWarnings().getMessage() != null) {

                            String levelMessage = "2:" + statement.getWarnings().getMessage();
                            logWindow.addMessageWithDate(levelMessage);
//                            logWindow.
                            System.out.println(statement.getWarnings().getMessage());

                            System.out.println(levelMessage);//delete

                            statement.clearWarnings();
                        }
                        logWindow.addMessageWithDate("2:add record submit failed!");
                    } // end try-catch
                    catch (SQLException ex) {
                        // this should never be called
                        ex.printStackTrace();
                    }
                }
            }

            System.out.println("numRowsAdded" + numRowsAdded);

            if (numRowsAdded > 0) {
                // update tableSelected and records label
                String tabName = projectManager.getSelectedTabName();              // tab name
                Tab tab = tabs.get(tabName);                                  // selected tab

                JTable table = tab.getTable();
                projectManager.loadTable(table);                              // load tableSelected data from database

                // reload new table data for modifiedTableData
                ModifiedTableData data = tab.getTableData();
                data.reloadData();

                TableFilter filter = tab.getFilter();                         // tableSelected filter
                filter.applyFilter();                                         // apply filter
                filter.applyColorHeaders();                                   // apply color headers

                ColumnPopupMenu ColumnPopupMenu = tab.getColumnPopupMenu();   // column popup menu 
                ColumnPopupMenu.loadAllCheckBoxItems();                       // refresh the data for the column pop up

                tab.addToTotalRowCount(numRowsAdded);                         // add the number of records added to the total records count
                JLabel recordsLabel = projectManager.getRecordsLabel();
                String recordsLabelText = tab.getRecordsLabel();              // store the records label string
                recordsLabel.setText(recordsLabelText);                       // update the records label text

                projectManager.setLastUpdateTime();                                // set the last update time from database

                JOptionPane.showMessageDialog(this,
                        numRowsAdded + " Add successfully!");                 // show dialog box that upload was successful
                createEmptyTable();                                           // create a new empty tableSelected with default 10 rows
            }
        }
    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {                                          

        projectManager.setAddRecordsWindowShow(false);
        projectManager.setDisableProjecetManagerFunction(true);
//        tableCellPopupWindow.windowClose();
        this.dispose();
    }                                         

    // add an empty row to the tableSelected
//        model.addRow(new Object[]{});
    /**
     * setKeyboardFocusManager Sets the Keyboard Focus Manager
     */
    private void setKeyboardFocusManager(JFrame frame) {

        /*
         No Tab key-pressed or key-released events are received by the key event listener. This is because the focus subsystem 
         consumes focus traversal keys, such as Tab and Shift Tab. To solve this, apply the following to the component that is 
         firing the key events 
         */
        table.setFocusTraversalKeysEnabled(false);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {// Allow to TAB-

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {

                if (e.getComponent() instanceof JTable) {

                    // this is called to either clear data or submit data
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && !table.isEditing()) {

                        // clear the row(s)
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            if (table.getSelectionBackground() == Color.RED) {
                                int[] rows = table.getSelectedRows();

                                if (rows != null) {
                                    for (int row : rows) {
                                        for (int col = 0; col < table.getColumnCount(); col++) {
                                            table.getModel().setValueAt("", row, col);
                                        }
                                    }
                                }
                                table.setSelectionBackground(defaultSelectedBG);

                                // check for empty rows/table
                                checkForEmptyRows();
                                if (rowsNotEmpty.isEmpty()) {
                                    btnSubmit.setEnabled(false);
                                } else {
                                    btnSubmit.setEnabled(true);
                                }
                            } // submit the data
                            else if (table.getSelectionBackground() != Color.RED) {
                                submit();
                            }
                        }
                    } // this toggles the red bg for clearing row data
                    else if (e.getKeyCode() == KeyEvent.VK_DELETE) {

                        if (e.getID() == KeyEvent.KEY_RELEASED) {
                            if (table.isEditing()) {
                                table.getCellEditor().stopCellEditing();
                            }

                            if (table.getSelectionBackground() == defaultSelectedBG) {
                                table.setSelectionBackground(Color.RED);
                            } else {
                                table.setSelectionBackground(defaultSelectedBG);
                            }
                        }
                    } // this is to tab and move to cells with arrow keys
//                    else if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_LEFT
//                            || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP
//                            || e.getKeyCode() == KeyEvent.VK_DOWN) {
//
//                        JTable tableSelected = (JTable) e.getComponent();
//
//                        if (e.getID() == KeyEvent.KEY_RELEASED) {
//                            System.out.println("add records tabs!");
//                            //if table get selected location is not the same as last selection
//                            if (tableSelected.getSelectedRow() != lastSelectedRow
//                                    || tableSelected.getSelectedColumn() != lastSelectedColumn) {
//
//                                if (lastSelectedRow == -1 || lastSelectedColumn == -1) {
//                                    lastSelectedRow = tableSelected.getSelectedRow();
//                                    lastSelectedColumn = tableSelected.getSelectedColumn();
//                                    tableCellPopupWindow = new PopupWindowInTableCell(frame, tableSelected);
//                                } else {
//                                    tableCellPopupWindow.windowClose();
//                                    tableCellPopupWindow = new PopupWindowInTableCell(frame, tableSelected);
//                                    lastSelectedRow = tableSelected.getSelectedRow();
//                                    lastSelectedColumn = tableSelected.getSelectedColumn();
//                                }// last popup window dispose and new popup window show at the selected cell
//                            } 
////                        } else if (e.getID() == KeyEvent.KEY_PRESSED) {
////                            if (selectedCol == tableSelected.getColumnCount() - 1) {
////
////                                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
////
////                                tableModel.addRow(new Object[]{});
////                            }
//
//                        } else {
//
//                        }
//                    }

                } // end table component condition
                // ctrl + D fills in the current date
                else if (e.getKeyCode() == KeyEvent.VK_D && e.isControlDown()) {
                    JTable table = (JTable) e.getComponent().getParent();
                    int column = table.getSelectedColumn();
                    if (table.getColumnName(column).toLowerCase().contains("date")) {
                        if (e.getID() != 401) {
                            return false;
                        } else {
                            JTextField selectCom = (JTextField) e.getComponent();
                            selectCom.requestFocusInWindow();
                            selectCom.selectAll();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();
                            String today = dateFormat.format(date);
                            selectCom.setText(today);
                        }// default date input with today's date}
                    }
                }

                return false;
            }
        });
    }

    /**
     * createEmptyTable creates an empty tableSelected with default 10 rows
     */
    private void createEmptyTable(String appDefaultValue) {
        columnNames = projectManager.getTabs().get(table.getName()).getTableColNames();

        if (table.getName().equals("tasks")) {

            // we don't want the ID column 
            columnNames = Arrays.copyOfRange(columnNames, 1, columnNames.length - 2);

            // set the tableSelected model - add 10 empty rows
            model = new DefaultTableModel(columnNames, 1);

            // add the tableSelected model to the tableSelected
            table.setModel(model);

            int changeColumnNum = table.getColumn("app").getModelIndex();

            for (int i = 0; i < table.getRowCount(); i++) {
                table.setValueAt(appDefaultValue, i, changeColumnNum);
            }

            // get tableSelected column width format
            float[] widths = tabs.get(table.getName()).getColWidthPercent();
            widths = Arrays.copyOfRange(widths, 1, widths.length - 2);

            projectManager.setColumnFormat(widths, table);

        } else {
            // we don't want the ID column 
            columnNames = Arrays.copyOfRange(columnNames, 1, columnNames.length - 2);

            // set the tableSelected model - add 10 empty rows
            model = new DefaultTableModel(columnNames, 1);

            // add the tableSelected model to the tableSelected
            table.setModel(model);

            // get tableSelected column width format
            float[] widths = tabs.get(table.getName()).getColWidthPercent();
            widths = Arrays.copyOfRange(widths, 1, widths.length - 2);

            projectManager.setColumnFormat(widths, table);
        }

    }

    /**
     * jSubmitActionPerformed This is performed when the submit button is
     * executed. Refactored by Carlos Igreja 7-28-2015
     *
     * @param evt
     */
    private void createEmptyTable() {

        String appDefaultType = projectManager.getSelectedTabName();

        createEmptyTable(appDefaultType);

    }

    /**
     * addTableListeners This is called to add the listeners to the
     * tableSelected The listeners added are the TableModel listener the
     * MouseListener and the KeyListener
     */
    public void addTableListeners(JFrame frame) {

        // add tableModelListener
        table.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {

                // isEditing is a class boolean triggered true on double click
                if (!isEditing) {
                    // if clearing row then do not validate
                    if (table.getSelectionBackground() != Color.RED) {

                        // check the cell for valid entry
                        int row = e.getLastRow();            // row index
                        int col = e.getColumn();             // column index

                        System.out.println("tableChanged at: " + row + " " + col);
                        validateCell(row, col);
                    }

                    // get value of cell
                    int row = e.getFirstRow();
                    int col = e.getColumn();
                    Object value = table.getValueAt(row, col);

                    // if cell value is empty
                    if (value == null || value.equals("")) {
                        // check to see if it was a deletion
                        if (!rowsNotEmpty.isEmpty() && rowsNotEmpty.contains(row)) {
                            checkForEmptyRows();
                        }
                    } // else add the row to the list as not empty
                    else {
                        rowsNotEmpty.add(row);
                    }

                    // if list is empty then the tableSelected is empty
                    if (!rowsNotEmpty.isEmpty() && !isEditing) {
                        btnSubmit.setEnabled(true);
                    }
                }

                // reset isEditing boolean
                isEditing = false;
            }

        });

        // add mouseListener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getClickCount() == 1) {
                    // if we click away the red delete should go away
                    if (table.getSelectionBackground() == Color.RED && !e.isControlDown()) {
                        table.setSelectionBackground(defaultSelectedBG);
                    }
                } // this enters edit mode
                else if (e.getClickCount() == 2) {
                    btnSubmit.setEnabled(false);
                    isEditing = true;
                    selectAllText(e);
                }
            }

//            @Override
//            public void mouseClicked(MouseEvent e) {
//
//                // if left mouse clicks
//                if (SwingUtilities.isLeftMouseButton(e)) {
//                    if (e.getClickCount() == 1) {
//
//                        //if table get selected location is not the same as last selection
//                        if (table.getSelectedRow() != lastSelectedRow
//                                || table.getSelectedColumn() != lastSelectedColumn) {
//
//                            if (lastSelectedRow == -1 || lastSelectedColumn == -1) {
//                                lastSelectedRow = table.getSelectedRow();
//                                lastSelectedColumn = table.getSelectedColumn();
//                                tableCellPopupWindow = new PopupWindowInTableCell(frame, table);
//                            } else {
//                                tableCellPopupWindow.windowClose();
//                                tableCellPopupWindow = new PopupWindowInTableCell(frame, table);
//                                lastSelectedRow = table.getSelectedRow();
//                                lastSelectedColumn = table.getSelectedColumn();
//                            }// last popup window dispose and new popup window show at the selected cell
//                        } else {
//                            //if current selection equals last selection nothing happens
//                        }
//                    }
//                } // end if left mouse clicks
//            }
        });
    }

    /**
     * selectAllText Select all text inside jTextField or a cell
     *
     * @param e
     */
    private void selectAllText(MouseEvent e) {

        JTable table = (JTable) e.getComponent();
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();
        if (column != -1) {
            table.getComponentAt(row, column).requestFocus();
            table.editCellAt(row, column);
            JTextField selectCom = (JTextField) table.getEditorComponent();
            if (selectCom != null) {
                selectCom.requestFocusInWindow();
                selectCom.selectAll();
            }
        }
    }

    /**
     * validateCell
     *
     * @param row
     * @param col
     * @return returns true if valid or false if error
     */
    public boolean validateCell(int row, int col) {

        String colName = table.getColumnName(col);           // column name
        Object cellValue = table.getValueAt(row, col);       // store cell value
        String errorMsg = "Error with " + colName
                + " in row " + (row + 1) + ".\n";            // error message
        boolean error = false;                               // error occurred

        switch (colName) {
            case "app":
                if (cellValue == null || cellValue.toString().equals("")) {
                    errorMsg += "Symbol cannot be null";
                    error = true;
                }
                break;
            case "title":
                break;
            case "step":
                break;
            case "description":
                break;
            case "instruction":
                break;
            case "programmer":
                break;
            case "rank":
                if (cellValue != null && !cellValue.toString().equals("")) {
                    if (!cellValue.toString().matches("[1-5]{1}")) {
                        errorMsg += "Priority must be an Integer (1-5)";
                        error = true;
                    }
                }
                break;
            case "dateAssigned":
                if (cellValue != null && !cellValue.toString().equals("")) {
                    if (!Validator.isValidDate("yyyy-MM-dd", cellValue.toString())) {
                        errorMsg += "Date format not correct: YYYY-MM-DD";
                        error = true;
                    }
                }
                break;
            case "dateDone":
                if (cellValue != null && !cellValue.toString().equals("")) {
                    if (!Validator.isValidDate("yyyy-MM-dd", cellValue.toString())) {
                        errorMsg += "Date format not correct: YYYY-MM-DD";
                        error = true;
                    }
                }
                break;
            case "date_":
                if (cellValue != null && !cellValue.toString().equals("")) {
                    if (!Validator.isValidDate("yyyy-MM-dd", cellValue.toString())) {
                        errorMsg += "Date format not correct: YYYY-MM-DD";
                        error = true;
                    }
                }
                break;
            case "taskID":
                break;
            case "notes":
                break;
            case "path":
                break;
            case "submitter":
                break;
            default:
                break;

        }// end switch

        if (error) {
            JOptionPane.showMessageDialog(table, errorMsg);
            //btnSubmit.setEnabled(true); 
        }

        return !error;  // if there was an error, return false for failed
    }

    /**
     * validateData Validates all the data in the tableSelected to make sure it
     * is correct. This is used to validate the data before it is executed to
     * the server and the database so that there will not be any errors.
     *
     * @return returns true if the data is all valid and false if the is a
     * single error
     */
    public boolean validateData() {

        int col = 0;                    // column index
        boolean isCellValid = true;    // if cell is valid entry 

        // if tableSelected is empty
        if (!rowsNotEmpty.isEmpty()) {

            // check data
            for (int row : rowsNotEmpty) {

                // if there was an error stop
                if (!isCellValid) {
                    break;
                }

                for (col = 0; col < table.getColumnCount(); col++) {

                    // if there was an error stop
                    if (!isCellValid) {
                        break;
                    }

                    // begin error message
                    isCellValid = validateCell(row, col);

                }// end col for loop
            }// end row for loop

            return isCellValid;
        }

        return false; // tableSelected is empty
    }

    /**
     * checkForEmptyRows This should be used when data is removed or deleted
     */
    public void checkForEmptyRows() {

        ArrayList<Integer> arrayCopy = new ArrayList(rowsNotEmpty);
        rowsNotEmpty.clear();

        // check List for empty rows
        for (int row : arrayCopy) {
            boolean isNotEmpty = false;
            for (int col = 0; col < table.getColumnCount(); col++) {
                Object value = table.getValueAt(row, col);
                if (value != null && !value.equals("")) {
                    isNotEmpty = true;
                    break;
                }
            }
            if (isNotEmpty) {
                rowsNotEmpty.add(row);
            }
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane scrollpane;
    // End of variables declaration                   

}
