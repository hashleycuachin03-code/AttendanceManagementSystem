/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package attendance_Checking_Main_Project1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class attendance_record_employee extends javax.swing.JFrame {

    private JComboBox<String> statusComboBox;
    private JLabel feedbackLabel;

    public attendance_record_employee() {
        initComponents();
        initializeModernUi();
    }

    private void recordTimeIn() {
        Integer employeeId = readEmployeeId();
        if (employeeId == null) {
            return;
        }
        String sql = "INSERT INTO attendance_record (employee_Id, attendance_Status, remarks) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            statement.setString(2, (String) statusComboBox.getSelectedItem());
            statement.setString(3, remarksTextArea.getText().trim());
            if (statement.executeUpdate() != 1) {
                showFeedback("Time In was not recorded.", ThemeManager.ERROR);
                return;
            }
            showFeedback("Time In recorded successfully.", ThemeManager.SUCCESS);
        } catch (SQLException ex) {
            showDatabaseError("record Time In", ex);
        }
    }

    private void recordTimeOut() {
        Integer employeeId = readEmployeeId();
        if (employeeId == null) {
            return;
        }
        String sql = "UPDATE attendance_record SET time_Out = CURRENT_TIMESTAMP WHERE employee_Id = ? AND time_Out IS NULL";
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                showFeedback("Time Out recorded successfully.", ThemeManager.SUCCESS);
            } else {
                showFeedback("No active Time In record found for this employee.", ThemeManager.ERROR);
            }
        } catch (SQLException ex) {
            showDatabaseError("record Time Out", ex);
        }
    }

    private Integer readEmployeeId() {
        String text = employeeIdTextField.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee ID cannot be empty.");
            return null;
        }
        try {
            return Integer.valueOf(text);
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(this, "Employee ID must be a number.");
            return null;
        }
    }

    private void showDatabaseError(String operation, SQLException error) {
        showFeedback("Could not " + operation + ".", ThemeManager.ERROR);
        JOptionPane.showMessageDialog(this, "Could not " + operation + ". " + error.getMessage(), "Database error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void initializeModernUi() {
        ThemeManager.initialize();
        setTitle("Employee Attendance");
        setMinimumSize(new Dimension(900, 620));
        setPreferredSize(new Dimension(1100, 720));

        statusComboBox = new JComboBox<>(new String[] { "Present", "Late", "Absent" });
        statusComboBox.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        statusComboBox.setPreferredSize(new Dimension(260, 38));
        statusComboBox.setBackground(ThemeManager.SURFACE);
        statusComboBox.setForeground(ThemeManager.TEXT);

        ThemeManager.styleField(employeeIdTextField);
        ThemeManager.styleTextArea(remarksTextArea);
        ThemeManager.styleButton(timeInButton, true);
        ThemeManager.styleButton(timeOutButton, false);
        ThemeManager.styleButton(btn_schedule, false);
        ThemeManager.styleButton(btnHome, false);

        JPanel root = new JPanel(new BorderLayout(0, 24));
        root.setBorder(BorderFactory.createEmptyBorder(28, 36, 24, 36));
        root.setBackground(ThemeManager.APP_BACKGROUND);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("EMPLOYEE SELF-SERVICE");
        eyebrow.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        eyebrow.setForeground(ThemeManager.PRIMARY);
        jLabel6.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26));
        jLabel6.setForeground(ThemeManager.TEXT);
        jLabel6.setText("Record your attendance");
        header.add(eyebrow);
        header.add(Box.createVerticalStrut(6));
        header.add(jLabel6);
        header.add(Box.createVerticalStrut(4));
        JLabel subtitle = new JLabel("Use the form below to start or end your workday.");
        subtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        subtitle.setForeground(ThemeManager.MUTED_TEXT);
        header.add(subtitle);
        root.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(ThemeManager.SURFACE);
        content.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeManager.BORDER),
                BorderFactory.createEmptyBorder(28, 30, 28, 30)));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 16, 16);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;

        addFormLabel(content, 0, "Employee ID", "Enter the ID assigned to you.");
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        content.add(employeeIdTextField, constraints);

        addFormLabel(content, 1, "Attendance status", "Choose the status for this check-in.");
        constraints.gridx = 1;
        constraints.gridy = 1;
        content.add(statusComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.insets = new Insets(4, 0, 8, 0);
        JLabel remarksLabel = new JLabel("Remarks");
        ThemeManager.styleLabel(remarksLabel);
        content.add(remarksLabel, constraints);

        constraints.gridy = 3;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 0, 20, 0);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(ThemeManager.BORDER));
        jScrollPane1.setPreferredSize(new Dimension(500, 120));
        content.add(jScrollPane1, constraints);

        constraints.gridy = 4;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 0, 0);
        JPanel actions = new JPanel();
        actions.setOpaque(false);
        actions.setLayout(new BoxLayout(actions, BoxLayout.X_AXIS));
        actions.add(timeInButton);
        actions.add(Box.createHorizontalStrut(10));
        actions.add(timeOutButton);
        actions.add(Box.createHorizontalGlue());
        actions.add(btn_schedule);
        actions.add(Box.createHorizontalStrut(10));
        actions.add(btnHome);
        content.add(actions, constraints);

        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setOpaque(false);
        cardWrapper.add(content, BorderLayout.CENTER);
        root.add(cardWrapper, BorderLayout.CENTER);

        feedbackLabel = new JLabel("Ready to record attendance.");
        feedbackLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        feedbackLabel.setForeground(ThemeManager.MUTED_TEXT);
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
        root.add(feedbackLabel, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    private void addFormLabel(JPanel panel, int row, String title, String helper) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.gridwidth = 1;
        labelConstraints.weightx = 0;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(0, 0, 16, 16);
        JPanel labelPanel = new JPanel();
        labelPanel.setOpaque(false);
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel(title);
        ThemeManager.styleLabel(titleLabel);
        JLabel helperLabel = new JLabel(helper);
        helperLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        helperLabel.setForeground(ThemeManager.MUTED_TEXT);
        labelPanel.add(titleLabel);
        labelPanel.add(Box.createVerticalStrut(3));
        labelPanel.add(helperLabel);
        panel.add(labelPanel, labelConstraints);
    }

    private void showFeedback(String message, Color color) {
        if (feedbackLabel != null) {
            feedbackLabel.setText(message);
            feedbackLabel.setForeground(color);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        employeeIdTextField = new javax.swing.JTextField();
        statusTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        remarksTextArea = new javax.swing.JTextArea();
        timeInButton = new javax.swing.JButton();
        timeOutButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();
        btn_schedule = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("employee ID:");

        jLabel2.setText("Status:");

        jLabel3.setText("Remarks:");

        employeeIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeIdTextFieldActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        remarksTextArea.setColumns(20);
        remarksTextArea.setRows(5);
        jScrollPane1.setViewportView(remarksTextArea);

        timeInButton.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        timeInButton.setText("TIME IN");
        timeInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeInButtonActionPerformed(evt);
            }
        });

        timeOutButton.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        timeOutButton.setText("TIME OUT");
        timeOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeOutButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("present/absent/late");

        jLabel5.setText("enter reason if absent");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Attendance Record");

        btnHome.setText("Exit");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btn_schedule.setText("SCHEDULE");
        btn_schedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_scheduleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(timeInButton, javax.swing.GroupLayout.DEFAULT_SIZE, 73,
                                                        Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(timeOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(employeeIdTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(statusTextField, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                127, Short.MAX_VALUE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel4)
                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                .createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btn_schedule,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(17, 17, 17))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 191,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(115, 115, 115)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(employeeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addGroup(jPanel1Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(statusTextField,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jLabel4)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel5))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(timeOutButton)
                                                        .addComponent(timeInButton))
                                                .addContainerGap(29, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                .createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btn_schedule)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnHome)
                                                .addGap(15, 15, 15)))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void timeOutButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_timeOutButtonActionPerformed
        recordTimeOut();
    }// GEN-LAST:event_timeOutButtonActionPerformed

    private void employeeIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_employeeIdTextFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_employeeIdTextFieldActionPerformed

    private void timeInButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_timeInButtonActionPerformed
        recordTimeIn();
    }// GEN-LAST:event_timeInButtonActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHomeActionPerformed
        System.exit(0);

    }// GEN-LAST:event_btnHomeActionPerformed

    private void btn_scheduleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_scheduleActionPerformed
        new employee_shifts_schedule().setVisible(true);
        this.dispose();
    }// GEN-LAST:event_btn_scheduleActionPerformed

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
            java.util.logging.Logger.getLogger(attendance_record_employee.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(attendance_record_employee.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(attendance_record_employee.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(attendance_record_employee.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new attendance_record_employee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btn_schedule;
    private javax.swing.JTextField employeeIdTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea remarksTextArea;
    private javax.swing.JTextField statusTextField;
    private javax.swing.JButton timeInButton;
    private javax.swing.JButton timeOutButton;
    // End of variables declaration//GEN-END:variables
}
