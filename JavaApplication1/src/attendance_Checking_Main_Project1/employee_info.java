
package attendance_Checking_Main_Project1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class employee_info extends javax.swing.JFrame {

    private static final String EMPLOYEE_COLUMNS = "employee_Id, first_Name, last_Name, email, phone_Number, address";

    public employee_info() {
        ThemeManager.initialize();
        initComponents();
        setTitle("Employee Management");
        setMinimumSize(new java.awt.Dimension(900, 700));
        getContentPane().setBackground(ThemeManager.APP_BACKGROUND);
        ThemeManager.styleTable(tbl_emloyeeInfo);
        ThemeManager.styleField(txtfname);
        ThemeManager.styleField(txtlname);
        ThemeManager.styleField(txtemail);
        ThemeManager.styleField(txtcontact);
        ThemeManager.styleField(txtaddress);
        ThemeManager.styleField(txtId);
        ThemeManager.styleButton(btn_add, true);
        ThemeManager.styleButton(btn_update, false);
        ThemeManager.styleButton(btn_delete, false);
        ThemeManager.styleButton(btnSearch, false);
        ThemeManager.styleButton(btnAttendanceRecord, false);
        ThemeManager.styleButton(btnEmployeeShift, false);
        ThemeManager.styleButton(btnHome, false);
        initializeAdminHomeLayout();
        refreshEmployeeData();
    }

    /** Reloads both views after a successful employee mutation. */
    private void refreshEmployeeData() {
        loadEmployeeIds();
        loadEmployees();
    }

    private void loadEmployeeIds() {
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT employee_Id FROM employee_info ORDER BY employee_Id");
                ResultSet result = statement.executeQuery()) {
            txtId.removeAllItems();
            while (result.next()) {
                txtId.addItem(result.getString("employee_Id"));
            }
        } catch (SQLException ex) {
            showDatabaseError("load employee IDs", ex);
        }
    }

    private void loadEmployees() {
        DefaultTableModel model = (DefaultTableModel) tbl_emloyeeInfo.getModel();
        model.setRowCount(0);
        String sql = "SELECT " + EMPLOYEE_COLUMNS + " FROM employee_info ORDER BY employee_Id";
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                model.addRow(new Object[] {
                        result.getInt("employee_Id"), result.getString("first_Name"),
                        result.getString("last_Name"), result.getString("email"),
                        result.getString("phone_Number"), result.getString("address")
                });
            }
        } catch (SQLException ex) {
            showDatabaseError("load employees", ex);
        }
    }

    private boolean hasSelectedEmployeeId() {
        if (txtId.getSelectedItem() != null) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Select an employee ID first.");
        return false;
    }

    private String selectedEmployeeId() {
        return txtId.getSelectedItem().toString();
    }

    private List<String> employeeFormValues() {
        List<String> values = new ArrayList<String>();
        values.add(txtfname.getText().trim());
        values.add(txtlname.getText().trim());
        values.add(txtemail.getText().trim());
        values.add(txtcontact.getText().trim());
        values.add(txtaddress.getText().trim());
        return values;
    }

    private boolean hasCompleteEmployeeForm(List<String> values) {
        for (String value : values) {
            if (value.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete all employee fields.");
                return false;
            }
        }
        return true;
    }

    private void bindEmployeeValues(PreparedStatement statement, List<String> values) throws SQLException {
        for (int index = 0; index < values.size(); index++) {
            statement.setString(index + 1, values.get(index));
        }
    }

    private void clearEmployeeForm() {
        txtfname.setText("");
        txtlname.setText("");
        txtemail.setText("");
        txtcontact.setText("");
        txtaddress.setText("");
        txtfname.requestFocusInWindow();
    }

    private void showDatabaseError(String operation, SQLException error) {
        JOptionPane.showMessageDialog(this, "Could not " + operation + ". " + error.getMessage(), "Database error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void initializeAdminHomeLayout() {
        setTitle("Admin Home | Employee Directory");
        setPreferredSize(new Dimension(1280, 800));

        JPanel root = new JPanel(new BorderLayout(0, 20));
        root.setBorder(BorderFactory.createEmptyBorder(28, 32, 22, 32));
        root.setBackground(ThemeManager.APP_BACKGROUND);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("ADMINISTRATION");
        eyebrow.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        eyebrow.setForeground(ThemeManager.PRIMARY);
        JLabel title = new JLabel("Employee directory");
        title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        title.setForeground(ThemeManager.TEXT);
        JLabel subtitle = new JLabel("View, create, and maintain employee records.");
        subtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        subtitle.setForeground(ThemeManager.MUTED_TEXT);
        header.add(eyebrow);
        header.add(Box.createVerticalStrut(5));
        header.add(title);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitle);
        root.add(header, BorderLayout.NORTH);

        JPanel directoryPanel = new JPanel(new BorderLayout(0, 12));
        directoryPanel.setBackground(ThemeManager.SURFACE);
        directoryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeManager.BORDER),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        JLabel directoryTitle = new JLabel("All employees");
        directoryTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        directoryTitle.setForeground(ThemeManager.TEXT);
        directoryPanel.add(directoryTitle, BorderLayout.NORTH);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(ThemeManager.BORDER));
        directoryPanel.add(jScrollPane1, BorderLayout.CENTER);

        JPanel editorPanel = new JPanel(new BorderLayout(0, 16));
        editorPanel.setBackground(ThemeManager.SURFACE);
        editorPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeManager.BORDER),
                BorderFactory.createEmptyBorder(20, 22, 20, 22)));
        JLabel editorTitle = new JLabel("Employee details");
        editorTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        editorTitle.setForeground(ThemeManager.TEXT);
        editorPanel.add(editorTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        addEditorField(form, 0, "First name", txtfname);
        addEditorField(form, 1, "Last name", txtlname);
        addEditorField(form, 2, "Email", txtemail);
        addEditorField(form, 3, "Phone", txtcontact);
        addEditorField(form, 4, "Address", txtaddress);
        addEditorField(form, 5, "Employee ID", txtId);
        addEditorField(form, 6, "Find record", btnSearch);
        editorPanel.add(form, BorderLayout.CENTER);

        JPanel editorActions = new JPanel();
        editorActions.setOpaque(false);
        editorActions.setLayout(new BoxLayout(editorActions, BoxLayout.X_AXIS));
        editorActions.add(btn_add);
        editorActions.add(Box.createHorizontalStrut(8));
        editorActions.add(btn_update);
        editorActions.add(Box.createHorizontalStrut(8));
        editorActions.add(btn_delete);
        editorPanel.add(editorActions, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, directoryPanel, editorPanel);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);
        splitPane.setDividerSize(8);
        splitPane.setResizeWeight(0.64);
        root.add(splitPane, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        JLabel footerText = new JLabel("Administration tools");
        footerText.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        footerText.setForeground(ThemeManager.MUTED_TEXT);
        JPanel navigation = new JPanel();
        navigation.setOpaque(false);
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.X_AXIS));
        navigation.add(btnEmployeeShift);
        navigation.add(Box.createHorizontalStrut(8));
        navigation.add(btnAttendanceRecord);
        navigation.add(Box.createHorizontalGlue());
        navigation.add(btnHome);
        footer.add(footerText, BorderLayout.WEST);
        footer.add(navigation, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(1000, 680));
        setLocationRelativeTo(null);
    }

    private void addEditorField(JPanel form, int row, String labelText, JComponent component) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(0, 0, 12, 12);
        JLabel label = new JLabel(labelText);
        ThemeManager.styleLabel(label);
        form.add(label, labelConstraints);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = row;
        fieldConstraints.weightx = 1;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(0, 0, 12, 0);
        form.add(component, fieldConstraints);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_emloyeeInfo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btn_add = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtfname = new javax.swing.JTextField();
        txtlname = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        txtcontact = new javax.swing.JTextField();
        txtaddress = new javax.swing.JTextField();
        btnAttendanceRecord = new javax.swing.JButton();
        btnEmployeeShift = new javax.swing.JButton();
        btnHome = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtId = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbl_emloyeeInfo.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null }
                },
                new String[] {
                        "employee id", "fname", "lname", "email", "contact", "address"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl_emloyeeInfo);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("EMPLOYEE INFO");

        btn_add.setText("add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_update.setText("update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_delete.setText("delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        jLabel2.setText("first name");

        jLabel3.setText("last name");

        jLabel4.setText("email");

        jLabel5.setText("contact");

        jLabel6.setText("address");

        txtfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfnameActionPerformed(evt);
            }
        });

        txtcontact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcontactActionPerformed(evt);
            }
        });

        txtaddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtaddressActionPerformed(evt);
            }
        });

        btnAttendanceRecord.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        btnAttendanceRecord.setText("attendance record");
        btnAttendanceRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttendanceRecordActionPerformed(evt);
            }
        });

        btnEmployeeShift.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        btnEmployeeShift.setText("Monitor Employee");
        btnEmployeeShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeShiftActionPerformed(evt);
            }
        });

        btnHome.setText("Log Out");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        jLabel7.setText("employee ID");

        btnSearch.setText("search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel8.setText("ADMIN HOME PANEL:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(201, 201, 201))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(12, 12, 12)
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                        .addComponent(jLabel3)
                                                                                        .addComponent(jLabel4)
                                                                                        .addComponent(jLabel2)
                                                                                        .addComponent(jLabel5)
                                                                                        .addComponent(jLabel6))
                                                                                .addGap(18, 18, 18)
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(layout
                                                                                                .createParallelGroup(
                                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(
                                                                                                        txtaddress,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                        142,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(
                                                                                                        txtcontact,
                                                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                        142,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addComponent(txtemail,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                142,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGroup(layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(layout
                                                                                                        .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(
                                                                                                                txtfname,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                142,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(
                                                                                                                txtlname,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                142,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(31, 31, 31)
                                                                                                .addGroup(layout
                                                                                                        .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                false)
                                                                                                        .addComponent(
                                                                                                                btnSearch,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                1,
                                                                                                                Short.MAX_VALUE)
                                                                                                        .addComponent(
                                                                                                                txtId,
                                                                                                                0, 1,
                                                                                                                Short.MAX_VALUE)
                                                                                                        .addComponent(
                                                                                                                jLabel7,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)))))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(btn_add)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(btn_update)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(btn_delete)))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(btnHome,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                127,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(btnEmployeeShift,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        Short.MAX_VALUE)
                                                                                .addComponent(btnAttendanceRecord,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        127,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addComponent(jScrollPane1,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 524,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(18, Short.MAX_VALUE)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtfname, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel3)
                                                        .addComponent(txtlname, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel4)
                                                        .addComponent(btnSearch))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(txtcontact,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel6,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(txtaddress,
                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(btnEmployeeShift, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnAttendanceRecord)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btn_add)
                                        .addComponent(btn_update)
                                        .addComponent(btn_delete)
                                        .addComponent(btnHome))
                                .addContainerGap()));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtcontactActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtcontactActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtcontactActionPerformed

    private void txtaddressActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtaddressActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtaddressActionPerformed

    private void btnAttendanceRecordActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAttendanceRecordActionPerformed
        new attendance_record_admin().setVisible(true);
        this.dispose();
    }// GEN-LAST:event_btnAttendanceRecordActionPerformed

    private void txtfnameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtfnameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtfnameActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_addActionPerformed
        List<String> values = employeeFormValues();
        if (!hasCompleteEmployeeForm(values)) {
            return;
        }
        String sql = "INSERT INTO employee_info (first_Name, last_Name, email, phone_Number, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            bindEmployeeValues(statement, values);
            if (statement.executeUpdate() != 1) {
                JOptionPane.showMessageDialog(this, "Employee record was not saved.");
                return;
            }
            clearEmployeeForm();
            refreshEmployeeData();
            JOptionPane.showMessageDialog(this, "Employee record added successfully.");
        } catch (SQLException ex) {
            showDatabaseError("add the employee", ex);
        }
    }// GEN-LAST:event_btn_addActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_updateActionPerformed
        if (!hasSelectedEmployeeId()) {
            return;
        }
        List<String> values = employeeFormValues();
        if (!hasCompleteEmployeeForm(values)) {
            return;
        }
        String sql = "UPDATE employee_info SET first_Name = ?, last_Name = ?, email = ?, phone_Number = ?, address = ? WHERE employee_Id = ?";
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            bindEmployeeValues(statement, values);
            statement.setString(6, selectedEmployeeId());
            if (statement.executeUpdate() != 1) {
                JOptionPane.showMessageDialog(this, "Employee record was not updated.");
                return;
            }
            clearEmployeeForm();
            refreshEmployeeData();
            JOptionPane.showMessageDialog(this, "Employee record updated successfully.");
        } catch (SQLException ex) {
            showDatabaseError("update the employee", ex);
        }
    }// GEN-LAST:event_btn_updateActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearchActionPerformed
        if (!hasSelectedEmployeeId()) {
            return;
        }
        String sql = "SELECT first_Name, last_Name, email, phone_Number, address FROM employee_info WHERE employee_Id = ?";
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, selectedEmployeeId());
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    JOptionPane.showMessageDialog(this, "No employee record found.");
                    return;
                }
                txtfname.setText(result.getString("first_Name"));
                txtlname.setText(result.getString("last_Name"));
                txtemail.setText(result.getString("email"));
                txtcontact.setText(result.getString("phone_Number"));
                txtaddress.setText(result.getString("address"));
            }
        } catch (SQLException ex) {
            showDatabaseError("find the employee", ex);
        }
    }// GEN-LAST:event_btnSearchActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_deleteActionPerformed
        if (!hasSelectedEmployeeId()) {
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this, "Delete employee " + selectedEmployeeId() + "?",
                "Confirm deletion", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement statement = connection
                        .prepareStatement("DELETE FROM employee_info WHERE employee_Id = ?")) {
            statement.setString(1, selectedEmployeeId());
            if (statement.executeUpdate() != 1) {
                JOptionPane.showMessageDialog(this, "Employee record was not deleted.");
                return;
            }
            clearEmployeeForm();
            refreshEmployeeData();
            JOptionPane.showMessageDialog(this, "Employee record deleted successfully.");
        } catch (SQLException ex) {
            showDatabaseError("delete the employee", ex);
        }
    }// GEN-LAST:event_btn_deleteActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHomeActionPerformed
        new login_admin_employee().setVisible(true);
        this.dispose();
    }// GEN-LAST:event_btnHomeActionPerformed

    private void btnEmployeeShiftActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEmployeeShiftActionPerformed
        new admin_shift_view().setVisible(true);
        this.dispose();
    }// GEN-LAST:event_btnEmployeeShiftActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(employee_info.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(employee_info.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(employee_info.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(employee_info.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login_admin_employee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAttendanceRecord;
    private javax.swing.JButton btnEmployeeShift;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_emloyeeInfo;
    private javax.swing.JComboBox<String> txtId;
    private javax.swing.JTextField txtaddress;
    private javax.swing.JTextField txtcontact;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtfname;
    private javax.swing.JTextField txtlname;
    // End of variables declaration//GEN-END:variables
}
