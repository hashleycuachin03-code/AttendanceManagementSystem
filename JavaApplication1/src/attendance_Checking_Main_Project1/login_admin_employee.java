/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package attendance_Checking_Main_Project1;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login_admin_employee extends javax.swing.JFrame {

    /**
     * Creates new form login_admin_employee
     */
    public login_admin_employee() {
        ThemeManager.initialize();
        initComponents();
        setTitle("Attendance Management System");
        setMinimumSize(new java.awt.Dimension(520, 420));
        ThemeManager.styleField(txtusername);
        ThemeManager.styleField(txtpassword);
        ThemeManager.styleButton(btnLogin, true);
        ThemeManager.styleButton(btnExit, false);
        jLabel3.setForeground(ThemeManager.TEXT);
        jLabel3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26));
    }

    private boolean authenticateAdmin(Connection connection, String username, String password) throws SQLException {
        String sql = "SELECT admin_ID FROM admin_users WHERE username = ? AND password = ?";
        return credentialsMatch(connection, sql, username, password);
    }

    private boolean authenticateEmployee(Connection connection, String username, String password) throws SQLException {
        String sql = "SELECT employee_ID FROM employee_users WHERE username = ? AND password = ?";
        return credentialsMatch(connection, sql, username, password);
    }

    private boolean credentialsMatch(Connection connection, String sql, String username, String password)
            throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }

    private void login() {
        String username = txtusername.getText().trim();
        String password = new String(txtpassword.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both username and password.");
            return;
        }
        try (Connection connection = DatabaseConnection.open()) {
            if (authenticateAdmin(connection, username, password)) {
                openScreen(new employee_info(), "Admin login successful!");
                return;
            }
            if (authenticateEmployee(connection, username, password)) {
                openScreen(new attendance_record_employee(), "Employee login successful!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(this, "Could not sign in. " + error.getMessage(), "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openScreen(javax.swing.JFrame screen, String message) {
        JOptionPane.showMessageDialog(this, message);
        screen.setVisible(true);
        dispose();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtpassword = new javax.swing.JPasswordField();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("username");

        jLabel2.setText("password");

        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("LOGIN");

        btnExit.setText("EXIT");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnLogin)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel1))
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel3)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        10, Short.MAX_VALUE)
                                                                .addComponent(txtpassword,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 149,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(txtusername))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                layout.createSequentialGroup()
                                                        .addGap(2, 2, 2)
                                                        .addComponent(btnExit)))
                                .addContainerGap(94, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jLabel3)
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnLogin)
                                        .addComponent(btnExit))
                                .addContainerGap(100, Short.MAX_VALUE)));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLoginActionPerformed
        login();
    }// GEN-LAST:event_btnLoginActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }// GEN-LAST:event_btnExitActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login_admin_employee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
