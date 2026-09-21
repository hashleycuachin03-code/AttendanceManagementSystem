package attendance_Checking_Main_Project1;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class employee_shifts_schedule extends javax.swing.JFrame {

    private int employeeShiftId = -1;

    public employee_shifts_schedule() {
        initComponents();
        populateShiftComboBox();

    }

    private void populateShiftComboBox() {
        DefaultComboBoxModel<Shift> shiftModel = new DefaultComboBoxModel<>();
        String sql = "SELECT shift_Id, start_time, end_time FROM shifts ORDER BY shift_Id";
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int shiftId = resultSet.getInt("shift_Id");
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");
                shiftModel.addElement(new Shift(shiftId, startTime + " - " + endTime));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading shifts: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        shiftComboBox.setModel(shiftModel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        employeeIdField = new javax.swing.JTextField();
        shiftComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        checkInButton = new javax.swing.JButton();
        checkOutButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("YOUR EMPLOYEE ID:");

        jLabel2.setText("Select Shift:");

        statusLabel.setText("Status: Not Checked In");

        checkInButton.setText("CHECK IN");
        checkInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInButtonActionPerformed(evt);
            }
        });

        checkOutButton.setText("CHECK OUT");
        checkOutButton.setEnabled(false);
        checkOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkOutButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("SHIFT SCHEDULE");

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(136, 136, 136)
                                                .addComponent(jLabel5))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(110, 110, 110)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                layout.createSequentialGroup()
                                                                        .addComponent(jLabel2)
                                                                        .addGap(33, 33, 33)
                                                                        .addComponent(shiftComboBox,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                149,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(checkInButton)
                                                                .addGap(39, 39, 39)
                                                                .addComponent(checkOutButton))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(62, 62, 62)
                                                .addComponent(jLabel1)
                                                .addGap(33, 33, 33)
                                                .addComponent(employeeIdField))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(47, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(employeeIdField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(shiftComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addComponent(statusLabel)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(checkInButton)
                                        .addComponent(checkOutButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)
                                .addContainerGap(63, Short.MAX_VALUE)));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void checkInButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_checkInButtonActionPerformed
        checkIn();
    }// GEN-LAST:event_checkInButtonActionPerformed

    private void checkOutButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_checkOutButtonActionPerformed
        checkOut();
    }// GEN-LAST:event_checkOutButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }// GEN-LAST:event_jButton1ActionPerformed

    private void checkIn() {
        try {
            int employeeId = Integer.parseInt(employeeIdField.getText());
            Shift selectedShift = (Shift) shiftComboBox.getSelectedItem();
            int shiftId = selectedShift.getShiftId();

            // Check if the employee exists in employee_info
            if (!employeeExists(employeeId)) {
                JOptionPane.showMessageDialog(this, "Employee ID not found in employee_info.", "Check-In Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the employee is already checked in
            if (isEmployeeCheckedIn(employeeId)) {
                JOptionPane.showMessageDialog(this, "Employee is already checked in.", "Check-In Error",
                        JOptionPane.ERROR_MESSAGE);
                checkOutButton.setEnabled(true);
                return;
            }

            String sql = "INSERT INTO employee_shifts (employee_Id, shift_Id, check_In) VALUES (?, ?, ?)";
            try (Connection connection = DatabaseConnection.open();
                    PreparedStatement preparedStatement = connection.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, employeeId);
                preparedStatement.setInt(2, shiftId);
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    // Retrieve the auto-generated key
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            employeeShiftId = generatedKeys.getInt(1); // Store the employee_shift_ID
                        } else {
                            statusLabel.setText("Check-in failed: Could not retrieve shift ID.");
                            return;
                        }
                    }

                    statusLabel.setText("Status: Checked In");
                    checkInButton.setEnabled(false);
                    checkOutButton.setEnabled(true);
                    JOptionPane.showMessageDialog(this, "you have now CHECKED IN. ");
                } else {
                    statusLabel.setText("Check-in failed.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error during check-in: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Invalid Employee ID. Please enter a number.");
        }
    }

    private void checkOut() {
        if (employeeShiftId == -1) {
            JOptionPane.showMessageDialog(this, "No active check-in found.", "Check-Out Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "UPDATE employee_shifts SET check_Out = ? WHERE employee_shift_ID = ?";
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, employeeShiftId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                statusLabel.setText("Status: Checked Out");
                checkInButton.setEnabled(true);
                checkOutButton.setEnabled(false);
                employeeShiftId = -1; // Reset employeeShiftId
                JOptionPane.showMessageDialog(this, "you have now CHECKED OUT. ");
            } else {
                statusLabel.setText("Check-out failed.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error during check-out: " + e.getMessage());
        }
    }

    private boolean employeeExists(int employeeId) {
        String sql = "SELECT employee_Id FROM employee_info WHERE employee_Id = ?";
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employeeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error checking employee existence: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean isEmployeeCheckedIn(int employeeId) {
        String sql = "SELECT COUNT(*) FROM employee_shifts WHERE employee_Id = ? AND check_Out IS NULL";
        try (Connection connection = DatabaseConnection.open();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employeeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error checking check-in status: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false; // Return false in case of an error
    }

    // Shift class to hold shift information
    class Shift {
        private int shiftId;
        private String shiftName;

        public Shift(int shiftId, String shiftName) {
            this.shiftId = shiftId;
            this.shiftName = shiftName;
        }

        public int getShiftId() {
            return shiftId;
        }

        public String getShiftName() {
            return shiftName;
        }

        @Override
        public String toString() {
            return shiftName; // Display the shift name in the combo box
        }
    }

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
            java.util.logging.Logger.getLogger(employee_shifts_schedule.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(employee_shifts_schedule.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(employee_shifts_schedule.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(employee_shifts_schedule.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new employee_shifts_schedule().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkInButton;
    private javax.swing.JButton checkOutButton;
    private javax.swing.JTextField employeeIdField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox<Shift> shiftComboBox;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
