package attendance_Checking_Main_Project1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;

/** Shared visual language for the Swing application. */
public final class ThemeManager {

    public static final Color APP_BACKGROUND = new Color(245, 247, 250);
    public static final Color SURFACE = Color.WHITE;
    public static final Color PRIMARY = new Color(11, 92, 173);
    public static final Color PRIMARY_HOVER = new Color(8, 74, 140);
    public static final Color TEXT = new Color(23, 32, 42);
    public static final Color MUTED_TEXT = new Color(82, 96, 109);
    public static final Color BORDER = new Color(217, 226, 236);
    public static final Color SUCCESS = new Color(19, 115, 51);
    public static final Color ERROR = new Color(179, 38, 30);

    private static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 12);

    private ThemeManager() {
    }

    public static void initialize() {
        UIManager.put("Panel.background", APP_BACKGROUND);
        UIManager.put("OptionPane.background", SURFACE);
        UIManager.put("TextField.background", SURFACE);
        UIManager.put("TextArea.background", SURFACE);
        UIManager.put("ComboBox.background", SURFACE);
        UIManager.put("Component.focusColor", PRIMARY);
        UIManager.put("Component.focusWidth", 2);
        UIManager.put("Button.arc", 8);
        UIManager.put("Component.arc", 8);
        UIManager.put("TextComponent.arc", 6);
        UIManager.put("ScrollBar.width", 12);
    }

    public static void styleField(JComponent component) {
        component.setFont(BODY_FONT);
        component.setForeground(TEXT);
        component.setBackground(SURFACE);
        component.setPreferredSize(new Dimension(260, 38));
        component.setBorder(fieldBorder());
    }

    public static void styleTextArea(JTextArea textArea) {
        textArea.setFont(BODY_FONT);
        textArea.setForeground(TEXT);
        textArea.setBackground(SURFACE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
    }

    public static void styleLabel(JComponent component) {
        component.setFont(LABEL_FONT);
        component.setForeground(MUTED_TEXT);
    }

    public static void styleButton(JButton button, boolean primary) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(primary ? Color.WHITE : PRIMARY);
        button.setBackground(primary ? PRIMARY : SURFACE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primary ? PRIMARY : BORDER),
                BorderFactory.createEmptyBorder(9, 18, 9, 18)));
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setOpaque(true);
    }

    public static void styleTable(JTable table) {
        table.setFont(BODY_FONT);
        table.setForeground(TEXT);
        table.setBackground(SURFACE);
        table.setRowHeight(36);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(new Color(220, 235, 250));
        table.setSelectionForeground(TEXT);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setBackground(new Color(234, 240, 246));
        table.getTableHeader().setReorderingAllowed(false);
    }

    public static Border fieldBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }
}
